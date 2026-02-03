package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.model.*;
import com.example.demo.repo.*;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio encargado del procesamiento de datos analíticos de GameHub.
 * Realiza cálculos de rankings, filtrado de historiales y generación de estadísticas
 * utilizando flujos (Streams) de Java para procesar la información de los repositorios.
 */
@Service
public class DataService {

    private final JugadorRepository jugadorRepo;
    private final PartidaRepository partidaRepo;

    // Inyección de los repositorios necesarios para acceder a la base de datos PostgreSQL
    public DataService(JugadorRepository jugadorRepo, PartidaRepository partidaRepo) {
        this.jugadorRepo = jugadorRepo;
        this.partidaRepo = partidaRepo;
    }

    /**
     * Genera un ranking de jugadores basado en la suma total de sus puntuaciones.
     * @return Lista de DTOs de ranking ordenados de mayor a menor puntuación.
     */
    public List<RankingJugadorDTO> rankingJugadores() {
        return jugadorRepo.findAll().stream().map(j -> {
            RankingJugadorDTO dto = new RankingJugadorDTO();
            dto.setJugadorId(j.getId());
            dto.setNombre(j.getNombre());
            
            // Suma de puntuaciones de todas las partidas del jugador
            int scoreTotal = j.getPartidas().stream().mapToInt(JugadorPartida::getScore).sum();
            // Suma del tiempo total invertido en el juego
            int duracionTotal = j.getPartidas().stream().mapToInt(jp -> jp.getPartida().getDuracion()).sum();
            
            dto.setScoreTotal(scoreTotal);
            dto.setDuracionTotal(duracionTotal);
            dto.setTotalPartidas(j.getPartidas().size());
            return dto;
        })
        // Ordenación descendente por puntuación total
        .sorted((a, b) -> Integer.compare(b.getScoreTotal(), a.getScoreTotal()))
        .collect(Collectors.toList());
    }

    /**
     * Obtiene todas las partidas en las que ha participado un jugador.
     * @param jugadorId ID del jugador a consultar.
     * @return Lista de partidas ordenadas por fecha reciente.
     */
    public List<HistorialPartidaDTO> historial(Long jugadorId) {
        // Validación de existencia del jugador en la base de datos de AWS
        Jugador j = jugadorRepo.findById(jugadorId).orElseThrow(() -> new RuntimeException("Jugador no encontrado"));
        
        return j.getPartidas().stream().map(jp -> {
            HistorialPartidaDTO dto = new HistorialPartidaDTO();
            dto.setPartidaId(jp.getPartida().getId());
            dto.setScore(jp.getScore());
            dto.setDuracion(jp.getPartida().getDuracion());
            dto.setFecha(jp.getPartida().getFecha());
            return dto;
        })
        // Ordenación cronológica inversa (más recientes primero)
        .sorted((a,b) -> b.getFecha().compareTo(a.getFecha()))
        .collect(Collectors.toList());
    }

    /**
     * Calcula métricas detalladas de rendimiento para un jugador específico.
     * Incluye promedios, máximos y mínimos de puntuación.
     */
    public EstadisticasJugadorDTO estadisticasJugador(Long jugadorId) {
        Jugador j = jugadorRepo.findById(jugadorId).orElseThrow(() -> new RuntimeException("Jugador no encontrado"));
        EstadisticasJugadorDTO dto = new EstadisticasJugadorDTO();
        
        dto.setJugadorId(j.getId());
        dto.setTotalPartidas(j.getPartidas().size());

        // Extracción de puntuaciones para cálculos estadísticos
        List<Integer> scores = j.getPartidas().stream().map(JugadorPartida::getScore).toList();
        dto.setScoreTotal(scores.stream().mapToInt(Integer::intValue).sum());
        dto.setScorePromedio(scores.isEmpty() ? 0 : scores.stream().mapToInt(Integer::intValue).average().orElse(0));
        dto.setScoreMaximo(scores.stream().mapToInt(Integer::intValue).max().orElse(0));
        dto.setScoreMinimo(scores.stream().mapToInt(Integer::intValue).min().orElse(0));

        // Cálculo de tiempos de juego
        List<Integer> duraciones = j.getPartidas().stream().map(jp -> jp.getPartida().getDuracion()).toList();
        dto.setDuracionTotal(duraciones.stream().mapToInt(Integer::intValue).sum());
        dto.setDuracionPromedio(duraciones.isEmpty() ? 0 : duraciones.stream().mapToInt(Integer::intValue).average().orElse(0));

        return dto;
    }

    /**
     * Recupera las últimas partidas registradas globalmente en el sistema.
     * @param limit Número máximo de partidas a recuperar.
     */
    public List<PartidaRecienteDTO> recientes(int limit) {
        return partidaRepo.findAll().stream()
                .sorted((a,b) -> b.getFecha().compareTo(a.getFecha()))
                .limit(limit)
                .map(p -> {
                    PartidaRecienteDTO dto = new PartidaRecienteDTO();
                    dto.setPartidaId(p.getId());
                    dto.setDuracion(p.getDuracion());
                    dto.setFecha(p.getFecha());
                    // Mapeo de los jugadores que participaron en esta partida específica
                    dto.setJugadores(p.getJugadores().stream().map(jp -> {
                        JugadorScoreDTO js = new JugadorScoreDTO();
                        js.setJugadorId(jp.getJugador().getId());
                        js.setNombre(jp.getJugador().getNombre());
                        js.setScore(jp.getScore());
                        return js;
                    }).collect(Collectors.toList()));
                    return dto;
                }).toList();
    }

    /**
     * Clasifica las partidas con mayor puntuación acumulada entre todos sus participantes.
     */
    public List<RankingPartidaDTO> rankingPartidas() {
        return partidaRepo.findAll().stream().map(p -> {
            RankingPartidaDTO dto = new RankingPartidaDTO();
            dto.setPartidaId(p.getId());
            dto.setDuracion(p.getDuracion());
            dto.setFecha(p.getFecha());
            // Suma de los puntos de todos los jugadores en la partida
            int scoreTotal = p.getJugadores().stream().mapToInt(JugadorPartida::getScore).sum();
            dto.setScoreTotal(scoreTotal);
            return dto;
        }).sorted((a,b) -> Integer.compare(b.getScoreTotal(), a.getScoreTotal()))
          .collect(Collectors.toList());
    }

    /**
     * Genera un resumen estadístico global de GameHub.
     * Proporciona una visión general para los departamentos de Administración y Dirección.
     */
    public EstadisticasJuegoDTO estadisticasJuego() {
        EstadisticasJuegoDTO dto = new EstadisticasJuegoDTO();
        List<Jugador> jugadores = jugadorRepo.findAll();
        List<Partida> partidas = partidaRepo.findAll();

        dto.setTotalJugadores(jugadores.size());
        dto.setTotalPartidas(partidas.size());
        
        // Promedios globales de score, duración y concurrencia por partida
        dto.setPromedioScorePorPartida(
                partidas.isEmpty() ? 0 : partidas.stream().mapToInt(p -> p.getJugadores().stream().mapToInt(JugadorPartida::getScore).sum()).average().orElse(0)
        );
        dto.setPromedioDuracionPorPartida(
                partidas.isEmpty() ? 0 : partidas.stream().mapToInt(Partida::getDuracion).average().orElse(0)
        );
        dto.setPromedioJugadoresPorPartida(
                partidas.isEmpty() ? 0 : partidas.stream().mapToInt(p -> p.getJugadores().size()).average().orElse(0)
        );
        return dto;
    }
}