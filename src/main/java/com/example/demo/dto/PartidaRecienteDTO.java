package com.example.demo.dto;

import java.time.LocalDateTime;
import java.util.List;

// DTO para mostrar partidas recientes
public class PartidaRecienteDTO {
    private Long partidaId;
    private int duracion;
    private LocalDateTime fecha;
    private List<JugadorScoreDTO> jugadores;

    public Long getPartidaId() { return partidaId; }
    public void setPartidaId(Long partidaId) { this.partidaId = partidaId; }

    public int getDuracion() { return duracion; }
    public void setDuracion(int duracion) { this.duracion = duracion; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public List<JugadorScoreDTO> getJugadores() { return jugadores; }
    public void setJugadores(List<JugadorScoreDTO> jugadores) { this.jugadores = jugadores; }
}
