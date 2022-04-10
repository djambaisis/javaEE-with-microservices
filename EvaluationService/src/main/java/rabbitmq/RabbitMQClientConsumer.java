/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rabbitmq;

/**
 *
 * @author isis
 */

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import dao.Database;
import java.util.List;
import model.SessionEvaluation;


public class RabbitMQClientConsumer {
    public static final String EXCHANGE_NAME = "evaluationsession";
	private static final String RABBITMQ_HOST_ENV = "RABBITMQ_HOST";
        Database d=new Database();
	        
	Connection connection;

	public RabbitMQClientConsumer(){
		ConnectionFactory factory = new ConnectionFactory();
		String rabbitMQHosts=System.getenv(RABBITMQ_HOST_ENV);
		System.out.println("RabbitMQ Host"+rabbitMQHosts);
		factory.setHost(rabbitMQHosts);

    	Connection connection;
		try {
			connection = createConnection(factory);
//			Connection connection = factory.newConnection();
			final Channel channel = connection.createChannel();

			channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
			String queueName = channel.queueDeclare().getQueue();
			channel.queueBind(queueName, EXCHANGE_NAME, "");
			
			Consumer consumer = new DefaultConsumer(channel) {
				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
						byte[] body) throws IOException {
					String message = new String(body, "UTF-8");
                                        System.out.println(d.getSessionEvaluation());
                                                                                        
					System.out.println(" [x] Received '" + message + "'");
				}
			};
			channel.basicConsume(queueName, true, consumer);

		} catch (InterruptedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Connection createConnection(ConnectionFactory factory) throws InterruptedException {
		// We implement an healthcheck. 

		boolean connectionIsReady = false;
		Connection connection = null;
		while (!connectionIsReady) {
			try {
				connection = factory.newConnection();
				connectionIsReady = true;
			} catch (Exception e) {
				System.out.println("Problem:" + e.getMessage());
				System.out.println("We will try to connect to RabbitMQ in 5s.");
				Thread.sleep(5000);
			}
		}

		System.out.println("Great !! Connected to RabbitMQ.");

		return connection;
	}

}
