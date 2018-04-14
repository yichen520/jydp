package com.jydp.other;
  
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/** 
 * 用于提供Rabbit的操作类 
 * 
 */  
@Component
public class RabbitProducer {  
	

	
    private final RabbitTemplate rabbitTemplate;  
  
    /**
     * 
     * @param rabbitTemplate
     */
    @Autowired
    public RabbitProducer(RabbitTemplate rabbitTemplate){  
        this.rabbitTemplate = rabbitTemplate;  
    }  
    
    /**
     * 
     * @param routingKey
     * @param message
     */
    public <T> void send(String routingKey,  T message) {  
    	send("rd-mq-exchange", routingKey, message);
    }
  
    /**
     * 
     * @param exchange
     * @param routingKey
     * @param message
     */
    public <T> void send(String exchange,String routingKey,  T message) {  
        //实现将message通过json转换&将对象发送  
        //convertAndSend(String exchange, String routingKey, Object message, MessagePostProcessor messagePostProcessor, CorrelationData correlationData)  
        rabbitTemplate.convertAndSend(exchange, routingKey, message, new MessagePostProcessor() {  
            //实现message操作处理实现  
            @Override  
            public Message postProcessMessage(Message message) throws AmqpException {  
                //设置信息的属性信息&设置发送模式（PERSISTENT:连续的）  
            	MessageProperties mp = message.getMessageProperties();
                mp.setDeliveryMode(MessageDeliveryMode.PERSISTENT);  
                mp.setContentType(MessageProperties.CONTENT_TYPE_JSON);
                return message;  
            }  
        }, new CorrelationData(String.valueOf(message)));  
    }  
}  