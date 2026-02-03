package com.example.demo.dto;

import java.time.LocalDateTime;

// DTO para el historial de partidas de un jugador
public class HistorialPartidaDTO {
    private Long partidaId;
    private int score;
    private int duracion;
    private LocalDateTime fecha;

    public Long getPartidaId() { return partidaId; }
    public void setPartidaId(Long partidaId) { this.partidaId = partidaId; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public int getDuracion() { return duracion; }
    public void setDuracion(int duracion) { this.duracion = duracion; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}
