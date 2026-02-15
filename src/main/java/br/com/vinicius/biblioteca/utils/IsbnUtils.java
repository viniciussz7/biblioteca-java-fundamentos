package br.com.vinicius.biblioteca.utils;

public final class IsbnUtils {

    private IsbnUtils() {}

    public static String normalizar(String isbn) {
        if (isbn == null) {
            throw new IllegalArgumentException("ISBN não pode ser nulo.");
        }
        return isbn.replaceAll("[^0-9]","");
    }

    public static void validar(String isbn) {
        if (isbn == null || isbn.isBlank())
            throw new IllegalArgumentException("isbn do livro vazio ou nulo.");
        if (!isbn.matches("\\d{13}")) {
            throw new IllegalArgumentException("ISBN deve conter exatamente 13 dígitos numéricos.");
        }
    }

}
