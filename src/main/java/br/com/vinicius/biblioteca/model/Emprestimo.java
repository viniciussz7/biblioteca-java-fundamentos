package br.com.vinicius.biblioteca.model;

import br.com.vinicius.biblioteca.utils.IsbnUtils;

import java.time.LocalDate;
import java.util.UUID;

public class Emprestimo {
    private final UUID id;
    private final String isbn;
    private final String nomeUsuario;
    private final LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;
    private StatusEmprestimo status;

    public Emprestimo(String isbn, String nomeUsuario) {
        String isbnNormalizado = IsbnUtils.normalizar(isbn);

        IsbnUtils.validar(isbnNormalizado);
        validarNomeUsuario(nomeUsuario);

        this.id = UUID.randomUUID();
        this.isbn = isbnNormalizado;
        this.nomeUsuario = nomeUsuario;
        this.dataEmprestimo = LocalDate.now();
        this.dataDevolucao = null;
        this.status = StatusEmprestimo.ATIVO;
    }

    private void validarNomeUsuario(String nomeUsuario) {
        if (nomeUsuario == null || nomeUsuario.isBlank())
            throw new IllegalArgumentException("Nome do usuário vazio ou nulo.");
    }

    public void finalizar() {
        if (!status.equals(StatusEmprestimo.ATIVO)) {
            throw new IllegalStateException("Impossível finalizar empréstimo que não está ativo.");
        }
        this.dataDevolucao = LocalDate.now();
        this.status = StatusEmprestimo.FINALIZADO;
    }

    public UUID getId() {
        return id;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public StatusEmprestimo getStatus() {
        return status;
    }
}
