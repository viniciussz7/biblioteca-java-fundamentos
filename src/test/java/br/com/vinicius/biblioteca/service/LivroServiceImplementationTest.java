package br.com.vinicius.biblioteca.service;

import br.com.vinicius.biblioteca.model.Livro;
import br.com.vinicius.biblioteca.repository.LivroRepositoryEmMemoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LivroServiceImplementationTest {

    private LivroRepositoryEmMemoria repositoryEmMemoria;
    private LivroServiceImplementation livroServiceImplementation;

    private Livro livroValido;

    @BeforeEach
    void setUp() {
        repositoryEmMemoria = new LivroRepositoryEmMemoria();
        livroServiceImplementation = new LivroServiceImplementation(repositoryEmMemoria);

        livroValido = new Livro(
                "Clean Code",
                "Robert C. Martin",
                2008,
                "9780132350884"
        );
    }

    @Test
    void deveCadastrarLivroComSucesso() {
        livroServiceImplementation.cadastrarLivro(livroValido);
        Livro livroEncontrado = livroServiceImplementation.buscarPorIsbn("9780132350884");
        assertNotNull(livroEncontrado);
    }

    @Test
    void deveLancarExcecaoAoCadastrarLivroNulo() {
        assertThrows(IllegalArgumentException.class, () ->
                livroServiceImplementation.cadastrarLivro(null)
        );
        assertTrue(livroServiceImplementation.listarTodos().isEmpty());
    }

    @Test
    void deveLancarExcecaoAoCadastrarLivroComIsbnDuplicado() {
        livroServiceImplementation.cadastrarLivro(livroValido);
        Livro outroLivroValidoComMesmoIsbn = new Livro(
                "Clean Code 2.0",
                "Robert C. Martin",
                2012,
                "9780132350884"
        );

        assertThrows(IllegalArgumentException.class, () -> {
           livroServiceImplementation.cadastrarLivro(outroLivroValidoComMesmoIsbn);
        });
        assertEquals(1, livroServiceImplementation.listarTodos().size());
    }

    @Test
    void deveBuscarLivroPorIsbn() {
        livroServiceImplementation.cadastrarLivro(livroValido);
        Livro livroEncontrado = livroServiceImplementation.buscarPorIsbn("9780132350884");

        assertNotNull(livroEncontrado);
        assertEquals("9780132350884", livroEncontrado.getIsbn());
    }

    @Test
    void deveLancarExcecaoQuandoLivroNaoEncontradoNaBusca() {
        String isbnInexistente = "0000000000000";

        assertThrows(IllegalArgumentException.class, () -> {
           livroServiceImplementation.buscarPorIsbn(isbnInexistente);
        });
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHaLivros() {

        var livros = livroServiceImplementation.listarTodos();

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

        livroServiceImplementation.cadastrarLivro(livro1);
        livroServiceImplementation.cadastrarLivro(livro2);

        var livros = livroServiceImplementation.listarTodos();

        assertEquals(2, livros.size());
        assertTrue(livros.contains(livro1));
        assertTrue(livros.contains(livro2));
    }

    @Test
    void deveRemoverLivroComSucesso() {
        livroServiceImplementation.cadastrarLivro(livroValido);
        Livro livroEncontrado = livroServiceImplementation.buscarPorIsbn("9780132350884");
        assertNotNull(livroEncontrado);
        livroServiceImplementation.removerLivro("9780132350884");
        assertTrue(livroServiceImplementation.listarTodos().isEmpty());
        assertThrows(IllegalArgumentException.class, () ->
                livroServiceImplementation.buscarPorIsbn("9780132350884")
        );
    }

    @Test
    void deveLancarExcecaoAoRemoverLivroInexistente() {
        String isbnInexistente = "0000000000000";

        assertThrows(IllegalArgumentException.class, () -> {
            livroServiceImplementation.removerLivro(isbnInexistente);
        });
    }

    @Test
    void deveAlterarTituloComSucesso() {
        livroServiceImplementation.cadastrarLivro(livroValido);
        String novoTitulo = "Clean Code 2.0";
        livroServiceImplementation.alterarTitulo("9780132350884", novoTitulo);
        Livro livroAtualizado = livroServiceImplementation.buscarPorIsbn("9780132350884");
        assertEquals(novoTitulo, livroAtualizado.getTitulo());
    }

    @Test
    void deveLancarExcecaoAoAlterarTituloDeLivroInexistente() {
        String isbnInexistente = "0000000000000";
        String novoTitulo = "Clean Code 2.0";

        assertThrows(IllegalArgumentException.class, () -> {
           livroServiceImplementation.alterarTitulo(isbnInexistente, novoTitulo);
        });
    }

    @Test
    void deveLancarExcecaoAoAlterarTituloParaValorInvalido() {
        livroServiceImplementation.cadastrarLivro(livroValido);
        String tituloVazio = "";
        String tituloNulo = null;

        assertThrows(IllegalArgumentException.class, () -> {
           livroServiceImplementation.alterarTitulo("9780132350884", tituloVazio);
        });

        assertThrows(IllegalArgumentException.class, () -> {
           livroServiceImplementation.alterarTitulo("9780132350884", tituloNulo);
        });
    }

    @Test
    void deveAlterarAutorComSucesso() {
        livroServiceImplementation.cadastrarLivro(livroValido);
        String novoAutor = "Bob Uncle";
        livroServiceImplementation.alterarAutor("9780132350884", novoAutor);
        Livro livroAtualizado = livroServiceImplementation.buscarPorIsbn("9780132350884");
        assertEquals(novoAutor, livroAtualizado.getAutor());
    }

    @Test
    void deveLancarExcecaoAoAlterarAutorDeLivroInexistente() {
        String isbnInexistente = "0000000000000";
        String novoAutor = "Bob Uncle";

        assertThrows(IllegalArgumentException.class, () -> {
            livroServiceImplementation.alterarAutor(isbnInexistente, novoAutor);
        });
    }

    @Test
    void deveLancarExcecaoAoAlterarAutorParaValorInvalido() {
        livroServiceImplementation.cadastrarLivro(livroValido);
        String autorVazio = "";
        String autorNulo = null;

        assertThrows(IllegalArgumentException.class, () -> {
            livroServiceImplementation.alterarAutor("9780132350884", autorVazio);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            livroServiceImplementation.alterarAutor("9780132350884", autorNulo);
        });
    }

    @Test
    void deveAlterarAnoPublicacaoComSucesso() {
        livroServiceImplementation.cadastrarLivro(livroValido);
        int novoAno = 2020;
        livroServiceImplementation.alterarAnoPublicacao("9780132350884", novoAno);
        Livro livroAtualizado = livroServiceImplementation.buscarPorIsbn("9780132350884");
        assertEquals(novoAno, livroAtualizado.getAnoPublicacao());
    }

    @Test
    void deveLancarExcecaoAoAlterarAnoDeLivroInexistente() {
        String isbnInexistente = "0000000000000";
        int novoAno = 2020;

        assertThrows(IllegalArgumentException.class, () -> {
           livroServiceImplementation.alterarAnoPublicacao(isbnInexistente, novoAno);
        });
    }

    @Test
    void deveLancarExcecaoAoAlterarAnoParaValorInvalido() {
        livroServiceImplementation.cadastrarLivro(livroValido);
        int anoNegativo = -100;
        int anoMaiorQueAtual = 2050;

        assertThrows(IllegalArgumentException.class, () -> {
           livroServiceImplementation.alterarAnoPublicacao("9780132350884", anoNegativo);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            livroServiceImplementation.alterarAnoPublicacao("9780132350884", anoMaiorQueAtual);
        });
    }

    @Test
    void deveMarcarLivroComoIndisponivelComSucesso() {
        livroServiceImplementation.cadastrarLivro(livroValido);
        livroServiceImplementation.marcarComoIndisponivel("9780132350884");
        Livro livroAtualizado = livroServiceImplementation.buscarPorIsbn("9780132350884");
        assertFalse(livroAtualizado.isDisponivel());
    }

    @Test
    void deveMarcarLivroComoDisponivelComSucesso() {
        livroServiceImplementation.cadastrarLivro(livroValido);
        livroServiceImplementation.marcarComoIndisponivel("9780132350884");
        livroServiceImplementation.marcarComoDisponivel("9780132350884");
        Livro livroAtualizado = livroServiceImplementation.buscarPorIsbn("9780132350884");
        assertTrue(livroAtualizado.isDisponivel());
    }

    @Test
    void deveLancarExcecaoAoMarcarDisponivelQuandoJaDisponivel() {
        livroServiceImplementation.cadastrarLivro(livroValido);
        assertThrows(IllegalArgumentException.class, () -> {
           livroServiceImplementation.marcarComoDisponivel("9780132350884");
        });
    }

    @Test
    void deveLancarExcecaoAoMarcarIndisponivelQuandoJaIndisponivel() {
        livroServiceImplementation.cadastrarLivro(livroValido);
        livroServiceImplementation.marcarComoIndisponivel("9780132350884");
        assertThrows(IllegalArgumentException.class, () -> {
           livroServiceImplementation.marcarComoIndisponivel("9780132350884");
        });
    }

    @Test
    void deveLancarExcecaoAoAlterarDisponibilidadeDeLivroInexistente() {
        assertThrows(IllegalArgumentException.class, () -> {
           livroServiceImplementation.marcarComoIndisponivel("9780132350884");
        });
    }



}
