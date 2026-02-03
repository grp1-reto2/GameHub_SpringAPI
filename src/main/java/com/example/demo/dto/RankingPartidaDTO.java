package com.example.demo.dto;

import java.time.LocalDateTime;

// DTO para ranking de partidas por score total
public class RankingPartidaDTO {
    private Long partidaId;
    private int scoreTotal;
    private int duracion;
    private LocalDateTime fecha;

    public Long getPartidaId() { return partidaId; }
    public void setPartidaId(Long partidaId) { this.partidaId = partidaId; }

    public int getScoreTotal() { return scoreTotal; }
    public void setScoreTotal(int scoreTotal) { this.scoreTotal = scoreTotal; }

    public int getDuracion() { return duracion; }
    public void setDuracion(int duracion) { this.duracion = duracion; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}
