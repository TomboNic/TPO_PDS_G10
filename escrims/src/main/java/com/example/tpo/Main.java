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
import com.example.tpo.views.ConsoleTee;
import com.example.tpo.views.Views;

public class Main {
    public static void main(String[] args) {
        com.example.tpo.views.ConsoleTee.install();
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        var um = com.example.tpo.model.singleton.UsuarioManager.getInstancia();
        var facade = new com.example.tpo.model.aplicacion.ScrimFacade();
        var notificador = new com.example.tpo.model.observer.NotificationSubscriber(new com.example.tpo.model.notificacion.NotificadorFactoryDev());
        notificador.setFacade(facade);
        facade.subscribe(notificador);
        new com.example.tpo.controller.AppController(scanner, um, facade, notificador).start();
    }
}
