package com.example.tpo.views;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public final class ViewFiles {
    private static final String DIR = "views";

    private ViewFiles() {}

    private static File ensureDir() {
        File dir = new File(DIR);
        if (!dir.exists()) dir.mkdirs();
        return dir;
    }

    public static void println(String viewName, String line) {
        ensureDir();
        File f = new File(DIR, viewName + ".log");
        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(f, true), StandardCharsets.UTF_8))) {
            pw.println(line);
        } catch (IOException ignored) {}
        System.out.println(line);
    }

    public static PrintWriter openWriter(String viewName, boolean append) throws IOException {
        ensureDir();
        File f = new File(DIR, viewName + ".log");
        return new PrintWriter(new OutputStreamWriter(new FileOutputStream(f, append), StandardCharsets.UTF_8));
    }
}

