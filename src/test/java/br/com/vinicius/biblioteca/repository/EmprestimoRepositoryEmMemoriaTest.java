package br.com.vinicius.biblioteca.repository;

import br.com.vinicius.biblioteca.model.Emprestimo;
import br.com.vinicius.biblioteca.model.StatusEmprestimo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class EmprestimoRepositoryEmMemoriaTest {
    private EmprestimoRepositoryEmMemoria emprestimoRepository;

    @BeforeEach
    void setUp() {
        emprestimoRepository = new EmprestimoRepositoryEmMemoria();
    }

    @Test
    void deveSalvarEmprestimo() {
        String isbn = "9780132350884";
        String usuario = "Vinícius";

        Emprestimo emprestimo = new Emprestimo(isbn, usuario);
        emprestimoRepository.salvar(emprestimo);
        List<Emprestimo> historico = emprestimoRepository.buscarPorIsbn(isbn);

        assertEquals(1, historico.size());
        assertEquals(emprestimo.getId(), historico.getFirst().getId());
    }

    @Test
    void deveLancarExcecaoAoSalvarEmprestimoNulo() {
        assertThrows(IllegalArgumentException.class, () -> {
           emprestimoRepository.salvar(null);
        });
    }

    @Test
    void deveBuscarEmprestimoPorIsbn() {
        String isbn = "9780132350884";
        String usuario1 = "Vinícius";
        String usuario2 = "Lua";

        Emprestimo emprestimo = new Emprestimo(isbn, usuario1);
        emprestimoRepository.salvar(emprestimo);
        emprestimo.finalizar();
        Emprestimo emprestimo1 = new Emprestimo(isbn, usuario2);
        emprestimoRepository.salvar(emprestimo1);
        List<Emprestimo> historico = emprestimoRepository.buscarPorIsbn(isbn);

        assertEquals(2, historico.size());
        assertTrue(historico.stream()
                .anyMatch(e -> e.getStatus() == StatusEmprestimo.FINALIZADO));
        assertTrue(historico.stream()
                .anyMatch(e -> e.getStatus() == StatusEmprestimo.ATIVO));
    }

    @Test
    void deveBuscarEmprestimoPorIsbnInexistenteERetornarListaVazia() {
        String isbn = "9780132350884";
        String isbnInexistente = "1780132350885";
        String usuario = "Vinícius";

        Emprestimo emprestimo = new Emprestimo(isbn, usuario);
        emprestimoRepository.salvar(emprestimo);
        List<Emprestimo> historico = emprestimoRepository.buscarPorIsbn(isbnInexistente);

        assertEquals(0, historico.size());
    }

    @Test
    void deveBuscarEmprestimoPorNomeUsuario() {
        String isbn1 = "9780132350884";
        String isbn2 = "1780132350885";
        String isbn3 = "1785555351222";
        String usuario = "Vinícius";
        String usuario2 = "Lua";

        Emprestimo emprestimo = new Emprestimo(isbn1, usuario);
        emprestimoRepository.salvar(emprestimo);
        Emprestimo emprestimo1 = new Emprestimo(isbn2, usuario);
        emprestimoRepository.salvar(emprestimo1);
        Emprestimo emprestimo2 = new Emprestimo(isbn3, usuario2);
        emprestimoRepository.salvar(emprestimo2);
        List<Emprestimo> historico = emprestimoRepository.buscarEmprestimoPorUsuario(usuario);

        assertEquals(2, historico.size());
    }

    @Test
    void deveBuscarEmprestimoAtivoPorIsbn() {
        String isbn = "9780132350884";
        String usuario = "Vinícius";

        Emprestimo emprestimo = new Emprestimo(isbn, usuario);
        emprestimoRepository.salvar(emprestimo);
        emprestimo.finalizar();
        Emprestimo emprestimo1 = new Emprestimo(isbn, usuario);
        emprestimoRepository.salvar(emprestimo1);
        Optional<Emprestimo> emprestimoAtivo = emprestimoRepository.buscarEmprestimoAtivoPorIsbn(isbn);

        assertTrue(emprestimoAtivo.isPresent());
    }

    @Test
    void deveRetornarVazioQuandoNaoExistirEmprestimoAtivo() {
        String isbn = "9780132350884";
        String usuario = "Vinícius";

        Emprestimo emprestimo = new Emprestimo(isbn, usuario);
        emprestimoRepository.salvar(emprestimo);
        emprestimo.finalizar();
        Optional<Emprestimo> emprestimoAtivo = emprestimoRepository.buscarEmprestimoAtivoPorIsbn(isbn);

        assertTrue(emprestimoAtivo.isEmpty());
    }

    @Test
    void deveRetornarVazioQuandoNaoExistirEmprestimo() {
        Optional<Emprestimo> ativo = emprestimoRepository.buscarEmprestimoAtivoPorIsbn("9780132350884");
        assertTrue(ativo.isEmpty());
    }

    @Test
    void deveBuscarTodosEmprestimosAtivos() {
        String isbn = "9780132350884";
        String isbn1 = "1780132350885";
        String isbn2 = "1785555351222";
        String usuario = "Vinícius";
        String usuario1 = "Lua";

        Emprestimo emprestimo = new Emprestimo(isbn, usuario);
        emprestimoRepository.salvar(emprestimo);
        Emprestimo emprestimo1 = new Emprestimo(isbn1, usuario1);
        emprestimoRepository.salvar(emprestimo1);
        Emprestimo emprestimo2 = new Emprestimo(isbn2, usuario);
        emprestimoRepository.salvar(emprestimo2);
        emprestimo2.finalizar();
        List<Emprestimo> emprestimosAtivos = emprestimoRepository.listarTodosAtivos();

        assertEquals(2, emprestimosAtivos.size());
    }

    @Test
    void deveBuscarTodosEmprestimos() {
        String isbn = "9780132350884";
        String isbn1 = "1780132350885";
        String isbn2 = "1785555351222";
        String usuario = "Vinícius";
        String usuario1 = "Lua";

        Emprestimo emprestimo = new Emprestimo(isbn, usuario);
        emprestimoRepository.salvar(emprestimo);
        Emprestimo emprestimo1 = new Emprestimo(isbn1, usuario1);
        emprestimoRepository.salvar(emprestimo1);
        Emprestimo emprestimo2 = new Emprestimo(isbn2, usuario);
        emprestimoRepository.salvar(emprestimo2);
        emprestimo.finalizar();
        List<Emprestimo> emprestimos = emprestimoRepository.listarTodos();

        assertEquals(3, emprestimos.size());
    }
}
