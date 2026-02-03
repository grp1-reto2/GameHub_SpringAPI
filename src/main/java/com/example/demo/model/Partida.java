package com.example.demo.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

// Entidad Partida
@Entity
public class Partida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int duracion;

    private LocalDateTime fecha = LocalDateTime.now(); // Fecha de creación de la partida

    // Relación M:N con Jugador a través de JugadorPartida
    @JsonIgnore
    @OneToMany(mappedBy = "partida", cascade = CascadeType.ALL)
    private Set<JugadorPartida> jugadores = new HashSet<>();

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getDuracion() { return duracion; }
    public void setDuracion(int duracion) { this.duracion = duracion; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public Set<JugadorPartida> getJugadores() { return jugadores; }
    public void setJugadores(Set<JugadorPartida> jugadores) { this.jugadores = jugadores; }
}
