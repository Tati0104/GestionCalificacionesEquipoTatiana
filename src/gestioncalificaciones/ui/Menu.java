package gestioncalificaciones.ui;

import src.gestioncalificaciones.model.Estudiante;
import src.gestioncalificaciones.service.IEstudianteService;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Capa de interfaz de usuario - menu interactivo en consola.
 *
 * SOLID - S (Single Responsibility):
 * Esta clase SOLO se encarga de mostrar datos y capturar entradas.
 *
 * SOLID - D (Dependency Inversion):
 * Depende de IEstudianteService (interfaz), nunca de la implementacion concreta.
 */
public class Menu {

    private final IEstudianteService service;
    private final Scanner scanner;

    private static final String LINEA   = "-".repeat(60);
    private static final String LINEA_D = "=".repeat(60);

    public Menu(IEstudianteService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    public void mostrar() {
        boolean activo = true;

        while (activo) {
            imprimirEncabezado();
            int opcion = leerOpcion();

            switch (opcion) {
                case 1 -> opcionRegistrar();
                case 2 -> opcionAgregarNota();
                case 3 -> opcionListar();
                case 4 -> opcionPromedio();
                case 5 -> opcionPromedioEstudiante();
                case 6 -> opcionMayorNota();
                case 7 -> opcionAprobados();
                case 8 -> opcionBuscar();
                case 0 -> {
                    System.out.println("\n  Hasta luego. Buena suerte en el semestre!\n");
                    activo = false;
                }
                default -> System.out.println("\n  Opcion invalida. Intente de nuevo.\n");
            }
        }

        scanner.close();
    }

    // ====================================================================
    // ENCABEZADO Y LECTURA DE OPCION
    // ====================================================================

    private void imprimirEncabezado() {
        System.out.println("\n" + LINEA_D);
        System.out.println("   SISTEMA DE GESTION DE CALIFICACIONES");
        System.out.println(LINEA_D);
        System.out.println("  1. Registrar estudiante");
        System.out.println("  2. Agregar nota a un estudiante");
        System.out.println("  3. Mostrar listado de estudiantes");
        System.out.println("  4. Calcular promedio general del grupo");
        System.out.println("  5. Ver promedio de un estudiante");
        System.out.println("  6. Estudiante con mayor nota");
        System.out.println("  7. Estudiantes aprobados");
        System.out.println("  8. Buscar estudiante por nombre");
        System.out.println("  0. Salir");
        System.out.println(LINEA);
        System.out.print("  Seleccione una opcion: ");
    }

    private int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // ====================================================================
    // OPCION 1 - REGISTRAR ESTUDIANTE
    // ====================================================================

    private void opcionRegistrar() {
        System.out.println("\n" + LINEA);
        System.out.println("  REGISTRAR ESTUDIANTE");
        System.out.println(LINEA);

        System.out.print("  Nombre: ");
        String nombre = scanner.nextLine().trim();

        try {
            service.registrarEstudiante(nombre);
            System.out.println("  Estudiante registrado. Use la opcion 2 para agregar sus notas.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("  Error: " + e.getMessage());
        }
    }

    // ====================================================================
    // OPCION 2 - AGREGAR NOTA A UN ESTUDIANTE
    // ====================================================================

    private void opcionAgregarNota() {
        System.out.println("\n" + LINEA);
        System.out.println("  AGREGAR NOTA A UN ESTUDIANTE");
        System.out.println(LINEA);

        System.out.print("  Nombre del estudiante: ");
        String nombre = scanner.nextLine().trim();

        System.out.print("  Nota (0.0 - 5.0): ");
        try {
            double nota = Double.parseDouble(scanner.nextLine().trim().replace(",", "."));
            service.agregarNotaAEstudiante(nombre, nota);
            System.out.printf("  Nota %.1f agregada a %s.%n", nota, nombre);
        } catch (NumberFormatException e) {
            System.out.println("  Nota invalida. Ingrese un numero como 3.5 o 4,2.");
        } catch (IllegalArgumentException e) {
            System.out.println("  Error: " + e.getMessage());
        }
    }

    // ====================================================================
    // OPCION 3 - LISTADO DE ESTUDIANTES
    // ====================================================================

    private void opcionListar() {
        System.out.println("\n" + LINEA);
        System.out.println("  LISTADO DE ESTUDIANTES");
        System.out.println(LINEA);

        List<Estudiante> lista = service.listarEstudiantes();

        if (lista.isEmpty()) {
            System.out.println("  No hay estudiantes registrados aun.");
            return;
        }

        int i = 1;
        for (Estudiante e : lista) {
            System.out.printf("  %2d. %s%n", i++, e);
        }
        System.out.println(LINEA);
        System.out.printf("  Total registrados: %d%n", lista.size());
    }

    // ====================================================================
    // OPCION 4 - PROMEDIO GENERAL DEL GRUPO
    // ====================================================================

    private void opcionPromedio() {
        System.out.println("\n" + LINEA);
        System.out.println("  PROMEDIO GENERAL DEL GRUPO");
        System.out.println(LINEA);

        if (service.listarEstudiantes().isEmpty()) {
            System.out.println("  No hay estudiantes registrados aun.");
            return;
        }

        double promedio = service.calcularPromedio();
        System.out.printf("  Promedio general del grupo: %.2f%n", promedio);

        String nivel;
        if (promedio >= 4.5)       nivel = "Excelente";
        else if (promedio >= 4.0)  nivel = "Sobresaliente";
        else if (promedio >= 3.0)  nivel = "Aprobado";
        else                       nivel = "Reprobado";

        System.out.println("  Nivel del grupo: " + nivel);
    }

    // ====================================================================
    // OPCION 5 - PROMEDIO DE UN ESTUDIANTE
    // ====================================================================

    private void opcionPromedioEstudiante() {
        System.out.println("\n" + LINEA);
        System.out.println("  PROMEDIO DE UN ESTUDIANTE");
        System.out.println(LINEA);

        System.out.print("  Nombre del estudiante: ");
        String nombre = scanner.nextLine().trim();

        try {
            double promedio = service.calcularPromedioEstudiante(nombre);
            Optional<Estudiante> est = service.buscarPorNombre(nombre);
            est.ifPresent(e -> {
                System.out.println("  Estudiante: " + e.getNombre());
                System.out.println("  Notas registradas: " + e.getNotas());
                System.out.printf("  Promedio: %.2f%n", promedio);
                System.out.println("  Estado: " + (e.estaAprobado() ? "APROBADO" : "REPROBADO"));
            });
        } catch (IllegalArgumentException e) {
            System.out.println("  Error: " + e.getMessage());
        }
    }

    // ====================================================================
    // OPCION 6 - MAYOR NOTA
    // ====================================================================

    private void opcionMayorNota() {
        System.out.println("\n" + LINEA);
        System.out.println("  ESTUDIANTE CON MAYOR NOTA");
        System.out.println(LINEA);

        Optional<Estudiante> mejor = service.estudianteConMayorNota();

        if (mejor.isEmpty()) {
            System.out.println("  No hay estudiantes registrados aun.");
        } else {
            System.out.println("  Mejor promedio: " + mejor.get());
        }
    }

    // ====================================================================
    // OPCION 7 - APROBADOS
    // ====================================================================

    private void opcionAprobados() {
        System.out.println("\n" + LINEA);
        System.out.println("  ESTUDIANTES APROBADOS (promedio >= 3.0)");
        System.out.println(LINEA);

        List<Estudiante> aprobados = service.obtenerAprobados();
        List<Estudiante> todos = service.listarEstudiantes();

        if (todos.isEmpty()) {
            System.out.println("  No hay estudiantes registrados aun.");
            return;
        }

        if (aprobados.isEmpty()) {
            System.out.println("  Ningun estudiante ha aprobado hasta el momento.");
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

    // ====================================================================
    // OPCION 8 - BUSCAR POR NOMBRE
    // ====================================================================

    private void opcionBuscar() {
        System.out.println("\n" + LINEA);
        System.out.println("  BUSCAR ESTUDIANTE");
        System.out.println(LINEA);

        System.out.print("  Nombre a buscar: ");
        String nombre = scanner.nextLine().trim();

        Optional<Estudiante> resultado = service.buscarPorNombre(nombre);

        if (resultado.isPresent()) {
            System.out.println("  Estudiante encontrado:");
            System.out.println("  -> " + resultado.get());
        } else {
            System.out.println("  No se encontro ningun estudiante con el nombre: \"" + nombre + "\"");
        }
    }
}
