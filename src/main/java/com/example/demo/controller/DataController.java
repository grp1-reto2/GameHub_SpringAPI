// Ranking, Historial, Estadisticas
package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.service.DataService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// Controlador unificado para Data (Ranking, Historial, Estadísticas)
@RestController // Define esta clase como un controlador REST que devuelve JSON
@RequestMapping("/api") // Prefijo base para todas las rutas de este controlador
@CrossOrigin(origins = "*") // Permite peticiones desde cualquier origen (CORS), vital para conectar con Odoo o el Frontend
public class DataController {

    private final DataService dataService;

    public DataController(DataService dataService) { this.dataService = dataService; }
    
    /**
     * Obtiene la lista de jugadores ordenados por su puntuación o nivel.
     * Útil para mostrar el Top Mundial en la web de GameHub.
     */
    @GetMapping("/jugadores/ranking")
    public List<RankingJugadorDTO> rankingJugadores() { return dataService.rankingJugadores(); }
    
    /**
     * Obtiene la lista de partidas pasadas de un jugador específico.
     * El ID único del jugador (proveniente de res.partner en Odoo).
     */
    @GetMapping("/jugadores/{id}/historial")
    public List<HistorialPartidaDTO> historialJugador(@PathVariable Long id) { return dataService.historial(id); }
    
    /**
     * Calcula métricas individuales como % de victorias, tiempo jugado o logros desbloqueados.
     * ID del jugador a consultar.
     */

    @GetMapping("/jugadores/{id}/estadisticas")
    public EstadisticasJugadorDTO estadisticasJugador(@PathVariable Long id) { return dataService.estadisticasJugador(id); }

    /**
     * Lista las últimas partidas realizadas en la plataforma.
     * Cantidad de resultados a mostrar (por defecto 10).
     */
    @GetMapping("/partidas/recientes")
    public List<PartidaRecienteDTO> partidasRecientes(@RequestParam(defaultValue = "10") int limit) {
        return dataService.recientes(limit);
    }
    
    /**
     * Obtiene el ranking global de las mejores partidas registradas (basado en records de puntos).
     */
    @GetMapping("/partidas/ranking")
    public List<RankingPartidaDTO> rankingPartidas() { return dataService.rankingPartidas(); }

    /**
     * Obtiene estadísticas globales del sistema de juego.
     * Ejemplo: Total de jugadores activos, partidas totales hoy, etc.
     */
    @GetMapping("/estadisticas")
    public EstadisticasJuegoDTO estadisticasJuego() { return dataService.estadisticasJuego(); }
}

