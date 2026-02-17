package br.com.vinicius.biblioteca.repository;

import br.com.vinicius.biblioteca.model.Emprestimo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmprestimoRepository {
    void salvar(Emprestimo emprestimo);
    Optional<Emprestimo> buscarPorId(UUID id);
    List<Emprestimo> buscarPorIsbn(String isbn);
    List<Emprestimo> buscarEmprestimoPorUsuario(String nomeUsuario);
    Optional<Emprestimo> buscarEmprestimoAtivoPorIsbn(String isbn);
    List<Emprestimo> listarTodosAtivos();
    List<Emprestimo> listarTodos();
}
