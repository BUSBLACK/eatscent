package com.example.eatscent.config;

import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Configuration
@EnableTransactionManagement
public class RabbitMQConfig {
    Logger logger = LoggerFactory.getLogger(RabbitMQConfig.class);
    @Value("${spring.rabbitmq.host}")
    private String host;
    @Value("${spring.rabbitmq.port}")
    private int port;
    @Value("${spring.rabbitmq.username}")
    private String username;
    @Value("${spring.rabbitmq.password}")
    private String password;

    /**
     * direct类型交换机生产者
     * */
    @Bean
    @Lazy
    public boolean direct_MQ_Producer(String excahngeName, String exchangeType,String Queue,String Binding,byte[] array) throws Exception{
        boolean flag = false;
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setPort(port);
        factory.setUsername(username);
        factory.setPassword(password);
        Connection con = null;
        try {
            //创建连接
            con = factory.newConnection();
            //创建通道
            Channel channel = con.createChannel();
            try {
                //创建交换机
                channel.exchangeDeclare(excahngeName,exchangeType,true);
                //创建队列
                channel.queueDeclare(Queue,true,false,false,null);
                //将交换机与队列绑定
                channel.queueBind(Queue,excahngeName,Binding);
                //发送消息
                channel.basicPublish(excahngeName,Binding, MessageProperties.PERSISTENT_TEXT_PLAIN,array);
                flag = true;
            } catch (IOException e) {
                logger.error("发送消息失败！",e);
                e.printStackTrace();
            }finally {
                //关闭通道连接
                channel.close();
                //关闭连接
                con.close();
            }
        } catch (IOException e) {
            logger.error("创建MQ连接失败！",e);
            e.printStackTrace();
        } catch (TimeoutException e) {
            logger.error("创建MQ连接失败！",e);
            e.printStackTrace();
        }
        return flag;
    }
    /**
     * direct类型交换机消费者
     * 推模式
     * */
    @Bean
    @Lazy
    public String[] direct_MQ_Consumer(String Queue) throws IOException, TimeoutException {
        final String[] message = {""};
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setPort(port);
        factory.setUsername(username);
        factory.setPassword(password);
        Connection con = null;
        try {
            //创建连接
            con = factory.newConnection();
            Channel channel = null;
            try {
                channel = con.createChannel();
                //设置客户端最多接收未被接收的数目
                channel.basicQos(64);
                Channel finalChannel = channel;
                //通过继承DefaultConsumer方法来实现消费者消息
                Consumer  consumer = new DefaultConsumer(finalChannel){
                    @Override
                    public void handleDelivery(String s1, Envelope envelope1, AMQP.BasicProperties basicProperties1, byte[] bytes) throws IOException{
                        message[0] = new String(bytes);
                        System.out.println("recv messahe : " + new String(bytes));
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        finalChannel.basicAck(envelope1.getDeliveryTag(),false);
                    }
                };
                channel.basicConsume(Queue,consumer);
                TimeUnit.SECONDS.sleep(5);
            } catch (IOException e) {
                logger.error("创建通道失败！",e);
                e.printStackTrace();
            }catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                //关闭资源
                channel.close();
                con.close();
            }
        } catch (IOException e) {
            logger.error("创建MQ连接失败！",e);
            e.printStackTrace();
        } catch (TimeoutException e) {
            logger.error("创建MQ连接失败！",e);
            e.printStackTrace();
        }

        return message;
    }
    /**
     * 拉模式消费者
     *
     * */
    @Bean
    @Lazy
    public String direct_MQ_Consumer1(String Queue) throws IOException, TimeoutException {
        String message = "";
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setPort(port);
        factory.setUsername(username);
        factory.setPassword(password);
        Connection con = null;
        try {
            //创建连接
            con = factory.newConnection();
            Channel channel = null;
            try {
                channel = con.createChannel();
                GetResponse getResponse = channel.basicGet(Queue,false);
                message = new String(getResponse.getBody());
                channel.basicAck(getResponse.getEnvelope().getDeliveryTag(),false);
                TimeUnit.SECONDS.sleep(5);
            } catch (IOException e) {
                logger.error("创建通道失败！",e);
                e.printStackTrace();
            }catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                //关闭资源
                channel.close();
                con.close();
            }
        } catch (IOException e) {
            logger.error("创建MQ连接失败！",e);
            e.printStackTrace();
        } catch (TimeoutException e) {
            logger.error("创建MQ连接失败！",e);
            e.printStackTrace();
        }

        return message;
    }
//    public static void main(String[] args){
//        String send = "整合PabbitMQ3";
//        String[] recv = {""};
//        RabbitMQConfig rabbitMQConfig = new RabbitMQConfig();
//        try {
//            rabbitMQConfig.direct_MQ_Producer("Test_direct","direct","direct_Queue","Test_direct",send.getBytes());
//            recv = rabbitMQConfig.direct_MQ_Consumer("direct_Queue");
//            System.out.println(recv);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (TimeoutException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
 //   }
}
