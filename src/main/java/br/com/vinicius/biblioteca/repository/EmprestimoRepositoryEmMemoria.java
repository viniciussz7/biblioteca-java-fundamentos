package br.com.vinicius.biblioteca.repository;

import br.com.vinicius.biblioteca.model.Emprestimo;
import br.com.vinicius.biblioteca.model.StatusEmprestimo;
import br.com.vinicius.biblioteca.utils.IsbnUtils;

import java.util.*;

public class EmprestimoRepositoryEmMemoria implements EmprestimoRepository{
    private final Map<UUID, Emprestimo> emprestimos = new HashMap<>();

    @Override
    public void salvar(Emprestimo emprestimo) {
        if (emprestimo == null) {
            throw new IllegalArgumentException("Empréstimo não pode ser nulo.");
        }
        if (emprestimos.containsKey(emprestimo.getId())) {
            throw new IllegalArgumentException("Já existe um empréstimo com este ID.");
        }
        emprestimos.put(emprestimo.getId(), emprestimo);
    }

    @Override
    public Optional<Emprestimo> buscarPorId(UUID id) {
        return Optional.ofNullable(emprestimos.get(id));
    }

    @Override
    public List<Emprestimo> buscarPorIsbn(String isbn) {
        String isbnNormalizado = IsbnUtils.normalizar(isbn);
        return emprestimos.values()
                .stream()
                .filter(e -> e.getIsbn().equals(isbnNormalizado))
                .toList();
    }

    @Override
    public List<Emprestimo> buscarEmprestimoPorUsuario(String nomeUsuario) {
        return emprestimos.values()
                .stream()
                .filter(e -> e.getNomeUsuario().equalsIgnoreCase(nomeUsuario))
                .toList();
    }

    @Override
    public Optional<Emprestimo> buscarEmprestimoAtivoPorIsbn(String isbn) {
        String isbnNormalizado = IsbnUtils.normalizar(isbn);

        return emprestimos.values()
                .stream()
                .filter(e -> e.getIsbn().equals(isbnNormalizado))
                .filter(e -> e.getStatus() == StatusEmprestimo.ATIVO)
                .findFirst();
    }

    @Override
    public List<Emprestimo> listarTodosAtivos() {
        return emprestimos.values()
                .stream()
                .filter(e -> e.getStatus() == StatusEmprestimo.ATIVO)
                .toList();
    }

    @Override
    public List<Emprestimo> listarTodos() {
        return new ArrayList<>(emprestimos.values());
    }
}
