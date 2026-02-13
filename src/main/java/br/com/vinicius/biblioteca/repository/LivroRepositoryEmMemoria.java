package br.com.vinicius.biblioteca.repository;

import br.com.vinicius.biblioteca.model.Livro;
import br.com.vinicius.biblioteca.utils.IsbnUtils;

import java.util.*;

public class LivroRepositoryEmMemoria implements LivroRepository {
    private final Map<String, Livro> livros = new HashMap<>();

    @Override
    public void salvar(Livro livro) {
        String isbnNormalizado = livro.getIsbn();
        if (livros.containsKey(isbnNormalizado)) {
            throw new IllegalArgumentException("Já existe um livro com este ISBN.");
        }
        livros.put(isbnNormalizado, livro);
    }

    @Override
    public Optional<Livro> buscarPorIsbn(String isbn) {
        String isbnNormalizado = IsbnUtils.normalizar(isbn);
        return Optional.ofNullable(livros.get(isbnNormalizado));
    }

    @Override
    public List<Livro> listarTodos() {
        return new ArrayList<>(livros.values());
    }

    @Override
    public void remover(String isbn) {
        String isbnNormalizado = IsbnUtils.normalizar(isbn);
        Livro removido = livros.remove(isbnNormalizado);
        if (removido == null) {
            throw new IllegalArgumentException("Não existe livro com este ISBN para remoção.");
        }
    }
}
