package gestioncalificaciones.ui;

import gestioncalificaciones.model.Estudiante;
import gestioncalificaciones.service.IEstudianteService;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Capa de interfaz de usuario — menú interactivo en consola.
 *
 * SOLID — S (Single Responsibility):
 * Esta clase SOLO se encarga de mostrar datos y capturar entradas.
 * No tiene lógica de negocio ni acceso a datos.
 *
 * SOLID — D (Dependency Inversion):
 * Depende de IEstudianteService (interfaz), nunca de la implementación
 * concreta. Si mañana cambia el servicio, este menú no se modifica.
 */
public class Menu {

    private final IEstudianteService service;
    private final Scanner scanner;

    // Separador visual reutilizable
    private static final String LINEA = "─".repeat(52);
    private static final String LINEA_D = "═".repeat(52);

    /**
     * @param service Implementación del servicio inyectada desde Main.
     */
    public Menu(IEstudianteService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Punto de entrada del menú. Ejecuta el bucle principal.
     */
    public void mostrar() {
        boolean activo = true;

        while (activo) {
            imprimirEncabezado();
            int opcion = leerOpcion();

            switch (opcion) {
                case 1 -> opcionRegistrar();
                case 2 -> opcionListar();
                case 3 -> opcionPromedio();
                case 4 -> opcionMayorNota();
                case 5 -> opcionAprobados();
                case 6 -> opcionBuscar();
                case 0 -> {
                    System.out.println("\n  Hasta luego. ¡Buena suerte en el semestre!\n");
                    activo = false;
                }
                default -> System.out.println("\n  ✘ Opción inválida. Intente de nuevo.\n");
            }
        }

        scanner.close();
    }

    // ════════════════════════════════════════════════════════════════════
    // ENCABEZADO Y LECTURA DE OPCIÓN
    // ════════════════════════════════════════════════════════════════════

    private void imprimirEncabezado() {
        System.out.println("\n" + LINEA_D);
        System.out.println("   SISTEMA DE GESTIÓN DE CALIFICACIONES");
        System.out.println(LINEA_D);
        System.out.println("  1. Registrar estudiante");
        System.out.println("  2. Mostrar listado de estudiantes");
        System.out.println("  3. Calcular promedio general");
        System.out.println("  4. Estudiante con mayor nota");
        System.out.println("  5. Estudiantes aprobados");
        System.out.println("  6. Buscar estudiante por nombre");
        System.out.println("  0. Salir");
        System.out.println(LINEA);
        System.out.print("  Seleccione una opción: ");
    }

    private int leerOpcion() {
        try {
            int opcion = Integer.parseInt(scanner.nextLine().trim());
            return opcion;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // ════════════════════════════════════════════════════════════════════
    // OPCIÓN 1 — REGISTRAR ESTUDIANTE
    // ════════════════════════════════════════════════════════════════════

    private void opcionRegistrar() {
        System.out.println("\n" + LINEA);
        System.out.println("  REGISTRAR ESTUDIANTE");
        System.out.println(LINEA);

        System.out.print("  Nombre: ");
        String nombre = scanner.nextLine().trim();

        System.out.print("  Nota final (0.0 – 5.0): ");
        try {
            double nota = Double.parseDouble(scanner.nextLine().trim().replace(",", "."));
            service.registrarEstudiante(nombre, nota);
        } catch (NumberFormatException e) {
            System.out.println("  ✘ Nota inválida. Ingrese un número como 3.5 o 4,2.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("  ✘ Error: " + e.getMessage());
        }
    }

    // ════════════════════════════════════════════════════════════════════
    // OPCIÓN 2 — LISTADO DE ESTUDIANTES
    // ════════════════════════════════════════════════════════════════════

    private void opcionListar() {
        System.out.println("\n" + LINEA);
        System.out.println("  LISTADO DE ESTUDIANTES");
        System.out.println(LINEA);

        List<Estudiante> lista = service.listarEstudiantes();

        if (lista.isEmpty()) {
            System.out.println("  No hay estudiantes registrados aún.");
            return;
        }

        int i = 1;
        for (Estudiante e : lista) {
            System.out.printf("  %2d. %s%n", i++, e);
        }
        System.out.println(LINEA);
        System.out.printf("  Total registrados: %d%n", lista.size());
    }

    // ════════════════════════════════════════════════════════════════════
    // OPCIÓN 3 — PROMEDIO GENERAL
    // ════════════════════════════════════════════════════════════════════

    private void opcionPromedio() {
        System.out.println("\n" + LINEA);
        System.out.println("  PROMEDIO GENERAL");
        System.out.println(LINEA);

        if (service.listarEstudiantes().isEmpty()) {
            System.out.println("  No hay estudiantes registrados aún.");
            return;
        }

        double promedio = service.calcularPromedio();
        System.out.printf("  Promedio general del grupo: %.2f%n", promedio);

        String nivel;
        if (promedio >= 4.5)
            nivel = "Excelente";
        else if (promedio >= 4.0)
            nivel = "Sobresaliente";
        else if (promedio >= 3.0)
            nivel = "Aprobado";
        else
            nivel = "Reprobado";

        System.out.println("  Nivel del grupo: " + nivel);
    }

    // ════════════════════════════════════════════════════════════════════
    // OPCIÓN 4 — MAYOR NOTA
    // ════════════════════════════════════════════════════════════════════

    private void opcionMayorNota() {
        System.out.println("\n" + LINEA);
        System.out.println("  ESTUDIANTE CON MAYOR NOTA");
        System.out.println(LINEA);

        Optional<Estudiante> mejor = service.estudianteConMayorNota();

        if (mejor.isEmpty()) {
            System.out.println("  No hay estudiantes registrados aún.");
        } else {
            System.out.println("  🏆 " + mejor.get());
        }
    }

    // ════════════════════════════════════════════════════════════════════
    // OPCIÓN 5 — APROBADOS
    // ════════════════════════════════════════════════════════════════════

    private void opcionAprobados() {
        System.out.println("\n" + LINEA);
        System.out.println("  ESTUDIANTES APROBADOS (nota ≥ 3.0)");
        System.out.println(LINEA);

        List<Estudiante> aprobados = service.obtenerAprobados();
        List<Estudiante> todos = service.listarEstudiantes();

        if (todos.isEmpty()) {
            System.out.println("  No hay estudiantes registrados aún.");
            return;
        }

        if (aprobados.isEmpty()) {
            System.out.println("  Ningún estudiante ha aprobado hasta el momento.");
        } else {
            int i = 1;
            for (Estudiante e : aprobados) {
                System.out.printf("  %2d. %s%n", i++, e);
            }
        }

        System.out.println(LINEA);
        System.out.printf("  Aprobados: %d / %d  (%.0f%%)%n",
                aprobados.size(),
                todos.size(),
                (aprobados.size() * 100.0) / todos.size());
    }

    // ════════════════════════════════════════════════════════════════════
    // OPCIÓN 6 — BUSCAR POR NOMBRE
    // ════════════════════════════════════════════════════════════════════

    private void opcionBuscar() {
        System.out.println("\n" + LINEA);
        System.out.println("  BUSCAR ESTUDIANTE");
        System.out.println(LINEA);

        System.out.print("  Nombre a buscar: ");
        String nombre = scanner.nextLine().trim();

        Optional<Estudiante> resultado = service.buscarPorNombre(nombre);

        if (resultado.isPresent()) {
            System.out.println("  Estudiante encontrado:");
            System.out.println("  → " + resultado.get());
        } else {
            System.out.println("  ✘ No se encontró ningún estudiante con el nombre: \"" + nombre + "\"");
        }
    }
}
