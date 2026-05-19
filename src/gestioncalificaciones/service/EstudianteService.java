package src.gestioncalificaciones.service;

import src.gestioncalificaciones.model.Estudiante;
import src.gestioncalificaciones.repository.IEstudianteRepository;
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
    public void registrarEstudiante(String nombre) {
        repository.registrar(new Estudiante(nombre));
    }

    @Override
    public void agregarNotaAEstudiante(String nombre, double nota) {
        Optional<Estudiante> encontrado = buscarPorNombre(nombre);
        if (encontrado.isEmpty()) {
            throw new IllegalArgumentException("No existe un estudiante con el nombre: " + nombre);
        }
        encontrado.get().agregarNota(nota);
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

    /** Promedio general del grupo (promedia los promedios individuales). */
    @Override
    public double calcularPromedio() {
        List<Estudiante> todos = repository.obtenerTodos();
        if (todos.isEmpty()) return 0.0;
        return todos.stream()
                .mapToDouble(Estudiante::calcularPromedio)
                .average()
                .orElse(0.0);
    }

    /** Promedio individual de un estudiante por nombre. */
    @Override
    public double calcularPromedioEstudiante(String nombre) {
        return buscarPorNombre(nombre)
                .map(Estudiante::calcularPromedio)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe un estudiante con el nombre: " + nombre));
    }

    @Override
    public Optional<Estudiante> estudianteConMayorNota() {
        return repository.obtenerTodos()
                .stream()
                .max(Comparator.comparingDouble(Estudiante::calcularPromedio));
    }

    @Override
    public List<Estudiante> obtenerAprobados() {
        return repository.obtenerTodos()
                .stream()
                .filter(Estudiante::estaAprobado)
                .sorted(Comparator.comparingDouble(Estudiante::calcularPromedio).reversed())
                .collect(Collectors.toList());
    }
}
