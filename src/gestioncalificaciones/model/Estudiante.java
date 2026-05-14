package gestioncalificaciones.model;

/**
 * Entidad principal del sistema.
 *
 * SOLID — S (Single Responsibility):
 * Esta clase SOLO representa un estudiante. No registra, no busca,
 * no calcula promedios — solo encapsula y valida sus propios datos.
 */
public class Estudiante {

    private final String nombre;
    private final double nota;

    /**
     * @param nombre Nombre completo del estudiante (no puede ser vacío).
     * @param nota   Nota final entre 0.0 y 5.0.
     */
    public Estudiante(String nombre, double nota) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        if (nota < 0.0 || nota > 5.0) {
            throw new IllegalArgumentException("La nota debe estar entre 0.0 y 5.0. Valor recibido: " + nota);
        }
        this.nombre = nombre.trim();
        this.nota = nota;
    }

    public String getNombre() {
        return nombre;
    }

    public double getNota() {
        return nota;
    }

    /**
     * Determina si el estudiante aprobó (nota >= 3.0).
     * Esta lógica SÍ pertenece al modelo porque depende solo de sus datos.
     */
    public boolean estaAprobado() {
        return nota >= 3.0;
    }

    @Override
    public String toString() {
        String estado = estaAprobado() ? "✔ APROBADO" : "✘ REPROBADO";
        return String.format("%-28s | Nota: %.1f | %s", nombre, nota, estado);
    }
}
