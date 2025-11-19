package br.com.fiap.resource;

import br.com.fiap.model.bo.CategoriaBO;
import br.com.fiap.model.to.CategoriaTO;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/categorias")
public class CategoriaResource {

    private CategoriaBO categoriaBO = new CategoriaBO();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(@Valid CategoriaTO categoria) {
        CategoriaTO resultado = categoriaBO.save(categoria);
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
        ArrayList<CategoriaTO> resultado = categoriaBO.listarCategoriasPorUsuario(idUsuario);
        Response.ResponseBuilder response = Response.ok();
        response.entity(resultado);
        return response.build();
    }

    @PUT
    @Path("/{idCategoria}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@Valid CategoriaTO categoria, @PathParam("idCategoria") Long idCategoria) {
        categoria.setIdCategoria(idCategoria);
        CategoriaTO resultado = categoriaBO.update(categoria);
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
    @Path("/{idCategoria}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("idCategoria") Long idCategoria) {
        Response.ResponseBuilder response;
        if (categoriaBO.delete(idCategoria)) {
            response = Response.status(204); // 204
        } else {
            response = Response.status(404); // 404
        }
        return response.build();
    }
}