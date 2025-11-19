package br.com.fiap.model.to;

public class UsuarioDashboardDTO {

    private long totalTarefasConcluidas;
    private double totalHorasProdutivas;
    private long tarefasPendentes;


    public long getTotalTarefasConcluidas() {
        return totalTarefasConcluidas;
    }

    public void setTotalTarefasConcluidas(long totalTarefasConcluidas) {
        this.totalTarefasConcluidas = totalTarefasConcluidas;
    }

    public double getTotalHorasProdutivas() {
        return totalHorasProdutivas;
    }

    public void setTotalHorasProdutivas(double totalHorasProdutivas) {
        this.totalHorasProdutivas = totalHorasProdutivas;
    }

    public long getTarefasPendentes() {
        return tarefasPendentes;
    }

    public void setTarefasPendentes(long tarefasPendentes) {
        this.tarefasPendentes = tarefasPendentes;
    }
}