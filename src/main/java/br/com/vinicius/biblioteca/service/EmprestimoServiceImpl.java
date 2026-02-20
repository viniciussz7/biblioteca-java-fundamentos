package br.com.vinicius.biblioteca.service;

import br.com.vinicius.biblioteca.exception.LivroJaEmprestadoException;
import br.com.vinicius.biblioteca.exception.LivroNaoEmprestadoException;
import br.com.vinicius.biblioteca.exception.LivroNaoEncontradoException;
import br.com.vinicius.biblioteca.model.Emprestimo;
import br.com.vinicius.biblioteca.model.Livro;
import br.com.vinicius.biblioteca.repository.EmprestimoRepository;
import br.com.vinicius.biblioteca.repository.LivroRepository;
import br.com.vinicius.biblioteca.utils.IsbnUtils;

import java.util.List;
import java.util.Optional;

public class EmprestimoServiceImpl implements EmprestimoService{
    private final EmprestimoRepository emprestimoRepository;
    private final LivroRepository livroRepository;

    public EmprestimoServiceImpl(EmprestimoRepository emprestimoRepository, LivroRepository livroRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.livroRepository = livroRepository;
    }


    @Override
    public void registrarEmprestimo(String isbn, String nomeUsuario) {
        String isbnNormalizado = IsbnUtils.normalizar(isbn);
        Livro livro = livroRepository.buscarPorIsbn(isbnNormalizado)
                .orElseThrow(() ->
                        new LivroNaoEncontradoException("Livro não encontrado."));

        if (emprestimoRepository.buscarEmprestimoAtivoPorIsbn(isbnNormalizado).isPresent()) {
            throw new LivroJaEmprestadoException("Livro já está emprestado!");
        }

        livro.emprestar();
        Emprestimo emprestimo = new Emprestimo(isbnNormalizado, nomeUsuario);
        emprestimoRepository.salvar(emprestimo);
    }

    @Override
    public void registrarDevolucao(String isbn) {
        String isbnNormalizado = IsbnUtils.normalizar(isbn);
        Livro livro = livroRepository.buscarPorIsbn(isbnNormalizado)
                .orElseThrow(() ->
                        new LivroNaoEncontradoException("Livro não encontrado."));
        Emprestimo emprestimo = emprestimoRepository.buscarEmprestimoAtivoPorIsbn(isbnNormalizado)
                .orElseThrow(() ->
                        new LivroNaoEmprestadoException("Livro ja está disponível!"));
        emprestimo.finalizar();
        livro.devolver();
    }

    @Override
    public List<Emprestimo> buscarEmprestimosAtivos() {
        return emprestimoRepository.listarTodosAtivos();
    }

    @Override
    public List<Emprestimo> buscarEmprestimosPorLivro(String isbn) {
        String isbnNormalizado = IsbnUtils.normalizar(isbn);
        return emprestimoRepository.buscarPorIsbn(isbnNormalizado);
    }

    @Override
    public Optional<Emprestimo> buscarEmprestimoAtivoPorIsbn(String isbn) {
        String isbnNormalizado = IsbnUtils.normalizar(isbn);
        return emprestimoRepository.buscarEmprestimoAtivoPorIsbn(isbnNormalizado);
    }

    @Override
    public List<Emprestimo> buscarEmprestimosPorUsuario(String nomeUsuario) {
        return emprestimoRepository.buscarEmprestimoPorUsuario(nomeUsuario);
    }
}
