package br.com.fiap.resource;

import br.com.fiap.model.bo.UsuarioBO;
import br.com.fiap.model.to.LoginRequestDTO;
import br.com.fiap.model.to.UsuarioTO;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;

@Path("/usuarios")
public class UsuarioResource {

    private UsuarioBO usuarioBO = new UsuarioBO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll() {
        ArrayList<UsuarioTO> resultado = usuarioBO.findAll();
        Response.ResponseBuilder response = Response.ok();
        response.entity(resultado);
        return response.build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") Long id) {
        UsuarioTO resultado = usuarioBO.findByCodigo(id);
        Response.ResponseBuilder response;
        if (resultado != null) {
            response = Response.ok(); // 200 OK
        } else {
            response = Response.status(404); // 404 Not Found
        }
        response.entity(resultado);
        return response.build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(@Valid UsuarioTO usuario) {
        UsuarioTO resultado = usuarioBO.save(usuario);
        Response.ResponseBuilder response;
        if (resultado != null) {
            response = Response.created(null); // 201 CREATED
        } else {
            response = Response.status(400); // 400 Bad Request
        }
        response.entity(resultado);
        return response.build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@Valid UsuarioTO usuario, @PathParam("id") Long id) {
        usuario.setIdUsuario(id);
        UsuarioTO resultado = usuarioBO.update(usuario);
        Response.ResponseBuilder response;
        if (resultado != null) {
            response = Response.ok(); // 200 OK
        } else {
            response = Response.status(400); // 400 Bad Request
        }
        response.entity(resultado);
        return response.build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") Long id) {
        Response.ResponseBuilder response;
        if (usuarioBO.delete(id)) {
            response = Response.status(204); // 204 No Content
        } else {
            response = Response.status(404); // 404 Not Found
        }
        return response.build();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@Valid LoginRequestDTO dto) {
        UsuarioTO usuarioLogado = usuarioBO.login(dto.getEmail(), dto.getSenha());
        Response.ResponseBuilder response;
        if (usuarioLogado != null) {
            response = Response.ok(); // 200 OK
            response.entity(usuarioLogado);
        } else {
            response = Response.status(401); // 401 Unauthorized
            response.entity("{\"error\":\"Email ou senha inválidos\"}");
        }
        return response.build();
    }

    @PUT
    @Path("/{idUsuario}/equipe/{idEquipe}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response entrarEmEquipe(@PathParam("idUsuario") Long idUsuario,@PathParam("idEquipe") Long idEquipe) {
        UsuarioTO usuarioAtualizado = usuarioBO.entrarEquipe(idUsuario, idEquipe);
        if (usuarioAtualizado != null) {
            // 200 OK
            return Response.ok(usuarioAtualizado).build();
        } else {
            // 400 Bad Request
            return Response.status(400)
                    .entity("{\"error\":\"Falha ao entrar na equipe. Usuário já está em uma equipe.\"}")
                    .build();
        }
    }

    @GET
    @Path("/equipe/{idEquipe}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarMembrosEquipe(@PathParam("idEquipe") Long idEquipe) {

        ArrayList<UsuarioTO> membros = usuarioBO.listarMembrosEquipe(idEquipe);
        Response.ResponseBuilder response = Response.ok();
        response.entity(membros);
        return response.build();
    }

    @DELETE
    @Path("/{idUsuario}/equipe")
    @Produces(MediaType.APPLICATION_JSON)
    public Response sairDaEquipe(@PathParam("idUsuario") Long idUsuario) {
        UsuarioTO usuarioAtualizado = usuarioBO.sairDaEquipe(idUsuario);
        Response.ResponseBuilder response;
        if (usuarioAtualizado != null) {
            response = Response.ok(); // 200 OK
            response.entity(usuarioAtualizado);
        } else {
            response = Response.status(400); // 400 Bad Request
            response.entity("{\"error\":\"Falha ao sair da equipe. Usuário não é membro ou é um gestor.\"}");
        }
        return response.build();
    }
}