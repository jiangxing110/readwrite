package com.zhiyun.readwrite.config;

import com.rabbitmq.client.AMQP;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Title: RabbitCofig
 * @ProjectName: readwrite
 * @Description: TODO
 * @author: jiangxing
 * @date 2019/5/1711:20
 */

@Slf4j
@Configuration
public class RabbitCofig {

    public static final String ExCHANGE_ONE = "com.zhiyun.wcs.one";
    public static final String DL_EXCHANGE = "DL_EXCHANGE";


    public static final String QUEUE_ONE = "SystemWcs";
    public static final String QUEUE_TWO = "SystemAps";
    public static final String REDIRECT_QUEUE = "REDIRECT_QUEUE";
    public static final String DL_QUEUE = "DL_QUEUE";
    /**
     * 死信队列 交换机标识符
     */
    private static final String DEAD_LETTER_QUEUE_KEY = "x-dead-letter-exchange";
    /**
     * 死信队列交换机绑定键标识符
     */
    private static final String DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";


    /**
     * 队列路由键
     */
    public static final String DF_SYSTEM_TASK_QUEUE_ROUTE_KEY = "com.df.#";
    public static final String KEY_R = "KEY_R";

    /**
     * 声明topic交换机 支持持久化.
     *
     * @return the exchange
     */
    @Bean("topExchange")
    public Exchange topExchange() {
        return ExchangeBuilder.topicExchange(ExCHANGE_ONE).durable(true).build();
    }

    @Bean("queueOne")
    public Queue queueOne() {
        Map<String, Object> args = new HashMap<String, Object>();
        //x-dead-letter-exchange    声明  死信交换机
        args.put("x-message-ttl", 6000);
        return QueueBuilder.durable(QUEUE_ONE).autoDelete().withArguments(args).build();
    }

    @Bean("queueTwo")
    public Queue queueTwo() {
        return QueueBuilder.durable(QUEUE_TWO).build();
    }

    @Bean
    public Binding topicBindingOne(@Qualifier("queueOne") Queue queue, @Qualifier("topExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(DF_SYSTEM_TASK_QUEUE_ROUTE_KEY).noargs();
    }

    @Bean
    public Binding topicBindingTwo(@Qualifier("queueTwo") Queue queue, @Qualifier("topExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(DF_SYSTEM_TASK_QUEUE_ROUTE_KEY).noargs();
    }

    /*----------------------------------------------------死信队列---------------------------------------------------------------*/
    /**
     * 1.满足条件：消息被拒绝（basic.reject/ basic.nack）并且不再重新投递 requeue=false
     *           消息超期 (rabbitmq  Time-To-Live -> messageProperties.setExpiration())
     *           队列超载
     * 2.过程: 生产者   -->  消息 --> 交换机  --> 队列  --> 变成死信  --> DLX交换机 -->队列 --> 消费者
     *
     *
     *
     *
     * */
    /**
     * 死信队列跟交换机类型没有关系 不一定为directExchange  不影响该类型交换机的特性.
     *
     * @return the exchange
     */
    @Bean(DL_EXCHANGE)
    public Exchange deadLetterExchange() {
        return ExchangeBuilder.topicExchange(DL_EXCHANGE).durable(true).build();
    }

    @Bean(DL_QUEUE)
    public Queue deadLetterQueue() {
        Map<String, Object> args = new HashMap<String, Object>();
        //x-dead-letter-exchange    声明  死信交换机
        args.put(DEAD_LETTER_QUEUE_KEY, DL_EXCHANGE);
        //x-dead-letter-routing-key    声明 死信路由键
        args.put(DEAD_LETTER_ROUTING_KEY, KEY_R);
        //过期时间
        args.put("x-message-ttl", 2000);
        return QueueBuilder.durable(DL_QUEUE).autoDelete().withArguments(args).build();
    }


    /**
     * 定义死信队列转发队列.
     *
     * @return the queue
     */
    @Bean(REDIRECT_QUEUE)
    public Queue redirectQueue() {
        return new Queue(REDIRECT_QUEUE, true, false, false, null);
    }

    /**
     * 死信路由通过 DL_KEY 绑定键绑定到死信队列上.
     *
     * @return the binding
     */
    @Bean
    public Binding deadLetterBinding(@Qualifier(DL_QUEUE) Queue queue, @Qualifier(DL_EXCHANGE) Exchange exchange) {

        return BindingBuilder.bind(queue).to(exchange).with(DF_SYSTEM_TASK_QUEUE_ROUTE_KEY).noargs();
    }

    /**
     * 死信路由通过 KEY_R 绑定键绑定到死信队列上.
     *
     * @return the binding
     */
    @Bean
    public Binding redirectBinding(@Qualifier(REDIRECT_QUEUE) Queue queue, @Qualifier(DL_EXCHANGE) Exchange exchange) {

        return BindingBuilder.bind(queue).to(exchange).with(KEY_R).noargs();
    }

    /* ------------------------------------------------- 延迟队列--------------------------------------------------------------------*/
/**
 * 延时队列顾名思义，即放置在该队列里面的消息是不需要立即消费的，而是等待一段时间之后取出消费。
 *     那么，为什么需要延迟消费呢？我们来看以下的场景
 *
 *     网上商城下订单后30分钟后没有完成支付，取消订单(如：淘宝、去哪儿网)
 *     系统创建了预约之后，需要在预约时间到达前一小时提醒被预约的双方参会
 *     系统中的业务失败之后，需要重试
 *     这些场景都非常常见，我们可以思考，比如第二个需求，系统创建了预约之后，需要在预约时间到达前一小时提醒被预约的双方参会。
 *     那么一天之中肯定是会有很多个预约的，时间也是不一定的，假设现在有1点 2点 3点 三个预约，如何让系统知道在当前时间等于0点
 *     1点 2点给用户发送信息呢，是不是需要一个轮询，一直去查看所有的预约，比对当前的系统时间和预约提前一小时的时间是否相等呢？
 *     这样做非常浪费资源而且轮询的时间间隔不好控制。如果我们使用延时消息队列呢，我们在创建时把需要通知的预约放入消息中间件中
 *     ，并且设置该消息的过期时间，等过期时间到达时再取出消费即可。
 *
 *     Rabbitmq实现延时队列一般而言有两种形式：
 *     第一种方式：利用两个特性： Time To Live(TTL)、Dead Letter Exchanges（DLX）
 *     第二种方式：利用rabbitmq中的插件x-delay-message
 *
 *     TTL
 *      RabbitMQ可以针对队列设置x-expires(则队列中所有的消息都有相同的过期时间)或者针对Message设置x-message-ttl
 *      (对消息进行单独设置，每条消息TTL可以不同)，来控制消息的生存时间，如果超时(两者同时设置以最先到期的时间为准)，则消息变为dead letter(死信)
 *
 *     Dead Letter Exchanges（DLX）
 *      RabbitMQ的Queue可以配置x-dead-letter-exchange和x-dead-letter-routing-key（可选）两个参数，如果队列内出现了dead letter，
 *      则按照这两个参数重新路由转发到指定的队列。
 *      x-dead-letter-exchange：出现dead letter之后将dead letter重新发送到指定exchange
 *      x-dead-letter-routing-key：出现dead letter之后将dead letter重新按照指定的routing-key发送
 *
 */


}
