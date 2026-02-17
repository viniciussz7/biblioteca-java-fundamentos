package br.com.vinicius.biblioteca.service;

import br.com.vinicius.biblioteca.model.Emprestimo;

import java.util.List;
import java.util.Optional;

public interface EmprestimoService {
    void registrarEmprestimo(String isbn, String nomeUsuario);
    void registrarDevolucao(String isbn);
    List<Emprestimo> buscarEmprestimosAtivos();
    List<Emprestimo> buscarEmprestimosPorLivro(String isbn);
    Optional<Emprestimo> buscarEmprestimoAtivoPorIsbn(String isbn);
    List<Emprestimo> buscarEmprestimosPorUsuario(String nomeUsuario);
}
