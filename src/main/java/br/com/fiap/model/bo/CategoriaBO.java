package br.com.fiap.model.bo;

import br.com.fiap.model.dao.CategoriaDAO;
import br.com.fiap.model.dao.TarefaDAO; // <<< 1. IMPORTAR O TAREFADAO
import br.com.fiap.model.to.CategoriaTO;
import java.util.ArrayList;

public class CategoriaBO {

    private CategoriaDAO categoriaDAO;
    private TarefaDAO tarefaDAO;

    public CategoriaTO save(CategoriaTO categoria) {
        categoriaDAO = new CategoriaDAO();
        return categoriaDAO.save(categoria);
    }

    public CategoriaTO update(CategoriaTO categoria) {
        categoriaDAO = new CategoriaDAO();
        return categoriaDAO.update(categoria);
    }

    public boolean delete(Long idCategoria) {
        categoriaDAO = new CategoriaDAO();
        tarefaDAO = new TarefaDAO();
        try {
            tarefaDAO.setCategoriaToNull(idCategoria);
            return categoriaDAO.delete(idCategoria);
        } catch (Exception e) {
            System.err.println("BO: Erro ao deletar categoria: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<CategoriaTO> listarCategoriasPorUsuario(Long idUsuario) {
        categoriaDAO = new CategoriaDAO();
        return categoriaDAO.findAllByUsuario(idUsuario);
    }

}