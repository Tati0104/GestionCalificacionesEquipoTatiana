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

    @Override
    public List<Estudiante> listarEstudiantes() {
        return repository.obtenerTodos()
                .stream()
                .sorted(Comparator.comparing(Estudiante::getNombre))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Estudiante> buscarPorNombre(String nombre) {
        if (nombre == null || nombre.isBlank())
            return Optional.empty();
        return repository.obtenerTodos()
                .stream()
                .filter(e -> e.getNombre().equalsIgnoreCase(nombre.trim()))
                .findFirst();
    }

    @Override
    public double calcularPromedio() {
        List<Estudiante> todos = repository.obtenerTodos();
        if (todos.isEmpty())
            return 0.0;
        return todos.stream()
                .mapToDouble(Estudiante::getNota)
                .average()
                .orElse(0);
    }

    @Override
    public Optional<Estudiante> estudianteConMayorNota() {
        return repository.obtenerTodos()
                .stream()
                .max(Comparator.comparingDouble(Estudiante::getNota));
    }

    @Override
    public List<Estudiante> obtenerAprobados() {
        return repository.obtenerTodos()
                .stream()
                .filter(Estudiante::estaAprobado)
                .sorted(Comparator.comparingDouble(Estudiante::getNota).reversed())
                .collect(Collectors.toList());
    }
}