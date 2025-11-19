package br.com.fiap.model.to;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class EquipeTO {
    private Long idEquipe;
    @NotBlank(message = "O nome da equipe é obrigatório")
    private String nomeEquipe;
    private String codigoEquipe;
    @NotNull(message = "O ID do gestor é obrigatório")
    private Long idGestor;
    private LocalDate dtCriacao;

    public Long getIdEquipe() {
        return idEquipe;
    }

    public void setIdEquipe(Long idEquipe) {
        this.idEquipe = idEquipe;
    }

    public String getNomeEquipe() {
        return nomeEquipe;
    }

    public void setNomeEquipe(String nomeEquipe) {
        this.nomeEquipe = nomeEquipe;
    }

    public String getCodigoEquipe() {
        return codigoEquipe;
    }

    public void setCodigoEquipe(String codigoEquipe) {
        this.codigoEquipe = codigoEquipe;
    }

    public Long getIdGestor() {
        return idGestor;
    }

    public void setIdGestor(Long idGestor) {
        this.idGestor = idGestor;
    }

    public LocalDate getDtCriacao() {
        return dtCriacao;
    }

    public void setDtCriacao(LocalDate dtCriacao) {
        this.dtCriacao = dtCriacao;
    }
}