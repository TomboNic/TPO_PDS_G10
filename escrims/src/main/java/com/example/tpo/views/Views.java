package com.example.tpo.views;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Small helper to log categorized messages to views/*.log
 * while still printing them (ConsoleTee should already tee System.out).
 * Adds light formatting to improve readability in the console.
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
        UI.title("Estado");
        System.out.println(message);
        System.out.println("+--------------------------------------------------------------+");
        append(new File(dir, "estado.log"), message);
    }

    // Buffers to render rosters in a compact table when the three lines arrive
    private static List<String> lastEquipo1 = null;
    private static List<String> lastEquipo2 = null;
    private static List<String> lastSuplentes = null;

    public static void rosters(String message) {
        // Always log raw line
        append(new File(dir, "rosters.log"), message);

        String m = message == null ? "" : message;
        String lower = m.toLowerCase();
        if (lower.startsWith("equipo1:")) {
            lastEquipo1 = parseList(m);
            maybeRenderRosters();
            return;
        }
        if (lower.startsWith("equipo2:")) {
            lastEquipo2 = parseList(m);
            maybeRenderRosters();
            return;
        }
        if (lower.startsWith("suplentes:")) {
            lastSuplentes = parseList(m);
            maybeRenderRosters();
            return;
        }

        // Fallback: print raw line
        System.out.println(message);
    }

    // Only log the rosters line without rendering to console
    public static void rostersLogOnly(String message) {
        append(new File(dir, "rosters.log"), message);
    }

    private static List<String> parseList(String line) {
        int idx = line.indexOf(':');
        if (idx < 0 || idx + 1 >= line.length()) return new ArrayList<>();
        String rest = line.substring(idx + 1).trim();
        if (rest.isEmpty() || rest.equals("-")) return new ArrayList<>();
        String[] parts = rest.split(",");
        List<String> out = new ArrayList<>();
        for (String p : parts) {
            String t = p.trim();
            if (!t.isEmpty()) out.add(t);
        }
        return out;
    }

    private static void maybeRenderRosters() {
        if (lastEquipo1 != null && lastEquipo2 != null) {
            UI.twoColumns("Rosters", "Equipo 1", lastEquipo1, "Equipo 2", lastEquipo2);
            if (lastSuplentes != null && !lastSuplentes.isEmpty()) {
                UI.note("Suplentes: " + String.join(", ", lastSuplentes));
            }
            // Reset after rendering to avoid duplicate prints
            lastEquipo1 = null;
            lastEquipo2 = null;
            lastSuplentes = null;
        }
    }

    public static void notify(String message) {
        System.out.println("[Notif] " + message);
        append(new File(dir, "notifications.log"), message);
    }
}
