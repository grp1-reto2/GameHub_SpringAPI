package com.example.demo.dto;

// DTO para estadísticas de un jugador
public class EstadisticasJugadorDTO {
    private Long jugadorId;
    private int totalPartidas;
    private int scoreTotal;
    private double scorePromedio;
    private int scoreMaximo;
    private int scoreMinimo;
    private int duracionTotal;
    private double duracionPromedio;

    public Long getJugadorId() { return jugadorId; }
    public void setJugadorId(Long jugadorId) { this.jugadorId = jugadorId; }

    public int getTotalPartidas() { return totalPartidas; }
    public void setTotalPartidas(int totalPartidas) { this.totalPartidas = totalPartidas; }

    public int getScoreTotal() { return scoreTotal; }
    public void setScoreTotal(int scoreTotal) { this.scoreTotal = scoreTotal; }

    public double getScorePromedio() { return scorePromedio; }
    public void setScorePromedio(double scorePromedio) { this.scorePromedio = scorePromedio; }

    public int getScoreMaximo() { return scoreMaximo; }
    public void setScoreMaximo(int scoreMaximo) { this.scoreMaximo = scoreMaximo; }

    public int getScoreMinimo() { return scoreMinimo; }
    public void setScoreMinimo(int scoreMinimo) { this.scoreMinimo = scoreMinimo; }

    public int getDuracionTotal() { return duracionTotal; }
    public void setDuracionTotal(int duracionTotal) { this.duracionTotal = duracionTotal; }

    public double getDuracionPromedio() { return duracionPromedio; }
    public void setDuracionPromedio(double duracionPromedio) { this.duracionPromedio = duracionPromedio; }
}
