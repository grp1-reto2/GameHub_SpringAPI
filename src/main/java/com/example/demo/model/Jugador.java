package com.example.demo.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Set;


@Entity // Entidad Jugador
@Table(name = "jugador") // Especifica el nombre de la tabla física
public class Jugador {

    @Id // Marca este campo como la clave primaria (Primary Key)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Indica que el ID es autoincremental
    private Long id;

    private String nombre;
    private String email;


    /**
     * Relación de Uno a Muchos con la entidad intermedia JugadorPartida.
     * Representa la participación del jugador en múltiples partidas (Relación M:N).
     * * @JsonIgnore: Evita bucles infinitos al serializar a JSON (Recursión infinita).
     * mappedBy: Indica que el campo "jugador" en JugadorPartida es el dueño de la relación.
     * cascade: Si se elimina el jugador, se gestionan sus registros relacionados según la lógica de negocio.
     */
    
    @JsonIgnore 
    @OneToMany(mappedBy = "jugador", cascade = CascadeType.ALL)
    private Set<JugadorPartida> partidas = new HashSet<>();

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Set<JugadorPartida> getPartidas() { return partidas; }
    public void setPartidas(Set<JugadorPartida> partidas) { this.partidas = partidas; }
}
