package com.example.tpo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.example.tpo.model.aplicacion.ScrimFacade;
import com.example.tpo.model.dominio.Resultado;
import com.example.tpo.model.dominio.Rol;
import com.example.tpo.model.dominio.Scrim;
import com.example.tpo.model.dominio.Usuario;
import com.example.tpo.model.moderacion.AdministradorFinal;
import com.example.tpo.model.moderacion.AutoModerador;
import com.example.tpo.model.moderacion.BotModerador;
import com.example.tpo.model.moderacion.IManejadorReporte;
import com.example.tpo.model.moderacion.ReporteConducta;
import com.example.tpo.model.moderacion.ReportRepository;
import com.example.tpo.model.observer.NotificationSubscriber;
import com.example.tpo.model.observer.events.LobbyCompleted;
import com.example.tpo.model.observer.events.ScrimCreated;
import com.example.tpo.model.observer.events.ScrimFinalized;
import com.example.tpo.model.observer.events.ScrimStateChanged;
import com.example.tpo.model.singleton.UsuarioManager;
import com.example.tpo.model.state.scrim.ScrimContext;
import com.example.tpo.views.UI;
import com.example.tpo.views.Views;

public class AppController {
    private final Scanner scanner;
    private final UsuarioManager um;
    private final ScrimFacade facade;
    private final NotificationSubscriber notificador;
    private Usuario usuarioActual;

    public AppController(Scanner scanner, UsuarioManager um, ScrimFacade facade, NotificationSubscriber notificador) {
        this.scanner = scanner;
        this.um = um;
        this.facade = facade;
        this.notificador = notificador;
    }

    public void start() {
        while (true) {
            mostrarMenu();
            int opcion = obtenerOpcion();
            switch (opcion) {
                case 1 -> cu1_RegistrarUsuario();
                case 2 -> cu2_AutenticarUsuario();
                case 3 -> cu3_CrearScrim();
                case 4 -> cu4_PostularseAScrim();
                case 5 -> cu5_EmparejamientoYLobby();
                case 6 -> cu6_ConfirmarParticipacion();
                case 7 -> cu7_IniciarScrim();
                case 8 -> cu8_FinalizarYEstadisticas();
                case 9 -> cu9_CancelarScrim();
                case 10 -> cu10_NotificarEventos();
                case 11 -> cu11_ModerarReportes();
                case 12 -> cu12_ReportarJugador();
                case 0 -> {
                    System.out.println("Gracias por usar el sistema!");
                    return;
                }
                default -> System.out.println("Opcion no valida");
            }
            System.out.println();
            UI.note("Presione ENTER para continuar...");
            scanner.nextLine();
        }
    }

    private void mostrarMenu() {
        List<String> opciones = List.of(
            "Registrar Usuario",
            "Autenticar Usuario",
            "Crear Scrim",
            "Postularse a Scrim",
            "Emparejar y Armar Lobby",
            "Confirmar Participacion",
            "Iniciar Scrim",
            "Finalizar y Cargar Estadisticas",
            "Cancelar Scrim",
            "Notificar Eventos",
            "Moderar Reportes",
            "Reportar Jugador"
        );
        System.out.println();
        UI.menu("Sistema de Scrims", opciones);
        System.out.print("Seleccione una opcion (0 para salir): ");
    }

    private int obtenerOpcion() {
        try { return Integer.parseInt(scanner.nextLine()); } catch (NumberFormatException e) { return -1; }
    }

    private void printEstado(ScrimContext ctx) {
        String nombre = (ctx != null && ctx.getState() != null) ? ctx.getState().nombre() : "(sin estado)";
        Views.estado("Estado actual: " + nombre);
    }

    private String safeName(Usuario u) {
        return u == null ? "?" : (u.getNombreUsuario() != null ? u.getNombreUsuario() : u.toString());
    }

    private void printRosters(int id) {
        var s = facade.getScrim(id);
        var eq1 = s.getEquipo1().stream().map(this::safeName).toList();
        var eq2 = s.getEquipo2().stream().map(this::safeName).toList();
        var sups = s.getSuplentes().stream().map(this::safeName).toList();
        UI.twoColumns("Rosters", "Equipo 1", eq1, "Equipo 2", eq2);
        if (!sups.isEmpty()) UI.note("Suplentes: " + String.join(", ", sups));
        // Log only (avoid triggering rosters view rendering twice)
        Views.rostersLogOnly("Equipo1: " + (eq1.isEmpty() ? "-" : String.join(", ", eq1)));
        Views.rostersLogOnly("Equipo2: " + (eq2.isEmpty() ? "-" : String.join(", ", eq2)));
        Views.rostersLogOnly("Suplentes: " + (sups.isEmpty() ? "-" : String.join(", ", sups)));
    }

    // Casos de uso
    private void cu1_RegistrarUsuario() {
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

        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            System.out.println("Email y password son obligatorios.");
            return;
        }
        if (nombre == null || nombre.trim().isEmpty() || apellido == null || apellido.trim().isEmpty() || username == null || username.trim().isEmpty()) {
            System.out.println("Datos incompletos: nombre, apellido y username son obligatorios.");
            return;
        }
        if (!UI.isValidEmail(email)) { System.out.println("Email invalido."); return; }
        if (password.length() < 6) { System.out.println("Password muy corta (min 6)."); return; }

        Usuario usuario = um.registrar(nombre, apellido, username, email, password);
        if (usuario != null) {
            System.out.println("\nRegistro exitoso!");
            System.out.println("Nombre completo: " + usuario.getNombre() + " " + usuario.getApellido());
            System.out.println("Username: " + usuario.getNombreUsuario());
            System.out.println("Email: " + usuario.getEmail());
        } else {
            System.out.println("\nError en el registro. El email podria estar ya registrado.");
        }
    }

    private void cu2_AutenticarUsuario() {
        System.out.println("\n=== CU2: Autenticar Usuario ===");
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        if (email == null || email.isBlank() || password == null || password.isBlank()) {
            System.out.println("Email y password son obligatorios.");
            return;
        }
        Usuario usuario = um.autenticar(email, password);
        if (usuario != null) {
            System.out.println("\nAutenticacion exitosa! Bienvenido " + usuario.getNombre() + " " + usuario.getApellido());
            usuarioActual = usuario;
        } else {
            System.out.println("\nError de autenticacion. Credenciales invalidas.");
        }
    }

    private void cu3_CrearScrim() {
        if (usuarioActual == null) { System.out.println("\nDebe autenticarse primero para crear un scrim."); return; }
        System.out.println("\n=== CU3: Crear Scrim ===");
        System.out.println("Seleccione el formato del Scrim:");
        System.out.println("1. 1vs1");
        System.out.println("2. 3vs3");
        System.out.println("3. 5vs5");
        System.out.print("Formato: ");
        int formato;
        try {
            formato = Integer.parseInt(scanner.nextLine());
            if (formato < 1 || formato > 3) { System.out.println("Formato invalido. Debe ser 1, 2 o 3."); return; }
        } catch (NumberFormatException e) { System.out.println("Formato invalido. Debe ser un numero."); return; }
        int cuposPorLado = switch (formato) { case 1 -> 1; case 2 -> 3; case 3 -> 5; default -> 5; };
        int id = facade.crearScrim(cuposPorLado);
        Scrim creado = facade.getScrim(id);
        if (creado != null) creado.setCreador(usuarioActual);
        System.out.println("Scrim creado con ID: " + id);
        System.out.println("Formato: " + cuposPorLado + "vs" + cuposPorLado);
        var ctx = facade.getContext(id);
        printEstado(ctx);
        String estado = (ctx != null && ctx.getState() != null) ? ctx.getState().nombre() : "BuscandoJugadores";
        notificador.onEvent(new ScrimStateChanged(null, estado, id));
    }

    private void cu4_PostularseAScrim() {
        if (usuarioActual == null) { System.out.println("\nDebe autenticarse primero para postularse a un scrim."); return; }
        System.out.println("\n=== CU4: Postularse a Scrim ===");
        System.out.print("Ingrese el ID del scrim: ");
        int scrimId;
        try { scrimId = Integer.parseInt(scanner.nextLine()); } catch (NumberFormatException e) { System.out.println("ID de scrim invalido."); return; }
        Scrim s = facade.getScrim(scrimId);
        if (s == null) { System.out.println("No se encontro el scrim con ID " + scrimId); return; }
        if (s.getEquipo1().contains(usuarioActual) || s.getEquipo2().contains(usuarioActual) || s.getSuplentes().contains(usuarioActual)) {
            System.out.println("Ya estas postulado o en un equipo de este scrim.");
            return;
        }
        int cuposPorEquipo = Math.max(1, s.getCupos() / 2);
        if (s.getEquipo1().size() >= cuposPorEquipo && s.getEquipo2().size() >= cuposPorEquipo) {
            System.out.println("Lo sentimos, el Scrim ya esta lleno (" + cuposPorEquipo + "vs" + cuposPorEquipo + ")");
            return;
        }
        System.out.println("Seleccione su rol:");
        System.out.println("1. TOP\n2. JUNGLE\n3. MID\n4. ADC\n5. SUPPORT");
        System.out.print("Rol: ");
        Rol rol;
        try {
            int opcionRol = Integer.parseInt(scanner.nextLine());
            rol = switch (opcionRol) {
                case 1 -> Rol.TOP; case 2 -> Rol.JUNGLE; case 3 -> Rol.MID; case 4 -> Rol.ADC; case 5 -> Rol.SUPPORT; default -> null;
            };
            if (rol == null) { System.out.println("Rol invalido"); return; }
        } catch (NumberFormatException e) { System.out.println("Opcion invalida"); return; }
        facade.postular(scrimId, usuarioActual, rol);
        System.out.println("Postulacion realizada.");
        System.out.println("Cupos por equipo: " + cuposPorEquipo);
        printRosters(scrimId);
    }

    private void cu5_EmparejamientoYLobby() {
        System.out.println("\n=== CU5: Emparejar y Armar Lobby ===");
        System.out.print("Ingrese el ID del scrim: ");
        int scrimId; try { scrimId = Integer.parseInt(scanner.nextLine()); } catch (NumberFormatException e) { System.out.println("ID de scrim invalido."); return; }
        Scrim s5 = facade.getScrim(scrimId);
        if (s5 == null) { System.out.println("No existe un scrim con ID " + scrimId); return; }
        facade.armarLobby(scrimId);
        System.out.println("Lobby armado.");
        printEstado(facade.getContext(scrimId));
        printRosters(scrimId);
    }

    private void cu6_ConfirmarParticipacion() {
        if (usuarioActual == null) { System.out.println("\nDebe autenticarse primero para confirmar participacion."); return; }
        System.out.println("\n=== CU6: Confirmar Participacion ===");
        System.out.println("Precondicion: Lobby armado");
        System.out.print("Ingrese el ID del scrim: ");
        int scrimId; try { scrimId = Integer.parseInt(scanner.nextLine()); } catch (NumberFormatException e) { System.out.println("ID de scrim invalido."); return; }
        Scrim s6 = facade.getScrim(scrimId);
        if (s6 == null) { System.out.println("No existe un scrim con ID " + scrimId); return; }
        var ctx6 = facade.getContext(scrimId);
        String estado6 = (ctx6 != null && ctx6.getState() != null) ? ctx6.getState().nombre() : "";
        if (!"LobbyArmado".equals(estado6)) { printEstado(ctx6); System.out.println("No se puede confirmar: el scrim no esta en LobbyArmado."); return; }
        boolean esParticipante = s6.getEquipo1().contains(usuarioActual) || s6.getEquipo2().contains(usuarioActual) || s6.getSuplentes().contains(usuarioActual);
        if (!esParticipante) { System.out.println("No estas en este scrim."); return; }
        facade.confirmar(scrimId, usuarioActual);
        System.out.println("Usuario " + usuarioActual.getNombreUsuario() + " confirmo participacion");
        printEstado(facade.getContext(scrimId));
        System.out.println("Postcondicion: Participacion confirmada para el usuario " + usuarioActual.getNombreUsuario());
    }

    private void cu7_IniciarScrim() {
        System.out.println("\n=== CU7: Iniciar Scrim ===");
        System.out.print("Ingrese el ID del scrim: ");
        int scrimId; try { scrimId = Integer.parseInt(scanner.nextLine()); } catch (NumberFormatException e) { System.out.println("ID de scrim invalido."); return; }
        Scrim s7 = facade.getScrim(scrimId);
        if (s7 == null) { System.out.println("No existe un scrim con ID " + scrimId); return; }
        var ctx7 = facade.getContext(scrimId);
        String estado7 = (ctx7 != null && ctx7.getState() != null) ? ctx7.getState().nombre() : "";
        if (!"Confirmado".equals(estado7)) { printEstado(ctx7); System.out.println("No se puede iniciar: el scrim debe estar Confirmado."); return; }
        facade.iniciar(scrimId);
        System.out.println("Scrim iniciado.");
        printEstado(facade.getContext(scrimId));
    }

    private void cu8_FinalizarYEstadisticas() {
        System.out.println("\n=== CU8: Finalizar y Cargar Estadisticas ===");
        System.out.print("Ingrese el ID del scrim: ");
        int scrimId; try { scrimId = Integer.parseInt(scanner.nextLine()); } catch (NumberFormatException e) { System.out.println("ID de scrim invalido."); return; }
        Scrim s8 = facade.getScrim(scrimId);
        if (s8 == null) { System.out.println("No existe un scrim con ID " + scrimId); return; }
        var ctx8 = facade.getContext(scrimId);
        String estado8 = (ctx8 != null && ctx8.getState() != null) ? ctx8.getState().nombre() : "";
        if (!"EnJuego".equals(estado8)) { printEstado(ctx8); System.out.println("No se puede finalizar: el scrim debe estar EnJuego."); return; }
        facade.finalizar(scrimId, new Resultado());
        System.out.println("Scrim finalizado y estadisticas cargadas.");
        printEstado(facade.getContext(scrimId));
    }

    private void cu9_CancelarScrim() {
        System.out.println("\n=== CU9: Cancelar Scrim ===");
        System.out.print("Ingrese el ID del scrim a cancelar: ");
        int scrimId; try { scrimId = Integer.parseInt(scanner.nextLine()); } catch (NumberFormatException e) { System.out.println("ID de scrim invalido."); return; }
        Scrim s9 = facade.getScrim(scrimId);
        if (s9 == null) { System.out.println("No existe un scrim con ID " + scrimId); return; }
        facade.cancelar(scrimId);
        System.out.println("Scrim cancelado.");
        printEstado(facade.getContext(scrimId));
    }

    private void cu10_NotificarEventos() {
        Views.notify("\n=== CU10: Notificar Eventos ===");
        Views.notify("Puede notificar por scrim especifico (recomendado para email a participantes).");
        System.out.print("Ingrese ID de scrim (o ENTER para usar fallback SMTP_TO): ");
        String line = scanner.nextLine();
        if (line != null && !line.trim().isEmpty()) {
            try {
                int id = Integer.parseInt(line.trim());
                notificador.onEvent(new ScrimStateChanged("Demo", "Demo", id));
                notificador.onEvent(new ScrimFinalized(id));
                Views.notify("Notificaciones enviadas para scrim=" + id);
                return;
            } catch (NumberFormatException ex) {
                Views.notify("ID invalido, usando fallback.");
            }
        }
        notificador.onEvent(new ScrimCreated());
        Views.notify("Evento generico notificado (destinatarios SMTP_TO).");
    }

    private void cu11_ModerarReportes() {
        System.out.println("\n=== CU11: Moderar Reportes ===");
        var repo = ReportRepository.get();
        var lista = repo.list();
        if (lista.isEmpty()) { System.out.println("No hay reportes pendientes."); return; }
        System.out.println("Reportes:");
        for (ReporteConducta r : lista) {
            String denunciado = r.getDenunciado() != null ? (r.getDenunciado().getNombreUsuario() != null ? r.getDenunciado().getNombreUsuario() : "?") : "?";
            String estado = (r.getSancion() != null && !r.getSancion().isBlank()) ? ("Resuelto: " + r.getSancion()) : "Pendiente";
            System.out.println(String.format(" %3d) %-15s | %-10s | %s", r.getIdReporte(), denunciado, estado, r.getMotivo() != null ? r.getMotivo().getClass().getSimpleName() : "(sin motivo)"));
        }
        System.out.print("Ingrese ID de reporte para moderar (o ENTER para volver): ");
        String line = scanner.nextLine();
        if (line == null || line.isBlank()) return;
        int id;
        try { id = Integer.parseInt(line.trim()); } catch (NumberFormatException ex) { System.out.println("ID invalido."); return; }
        var r = repo.find(id);
        if (r == null) { System.out.println("Reporte no encontrado."); return; }

        System.out.println("Seleccionar accion: \n 1) Advertir\n 2) Suspender\n 3) Desestimar");
        System.out.print("Opcion: ");
        int op;
        try { op = Integer.parseInt(scanner.nextLine()); } catch (NumberFormatException ex) { System.out.println("Opcion invalida."); return; }
        String sancion = null;
        switch (op) {
            case 1 -> sancion = "Advertencia";
            case 2 -> {
                System.out.print("Dias de suspension: ");
                String dias = scanner.nextLine();
                sancion = "Suspension " + (dias != null && !dias.isBlank() ? dias.trim() : "?") + " dias";
            }
            case 3 -> sancion = "Desestimado";
            default -> { System.out.println("Opcion invalida."); return; }
        }
        r.setSancion(sancion);

        // Pasar por cadena (placeholder)
        IManejadorReporte autoMod = new AutoModerador();
        IManejadorReporte botMod = new BotModerador();
        IManejadorReporte adminMod = new AdministradorFinal();
        autoMod.setSiguiente(botMod);
        botMod.setSiguiente(adminMod);
        autoMod.manejar(r);
        System.out.println("Reporte #" + r.getIdReporte() + " resuelto: " + r.getSancion());
    }

    private void cu12_ReportarJugador() {
        if (usuarioActual == null) { System.out.println("\nDebe autenticarse primero para reportar."); return; }
        System.out.println("\n=== CU12: Reportar Jugador ===");
        System.out.print("Ingrese el ID del scrim: ");
        int scrimId; try { scrimId = Integer.parseInt(scanner.nextLine()); } catch (NumberFormatException e) { System.out.println("ID de scrim invalido."); return; }
        Scrim s = facade.getScrim(scrimId);
        if (s == null) { System.out.println("No existe un scrim con ID " + scrimId); return; }
        List<Usuario> participantes = new ArrayList<>();
        participantes.addAll(s.getEquipo1());
        participantes.addAll(s.getEquipo2());
        participantes.addAll(s.getSuplentes());
        if (participantes.isEmpty()) { System.out.println("El scrim no tiene participantes para reportar."); return; }
        System.out.println("Seleccionar jugador a reportar:");
        for (int i = 0; i < participantes.size(); i++) {
            Usuario u = participantes.get(i);
            String name = (u != null && u.getNombreUsuario() != null) ? u.getNombreUsuario() : (u != null ? u.toString() : "?");
            System.out.println(String.format(" %2d) %s", (i + 1), name));
        }
        System.out.print("Opcion: ");
        int idx; try { idx = Integer.parseInt(scanner.nextLine()); } catch (NumberFormatException e) { System.out.println("Opcion invalida."); return; }
        if (idx < 1 || idx > participantes.size()) { System.out.println("Opcion fuera de rango."); return; }
        Usuario denunciado = participantes.get(idx - 1);
        if (denunciado == null) { System.out.println("Jugador invalido."); return; }
        if (denunciado.getNombreUsuario() != null && usuarioActual.getNombreUsuario() != null && denunciado.getNombreUsuario().equals(usuarioActual.getNombreUsuario())) {
            System.out.println("No puede reportarse a si mismo.");
            return;
        }
        System.out.println("Motivo:\n 1) Toxicidad\n 2) No Show\n 3) Abandono\n 4) Fraude");
        System.out.print("Elija motivo: ");
        int mot; try { mot = Integer.parseInt(scanner.nextLine()); } catch (NumberFormatException e) { System.out.println("Opcion invalida."); return; }
        com.example.tpo.model.tipo.ITipoPenalidad motivo = switch (mot) {
            case 1 -> new com.example.tpo.model.tipo.PToxicidad();
            case 2 -> new com.example.tpo.model.tipo.PNoShow();
            case 3 -> new com.example.tpo.model.tipo.PAbandono();
            case 4 -> new com.example.tpo.model.tipo.PFraude();
            default -> null;
        };
        if (motivo == null) { System.out.println("Motivo invalido."); return; }
        System.out.print("Descripcion (opcional): ");
        String desc = scanner.nextLine();
        ReporteConducta rep = new ReporteConducta();
        rep.setReportante(usuarioActual);
        rep.setDenunciado(denunciado);
        rep.setMotivo(motivo);
        rep.setDescripcion(desc);
        rep.setScrim(s);
        rep.setFecha(java.time.Instant.now());
        // Guardar en repositorio para moderacion
        ReportRepository.get().add(rep);
        IManejadorReporte autoMod = new AutoModerador();
        IManejadorReporte botMod = new BotModerador();
        IManejadorReporte adminMod = new AdministradorFinal();
        autoMod.setSiguiente(botMod);
        botMod.setSiguiente(adminMod);
        autoMod.manejar(rep);
        System.out.println("Reporte enviado. Gracias por colaborar.");
    }
}
