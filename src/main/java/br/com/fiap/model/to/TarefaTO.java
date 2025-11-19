package br.com.fiap.model.to;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;

public class TarefaTO {

    private Long idTarefa;

    @NotBlank(message = "O título é obrigatório")
    private String titulo;

    private String descricao;
    private Long idCategoria;

    @FutureOrPresent(message = "A data de vencimento deve ser hoje ou no futuro")
    private LocalDate dtVencimento;

    private Double tempoEstimadoH;

    @Pattern(regexp = "Pendente|Em Andamento|Concluída", message = "O status deve ser 'Pendente', 'Em Andamento' ou 'Concluída'")
    private String status;

    @NotNull(message = "O ID do usuário (dono da tarefa) é obrigatório")
    private Long idUsuario;

    private LocalDate dtCriacao;
    private LocalDate dtConclusao;


    public Long getIdTarefa() {
        return idTarefa;
    }

    public void setIdTarefa(Long idTarefa) {
        this.idTarefa = idTarefa;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public LocalDate getDtVencimento() {
        return dtVencimento;
    }

    public void setDtVencimento(LocalDate dtVencimento) {
        this.dtVencimento = dtVencimento;
    }

    public Double getTempoEstimadoH() {
        return tempoEstimadoH;
    }

    public void setTempoEstimadoH(Double tempoEstimadoH) {
        this.tempoEstimadoH = tempoEstimadoH;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public LocalDate getDtCriacao() {
        return dtCriacao;
    }

    public void setDtCriacao(LocalDate dtCriacao) {
        this.dtCriacao = dtCriacao;
    }

    public LocalDate getDtConclusao() {
        return dtConclusao;
    }

    public void setDtConclusao(LocalDate dtConclusao) {
        this.dtConclusao = dtConclusao;
    }
}