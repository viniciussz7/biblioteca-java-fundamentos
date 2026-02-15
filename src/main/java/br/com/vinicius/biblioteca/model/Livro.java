package br.com.vinicius.biblioteca.model;

import br.com.vinicius.biblioteca.utils.IsbnUtils;

import java.time.LocalDate;
import java.util.UUID;

public final class Livro {
    private final UUID id;
    private String titulo;
    private String autor;
    private int anoPublicacao;
    private final String isbn;
    //private boolean disponivel;
    private StatusLivro status;

    public Livro(String titulo, String autor, int anoPublicacao, String isbn) {
        String isbnNormalizado = IsbnUtils.normalizar(isbn);

        IsbnUtils.validar(isbnNormalizado);
        validarTitulo(titulo);
        validarAutor(autor);
        validarAnoPublicacao(anoPublicacao);

        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
        this.isbn = isbnNormalizado;
        this.id = UUID.randomUUID();
        this.status = StatusLivro.DISPONIVEL;
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

    public void emprestar() {
        if (this.status == StatusLivro.EMPRESTADO) {
            throw new IllegalStateException("Livro já está emprestado.");
        }
        this.status = StatusLivro.EMPRESTADO;

    }

    public void devolver() {
        if (this.status == StatusLivro.DISPONIVEL) {
            throw new IllegalStateException("Livro já está disponível.");
        }
        this.status = StatusLivro.DISPONIVEL;
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

    public StatusLivro getStatus() {
        return status;
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
