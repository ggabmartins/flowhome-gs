package br.com.fiap.model.bo;
import br.com.fiap.model.dao.UsuarioDAO;
import br.com.fiap.model.to.UsuarioTO;
import java.util.ArrayList;

public class UsuarioBO {
    private UsuarioDAO usuarioDAO;

    public ArrayList<UsuarioTO> findAll() {
        usuarioDAO = new UsuarioDAO();
        return usuarioDAO.findAll();
    }

    public UsuarioTO findByCodigo(Long codigo) {
        usuarioDAO = new UsuarioDAO();
        return usuarioDAO.findByCodigo(codigo);
    }

    public UsuarioTO save(UsuarioTO usuario) {
        usuarioDAO = new UsuarioDAO();
        if (usuarioDAO.cpfExiste(usuario.getCpf()) || usuarioDAO.emailExiste(usuario.getEmail())) {
            System.err.println("BO: CPF ou Email já cadastrado.");
            return null;
        }
        return usuarioDAO.save(usuario);
    }

    public boolean delete(Long codigo) {
        usuarioDAO = new UsuarioDAO();
        return usuarioDAO.delete(codigo);
    }

    public UsuarioTO update(UsuarioTO usuario) {
        usuarioDAO = new UsuarioDAO();
        return usuarioDAO.update(usuario);
    }

    public UsuarioTO login(String email, String senha) {
        usuarioDAO = new UsuarioDAO();
        return usuarioDAO.findByLogin(email, senha);
    }

    public UsuarioTO entrarEquipe(Long idUsuario, Long idEquipe) {
        usuarioDAO = new UsuarioDAO();
        UsuarioTO usuario = usuarioDAO.findByCodigo(idUsuario);
        if (usuario.getIdEquipe() != null) {
            System.err.println("BO: Usuário (ID " + idUsuario + ") já pertence a uma equipe.");
            return null;
        }
        return usuarioDAO.entrarNaEquipe(idUsuario, idEquipe);
    }
    public ArrayList<UsuarioTO> listarMembrosEquipe(Long idEquipe) {
        usuarioDAO = new UsuarioDAO();
        return usuarioDAO.findAllByEquipe(idEquipe);
    }
    public UsuarioTO sairDaEquipe(Long idUsuario) {
        usuarioDAO = new UsuarioDAO();
        UsuarioTO usuario = usuarioDAO.findByCodigo(idUsuario);
        if (usuario == null) {
            System.err.println("BO: Usuário " + idUsuario + " não encontrado.");
            return null;
        }
        if (usuario.getIdEquipe() == null) {
            System.err.println("BO: Usuário " + idUsuario + " já não está em uma equipe.");
            return null;
        }
        if (usuario.getIsGestor() == 1) {
            System.err.println("BO: Usuário " + idUsuario + " é um GESTOR e não pode sair da equipe.");
            return null;
        }
        return usuarioDAO.sairDaEquipe(idUsuario);
    }
}