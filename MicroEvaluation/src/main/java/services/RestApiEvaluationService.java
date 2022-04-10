/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

/**
 *
 * @author isis
 */

import com.udmmicro.micro.evaluation.controllers.EvaluationJpaController;
import com.udmmicro.micro.evaluation.entities.Evaluation;
import com.udmmicro.micro.evaluation.rabbitmq.RabbitMQClientProvider;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("evaluation")
public class RestApiEvaluationService {
    
    EvaluationJpaController evaluationController =new EvaluationJpaController();
    RabbitMQClientProvider rabbitmqClientProvider = new RabbitMQClientProvider();
    
    
    @GET
    @Path("list")
    public Response getAllCustomers() {
		
        List<Evaluation> evaluations = evaluationController.findEvaluationEntities();
        rabbitmqClientProvider.sendMessage("showsessionevaluationlist");
        return Response.ok(evaluations).build();
    }
	
    @POST
    @Path("add")
    public Response postEvaluation(Evaluation evaluation) {
        try {
            evaluationController.create(evaluation);
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception ex) {
            Logger.getLogger(RestApiEvaluationService.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error: "+ex.getMessage()).build();
        }
    }
}
