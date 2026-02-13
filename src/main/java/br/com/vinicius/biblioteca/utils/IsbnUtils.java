package br.com.vinicius.biblioteca.utils;

public final class IsbnUtils {

    private IsbnUtils() {}

    public static String normalizar(String isbn) {
        if (isbn == null) {
            throw new IllegalArgumentException("ISBN não pode ser nulo.");
        }
        return isbn.replaceAll("[^0-9]","");
    }

}
