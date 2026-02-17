package br.com.vinicius.biblioteca.service;

import br.com.vinicius.biblioteca.exception.LivroJaEmprestadoException;
import br.com.vinicius.biblioteca.exception.LivroNaoEmprestadoException;
import br.com.vinicius.biblioteca.exception.LivroNaoEncontradoException;
import br.com.vinicius.biblioteca.model.Emprestimo;
import br.com.vinicius.biblioteca.model.Livro;

import br.com.vinicius.biblioteca.model.StatusEmprestimo;
import br.com.vinicius.biblioteca.model.StatusLivro;
import br.com.vinicius.biblioteca.repository.EmprestimoRepositoryEmMemoria;
import br.com.vinicius.biblioteca.repository.LivroRepositoryEmMemoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class EmprestimoServiceImplTest {

    private LivroRepositoryEmMemoria livroRepository;
    private EmprestimoRepositoryEmMemoria emprestimoRepository;
    private EmprestimoServiceImpl emprestimoService;

    private Livro livroValido;
    private Emprestimo emprestimoValido;

    @BeforeEach
    void setUp() {
        emprestimoRepository = new EmprestimoRepositoryEmMemoria();
        livroRepository = new LivroRepositoryEmMemoria();
        emprestimoService = new EmprestimoServiceImpl(emprestimoRepository, livroRepository);
    }

    @Test
    void deveRegistrarEmprestimoQuandoLivroExisteEEstaDisponivel() {
        Livro livro = new Livro(
                "Clean Code",
                "Robert C. Martin",
                2008,
                "9780132350884"
        );
        livroRepository.salvar(livro);
        Optional<Livro> livroRetornado = livroRepository.buscarPorIsbn("9780132350884");

        assertTrue(livroRetornado.isPresent());
        assertEquals(StatusLivro.DISPONIVEL, livroRetornado.get().getStatus());

        emprestimoService.registrarEmprestimo("9780132350884", "Vinícius");

        Optional<Emprestimo> emprestimoRetornado =  emprestimoService.buscarEmprestimoAtivoPorIsbn("9780132350884");
        Livro livroAtualizado = livroRepository.buscarPorIsbn("9780132350884").orElseThrow();

        assertEquals(StatusLivro.EMPRESTADO, livroAtualizado.getStatus());
        assertTrue(emprestimoRetornado.isPresent());
        assertEquals(StatusEmprestimo.ATIVO, emprestimoRetornado.get().getStatus());
        assertEquals("Vinícius", emprestimoRetornado.get().getNomeUsuario());
        assertEquals("9780132350884", emprestimoRetornado.get().getIsbn());
    }

    @Test
    void deveLancarExcecaoQuandoLivroJaEstaEmprestado() {
        Livro livro = new Livro(
                "Clean Code",
                "Robert C. Martin",
                2008,
                "9780132350884"
        );
        livroRepository.salvar(livro);
        emprestimoService.registrarEmprestimo("9780132350884", "Vinícius");
        Livro livroAtualizado = livroRepository.buscarPorIsbn("9780132350884").orElseThrow();

        assertThrows(LivroJaEmprestadoException.class, () -> {
            emprestimoService.registrarEmprestimo("9780132350884", "Lua");
        });
        assertEquals(StatusLivro.EMPRESTADO, livroAtualizado.getStatus());
        List<Emprestimo> emprestimos = emprestimoService.buscarEmprestimosPorLivro("9780132350884");
        assertEquals(1, emprestimos.size());
    }

    @Test
    void deveLancarExcecaoQuandoLivroNaoExiste() {
        assertThrows(LivroNaoEncontradoException.class, () -> {
            emprestimoService.registrarEmprestimo("9780132350884", "Lua");
        });
        List<Emprestimo> emprestimos = emprestimoService.buscarEmprestimosPorLivro("9780132350884");

        assertTrue(emprestimos.isEmpty());
    }

    @Test
    void deveRegistrarDevolucaoQuandoLivroEstaEmprestado() {
        Livro livro = new Livro(
                "Clean Code",
                "Robert C. Martin",
                2008,
                "9780132350884"
        );
        livroRepository.salvar(livro);
        emprestimoService.registrarEmprestimo("9780132350884", "Vinícius");
        emprestimoService.registrarDevolucao("9780132350884");
        List<Emprestimo> emprestimos = emprestimoService.buscarEmprestimosPorLivro("9780132350884");
        Optional<Emprestimo> emprestimoAtivo = emprestimoService.buscarEmprestimoAtivoPorIsbn("9780132350884");
        Optional<Livro> livroAtualizado = livroRepository.buscarPorIsbn("9780132350884");

        assertTrue(emprestimoAtivo.isEmpty());
        assertEquals(1, emprestimos.size());
        assertEquals(StatusEmprestimo.FINALIZADO, emprestimos.getFirst().getStatus());
        assertTrue(livroAtualizado.isPresent());
        assertEquals(StatusLivro.DISPONIVEL, livroAtualizado.get().getStatus());
    }

    @Test
    void deveLancarExcecaoQuandoLivroNaoEstaEmprestadoAoDevolver() {
        Livro livro = new Livro(
                "Clean Code",
                "Robert C. Martin",
                2008,
                "9780132350884"
        );
        livroRepository.salvar(livro);
        emprestimoService.registrarEmprestimo("9780132350884", "Vinícius");
        emprestimoService.registrarDevolucao("9780132350884");
        Optional<Livro> livroAtualizado = livroRepository.buscarPorIsbn("9780132350884");

        assertEquals(StatusLivro.DISPONIVEL, livroAtualizado.get().getStatus());
        assertThrows(LivroNaoEmprestadoException.class, () -> {
           emprestimoService.registrarDevolucao("9780132350884");
        });
    }

    @Test
    void deveLancarExcecaoQuandoLivroNaoExisteAoDevolver() {
        assertThrows(LivroNaoEncontradoException.class, () -> {
            emprestimoService.registrarDevolucao("9780132350884");
        });
    }
}
