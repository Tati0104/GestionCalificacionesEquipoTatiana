package src.gestioncalificaciones;

import src.gestioncalificaciones.repository.EstudianteRepository;
import src.gestioncalificaciones.repository.IEstudianteRepository;
import src.gestioncalificaciones.service.EstudianteService;
import src.gestioncalificaciones.service.IEstudianteService;
import gestioncalificaciones.ui.Menu;

public class Main {
    public static void main(String[] args) {
        IEstudianteRepository repository = new EstudianteRepository();
        IEstudianteService service = new EstudianteService(repository);
        Menu menu = new Menu(service);
        menu.mostrar();
    }
}