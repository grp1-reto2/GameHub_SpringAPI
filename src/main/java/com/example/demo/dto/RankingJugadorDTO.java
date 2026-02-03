package com.example.demo.dto;

// DTO para el ranking de jugadores
public class RankingJugadorDTO {
    private Long jugadorId;
    private String nombre;
    private int scoreTotal;
    private int duracionTotal; // suma de duración de todas sus partidas
    private int totalPartidas;

    public Long getJugadorId() { return jugadorId; }
    public void setJugadorId(Long jugadorId) { this.jugadorId = jugadorId; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getScoreTotal() { return scoreTotal; }
    public void setScoreTotal(int scoreTotal) { this.scoreTotal = scoreTotal; }

    public int getDuracionTotal() { return duracionTotal; }
    public void setDuracionTotal(int duracionTotal) { this.duracionTotal = duracionTotal; }

    public int getTotalPartidas() { return totalPartidas; }
    public void setTotalPartidas(int totalPartidas) { this.totalPartidas = totalPartidas; }
}
