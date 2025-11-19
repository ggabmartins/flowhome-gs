package br.com.fiap.model.dao;

import br.com.fiap.model.to.UsuarioTO;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class UsuarioDAO {
    private UsuarioTO mapResultSetToUsuarioTO(ResultSet rs) throws SQLException {
        UsuarioTO usuario = new UsuarioTO();
        usuario.setIdUsuario(rs.getLong("id_usuario"));
        usuario.setNome(rs.getString("nome"));
        usuario.setCpf(rs.getString("cpf"));
        usuario.setEmail(rs.getString("email"));
        usuario.setTelefone(rs.getString("telefone"));
        Date dtNasc = rs.getDate("dt_nascimento");
        if (dtNasc != null) {
            usuario.setDtNascimento(dtNasc.toLocalDate());
        }
        usuario.setSenha(rs.getString("senha"));
        usuario.setIsGestor(rs.getInt("is_gestor"));
        usuario.setIdEquipe(rs.getLong("id_equipe"));
        if (rs.getObject("id_equipe") == null) {
            usuario.setIdEquipe(null);
        }
        usuario.setDtCadastro(rs.getDate("dt_cadastro").toLocalDate());
        return usuario;
    }

    public ArrayList<UsuarioTO> findAll() {
        ArrayList<UsuarioTO> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM T_USUARIO ORDER BY nome";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    usuarios.add(mapResultSetToUsuarioTO(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro na Consulta (findAll): " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return usuarios;
    }
    public UsuarioTO findByCodigo(Long idUsuario) {
        UsuarioTO usuario = new UsuarioTO();
        String sql = "SELECT * FROM T_USUARIO WHERE id_usuario = ?";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                usuario = mapResultSetToUsuarioTO(rs);
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro na Consulta (findByCodigo): " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return usuario;
    }

    public UsuarioTO save(UsuarioTO usuario) {
        String sql = "INSERT INTO T_USUARIO (id_usuario, nome, cpf, email, telefone, dt_nascimento, senha, is_gestor, id_equipe, dt_cadastro) " +
                "VALUES (SEQ_USUARIO.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getCpf());
            ps.setString(3, usuario.getEmail());
            ps.setString(4, usuario.getTelefone());
            ps.setDate(5, Date.valueOf(usuario.getDtNascimento()));
            ps.setString(6, usuario.getSenha());
            ps.setInt(7, 0);
            ps.setNull(8, java.sql.Types.NUMERIC);
            ps.setDate(9, Date.valueOf(LocalDate.now()));
            if (ps.executeUpdate() > 0) {
                return usuario;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao Salvar (save): " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return null;
    }

    public UsuarioTO update(UsuarioTO usuario) {
        String sql = "UPDATE T_USUARIO SET nome = ?, cpf = ?, email = ?, telefone = ?, dt_nascimento = ? " +
                "WHERE id_usuario = ?";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getCpf());
            ps.setString(3, usuario.getEmail());
            ps.setString(4, usuario.getTelefone());
            if (usuario.getDtNascimento() != null) {
                ps.setDate(5, Date.valueOf(usuario.getDtNascimento()));
            } else {
                ps.setNull(5, java.sql.Types.DATE);
            }
            ps.setLong(6, usuario.getIdUsuario());
            if (ps.executeUpdate() > 0) {
                return usuario;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao Atualizar (update): " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return null;
    }

    public boolean delete(Long idUsuario) {
        String sql = "DELETE FROM T_USUARIO WHERE id_usuario = ?";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idUsuario);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao Excluir (delete): " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return false;
    }

    public UsuarioTO findByLogin(String email, String senha) {
        UsuarioTO usuario = new UsuarioTO();
        String sql = "SELECT * FROM T_USUARIO WHERE email = ? AND senha = ?";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, senha);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                usuario = mapResultSetToUsuarioTO(rs);
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro na Consulta (findByLogin): " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return usuario;
    }

    public boolean cpfExiste(String cpf) {
        String sql = "SELECT COUNT(*) FROM T_USUARIO WHERE cpf = ?";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setString(1, cpf);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao verificar CPF: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return false;
    }

    public boolean emailExiste(String email) {
        String sql = "SELECT COUNT(*) FROM T_USUARIO WHERE email = ?";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao verificar Email: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return false;
    }
    public void esvaziarEquipe(Long idEquipe) {
        String sql = "UPDATE T_USUARIO SET id_equipe = NULL WHERE id_equipe = ?";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idEquipe);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao esvaziar equipe (desassociar usuários): " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
    }

    public UsuarioTO entrarNaEquipe(Long idUsuario, Long idEquipe) {
        String sql = "UPDATE T_USUARIO SET id_equipe = ? WHERE id_usuario = ?";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idEquipe);
            ps.setLong(2, idUsuario);
            if (ps.executeUpdate() > 0) {
                return this.findByCodigo(idUsuario);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao entrar na equipe: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return null;
    }

    public ArrayList<UsuarioTO> findAllByEquipe(Long idEquipe) {
        String sql = "SELECT * FROM T_USUARIO WHERE id_equipe = ? ORDER BY nome";
        ArrayList<UsuarioTO> usuarios = new ArrayList<>();
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, idEquipe);
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    usuarios.add(mapResultSetToUsuarioTO(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar usuários por equipe: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return usuarios;
    }

    public UsuarioTO sairDaEquipe(Long idUsuario) {
        String sql = "UPDATE T_USUARIO SET id_equipe = NULL WHERE id_usuario = ?";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {

            ps.setLong(1, idUsuario);

            if (ps.executeUpdate() > 0) {
                return this.findByCodigo(idUsuario);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao sair da equipe: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return null;
    }
}