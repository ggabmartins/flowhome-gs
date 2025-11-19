package br.com.fiap.model.to;

public class MembroStatsDTO {

    private long idUsuario;
    private String nomeUsuario;
    private long totalTarefasConcluidas;
    private double totalHorasProdutivas;
    private long tarefasPendentes;

    // Getters e Setters

    public long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(long idUsuario) { this.idUsuario = idUsuario; }

    public String getNomeUsuario() { return nomeUsuario; }
    public void setNomeUsuario(String nomeUsuario) { this.nomeUsuario = nomeUsuario; }

    public long getTotalTarefasConcluidas() { return totalTarefasConcluidas; }
    public void setTotalTarefasConcluidas(long totalTarefasConcluidas) { this.totalTarefasConcluidas = totalTarefasConcluidas; }

    public double getTotalHorasProdutivas() { return totalHorasProdutivas; }
    public void setTotalHorasProdutivas(double totalHorasProdutivas) { this.totalHorasProdutivas = totalHorasProdutivas; }

    public long getTarefasPendentes() { return tarefasPendentes; }
    public void setTarefasPendentes(long tarefasPendentes) { this.tarefasPendentes = tarefasPendentes; }
}