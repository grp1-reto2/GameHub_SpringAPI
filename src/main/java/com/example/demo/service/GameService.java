package com.example.demo.service;

import com.example.demo.dto.PartidaDTO;
import com.example.demo.model.*;
import com.example.demo.repo.*;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio encargado de la lógica de negocio para las partidas de GameHub.
 * Gestiona el ciclo de vida de una partida y la relación muchos a muchos 
 * entre Jugadores y Partidas a través de la entidad intermedia JugadorPartida.
 */
@Service
public class GameService {

    // Repositorios para acceder a las tablas en la base de datos PostgreSQL de AWS
    private final JugadorRepository jugadorRepo;
    private final PartidaRepository partidaRepo;
    private final JugadorPartidaRepository jpRepo;

    /**
     * Constructor con inyección de dependencias para los repositorios necesarios.
     */
    public GameService(JugadorRepository j, PartidaRepository p, JugadorPartidaRepository jp) {
        this.jugadorRepo = j;
        this.partidaRepo = p;
        this.jpRepo = jp;
    }

    /**
     * Registra una nueva partida y asigna los resultados a cada jugador participante.
     * @param dto Objeto con la duración y listas de IDs de jugadores y sus respectivos scores.
     */
    public void crear(PartidaDTO dto) {
        // 1. Crear y persistir la entidad Partida básica
        Partida partida = new Partida();
        partida.setDuracion(dto.getDuracion());
        partidaRepo.save(partida);

        // 2. Iterar sobre los jugadores para crear la relación y asignar el score individual
        for (int i = 0; i < dto.getJugadorIds().size(); i++) {
            // Se busca el jugador en la BD (que coincide con los contactos de Odoo)
            Jugador jugador = jugadorRepo.findById(dto.getJugadorIds().get(i))
                    .orElseThrow(() -> new RuntimeException("Jugador no encontrado"));
            
            // Se crea la relación intermedia Jugador-Partida
            JugadorPartida jp = new JugadorPartida();
            jp.setJugador(jugador);
            jp.setPartida(partida);
            jp.setScore(dto.getScores().get(i)); // Asignar puntuación de este jugador
            jpRepo.save(jp);
        }
    }

    /**
     * Obtiene el listado de todas las partidas convirtiéndolas a formato DTO para el frontend.
     */
    public List<PartidaDTO> obtenerTodas() {
        return partidaRepo.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Recupera una partida específica por su ID.
     */
    public PartidaDTO obtenerPorId(Long id) {
        return toDTO(partidaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Partida no encontrada")));
    }

    /**
     * Actualiza los datos de una partida, permitiendo modificar la duración 
     * y las puntuaciones de los jugadores asociados.
     */
    public void actualizar(Long id, PartidaDTO dto) {
        Partida p = partidaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Partida no encontrada"));
        
        // Actualizar datos generales de la partida
        p.setDuracion(dto.getDuracion());
        partidaRepo.save(p);

        // Actualizar las puntuaciones en la tabla intermedia
        for (JugadorPartida jp : p.getJugadores()) {
            int index = dto.getJugadorIds().indexOf(jp.getJugador().getId());
            if (index != -1) {
                jp.setScore(dto.getScores().get(index));
                jpRepo.save(jp);
            }
        }
    }

    /**
     * Elimina una partida por su identificador. 
     * Nota: Dependiendo de la configuración de JPA, esto puede borrar en cascada las relaciones.
     */
    public void eliminar(Long id) { 
        partidaRepo.deleteById(id); 
    }

    /**
     * Método privado auxiliar para convertir una entidad Partida en un PartidaDTO.
     * Facilita la comunicación con la API REST y oculta la complejidad del modelo.
     */
    private PartidaDTO toDTO(Partida p) {
        PartidaDTO dto = new PartidaDTO();
        dto.setId(p.getId());
        dto.setDuracion(p.getDuracion());
        // Extraer los IDs de jugadores de la relación intermedia
        dto.setJugadorIds(p.getJugadores().stream()
                .map(jp -> jp.getJugador().getId())
                .collect(Collectors.toList()));
        // Extraer los scores de la relación intermedia
        dto.setScores(p.getJugadores().stream()
                .map(JugadorPartida::getScore)
                .collect(Collectors.toList()));
        return dto;
    }
}