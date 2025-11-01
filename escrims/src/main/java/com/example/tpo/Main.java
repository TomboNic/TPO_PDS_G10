package com.example.tpo;

import java.util.Scanner;
import com.example.tpo.model.aplicacion.ScrimFacade;
import com.example.tpo.model.dominio.*;
import com.example.tpo.model.singleton.UsuarioManager;
import com.example.tpo.model.observer.NotificationSubscriber;
import com.example.tpo.model.notificacion.NotificadorFactoryDev;
import com.example.tpo.model.observer.events.*;
import com.example.tpo.model.state.scrim.ScrimContext;
import com.example.tpo.model.moderacion.*;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static UsuarioManager um = UsuarioManager.getInstancia();
    private static ScrimFacade facade = new ScrimFacade();
    private static NotificationSubscriber notificador = new NotificationSubscriber(new NotificadorFactoryDev());
    private static Usuario usuarioActual = null;

    public static void main(String[] args) {
        facade.subscribe(notificador);
        
        while (true) {
            mostrarMenu();
            int opcion = obtenerOpcion();
            
            switch (opcion) {
                case 1:
                    cu1_RegistrarUsuario();
                    break;
                case 2:
                    cu2_AutenticarUsuario();
                    break;
                case 3:
                    cu3_CrearScrim();
                    break;
                case 4:
                    cu4_PostularseAScrim();
                    break;
                case 5:
                    cu5_EmparejamientoYLobby();
                    break;
                case 6:
                    cu6_ConfirmarParticipacion();
                    break;
                case 7:
                    cu7_IniciarScrim();
                    break;
                case 8:
                    cu8_FinalizarYEstadisticas();
                    break;
                case 9:
                    cu9_CancelarScrim();
                    break;
                case 10:
                    cu10_NotificarEventos();
                    break;
                case 11:
                    cu11_ModerarReportes();
                    break;
                case 0:
                    System.out.println("¡Gracias por usar el sistema!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Opción no válida");
            }
            
            System.out.println("\nPresione ENTER para continuar...");
            scanner.nextLine();
        }
    }

    // Implementaciones de Casos de Uso



    private static void printEstado(ScrimContext ctx) {
        String nombre = (ctx != null && ctx.getState() != null) ? ctx.getState().nombre() : "(sin estado)";
        System.out.println("Estado actual: " + nombre);
    }

    private static void printRosters(ScrimFacade facade, int id) {
        var s = facade.getScrim(id);
        System.out.println("Equipo1: " + s.getEquipo1().stream().map(u -> safeName(u)).reduce((a,b) -> a+", "+b).orElse("-"));
        System.out.println("Equipo2: " + s.getEquipo2().stream().map(u -> safeName(u)).reduce((a,b) -> a+", "+b).orElse("-"));
        System.out.println("Suplentes: " + s.getSuplentes().stream().map(u -> safeName(u)).reduce((a,b) -> a+", "+b).orElse("-"));
    }

    private static String safeName(Usuario u) { 
        return u == null ? "?" : (u.getNombreUsuario() != null ? u.getNombreUsuario() : u.toString()); 
    }

    private static void mostrarMenu() {
        System.out.println("\n=== Sistema de Scrims ===");
        System.out.println("1. Registrar Usuario");
        System.out.println("2. Autenticar Usuario");
        System.out.println("3. Crear Scrim");
        System.out.println("4. Postularse a Scrim");
        System.out.println("5. Emparejar y Armar Lobby");
        System.out.println("6. Confirmar Participación");
        System.out.println("7. Iniciar Scrim");
        System.out.println("8. Finalizar y Cargar Estadísticas");
        System.out.println("9. Cancelar Scrim");
        System.out.println("10. Notificar Eventos");
        System.out.println("11. Moderar Reportes");
        System.out.println("0. Salir");
        System.out.print("\nSeleccione una opción: ");
    }

    private static int obtenerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void cu1_RegistrarUsuario() {
        System.out.println("\n=== CU1: Registrar Usuario ===");
        System.out.println("Ingrese los datos del usuario:");
        
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Apellido: ");
        String apellido = scanner.nextLine();
        
        System.out.print("Username: ");
        String username = scanner.nextLine();
        
        System.out.print("Email: ");
        String email = scanner.nextLine();
        
        System.out.print("Password: ");
        String password = scanner.nextLine();

        Usuario usuario = um.registrar(nombre, apellido, username, email, password);
        
        if (usuario != null) {
            System.out.println("\n¡Registro exitoso!");
            System.out.println("Datos del usuario registrado:");
            System.out.println("Nombre completo: " + usuario.getNombre() + " " + usuario.getApellido());
            System.out.println("Username: " + usuario.getNombreUsuario());
            System.out.println("Email: " + usuario.getEmail());
        } else {
            System.out.println("\nError en el registro. El email podría estar ya registrado.");
        }
    }

    private static void cu2_AutenticarUsuario() {
        System.out.println("\n=== CU2: Autenticar Usuario ===");
        
        System.out.print("Email: ");
        String email = scanner.nextLine();
        
        System.out.print("Password: ");
        String password = scanner.nextLine();

        Usuario usuario = um.autenticar(email, password);
        
        if (usuario != null) {
            System.out.println("\n¡Autenticación exitosa!");
            System.out.println("Bienvenido " + usuario.getNombre() + " " + usuario.getApellido());
            usuarioActual = usuario;
        } else {
            System.out.println("\nError de autenticación. Credenciales inválidas.");
        }
    }

    private static void cu3_CrearScrim() {
        if (usuarioActual == null) {
            System.out.println("\nDebe autenticarse primero para crear un scrim.");
            return;
        }

        System.out.println("\n=== CU3: Crear Scrim ===");
        System.out.println("Seleccione el formato del Scrim:");
        System.out.println("1. 1vs1");
        System.out.println("2. 3vs3");
        System.out.println("3. 5vs5");
        System.out.print("Formato: ");
        
        int formato;
        try {
            formato = Integer.parseInt(scanner.nextLine());
            if (formato < 1 || formato > 3) {
                System.out.println("Formato inválido. Debe ser 1, 2 o 3.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Formato inválido. Debe ser un número.");
            return;
        }

        int cuposPorLado;
        switch (formato) {
            case 1: cuposPorLado = 1; break;
            case 2: cuposPorLado = 3; break;
            case 3: cuposPorLado = 5; break;
            default: cuposPorLado = 5;
        }

        int id = facade.crearScrim(cuposPorLado);
        System.out.println("Scrim creado con ID: " + id);
        System.out.println("Formato: " + cuposPorLado + "vs" + cuposPorLado);
        printEstado(facade.getContext(id));
    }

    private static void cu4_PostularseAScrim() {
        if (usuarioActual == null) {
            System.out.println("\nDebe autenticarse primero para postularse a un scrim.");
            return;
        }

        System.out.println("\n=== CU4: Postularse a Scrim ===");
        System.out.print("Ingrese el ID del scrim: ");
        int scrimId;
        try {
            scrimId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID de scrim inválido.");
            return;
        }

        Scrim s = facade.getScrim(scrimId);
        if (s == null) {
            System.out.println("No se encontró el scrim con ID " + scrimId);
            return;
        }

        // Verificar si hay cupo disponible
        int cuposPorEquipo = s.getCupos() / 2;
        if (s.getEquipo1().size() >= cuposPorEquipo && s.getEquipo2().size() >= cuposPorEquipo) {
            System.out.println("Lo sentimos, el Scrim ya está lleno (" + cuposPorEquipo + "vs" + cuposPorEquipo + ")");
            return;
        }

        System.out.println("Seleccione su rol:");
        System.out.println("1. TOP");
        System.out.println("2. JUNGLE");
        System.out.println("3. MID");
        System.out.println("4. ADC");
        System.out.println("5. SUPPORT");
        System.out.print("Rol: ");
        
        Rol rol;
        try {
            int opcionRol = Integer.parseInt(scanner.nextLine());
            switch (opcionRol) {
                case 1: rol = Rol.TOP; break;
                case 2: rol = Rol.JUNGLE; break;
                case 3: rol = Rol.MID; break;
                case 4: rol = Rol.ADC; break;
                case 5: rol = Rol.SUPPORT; break;
                default:
                    System.out.println("Rol inválido");
                    return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Opción inválida");
            return;
        }

        facade.postular(scrimId, usuarioActual, rol);
        System.out.println("Postulación realizada.");
        System.out.println("\nCupos por equipo: " + cuposPorEquipo);
        
        // Actualizar la información del Scrim después de la postulación
        s = facade.getScrim(scrimId); // Obtener el estado actualizado
        System.out.println("Equipo1: " + s.getEquipo1().size() + "/" + cuposPorEquipo + " jugadores");
        System.out.println("Equipo2: " + s.getEquipo2().size() + "/" + cuposPorEquipo + " jugadores");
        printRosters(facade, scrimId);
    }

    private static void cu5_EmparejamientoYLobby() {
        System.out.println("\n=== CU5: Emparejar y Armar Lobby ===");
        System.out.print("Ingrese el ID del scrim: ");
        int scrimId;
        try {
            scrimId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID de scrim inválido.");
            return;
        }

        facade.armarLobby(scrimId);
        System.out.println("Lobby armado.");
        printEstado(facade.getContext(scrimId));
        printRosters(facade, scrimId);
    }

    private static void cu6_ConfirmarParticipacion() {
        if (usuarioActual == null) {
            System.out.println("\nDebe autenticarse primero para confirmar participación.");
            return;
        }

        System.out.println("\n=== CU6: Confirmar Participación ===");
        System.out.println("Precondición: Lobby armado");
        System.out.print("Ingrese el ID del scrim: ");
        int scrimId;
        try {
            scrimId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID de scrim inválido.");
            return;
        }

        facade.confirmar(scrimId, usuarioActual);
        System.out.println("Usuario " + usuarioActual.getNombreUsuario() + " confirmó participación");
        printEstado(facade.getContext(scrimId));
        System.out.println("Postcondición: Participación confirmada para el usuario " + usuarioActual.getNombreUsuario());
    }

    private static void cu7_IniciarScrim() {
        System.out.println("\n=== CU7: Iniciar Scrim ===");
        System.out.print("Ingrese el ID del scrim: ");
        int scrimId;
        try {
            scrimId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID de scrim inválido.");
            return;
        }

        facade.iniciar(scrimId);
        System.out.println("Scrim iniciado.");
        printEstado(facade.getContext(scrimId));
    }

    private static void cu8_FinalizarYEstadisticas() {
        System.out.println("\n=== CU8: Finalizar y Cargar Estadísticas ===");
        System.out.print("Ingrese el ID del scrim: ");
        int scrimId;
        try {
            scrimId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID de scrim inválido.");
            return;
        }

        facade.finalizar(scrimId, new Resultado());
        System.out.println("Scrim finalizado y estadísticas cargadas.");
        printEstado(facade.getContext(scrimId));
    }

    private static void cu9_CancelarScrim() {
        System.out.println("\n=== CU9: Cancelar Scrim ===");
        System.out.print("Ingrese el ID del scrim a cancelar: ");
        int scrimId;
        try {
            scrimId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID de scrim inválido.");
            return;
        }

        facade.cancelar(scrimId);
        System.out.println("Scrim cancelado.");
        printEstado(facade.getContext(scrimId));
    }

    private static void cu10_NotificarEventos() {
        System.out.println("\n=== CU10: Notificar Eventos ===");
        System.out.println("Simulando notificaciones del sistema...");
        notificador.onEvent(new ScrimCreated());
        System.out.println("Eventos notificados exitosamente.");
    }

    private static void cu11_ModerarReportes() {
        System.out.println("\n=== CU11: Moderar Reportes ===");
        System.out.println("Procesando reporte de conducta...");
        
        ReporteConducta reporte = new ReporteConducta();
        IManejadorReporte autoMod = new AutoModerador();
        IManejadorReporte botMod = new BotModerador();
        IManejadorReporte adminMod = new AdministradorFinal();

        autoMod.setSiguiente(botMod);
        botMod.setSiguiente(adminMod);

        autoMod.manejar(reporte);
        System.out.println("Reporte procesado por la cadena de moderación.");
    }
}

