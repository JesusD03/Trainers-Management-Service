package co.edu.icesi.controller;

import co.edu.icesi.model.Entrenador;
import co.edu.icesi.service.EntrenadorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entrenadores")
@Tag(name = "Entrenadores", description = "API para gestionar entrenadores")
public class EntrenadorController {

    private final EntrenadorService entrenadorService;

    public EntrenadorController(EntrenadorService entrenadorService) {
        this.entrenadorService = entrenadorService;
    }

    @Operation(summary = "Listar entrenadores", description = "Obtiene una lista de todos los entrenadores")
    @GetMapping
    public List<Entrenador> listarEntrenadores() {
        return entrenadorService.listarEntrenadores();
    }

    @Operation(summary = "Agregar un nuevo entrenador", description = "Crea un nuevo entrenador en la base de datos")
    @PostMapping
    public Entrenador agregarEntrenador(@RequestBody Entrenador entrenador) {
        return entrenadorService.agregarEntrenador(entrenador);
    }

    @Operation(summary = "Obtener un entrenador por ID", description = "Busca un entrenador por su identificador único")
    @GetMapping("/{id}")
    public Entrenador obtenerEntrenador(@PathVariable Long id) {
        return entrenadorService.obtenerEntrenador(id);
    }
}
