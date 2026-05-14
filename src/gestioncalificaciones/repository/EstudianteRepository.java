package gestioncalificaciones.repository;

import gestioncalificaciones.model.Estudiante;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * SOLID — S: solo guarda y recupera estudiantes.
 * SOLID — D: implementa IEstudianteRepository.
 * === APRENDIZ 1 — Tatiana Chavez ===
 */
public class EstudianteRepository implements IEstudianteRepository {

    private final List<Estudiante> estudiantes = new ArrayList<>();

    @Override
    public void registrar(Estudiante estudiante) {
        if (estudiante == null) {
            throw new IllegalArgumentException("El estudiante no puede ser nulo.");
        }
        boolean yaExiste = estudiantes.stream()
                .anyMatch(e -> e.getNombre().equalsIgnoreCase(estudiante.getNombre()));
        if (yaExiste) {
            throw new IllegalStateException(
                    "Ya existe un estudiante con el nombre: " + estudiante.getNombre());
        }
        estudiantes.add(estudiante);
        System.out.println("  ✔ Estudiante registrado correctamente.");
    }

    @Override
    public List<Estudiante> obtenerTodos() {
        return Collections.unmodifiableList(estudiantes);
    }
}