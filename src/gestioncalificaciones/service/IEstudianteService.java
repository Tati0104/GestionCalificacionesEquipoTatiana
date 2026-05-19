package src.gestioncalificaciones.service;

import src.gestioncalificaciones.model.Estudiante;
import java.util.List;
import java.util.Optional;

/**
 * Contrato de logica de negocio para el modulo de calificaciones.
 *
 * SOLID - I (Interface Segregation):
 * Esta interfaz agrupa operaciones cohesivas del mismo dominio.
 *
 * SOLID - D (Dependency Inversion):
 * Menu.java depende de esta interfaz, nunca de EstudianteService directamente.
 */
public interface IEstudianteService {

    // -- Modulo Registro --------------------------------------------------
    void registrarEstudiante(String nombre);

    void agregarNotaAEstudiante(String nombre, double nota);

    // -- Modulo Listado y Busqueda ----------------------------------------
    List<Estudiante> listarEstudiantes();

    Optional<Estudiante> buscarPorNombre(String nombre);

    // -- Modulo Estadisticas ----------------------------------------------
    double calcularPromedio();

    double calcularPromedioEstudiante(String nombre);

    Optional<Estudiante> estudianteConMayorNota();

    List<Estudiante> obtenerAprobados();
}
