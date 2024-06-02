package com.padelstats.stats_manager.controllers;

import com.padelstats.stats_manager.entities.Jugador;
import com.padelstats.stats_manager.services.IJugadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jugador")
public class JugadorController {

    @Autowired
    IJugadorService iJugadorService;

    @PostMapping("/save")
    public ResponseEntity<?> guardar(@ModelAttribute Jugador j) {
        iJugadorService.save(j);

        return ResponseEntity.ok(j);
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
