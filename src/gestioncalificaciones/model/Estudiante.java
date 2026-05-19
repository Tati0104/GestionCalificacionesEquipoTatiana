package src.gestioncalificaciones.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Entidad principal del sistema.
 *
 * SOLID — S (Single Responsibility):
 * Esta clase SOLO representa un estudiante. No registra, no busca — solo
 * encapsula sus datos y calcula su propio promedio.
 */
public class Estudiante {

    private final String nombre;
    private final List<Double> notas = new ArrayList<>();

    /**
     * @param nombre Nombre completo del estudiante (no puede ser vacío).
     */
    public Estudiante(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }

    /**
     * Agrega una nota al historial del estudiante.
     *
     * @param nota Valor entre 0.0 y 5.0.
     */
    public void agregarNota(double nota) {
        if (nota < 0.0 || nota > 5.0) {
            throw new IllegalArgumentException(
                    "La nota debe estar entre 0.0 y 5.0. Valor recibido: " + nota);
        }
        notas.add(nota);
    }

    public String getNombre() {
        return nombre;
    }

    /** Retorna copia defensiva de la lista de notas. */
    public List<Double> getNotas() {
        return Collections.unmodifiableList(notas);
    }

    /**
     * Calcula el promedio de todas las notas del estudiante.
     * Retorna 0.0 si aún no tiene notas registradas.
     */
    public double calcularPromedio() {
        if (notas.isEmpty()) return 0.0;
        return notas.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    /**
     * Retorna el promedio del estudiante.
     * Mantiene compatibilidad con el código del service que llama getNota().
     */
    public double getNota() {
        return calcularPromedio();
    }

    /**
     * Determina si el estudiante aprobó (promedio >= 3.0).
     */
    public boolean estaAprobado() {
        return calcularPromedio() >= 3.0;
    }

    @Override
    public String toString() {
        String estado = estaAprobado() ? "APROBADO" : "REPROBADO";
        String notasStr = notas.isEmpty() ? "sin notas" : notas.toString();
        return String.format("%-28s | Notas: %-22s | Promedio: %.2f | %s",
                nombre, notasStr, calcularPromedio(), estado);
    }
}