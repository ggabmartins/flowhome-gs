package br.com.fiap.model.dao;

import br.com.fiap.model.to.CategoriaTO;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class CategoriaDAO {

    private CategoriaTO mapResultSetToCategoriaTO(ResultSet rs) throws SQLException {
        CategoriaTO categoria = new CategoriaTO();
        categoria.setIdCategoria(rs.getLong("id_categoria"));
        categoria.setNome(rs.getString("nome"));
        categoria.setCorHex(rs.getString("cor_hex"));
        categoria.setIdUsuario(rs.getLong("id_usuario"));
        categoria.setDtCriacao(rs.getDate("dt_criacao").toLocalDate());
        return categoria;
    }

    public CategoriaTO save(CategoriaTO categoria) {
        String sql = "INSERT INTO T_CATEGORIA (id_categoria, nome, cor_hex, id_usuario, dt_criacao) " +
                "VALUES (SEQ_CATEGORIA.NEXTVAL, ?, ?, ?, ?)";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setString(1, categoria.getNome());
            ps.setString(2, categoria.getCorHex());
            ps.setLong(3, categoria.getIdUsuario());
            ps.setDate(4, Date.valueOf(LocalDate.now()));
            if (ps.executeUpdate() > 0) {
                return categoria;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao Salvar Categoria: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return null;
    }

    public CategoriaTO update(CategoriaTO categoria) {
        String sql = "UPDATE T_CATEGORIA SET nome = ?, cor_hex = ? WHERE id_categoria = ?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setString(1, categoria.getNome());
            ps.setString(2, categoria.getCorHex());
            ps.setLong(3, categoria.getIdCategoria());

            if (ps.executeUpdate() > 0) {
                return categoria;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao Atualizar Categoria: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return null;
    }

    public boolean delete(Long idCategoria) {
        // (Nota: A lógica complexa de desassociar tarefas foi removida para simplificar)
        String sql = "DELETE FROM T_CATEGORIA WHERE id_categoria = ?";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idCategoria);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao Deletar Categoria: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return false;
    }

    public ArrayList<CategoriaTO> findAllByUsuario(Long idUsuario) {
        String sql = "SELECT * FROM T_CATEGORIA WHERE id_usuario = ? ORDER BY nome ASC";
        ArrayList<CategoriaTO> categorias = new ArrayList<>();
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    categorias.add(mapResultSetToCategoriaTO(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro na Consulta (Categorias por Usuario): " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return categorias;
    }

    public CategoriaTO findByCodigo(Long idCategoria) {
        String sql = "SELECT * FROM T_CATEGORIA WHERE id_categoria = ?";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idCategoria);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToCategoriaTO(rs);
            }
        } catch (SQLException e) {
            System.out.println("Erro na Consulta (Categoria por ID): " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return null;
    }
}