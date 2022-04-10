package controller;


import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dao.Database;
import model.SessionEvaluation;
import rabbitmq.LogRabbitmq;

import java.util.List;
import rabbitmq.RabbitMQClientProvider;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("sessionevaluation")
public class SessionEvaluationController {
	
	RabbitMQClientProvider rabbitmq= new RabbitMQClientProvider();
	
    public Response getAllCustomers() {
		
        List<SessionEvaluation> customers = Database.getSessionEvaluations();
        return Response.ok(customers).build();
    }
	
    @GET
	 public Response getSessionEvaluation() {
		 Database d=new Database();
	        List<SessionEvaluation> result = d.getSessionEvaluation();
	        
	        return Response.ok(result).build();
	    }
    
    @POST
  	 public Response postSessionEvaluation(SessionEvaluation session) {
  		 Database d=new Database();
  		d.addSessionEvaluation(session); 
  		rabbitmq.sendMessage(session.toString());
  	        return Response.ok("success").build();
  	    }
}
