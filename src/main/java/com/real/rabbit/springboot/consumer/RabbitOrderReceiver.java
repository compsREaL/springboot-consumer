package com.real.rabbit.springboot.consumer;

import com.rabbitmq.client.Channel;
import com.real.rabbit.springboot.entity.Order;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author: mabin
 * @create: 2019/4/26 12:14
 */
@Component
public class RabbitOrderReceiver {

//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue(value = "order-queue",durable = "true"),
//            exchange = @Exchange(name = "order-exchange",durable = "true",type = "topic"),
//            key = "order.*"
//    ))
//    @RabbitHandler
//    public void onMessage(@Payload Order order, @Headers Map<String,Object> headers, Channel channel) throws Exception{
//        //消费者操作
//        System.out.println("------收到消息，开始消费-------");
//        System.out.println("订单号："+order.getId());
//
//        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
//        //签收操作，消费者消费完消息后主动给mq回送一个响应
//        channel.basicAck(deliveryTag,false);
//    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${spring.rabbitmq.listener.order.queue.name}",
            durable = "${spring.rabbitmq.listener.order.queue.durable}"),
            exchange = @Exchange(
                    value = "${spring.rabbitmq.listener.order.exchange.name}",
                    durable = "${spring.rabbitmq.listener.order.exchange.durable}",
                    type = "${spring.rabbitmq.listener.order.exchange.type}",
                    ignoreDeclarationExceptions = "${spring.rabbitmq.listener.order.exchange.ignoreDeclarationException}"
            ),
            key = "${spring.rabbitmq.listener.order.key}"
    ))
    @RabbitHandler
    public void onOrderMessage(@Payload Order order,Channel channel,@Headers Map<String,Object> headers) throws Exception{
        System.err.println("---------------------");
        System.err.println("消费端order："+ order.getId());
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        //手工ack
        channel.basicAck(deliveryTag, false);
    }

}
