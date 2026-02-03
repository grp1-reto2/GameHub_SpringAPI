package com.example.demo.service;

import com.example.demo.dto.JugadorDTO;
import com.example.demo.model.Jugador;
import com.example.demo.repo.JugadorRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio encargado de la lógica de negocio para los Jugadores.
 * Actúa como capa intermedia entre el Controlador (API) y el Repositorio (Base de Datos).
 */
@Service // Marca la clase como un componente de servicio gestionado por Spring
public class JugadorService {

    private final JugadorRepository repo;

    // Inyección de dependencias del repositorio para interactuar con la tabla de jugadores
    public JugadorService(JugadorRepository repo) { 
        this.repo = repo; 
    }

    /**
     * Registra un nuevo jugador en el sistema.
     * @param dto Objeto de transferencia con los datos del nuevo jugador.
     * @return La entidad Jugador persistida en la base de datos de AWS.
     */
    public Jugador crear(JugadorDTO dto) {
        // Transformamos el DTO de entrada en una entidad de modelo
        Jugador j = new Jugador();
        j.setNombre(dto.getNombre());
        j.setEmail(dto.getEmail());
        
        // Guardamos en la base de datos
        return repo.save(j);
    }

    /**
     * Recupera todos los jugadores y los convierte a formato DTO para la API.
     */
    public List<JugadorDTO> obtenerTodos() {
        // Usamos Streams para mapear cada entidad de la lista a un DTO
        return repo.findAll().stream()
                   .map(this::toDTO)
                   .collect(Collectors.toList());
    }

    /**
     * Busca un jugador por su identificador único.
     * @param id Identificador (ID) del jugador.
     * @throws RuntimeException si el jugador no existe en la base de datos.
     */
    public JugadorDTO obtenerPorId(Long id) {
        return toDTO(repo.findById(id)
                   .orElseThrow(() -> new RuntimeException("Jugador no encontrado")));
    }

    /**
     * Actualiza los datos de un jugador existente.
     * @param id ID del jugador a modificar.
     * @param dto Datos actualizados (Nombre y Email).
     */
    public Jugador actualizar(Long id, JugadorDTO dto) {
        // Primero verificamos que el jugador existe en el sistema
        Jugador j = repo.findById(id)
                        .orElseThrow(() -> new RuntimeException("Jugador no encontrado"));
        
        // Actualizamos los campos permitidos
        j.setNombre(dto.getNombre());
        j.setEmail(dto.getEmail());
        
        // El método save() actualizará el registro existente debido a que ya tiene un ID
        return repo.save(j);
    }

    /**
     * Elimina el registro de un jugador permanentemente.
     * @param id ID del jugador a borrar.
     */
    public void eliminar(Long id) { 
        repo.deleteById(id); 
    }

    /**
     * Método privado auxiliar (Mapeador) para convertir una entidad de base de datos
     * en un objeto DTO limpio para enviar a la web o a Odoo.
     */
    private JugadorDTO toDTO(Jugador j) {
        JugadorDTO dto = new JugadorDTO();
        dto.setId(j.getId());
        dto.setNombre(j.getNombre());
        dto.setEmail(j.getEmail());
        return dto;
    }
}