// Paquete que contiene los controladores para la lógica de negocio de GameHub
package com.example.demo.controller;

import com.example.demo.dto.PartidaDTO;
import com.example.demo.service.GameService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador encargado de las operaciones CRUD (Crear, Leer, Actualizar, Borrar)
 * para las partidas y resultados de los juegos.
 */
@RestController // Indica que esta clase es un componente de Spring que maneja peticiones REST
@RequestMapping("/api/partidas") // Define el punto de entrada de la URL para este recurso
@CrossOrigin(origins = "*") // Habilita el intercambio de recursos de origen cruzado (CORS) para el frontend web
public class GameController {

    // Referencia al servicio que contiene la lógica de negocio de las partidas
    private final GameService service;

    /**
     * Constructor para la inyección de dependencias. 
     * Spring se encarga de instanciar el GameService automáticamente.
     */
    public GameController(GameService service) { 
        this.service = service; 
    }

    /**
     * Crea una nueva partida en el sistema.
     * @param dto Objeto de transferencia de datos con la información de la partida (Jugador, Juego, Puntos).
     * @return Mensaje de confirmación tras persistir los datos.
     */
    @PostMapping
    public String crear(@RequestBody PartidaDTO dto) {
        // Llama al servicio para registrar la partida en la base de datos (PostgreSQL en AWS)
        service.crear(dto);
        return "Partida creada";
    }

    /**
     * Recupera el listado completo de todas las partidas registradas.
     * Útil para vistas de administración general de GameHub.
     */
    @GetMapping
    public List<PartidaDTO> listar() { 
        return service.obtenerTodas(); 
    }

    /**
     * Busca y devuelve una partida específica por su identificador único.
     * @param id El ID de la partida almacenada.
     */
    @GetMapping("/{id}")
    public PartidaDTO obtener(@PathVariable Long id) { 
        return service.obtenerPorId(id); 
    }

    /**
     * Modifica los datos de una partida existente.
     * @param id ID de la partida que se desea actualizar.
     * @param dto Nuevos datos (ej. actualización de puntuación final o estado).
     */
    @PutMapping("/{id}")
    public String actualizar(@PathVariable Long id, @RequestBody PartidaDTO dto) {
        service.actualizar(id, dto);
        return "Partida actualizada";
    }

    /**
     * Elimina una partida del sistema.
     * @param id ID de la partida que se borrará permanentemente.
     */
    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return "Partida eliminada";
    }
}