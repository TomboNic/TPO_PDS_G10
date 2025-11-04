package com.example.tpo.views;

import java.util.ArrayList;
import java.util.List;

public final class UI {
    private UI() {}

    private static final int DEFAULT_WIDTH = 64;

    private static String repeat(char c, int n) {
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) sb.append(c);
        return sb.toString();
    }

    private static String pad(String s, int width) {
        if (s == null) s = "";
        if (s.length() >= width) return s.substring(0, width);
        return s + repeat(' ', width - s.length());
    }

    private static String center(String s, int width) {
        if (s == null) s = "";
        if (s.length() >= width) return s.substring(0, width);
        int total = width - s.length();
        int left = total / 2;
        int right = total - left;
        return repeat(' ', left) + s + repeat(' ', right);
    }

    public static void title(String text) {
        boxTitle(text, DEFAULT_WIDTH);
    }

    public static void boxTitle(String text, int width) {
        int inner = Math.max(10, width - 2);
        System.out.println("+" + repeat('-', inner) + "+");
        System.out.println("|" + center(text, inner) + "|");
        System.out.println("+" + repeat('-', inner) + "+");
    }

    public static void menu(String title, List<String> options) {
        menu(title, options, DEFAULT_WIDTH);
    }

    public static void menu(String title, List<String> options, int width) {
        int inner = Math.max(20, width - 2);
        System.out.println("+" + repeat('=', inner) + "+");
        System.out.println("|" + center(title, inner) + "|");
        System.out.println("+" + repeat('-', inner) + "+");
        int idx = 1;
        for (String opt : options) {
            String line = String.format(" %2d) %s", idx++, opt);
            System.out.println("|" + pad(line, inner) + "|");
        }
        System.out.println("+" + repeat('=', inner) + "+");
    }

    public static void note(String text) {
        System.out.println("[.] " + text);
    }

    public static void ok(String text) {
        System.out.println("[OK] " + text);
    }

    public static void warn(String text) {
        System.out.println("[!] " + text);
    }

    public static void error(String text) {
        System.out.println("[X] " + text);
    }

    public static void twoColumns(String title, String leftHeader, List<String> left, String rightHeader, List<String> right) {
        int width = DEFAULT_WIDTH;
        int inner = width - 2;
        int colPad = 3; // spaces between columns and borders
        int colWidth = (inner - colPad - colPad) / 2;

        // Ensure lists are not null
        if (left == null) left = new ArrayList<>();
        if (right == null) right = new ArrayList<>();

        int rows = Math.max(left.size(), right.size());

        System.out.println("+" + repeat('=', inner) + "+");
        System.out.println("|" + center(title, inner) + "|");
        System.out.println("+" + repeat('-', inner) + "+");

        String header = pad(leftHeader, colWidth) + repeat(' ', colPad) + pad(rightHeader, colWidth);
        System.out.println("|" + pad(header, inner) + "|");
        System.out.println("+" + repeat('-', inner) + "+");

        for (int i = 0; i < rows; i++) {
            String l = i < left.size() ? left.get(i) : "";
            String r = i < right.size() ? right.get(i) : "";
            String line = pad(l, colWidth) + repeat(' ', colPad) + pad(r, colWidth);
            System.out.println("|" + pad(line, inner) + "|");
        }

        System.out.println("+" + repeat('=', inner) + "+");
    }

    // Simple helpers for validations and prompts (optional usage)
    public static boolean isValidEmail(String email) {
        if (email == null) return false;
        String e = email.trim();
        if (e.isEmpty()) return false;
        int at = e.indexOf('@');
        int dot = e.lastIndexOf('.');
        return at > 0 && dot > at + 1 && dot < e.length() - 1;
    }
}
