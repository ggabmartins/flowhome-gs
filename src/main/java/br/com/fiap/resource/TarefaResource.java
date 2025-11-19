package br.com.fiap.resource;

import br.com.fiap.model.bo.TarefaBO;
import br.com.fiap.model.to.TarefaTO;
import br.com.fiap.model.to.UsuarioDashboardDTO;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import br.com.fiap.model.to.MembroStatsDTO;

@Path("/tarefas")
public class TarefaResource {

    private TarefaBO tarefaBO = new TarefaBO();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(@Valid TarefaTO tarefa) {
        TarefaTO resultado = tarefaBO.save(tarefa);
        Response.ResponseBuilder response;
        if (resultado != null) {
            response = Response.created(null); // 201
        } else {
            response = Response.status(400); // 400
        }
        response.entity(resultado);
        return response.build();
    }

    @GET
    @Path("/usuario/{idUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarPorUsuario(@PathParam("idUsuario") Long idUsuario) {
        ArrayList<TarefaTO> resultado = tarefaBO.listarTarefasPorUsuario(idUsuario);
        Response.ResponseBuilder response = Response.ok();
        response.entity(resultado);
        return response.build();
    }

    @PUT
    @Path("/{idTarefa}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@Valid TarefaTO tarefa, @PathParam("idTarefa") Long idTarefa) {
        tarefa.setIdTarefa(idTarefa);
        TarefaTO resultado = tarefaBO.update(tarefa);
        Response.ResponseBuilder response;
        if (resultado != null) {
            response = Response.ok(); // 200
        } else {
            response = Response.status(400); // 400
        }
        response.entity(resultado);
        return response.build();
    }

    @DELETE
    @Path("/{idTarefa}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("idTarefa") Long idTarefa) {
        Response.ResponseBuilder response;
        if (tarefaBO.delete(idTarefa)) {
            response = Response.status(204); // 204
        } else {
            response = Response.status(404); // 404
        }
        return response.build();
    }
    @GET
    @Path("/{idUsuario}/stats")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStatsDoUsuario(@PathParam("idUsuario") Long idUsuario) {
        TarefaBO usuarioBO = new TarefaBO();
        UsuarioDashboardDTO stats = usuarioBO.getStats(idUsuario);
        Response.ResponseBuilder response;
        if (stats != null) {
            response = Response.ok(); // 200 OK
        } else {
            response = Response.status(404); // 404 Not Found
        }
        response.entity(stats);
        return response.build();
    }

    @GET
    @Path("/equipe/{idEquipe}/stats")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStatsDaEquipe(@PathParam("idEquipe") Long idEquipe) {
        TarefaBO tarefaBO = new TarefaBO();
        ArrayList<MembroStatsDTO> stats = tarefaBO.getStatsPorEquipe(idEquipe);

        Response.ResponseBuilder response = Response.ok();
        response.entity(stats);
        return response.build();
    }
}