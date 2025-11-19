package br.com.fiap.model.dao;

import br.com.fiap.model.to.EquipeTO;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class EquipeDAO {

    private EquipeTO mapResultSetToEquipeTO(ResultSet rs) throws SQLException {
        EquipeTO equipe = new EquipeTO();
        equipe.setIdEquipe(rs.getLong("id_equipe"));
        equipe.setNomeEquipe(rs.getString("nome_equipe"));
        equipe.setCodigoEquipe(rs.getString("codigo_equipe"));
        equipe.setIdGestor(rs.getLong("id_gestor"));
        equipe.setDtCriacao(rs.getDate("dt_criacao").toLocalDate());
        return equipe;
    }

    public EquipeTO save(EquipeTO equipe) {
        String sql = "INSERT INTO T_EQUIPE (id_equipe, nome_equipe, codigo_equipe, id_gestor, dt_criacao) " +
                "VALUES (SEQ_EQUIPE.NEXTVAL, ?, ?, ?, ?)";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setString(1, equipe.getNomeEquipe());
            ps.setString(2, equipe.getCodigoEquipe());
            ps.setLong(3, equipe.getIdGestor());
            ps.setDate(4, Date.valueOf(LocalDate.now()));

            if (ps.executeUpdate() > 0) {
                return equipe;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao Salvar Equipe: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return null;
    }

    public EquipeTO update(EquipeTO equipe) {
        String sql = "UPDATE T_EQUIPE SET nome_equipe = ? WHERE id_equipe = ?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setString(1, equipe.getNomeEquipe());
            ps.setLong(2, equipe.getIdEquipe());

            if (ps.executeUpdate() > 0) {
                return equipe;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao Atualizar Equipe: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return null;
    }

    public boolean delete(Long idEquipe) {
        String sql = "DELETE FROM T_EQUIPE WHERE id_equipe = ?";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idEquipe);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao Deletar Equipe: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return false;
    }

    public EquipeTO findByCodigo(Long idEquipe) {
        String sql = "SELECT * FROM T_EQUIPE WHERE id_equipe = ?";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idEquipe);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToEquipeTO(rs);
            }
        } catch (SQLException e) {
            System.out.println("Erro na Consulta (Equipe por ID): " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return null;
    }

    public EquipeTO findByCodigoEquipe(String codigoEquipe) {
        String sql = "SELECT * FROM T_EQUIPE WHERE codigo_equipe = ?";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setString(1, codigoEquipe);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToEquipeTO(rs);
            }
        } catch (SQLException e) {
            System.out.println("Erro na Consulta (Equipe por Código): " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return null;
    }

    public ArrayList<EquipeTO> findAllByGestor(Long idGestor) {
        String sql = "SELECT * FROM T_EQUIPE WHERE id_gestor = ? ORDER BY nome_equipe ASC";
        ArrayList<EquipeTO> equipes = new ArrayList<>();
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idGestor);
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    equipes.add(mapResultSetToEquipeTO(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro na Consulta (Equipes por Gestor): " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return equipes;
    }

}