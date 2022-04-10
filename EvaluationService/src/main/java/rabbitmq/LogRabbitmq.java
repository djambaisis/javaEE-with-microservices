package rabbitmq;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import model.SessionEvaluation;

public class LogRabbitmq {
	private Channel currentChanel;
	private static final String RABBITMQ_HOST_ENV = "RABBITMQ_HOST";

	public static final String EXCHANGE_NAME = "logs";
	
	public  LogRabbitmq()
			{
		ConnectionFactory factory = new ConnectionFactory();
		try {
			factory.setUri(getRabbitMQURI());
			Connection connection = factory.newConnection();
			currentChanel = connection.createChannel();

			currentChanel.exchangeDeclare(EXCHANGE_NAME, "fanout");
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private URI getRabbitMQURI() {
		String hostVariable = System.getenv(RABBITMQ_HOST_ENV);
		
		String hostValue = hostVariable != null && !hostVariable.isEmpty() ? "amqp://" + hostVariable + ":5672"
				: "amqp://localhost:5672";
		return URI.create(hostValue);
	}
	
	
	public void sendMessage(SessionEvaluation ses) {
		try {
			currentChanel.basicPublish(EXCHANGE_NAME, "", null,("Created: "+ses.toString()).getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	

}
