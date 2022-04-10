/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.udmmicro.micro.evaluation.rabbitmq;

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

/**
 *
 * @author ppitbull
 */
public class RabbitMQClientProvider {
    private Channel currentChanel;
	private static final String RABBITMQ_HOST_ENV = "RABBITMQ_HOST";

	public static final String EXCHANGE_NAME = "evaluationsession";
	
	public  RabbitMQClientProvider()
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
		System.out.println(hostVariable);
		String hostValue = hostVariable != null && !hostVariable.isEmpty() ? "amqp://" + hostVariable + ":5672"
				: "amqp://localhost:5672";
		return URI.create(hostValue);
	}
	
	
	public void sendMessage(String msg) {
		try {
			currentChanel.basicPublish(EXCHANGE_NAME, "", null,msg.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
}
