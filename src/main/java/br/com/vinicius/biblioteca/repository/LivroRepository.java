package br.com.vinicius.biblioteca.repository;

import br.com.vinicius.biblioteca.model.Livro;

import java.util.List;
import java.util.Optional;

public interface LivroRepository {
    void salvar(Livro livro);
    Optional<Livro> buscarPorIsbn(String isbn);
    List<Livro> listarTodos();
    void remover(String isbn);
}
