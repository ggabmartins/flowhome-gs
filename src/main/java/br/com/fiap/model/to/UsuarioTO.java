package br.com.fiap.model.to;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class UsuarioTO {
    private Long idUsuario;
    @NotBlank(message = "O nome é obrigatório")
    private String nome;
    @NotBlank(message = "O CPF é obrigatório")
    @Size(min = 11, max = 11)
    private String cpf;
    @NotBlank(message = "O email é obrigatório")
    @Email
    private String email;
    @NotBlank(message = "O telefone é obrigatório")
    @Size(min = 10, max = 15)
    private String telefone;
    @Past(message = "A data de nascimento deve ser no passado")
    private LocalDate dtNascimento;
    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 6, max = 20)
    private String senha;
    private int isGestor;
    private Long idEquipe;
    private LocalDate dtCadastro;

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public LocalDate getDtNascimento() {
        return dtNascimento;
    }

    public void setDtNascimento(LocalDate dtNascimento) {
        this.dtNascimento = dtNascimento;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getIsGestor() {
        return isGestor;
    }

    public void setIsGestor(int isGestor) {
        this.isGestor = isGestor;
    }

    public Long getIdEquipe() {
        return idEquipe;
    }

    public void setIdEquipe(Long idEquipe) {
        this.idEquipe = idEquipe;
    }

    public LocalDate getDtCadastro() {
        return dtCadastro;
    }

    public void setDtCadastro(LocalDate dtCadastro) {
        this.dtCadastro = dtCadastro;
    }
}