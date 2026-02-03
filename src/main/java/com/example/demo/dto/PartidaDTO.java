package com.example.demo.dto;

import java.util.List;

// DTO para CRUD de partidas
public class PartidaDTO {

    private Long id;
    private int duracion;
    private List<Long> jugadorIds;
    private List<Integer> scores;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getDuracion() { return duracion; }
    public void setDuracion(int duracion) { this.duracion = duracion; }

    public List<Long> getJugadorIds() { return jugadorIds; }
    public void setJugadorIds(List<Long> jugadorIds) { this.jugadorIds = jugadorIds; }

    public List<Integer> getScores() { return scores; }
    public void setScores(List<Integer> scores) { this.scores = scores; }
}
