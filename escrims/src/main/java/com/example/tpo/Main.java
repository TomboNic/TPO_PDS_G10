package com.example.tpo;

import com.example.tpo.model.aplicacion.ScrimFacade;
import com.example.tpo.model.dominio.Usuario;
import com.example.tpo.model.dominio.Rol;
import com.example.tpo.model.dominio.Resultado;
import com.example.tpo.model.singleton.UsuarioManager;
import com.example.tpo.model.observer.NotificationSubscriber;
import com.example.tpo.model.notificacion.NotificadorFactoryDev;
import com.example.tpo.model.observer.events.ScrimCreated;
import com.example.tpo.model.state.scrim.ScrimContext;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Demo completa ===");

        // Usuarios: registrar y autenticar
        UsuarioManager um = UsuarioManager.getInstancia();
        Usuario u1 = um.registrar("Ana", "Pérez", "anap", "ana@test.com", "1234");
        Usuario u2 = um.registrar("Ben", "García", "bengar", "ben@test.com", "abcd");
        Usuario u3 = um.registrar("Caro", "López", "carol", "caro@test.com", "pass");
        System.out.println("Usuarios registrados: " + um.listar().size());
        System.out.println("Estado mail u1: " + (u1.getEstadoMail() != null ? u1.getEstadoMail().getClass().getSimpleName() : "null"));

        Usuario loginOk = um.autenticar("ana@test.com", "1234");
        Usuario loginFail = um.autenticar("ana@test.com", "wrong");
        System.out.println("Login OK? " + (loginOk != null) + ", Login Fail? " + (loginFail == null));

        // Scrim: crear, postular, confirmar, iniciar, finalizar
        ScrimFacade facade = new ScrimFacade();
        // Suscribir notificaciones a eventos de dominio
        facade.subscribe(new NotificationSubscriber(new NotificadorFactoryDev()));

        int scrimId = facade.crearScrim();
        System.out.println("Scrim creado con id: " + scrimId);

        // Estado inicial
        printEstado(facade.getContext(scrimId));

        facade.postular(scrimId, u1, new Rol());
        facade.postular(scrimId, u2, new Rol());
        // Un tercero va a suplentes porque hay cupo 1 por lado por defecto
        facade.postular(scrimId, u3, new Rol());

        System.out.println("Postulados equipo1: " + facade.getScrim(scrimId).getEquipo1().size()
                + ", equipo2: " + facade.getScrim(scrimId).getEquipo2().size());
        System.out.println("Suplentes: " + facade.getScrim(scrimId).getSuplentes().size());
        printRosters(facade, scrimId);
        printEstado(facade.getContext(scrimId));

        facade.armarLobby(scrimId); // opcional, la transición también ocurre al llenar cupos
        printEstado(facade.getContext(scrimId));

        facade.confirmar(scrimId, u1);
        printEstado(facade.getContext(scrimId));

        facade.iniciar(scrimId);
        printEstado(facade.getContext(scrimId));

        facade.finalizar(scrimId, new Resultado());
        printEstado(facade.getContext(scrimId));
        System.out.println("Flujo de scrim ejecutado (crear->postular->confirmar->iniciar->finalizar)");

        // Notificaciones: suscriptor simple (simulado)
        NotificationSubscriber sub = new NotificationSubscriber(new NotificadorFactoryDev());
        sub.onEvent(new ScrimCreated());
        System.out.println("Evento ScrimCreated notificado (simulado)");
    }

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

    private static String safeName(Usuario u) { return u == null ? "?" : (u.getNombreUsuario() != null ? u.getNombreUsuario() : u.toString()); }
}
