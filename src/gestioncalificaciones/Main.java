package gestioncalificaciones;

import gestioncalificaciones.repository.EstudianteRepository;
import gestioncalificaciones.repository.IEstudianteRepository;
import gestioncalificaciones.service.EstudianteService;
import gestioncalificaciones.service.IEstudianteService;
import gestioncalificaciones.ui.Menu;

public class Main {
    public static void main(String[] args) {
        IEstudianteRepository repository = new EstudianteRepository();
        IEstudianteService service = new EstudianteService(repository);
        Menu menu = new Menu(service);
        menu.mostrar();
    }
}