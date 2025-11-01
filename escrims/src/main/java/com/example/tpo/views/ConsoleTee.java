package com.example.tpo.views;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/**
 * Installs a tee on System.out that also appends to views/main.log
 * while preserving console output.
 */
public final class ConsoleTee {
    private ConsoleTee() {}

    public static void install() {
        try {
            File dir = new File("views");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File mainLog = new File(dir, "main.log");
            OutputStream fileOut = new FileOutputStream(mainLog, true);
            PrintStream tee = new TeePrintStream(System.out, fileOut);
            System.setOut(tee);
        } catch (IOException ex) {
            // If tee setup fails, keep default System.out
        }
    }

    private static final class TeePrintStream extends PrintStream {
        private final PrintStream console;
        private final OutputStream file;

        TeePrintStream(PrintStream console, OutputStream file) {
            super(console, true, StandardCharsets.UTF_8);
            this.console = console;
            this.file = file;
        }

        @Override
        public void println(String x) {
            try {
                // Write to console
                console.println(x);
                // Write to file (append newline)
                String line = x + System.lineSeparator();
                file.write(line.getBytes(StandardCharsets.UTF_8));
                file.flush();
            } catch (IOException e) {
                // Fallback to console only
                console.println(x);
            }
        }

        @Override
        public void print(String s) {
            try {
                console.print(s);
                file.write(s.getBytes(StandardCharsets.UTF_8));
                file.flush();
            } catch (IOException e) {
                console.print(s);
            }
        }

        @Override
        public void println() {
            println("");
        }
    }
}

