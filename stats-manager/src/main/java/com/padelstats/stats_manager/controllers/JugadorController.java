package com.padelstats.stats_manager.controllers;

import com.padelstats.stats_manager.entities.Jugadores;
import com.padelstats.stats_manager.services.IJugadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/jugadores")
public class JugadorController {

    @Autowired
    IJugadorService iJugadorService;

    @GetMapping("")
    public List<Jugadores> showAll() {
        return iJugadorService.findAll();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Jugadores> jugadorPorId(@PathVariable("id") String id) {
        Jugadores jugador = iJugadorService.findById(id).orElse(null);
        HttpStatus status = jugador != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(jugador, status);
    }

    @PostMapping("insertar")
    public ResponseEntity<Jugadores> insertarJugador(@RequestBody Jugadores jugador) {
        Jugadores nuevoJugador = iJugadorService.save(jugador);
        return new ResponseEntity<>(nuevoJugador, HttpStatus.CREATED);
    }

    @PostMapping("insertarTodos")
    public ResponseEntity<List<Jugadores>> insertarJugadores(@RequestBody List<Jugadores> jugadores) {
        try {
            /*List<Jugadores> nuevosJugadores = iJugadorService.saveAll(jugadores);
            return new ResponseEntity<>(nuevosJugadores, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }*/
            List<Jugadores> jugadoresConPareja = new ArrayList<>(jugadores);
            List<Jugadores> jugadoresSinPareja = jugadores.stream()
                    .peek(jugador -> jugador.setPareja(null))
                    .toList();
            List<Jugadores> nuevosJugadores = iJugadorService.saveAll(jugadoresSinPareja);

            for (Jugadores jugador : jugadores) {
                if (jugador.getPareja() != null) {
                    Optional<Jugadores> jugadorPareja = iJugadorService.findById(jugador.getPareja().getId());
                    if (jugadorPareja.isPresent()) {
                        jugadorPareja.get().setPareja(jugador);
                        iJugadorService.save(jugadorPareja.get());
                    }
                    Jugadores jugadorActualizado = iJugadorService.findById(jugador.getId()).orElse(null);
                    if (jugadorActualizado != null) {
                        jugadorActualizado.setPareja(jugador.getPareja());
                        iJugadorService.save(jugadorActualizado);
                    }
                }
            }

            return new ResponseEntity<>(nuevosJugadores, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Jugadores> modificarJugador(@PathVariable("id") String id, @RequestBody Jugadores jugNue) {
        Jugadores jugExis = iJugadorService.findById(id).orElse(null);
        if (jugExis != null) {
            jugExis.setNombre(!jugNue.getNombre().equals(jugExis.getNombre()) ?
                    jugNue.getNombre() : jugExis.getNombre());
            //TO-DO sacar esto a un método

            Jugadores jugadorModificado = iJugadorService.saveAndFlush(jugExis);
            return new ResponseEntity<>(jugadorModificado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrarJugador(@PathVariable("id") String id) {
        iJugadorService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

        /*@Autowired
        IJugadorService jugadorService;

        @GetMapping("listar")
        public ResponseEntity<List<Jugador>> listarJugadores() {
            List<Jugador> jugadores = jugadorService.findAll();
            return new ResponseEntity<>(jugadores, HttpStatus.OK);
        }

        @GetMapping(value = "/{id}", produces = "application/json")
        public ResponseEntity<Jugador> jugadorporId(@PathVariable("id") int id) {
            Jugador jugador = jugadorService.findById(id).orElse(null);
            HttpStatus status = (jugador != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(jugador, status);
        }

        @PostMapping("insertar")
        public ResponseEntity<Jugador> insertarJugador(@RequestBody Jugador jugador) {
            Jugador nuevoJugador = jugadorService.save(jugador);
            return new ResponseEntity<>(nuevoJugador, HttpStatus.CREATED);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> borrarJugador(@PathVariable("id") int id) {
            jugadorService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }*/
}
