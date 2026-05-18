package src.gestioncalificaciones.service;

import src.gestioncalificaciones.model.Estudiante;
import java.util.List;
import java.util.Optional;

/**
 * Contrato de lógica de negocio para el módulo de calificaciones.
 *
 * SOLID — I (Interface Segregation):
 * Esta interfaz agrupa operaciones cohesivas del mismo dominio.
 * Si el sistema creciera con módulos muy distintos (ej: reportes PDF,
 * notificaciones), se crearían interfaces separadas en lugar de
 * agregar métodos aquí.
 *
 * SOLID — D (Dependency Inversion):
 * Menu.java depende de esta interfaz, nunca de EstudianteService directamente.
 */
public interface IEstudianteService {

    // ── Módulo Registro ──────────────────────────────────────────────
    void registrarEstudiante(String nombre, double nota);

    // ── Módulo Listado y Búsqueda (Aprendiz 3) ───────────────────────
    List<Estudiante> listarEstudiantes();

    Optional<Estudiante> buscarPorNombre(String nombre);

    // ── Módulo Estadísticas (Aprendiz 4) ─────────────────────────────
    double calcularPromedio();

    Optional<Estudiante> estudianteConMayorNota();

    List<Estudiante> obtenerAprobados();
}
