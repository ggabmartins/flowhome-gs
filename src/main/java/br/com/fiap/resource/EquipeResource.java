package br.com.fiap.resource;

import br.com.fiap.model.bo.EquipeBO;
import br.com.fiap.model.to.EquipeTO;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/equipes")
public class EquipeResource {

    private EquipeBO equipeBO = new EquipeBO();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(@Valid EquipeTO equipe) {
        EquipeTO resultado = equipeBO.save(equipe);
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
    @Path("/gestor/{idGestor}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarPorGestor(@PathParam("idGestor") Long idGestor) {
        ArrayList<EquipeTO> resultado = equipeBO.listarEquipesPorGestor(idGestor);
        Response.ResponseBuilder response = Response.ok();
        response.entity(resultado);
        return response.build();
    }

    @GET
    @Path("/buscar/{codigoEquipe}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorCodigo(@PathParam("codigoEquipe") String codigoEquipe) {
        EquipeTO resultado = equipeBO.buscarPorCodigo(codigoEquipe);
        Response.ResponseBuilder response;
        if (resultado != null) {
            response = Response.ok(); // 200
        } else {
            response = Response.status(404); // 404
        }
        response.entity(resultado);
        return response.build();
    }

    @GET
    @Path("/{idEquipe}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByCodigo(@PathParam("idEquipe") Long idEquipe) {
        EquipeTO resultado = equipeBO.findByCodigo(idEquipe);
        Response.ResponseBuilder response;
        if (resultado != null) {
            response = Response.ok(); // 200
        } else {
            response = Response.status(404); // 404
        }
        response.entity(resultado);
        return response.build();
    }

    @PUT
    @Path("/{idEquipe}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@Valid EquipeTO equipe, @PathParam("idEquipe") Long idEquipe) {
        equipe.setIdEquipe(idEquipe);
        EquipeTO resultado = equipeBO.update(equipe);
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
    @Path("/{idEquipe}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("idEquipe") Long idEquipe) {
        Response.ResponseBuilder response;
        if (equipeBO.delete(idEquipe)) {
            response = Response.status(204); // 204
        } else {
            response = Response.status(404); // 404
        }
        return response.build();
    }
}