package gestioncalificaciones.service;

import gestioncalificaciones.model.Estudiante;
import gestioncalificaciones.repository.IEstudianteRepository;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EstudianteService implements IEstudianteService {

    private final IEstudianteRepository repository;

    public EstudianteService(IEstudianteRepository repository) {
        this.repository = repository;
    }

    @Override
    public void registrarEstudiante(String nombre, double nota) {
        repository.registrar(new Estudiante(nombre, nota));
    }

    // SOLID — S: este método solo lista estudiantes ordenados
    @Override
    public List<Estudiante> listarEstudiantes() {
        return repository.obtenerTodos()
                .stream()
                .sorted(Comparator.comparing(Estudiante::getNombre))
                .collect(Collectors.toList());
    }

    // Búsqueda insensible a mayúsculas usando Optional (sin retornar null)
    @Override
    public Optional<Estudiante> buscarPorNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            return Optional.empty();
        }

        return repository.obtenerTodos()
                .stream()
                .filter(e -> e.getNombre().equalsIgnoreCase(nombre.trim()))
                .findFirst();
    }

    @Override
    public double calcularPromedio() {
        List<Estudiante> todos = repository.obtenerTodos();
        if (todos.isEmpty()) {
            return 0;
        }

        return todos.stream()
                .mapToDouble(Estudiante::getNota)
                .average()
                .orElse(0);
    }

    @Override
    public Optional<Estudiante> estudianteConMayorNota() {
        return Optional.empty();
    }

    @Override
    public List<Estudiante> obtenerAprobados() {
        return List.of();
    }
}