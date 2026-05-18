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
    public void registrarEstudiante(String nombre, double nota) {
        repository.registrar(new Estudiante(nombre, nota));
    }

    @Override
    public List<Estudiante> listarEstudiantes() {
        return List.of();
    }

    @Override
    public Optional<Estudiante> buscarPorNombre(String nombre) {
        return Optional.empty();
    }

    @Override
    public double calcularPromedio() {
        return 0;
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