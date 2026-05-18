package src.gestioncalificaciones.service;

import src.gestioncalificaciones.model.Estudiante;
import src.gestioncalificaciones.repository.IEstudianteRepository;
import java.util.List;
import java.util.Optional;

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
        List<Estudiante> todos = repository.obtenerTodos();
        if (todos.isEmpty()) {
            return 0.0;
        }
        return todos.stream()
                .mapToDouble(Estudiante::getNota)
                .sum() / todos.size();
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