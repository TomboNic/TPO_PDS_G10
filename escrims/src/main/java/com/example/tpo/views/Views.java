package com.example.tpo.views;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Small helper to log categorized messages to views/*.log
 * while still printing them (ConsoleTee should already tee System.out).
 */
public final class Views {
    private static final File dir = new File("views");

    private Views() {}

    private static void append(File target, String message) {
        try {
            if (!dir.exists()) dir.mkdirs();
            try (FileOutputStream fos = new FileOutputStream(target, true)) {
                fos.write((message + System.lineSeparator()).getBytes(StandardCharsets.UTF_8));
                fos.flush();
            }
        } catch (IOException ignored) {
        }
    }

    public static void estado(String message) {
        System.out.println(message);
        append(new File(dir, "estado.log"), message);
    }

    public static void rosters(String message) {
        System.out.println(message);
        append(new File(dir, "rosters.log"), message);
    }

    public static void notify(String message) {
        System.out.println(message);
        append(new File(dir, "notifications.log"), message);
    }
}

