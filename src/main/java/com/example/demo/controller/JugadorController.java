// Paquete que define la ubicación del controlador dentro del proyecto Spring Boot
package com.example.demo.controller;

import com.example.demo.dto.JugadorDTO;
import com.example.demo.model.Jugador;
import com.example.demo.service.JugadorService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador REST para la gestión integral de Jugadores (CRUD).
 * Este componente permite administrar los perfiles de usuario de GameHub,
 * incluyendo campos como Nickname, Nivel y Puntos.
 */
@RestController // Define que la clase manejará respuestas en formato JSON
@RequestMapping("/api/jugadores") // Define la ruta base para acceder a este recurso
@CrossOrigin(origins = "*") // Permite la conexión desde el frontend web y el entorno local de Odoo
public class JugadorController {

    // Referencia al servicio que contiene la lógica de negocio y persistencia
    private final JugadorService service;

    /**
     * Constructor para inyectar la dependencia del servicio de jugadores.
     */
    public JugadorController(JugadorService service) { 
        this.service = service; 
    }

    /**
     * Crea un nuevo registro de jugador en el sistema.
     * @param dto Objeto con los datos iniciales (Nickname, etc.).
     * @return El objeto Jugador creado y guardado en la base de datos de AWS.
     */
    @PostMapping
    public Jugador crear(@RequestBody JugadorDTO dto) { 
        return service.crear(dto); 
    }

    /**
     * Recupera la lista completa de jugadores registrados.
     * @return Listado de DTOs con la información pública de los jugadores.
     */
    @GetMapping
    public List<JugadorDTO> listar() { 
        return service.obtenerTodos(); 
    }

    /**
     * Busca los detalles de un jugador específico mediante su identificador.
     * @param id ID único del jugador (mapeado con res.partner en Odoo).
     * @return DTO con la información detallada del jugador solicitado.
     */
    @GetMapping("/{id}")
    public JugadorDTO obtener(@PathVariable Long id) { 
        return service.obtenerPorId(id); 
    }

    /**
     * Actualiza la información de un jugador existente.
     * Útil para actualizar niveles, puntos acumulados o logros.
     * @param id ID del jugador a modificar.
     * @param dto Datos actualizados que se enviarán al servicio.
     * @return El objeto Jugador tras la actualización.
     */
    @PutMapping("/{id}")
    public Jugador actualizar(@PathVariable Long id, @RequestBody JugadorDTO dto) {
        return service.actualizar(id, dto);
    }

    /**
     * Elimina permanentemente un jugador del sistema.
     * @param id ID del jugador que se desea borrar.
     * @return Mensaje de confirmación de la operación exitosa.
     */
    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return "Jugador eliminado";
    }
}