package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.Jugador;

public interface JugadorRepository extends JpaRepository<Jugador, Long> {}
