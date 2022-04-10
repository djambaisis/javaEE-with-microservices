/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import com.udmmicro.micro.evaluation.rabbitmq.RabbitMQClientConsumer;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 * @author isis
 */

@ApplicationPath("/")
public class EvaluationServiceApplication extends Application {
    public EvaluationServiceApplication()
    {
        super();
        new RabbitMQClientConsumer();
        
    }
}
