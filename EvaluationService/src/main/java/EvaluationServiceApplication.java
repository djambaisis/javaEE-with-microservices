import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import rabbitmq.RabbitMQClientConsumer;

@ApplicationPath("/")
public class EvaluationServiceApplication extends Application {

    public EvaluationServiceApplication() {
        super();
        new RabbitMQClientConsumer();
    }
	
	

}
