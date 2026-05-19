package src.gestioncalificaciones.repository;

import src.gestioncalificaciones.model.Estudiante;
import java.util.List;

/**
 * Contrato de acceso a datos para estudiantes.
 *
 * SOLID — D (Dependency Inversion):
 * Las capas superiores (Service, Menu) dependen de ESTA interfaz,
 * no de EstudianteRepository directamente. Si mañana cambiamos
 * el almacenamiento (lista → base de datos → archivo), solo
 * cambia la implementación concreta, nada más.
 *
 * SOLID — O (Open/Closed):
 * El sistema está abierto a nuevas implementaciones (ej: guardar
 * en archivo) sin modificar el código que ya funciona.
 */
public interface IEstudianteRepository {

    /**
     * Persiste un estudiante en el almacenamiento.
     * 
     * @param estudiante Objeto Estudiante válido a registrar.
     */
    void registrar(Estudiante estudiante);

    /**
     * Retorna todos los estudiantes registrados.
     * 
     * @return Lista inmutable (copia defensiva) de estudiantes.
     */
    List<Estudiante> obtenerTodos();
}
