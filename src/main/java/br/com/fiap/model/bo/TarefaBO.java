package br.com.fiap.model.bo;

import br.com.fiap.model.dao.TarefaDAO;
import br.com.fiap.model.dao.UsuarioDAO;
import br.com.fiap.model.to.TarefaTO;
import br.com.fiap.model.to.UsuarioDashboardDTO;
import br.com.fiap.model.to.MembroStatsDTO;

import java.util.ArrayList;

public class TarefaBO {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private TarefaDAO tarefaDAO = new TarefaDAO();

    public TarefaTO save(TarefaTO tarefa) {
        return tarefaDAO.save(tarefa);
    }

    public TarefaTO update(TarefaTO tarefa) {
        return tarefaDAO.update(tarefa);
    }

    public boolean delete(Long idTarefa) {
        return tarefaDAO.delete(idTarefa);
    }

    public ArrayList<TarefaTO> listarTarefasPorUsuario(Long idUsuario) {
        return tarefaDAO.findAllByUsuario(idUsuario);
    }

    public UsuarioDashboardDTO getStats(Long idUsuario) {
        if (usuarioDAO.findByCodigo(idUsuario) == null) {
            System.err.println("BO: Usuário " + idUsuario + " não encontrado para buscar stats.");
            return null;
        }
        return tarefaDAO.getStatsByUsuario(idUsuario);
    }

    public ArrayList<MembroStatsDTO> getStatsPorEquipe(Long idEquipe) {
        return tarefaDAO.getStatsByEquipe(idEquipe);
    }
}