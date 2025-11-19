package br.com.fiap.model.bo;

import br.com.fiap.model.dao.EquipeDAO;
import br.com.fiap.model.dao.UsuarioDAO;
import br.com.fiap.model.to.EquipeTO;
import br.com.fiap.model.to.UsuarioTO;

import java.security.SecureRandom;
import java.util.ArrayList;

public class EquipeBO {
    private UsuarioDAO usuarioDAO;
    private EquipeDAO equipeDAO;

    private String gerarCodigoEquipe() {
        String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            sb.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        return sb.toString();
    }

    public EquipeTO save(EquipeTO equipe) {
        equipeDAO = new EquipeDAO();
        String novoCodigo;
        do {
            novoCodigo = gerarCodigoEquipe();
        } while (equipeDAO.findByCodigoEquipe(novoCodigo) != null);
        equipe.setCodigoEquipe(novoCodigo);

        return equipeDAO.save(equipe);
    }

    public EquipeTO update(EquipeTO equipe) {
        equipeDAO = new EquipeDAO();
        return equipeDAO.update(equipe);
    }

    public boolean delete(Long idEquipe) {
        equipeDAO = new EquipeDAO();
        usuarioDAO = new UsuarioDAO();
        try {
            usuarioDAO.esvaziarEquipe(idEquipe);
            return equipeDAO.delete(idEquipe);

        } catch (Exception e) {
            System.err.println("BO: Erro ao deletar equipe: " + e.getMessage());
            return false;
        }
    }

    public EquipeTO findByCodigo(Long idEquipe) {
        equipeDAO = new EquipeDAO();
        return equipeDAO.findByCodigo(idEquipe);
    }

    public EquipeTO buscarPorCodigo(String codigoEquipe) {
        equipeDAO = new EquipeDAO();
        return equipeDAO.findByCodigoEquipe(codigoEquipe);
    }

    public ArrayList<EquipeTO> listarEquipesPorGestor(Long idGestor) {
        equipeDAO = new EquipeDAO();
        return equipeDAO.findAllByGestor(idGestor);
    }

}