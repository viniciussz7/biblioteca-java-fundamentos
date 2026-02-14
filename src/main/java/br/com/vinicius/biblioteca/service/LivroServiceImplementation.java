package br.com.vinicius.biblioteca.service;

import br.com.vinicius.biblioteca.model.Livro;
import br.com.vinicius.biblioteca.repository.LivroRepository;

import java.util.List;

public class LivroServiceImplementation implements LivroService {

    private final LivroRepository repository;

    public LivroServiceImplementation(LivroRepository repository) {
        this.repository = repository;
    }

    // ========================
    // Cadastro
    // ========================
    @Override
    public void cadastrarLivro(Livro livro) {
        if (livro == null) {
            throw new IllegalArgumentException("Livro não pode ser nulo.");
        }
        repository.salvar(livro);
    }

    @Override
    public Livro buscarPorIsbn(String isbn) {
        return repository.buscarPorIsbn(isbn)
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado."));
    }

    @Override
    public List<Livro> listarTodos() {
        return repository.listarTodos();
    }

    @Override
    public void removerLivro(String isbn) {
        repository.remover(isbn);

    }

    @Override
    public void alterarTitulo(String isbn, String novoTitulo) {
        Livro livro = obterLivroOuLancarExcecao(isbn);
        livro.alterarTitulo(novoTitulo);
    }

    @Override
    public void alterarAutor(String isbn, String novoAutor) {
        Livro livro = obterLivroOuLancarExcecao(isbn);
        livro.alterarAutor(novoAutor);
    }

    @Override
    public void alterarAnoPublicacao(String isbn, int novoAno) {
        Livro livro = obterLivroOuLancarExcecao(isbn);
        livro.alterarAnoPublicacao(novoAno);
    }

    @Override
    public void marcarComoDisponivel(String isbn) {
        obterLivroOuLancarExcecao(isbn).marcarComoDisponivel();
    }

    @Override
    public void marcarComoIndisponivel(String isbn) {
        obterLivroOuLancarExcecao(isbn).marcarComoIndisponivel();
    }

    private Livro obterLivroOuLancarExcecao(String isbn) {
        return repository.buscarPorIsbn(isbn)
                .orElseThrow(() ->
                        new IllegalArgumentException("Livro não encontrado."));
    }
}
