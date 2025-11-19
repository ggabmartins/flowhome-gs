package br.com.fiap.model.dao;
import br.com.fiap.model.to.UsuarioDashboardDTO;
import br.com.fiap.model.to.TarefaTO;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import br.com.fiap.model.to.MembroStatsDTO;

public class TarefaDAO {

    private TarefaTO mapResultSetToTarefaTO(ResultSet rs) throws SQLException {
        TarefaTO tarefa = new TarefaTO();
        tarefa.setIdTarefa(rs.getLong("id_tarefa"));
        tarefa.setTitulo(rs.getString("titulo"));
        tarefa.setDescricao(rs.getString("descricao"));
        tarefa.setIdCategoria(rs.getLong("id_categoria"));
        if (rs.getObject("id_categoria") == null) {
            tarefa.setIdCategoria(null);
        }
        Date dtVenc = rs.getDate("dt_vencimento");
        if (dtVenc != null) {
            tarefa.setDtVencimento(dtVenc.toLocalDate());
        }
        tarefa.setTempoEstimadoH(rs.getDouble("tempo_estimado_h"));
        if (rs.getObject("tempo_estimado_h") == null) {
            tarefa.setTempoEstimadoH(null);
        }
        tarefa.setStatus(rs.getString("status"));
        tarefa.setIdUsuario(rs.getLong("id_usuario"));
        tarefa.setDtCriacao(rs.getDate("dt_criacao").toLocalDate());
        Date dtConc = rs.getDate("dt_conclusao");
        if (dtConc != null) {
            tarefa.setDtConclusao(dtConc.toLocalDate());
        }
        return tarefa;
    }

    public TarefaTO save(TarefaTO tarefa) {
        String sql = "INSERT INTO T_TAREFA (id_tarefa, titulo, descricao, id_categoria, dt_vencimento, tempo_estimado_h, status, id_usuario, dt_criacao, dt_conclusao) " + "VALUES (SEQ_TAREFA.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setString(1, tarefa.getTitulo());
            ps.setString(2, tarefa.getDescricao());
            if (tarefa.getIdCategoria() != null) {
                ps.setLong(3, tarefa.getIdCategoria());
            } else {
                ps.setNull(3, Types.NUMERIC);
            }
            if (tarefa.getDtVencimento() != null) {
                ps.setDate(4, Date.valueOf(tarefa.getDtVencimento()));
            } else {
                ps.setNull(4, Types.DATE);
            }
            if (tarefa.getTempoEstimadoH() != null) {
                ps.setDouble(5, tarefa.getTempoEstimadoH());
            } else {
                ps.setNull(5, Types.NUMERIC);
            }
            ps.setString(6, "Pendente");
            ps.setLong(7, tarefa.getIdUsuario());
            ps.setDate(8, Date.valueOf(LocalDate.now()));
            ps.setNull(9, Types.DATE);
            if (ps.executeUpdate() > 0) {
                return tarefa;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao Salvar Tarefa: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return null;
    }

    public TarefaTO update(TarefaTO tarefa) {
        String sql = "UPDATE T_TAREFA SET titulo = ?, descricao = ?, id_categoria = ?, dt_vencimento = ?, " +
                "tempo_estimado_h = ?, status = ?, dt_conclusao = ? WHERE id_tarefa = ?";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setString(1, tarefa.getTitulo());
            ps.setString(2, tarefa.getDescricao());

            if (tarefa.getIdCategoria() != null) {
                ps.setLong(3, tarefa.getIdCategoria());
            } else {
                ps.setNull(3, Types.NUMERIC);
            }

            if (tarefa.getDtVencimento() != null) {
                ps.setDate(4, Date.valueOf(tarefa.getDtVencimento()));
            } else {
                ps.setNull(4, Types.DATE);
            }

            if (tarefa.getTempoEstimadoH() != null) {
                ps.setDouble(5, tarefa.getTempoEstimadoH());
            } else {
                ps.setNull(5, Types.NUMERIC);
            }

            ps.setString(6, tarefa.getStatus());

            if ("Concluída".equals(tarefa.getStatus())) {
                ps.setDate(7, Date.valueOf(LocalDate.now()));
            } else {
                ps.setNull(7, Types.DATE);
            }

            ps.setLong(8, tarefa.getIdTarefa());

            if (ps.executeUpdate() > 0) {
                return tarefa;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao Atualizar Tarefa: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return null;
    }

    public boolean delete(Long idTarefa) {
        String sql = "DELETE FROM T_TAREFA WHERE id_tarefa = ?";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idTarefa);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao Deletar Tarefa: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return false;
    }

    public ArrayList<TarefaTO> findAllByUsuario(Long idUsuario) {
        String sql = "SELECT * FROM T_TAREFA WHERE id_usuario = ? ORDER BY dt_criacao DESC";
        ArrayList<TarefaTO> tarefas = new ArrayList<>();
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    tarefas.add(mapResultSetToTarefaTO(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro na Consulta (Tarefas por Usuario): " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return tarefas;
    }

    public void setCategoriaToNull(Long idCategoria) {
        String sql = "UPDATE T_TAREFA SET id_categoria = NULL WHERE id_categoria = ?";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idCategoria);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao desassociar tarefas: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
    }
    public UsuarioDashboardDTO getStatsByUsuario(Long idUsuario) {
        String sql = "SELECT " +
                "    COUNT(CASE WHEN status = 'Concluída' THEN 1 END) AS totalTarefasConcluidas, " +
                "    SUM(CASE WHEN status = 'Concluída' THEN COALESCE(tempo_estimado_h, 0) ELSE 0 END) AS totalHorasProdutivas, " +
                "    COUNT(CASE WHEN status IN ('Pendente', 'Em Andamento') THEN 1 END) AS tarefasPendentes " +
                "FROM T_TAREFA " +
                "WHERE id_usuario = ?";

        UsuarioDashboardDTO stats = new UsuarioDashboardDTO();
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                stats.setTotalTarefasConcluidas(rs.getLong("totalTarefasConcluidas"));
                stats.setTotalHorasProdutivas(rs.getDouble("totalHorasProdutivas"));
                stats.setTarefasPendentes(rs.getLong("tarefasPendentes"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao calcular estatísticas do usuário: " + e.getMessage());
            return null;
        } finally {
            ConnectionFactory.closeConnection();
        }
        return stats;
    }

    public ArrayList<MembroStatsDTO> getStatsByEquipe(Long idEquipe) {
        String sql = "SELECT " +
                "    u.id_usuario, " +
                "    u.nome, " +
                "    COUNT(CASE WHEN t.status = 'Concluída' THEN 1 END) AS totalTarefasConcluidas, " +
                "    SUM(CASE WHEN t.status = 'Concluída' THEN COALESCE(t.tempo_estimado_h, 0) ELSE 0 END) AS totalHorasProdutivas, " +
                "    COUNT(CASE WHEN t.status IN ('Pendente', 'Em Andamento') THEN 1 END) AS tarefasPendentes " +
                "FROM T_USUARIO u " +
                "LEFT JOIN T_TAREFA t ON u.id_usuario = t.id_usuario " +
                "WHERE u.id_equipe = ? " +
                "GROUP BY u.id_usuario, u.nome " +
                "ORDER BY totalHorasProdutivas DESC";

        ArrayList<MembroStatsDTO> listaStats = new ArrayList<>();
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idEquipe);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MembroStatsDTO stats = new MembroStatsDTO();
                stats.setIdUsuario(rs.getLong("id_usuario"));
                stats.setNomeUsuario(rs.getString("nome"));
                stats.setTotalTarefasConcluidas(rs.getLong("totalTarefasConcluidas"));
                stats.setTotalHorasProdutivas(rs.getDouble("totalHorasProdutivas"));
                stats.setTarefasPendentes(rs.getLong("tarefasPendentes"));
                listaStats.add(stats);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao calcular estatísticas da equipe: " + e.getMessage());
            return new ArrayList<>();
        } finally {
            ConnectionFactory.closeConnection();
        }
        return listaStats;
    }
}