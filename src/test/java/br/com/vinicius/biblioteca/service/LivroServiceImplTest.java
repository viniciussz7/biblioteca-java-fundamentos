package br.com.vinicius.biblioteca.service;

import br.com.vinicius.biblioteca.model.Livro;
import br.com.vinicius.biblioteca.model.StatusLivro;
import br.com.vinicius.biblioteca.repository.LivroRepositoryEmMemoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LivroServiceImplTest {

    private LivroRepositoryEmMemoria repositoryEmMemoria;
    private LivroServiceImpl livroServiceImpl;

    private Livro livroValido;

    @BeforeEach
    void setUp() {
        repositoryEmMemoria = new LivroRepositoryEmMemoria();
        livroServiceImpl = new LivroServiceImpl(repositoryEmMemoria);

        livroValido = new Livro(
                "Clean Code",
                "Robert C. Martin",
                2008,
                "9780132350884"
        );
    }

    @Test
    void deveCadastrarLivroComSucesso() {
        livroServiceImpl.cadastrarLivro(livroValido);
        Livro livroEncontrado = livroServiceImpl.buscarPorIsbn("9780132350884");
        assertNotNull(livroEncontrado);
    }

    @Test
    void deveLancarExcecaoAoCadastrarLivroNulo() {
        assertThrows(IllegalArgumentException.class, () ->
                livroServiceImpl.cadastrarLivro(null)
        );
        assertTrue(livroServiceImpl.listarTodos().isEmpty());
    }

    @Test
    void deveLancarExcecaoAoCadastrarLivroComIsbnDuplicado() {
        livroServiceImpl.cadastrarLivro(livroValido);
        Livro outroLivroValidoComMesmoIsbn = new Livro(
                "Clean Code 2.0",
                "Robert C. Martin",
                2012,
                "9780132350884"
        );

        assertThrows(IllegalArgumentException.class, () -> {
           livroServiceImpl.cadastrarLivro(outroLivroValidoComMesmoIsbn);
        });
        assertEquals(1, livroServiceImpl.listarTodos().size());
    }

    @Test
    void deveBuscarLivroPorIsbn() {
        livroServiceImpl.cadastrarLivro(livroValido);
        Livro livroEncontrado = livroServiceImpl.buscarPorIsbn("9780132350884");

        assertNotNull(livroEncontrado);
        assertEquals("9780132350884", livroEncontrado.getIsbn());
    }

    @Test
    void deveLancarExcecaoQuandoLivroNaoEncontradoNaBusca() {
        String isbnInexistente = "0000000000000";

        assertThrows(IllegalArgumentException.class, () -> {
           livroServiceImpl.buscarPorIsbn(isbnInexistente);
        });
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHaLivros() {

        var livros = livroServiceImpl.listarTodos();

        assertNotNull(livros);
        assertTrue(livros.isEmpty());
    }

    @Test
    void deveListarTodosOsLivros() {

        Livro livro1 = new Livro(
                "Clean Code",
                "Robert C. Martin",
                2008,
                "9780132350884"
        );

        Livro livro2 = new Livro(
                "Effective Java",
                "Joshua Bloch",
                2018,
                "9780134685991"
        );

        livroServiceImpl.cadastrarLivro(livro1);
        livroServiceImpl.cadastrarLivro(livro2);

        var livros = livroServiceImpl.listarTodos();

        assertEquals(2, livros.size());
        assertTrue(livros.contains(livro1));
        assertTrue(livros.contains(livro2));
    }

    @Test
    void deveRemoverLivroComSucesso() {
        livroServiceImpl.cadastrarLivro(livroValido);
        Livro livroEncontrado = livroServiceImpl.buscarPorIsbn("9780132350884");
        assertNotNull(livroEncontrado);
        livroServiceImpl.removerLivro("9780132350884");
        assertTrue(livroServiceImpl.listarTodos().isEmpty());
        assertThrows(IllegalArgumentException.class, () ->
                livroServiceImpl.buscarPorIsbn("9780132350884")
        );
    }

    @Test
    void deveLancarExcecaoAoRemoverLivroInexistente() {
        String isbnInexistente = "0000000000000";

        assertThrows(IllegalArgumentException.class, () -> {
            livroServiceImpl.removerLivro(isbnInexistente);
        });
    }

    @Test
    void deveAlterarTituloComSucesso() {
        livroServiceImpl.cadastrarLivro(livroValido);
        String novoTitulo = "Clean Code 2.0";
        livroServiceImpl.alterarTitulo("9780132350884", novoTitulo);
        Livro livroAtualizado = livroServiceImpl.buscarPorIsbn("9780132350884");
        assertEquals(novoTitulo, livroAtualizado.getTitulo());
    }

    @Test
    void deveLancarExcecaoAoAlterarTituloDeLivroInexistente() {
        String isbnInexistente = "0000000000000";
        String novoTitulo = "Clean Code 2.0";

        assertThrows(IllegalArgumentException.class, () -> {
           livroServiceImpl.alterarTitulo(isbnInexistente, novoTitulo);
        });
    }

    @Test
    void deveLancarExcecaoAoAlterarTituloParaValorInvalido() {
        livroServiceImpl.cadastrarLivro(livroValido);
        String tituloVazio = "";
        String tituloNulo = null;

        assertThrows(IllegalArgumentException.class, () -> {
           livroServiceImpl.alterarTitulo("9780132350884", tituloVazio);
        });

        assertThrows(IllegalArgumentException.class, () -> {
           livroServiceImpl.alterarTitulo("9780132350884", tituloNulo);
        });
    }

    @Test
    void deveAlterarAutorComSucesso() {
        livroServiceImpl.cadastrarLivro(livroValido);
        String novoAutor = "Bob Uncle";
        livroServiceImpl.alterarAutor("9780132350884", novoAutor);
        Livro livroAtualizado = livroServiceImpl.buscarPorIsbn("9780132350884");
        assertEquals(novoAutor, livroAtualizado.getAutor());
    }

    @Test
    void deveLancarExcecaoAoAlterarAutorDeLivroInexistente() {
        String isbnInexistente = "0000000000000";
        String novoAutor = "Bob Uncle";

        assertThrows(IllegalArgumentException.class, () -> {
            livroServiceImpl.alterarAutor(isbnInexistente, novoAutor);
        });
    }

    @Test
    void deveLancarExcecaoAoAlterarAutorParaValorInvalido() {
        livroServiceImpl.cadastrarLivro(livroValido);
        String autorVazio = "";
        String autorNulo = null;

        assertThrows(IllegalArgumentException.class, () -> {
            livroServiceImpl.alterarAutor("9780132350884", autorVazio);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            livroServiceImpl.alterarAutor("9780132350884", autorNulo);
        });
    }

    @Test
    void deveAlterarAnoPublicacaoComSucesso() {
        livroServiceImpl.cadastrarLivro(livroValido);
        int novoAno = 2020;
        livroServiceImpl.alterarAnoPublicacao("9780132350884", novoAno);
        Livro livroAtualizado = livroServiceImpl.buscarPorIsbn("9780132350884");
        assertEquals(novoAno, livroAtualizado.getAnoPublicacao());
    }

    @Test
    void deveLancarExcecaoAoAlterarAnoDeLivroInexistente() {
        String isbnInexistente = "0000000000000";
        int novoAno = 2020;

        assertThrows(IllegalArgumentException.class, () -> {
           livroServiceImpl.alterarAnoPublicacao(isbnInexistente, novoAno);
        });
    }

    @Test
    void deveLancarExcecaoAoAlterarAnoParaValorInvalido() {
        livroServiceImpl.cadastrarLivro(livroValido);
        int anoNegativo = -100;
        int anoMaiorQueAtual = 2050;

        assertThrows(IllegalArgumentException.class, () -> {
           livroServiceImpl.alterarAnoPublicacao("9780132350884", anoNegativo);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            livroServiceImpl.alterarAnoPublicacao("9780132350884", anoMaiorQueAtual);
        });
    }

    @Test
    void deveEmprestarComSucesso() {
        livroServiceImpl.cadastrarLivro(livroValido);
        livroServiceImpl.emprestar("9780132350884");
        Livro livroAtualizado = livroServiceImpl.buscarPorIsbn("9780132350884");
        assertEquals(StatusLivro.EMPRESTADO, livroAtualizado.getStatus());
    }

    @Test
    void deveDevolverComSucesso() {
        livroServiceImpl.cadastrarLivro(livroValido);
        livroServiceImpl.emprestar("9780132350884");
        livroServiceImpl.devolver("9780132350884");
        Livro livroAtualizado = livroServiceImpl.buscarPorIsbn("9780132350884");
        assertEquals(StatusLivro.DISPONIVEL, livroAtualizado.getStatus());
    }

    @Test
    void deveLancarExcecaoAoDevolverQuandoJaDisponivel() {
        livroServiceImpl.cadastrarLivro(livroValido);
        assertThrows(IllegalStateException.class, () -> {
           livroServiceImpl.devolver("9780132350884");
        });
    }

    @Test
    void deveLancarExcecaoAoEmprestarQuandoJaIndisponivel() {
        livroServiceImpl.cadastrarLivro(livroValido);
        livroServiceImpl.emprestar("9780132350884");
        assertThrows(IllegalStateException.class, () -> {
           livroServiceImpl.emprestar("9780132350884");
        });
    }

    @Test
    void deveLancarExcecaoAoAlterarDisponibilidadeDeLivroInexistente() {
        assertThrows(IllegalArgumentException.class, () -> {
           livroServiceImpl.emprestar("9780132350884");
        });
    }



}
