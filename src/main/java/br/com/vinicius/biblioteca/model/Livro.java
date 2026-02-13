package br.com.vinicius.biblioteca.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public final class Livro {
    private final UUID id;
    private String titulo;
    private String autor;
    private int anoPublicacao;
    private final String isbn;
    private boolean disponivel;

    public Livro(String titulo, String autor, int anoPublicacao, String isbn) {
        String isbnNormalizado = normalizarIsbn(isbn);

        validarTitulo(titulo);
        validarAutor(autor);
        validarIsbn(isbnNormalizado);
        validarAnoPublicacao(anoPublicacao);

        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
        this.isbn = isbnNormalizado;
        this.id = UUID.randomUUID();
        this.disponivel = true;
    }

    //----- Métodos de dominio -------
    public void alterarTitulo(String titulo) {
        validarTitulo(titulo);
        this.titulo = titulo;
    }

    public void alterarAutor(String autor) {
        validarAutor(autor);
        this.autor = autor;
    }

    public void alterarAnoPublicacao(int anoPublicacao) {
        validarAnoPublicacao(anoPublicacao);
        this.anoPublicacao = anoPublicacao;
    }

    public void marcarComoDisponivel() {
        if (this.disponivel) {
            throw new IllegalArgumentException("Livro já está disponível.");
        }
        this.disponivel = true;
    }

    public void marcarComoIndisponivel() {
        if (!this.disponivel) {
            throw new IllegalArgumentException("Livro já está indisponível.");
        }
        this.disponivel = false;
    }

    //----- Métodos de validação
    private void validarTitulo(String titulo) {
        if (titulo == null || titulo.isBlank())
            throw new IllegalArgumentException("Título do livro vazio ou nulo.");
    }

    private void validarAutor(String autor) {
        if (autor == null || autor.isBlank())
            throw new IllegalArgumentException("Autor do livro vazio ou nulo.");
    }

    private String normalizarIsbn(String isbn) {
        if (isbn == null) {
            throw new IllegalArgumentException("ISBN não pode ser nulo.");
        }
        return isbn.replaceAll("[^0-9]","");
    }

    private void validarIsbn(String isbn) {
        if (isbn == null || isbn.isBlank())
            throw new IllegalArgumentException("isbn do livro vazio ou nulo.");
        if (!isbn.matches("\\d{13}")) {
            throw new IllegalArgumentException("ISBN deve conter exatamente 13 dígitos numéricos.");
        }
    }

    private void validarAnoPublicacao(int anoPublicacao) {
        if (anoPublicacao <= 0 || anoPublicacao > LocalDate.now().getYear())
            throw new IllegalArgumentException("Ano negativo ou zero ou maior que o ano atual.");
    }

    //----- Getters
    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public int getAnoPublicacao() {
        return anoPublicacao;
    }

    public String getIsbn() {
        return isbn;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass()) return false;

        Livro livro = (Livro) o;
        return isbn.equals(livro.isbn);
    }

    @Override
    public int hashCode() {
        return isbn.hashCode();
    }
}
