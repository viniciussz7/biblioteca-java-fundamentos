package br.com.vinicius.biblioteca.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LivroTest {

    private String tituloValido;
    private String autorValido;
    private int anoPublicacaoValido;
    private String isbnValido;

    @BeforeEach
    void setUp() {
        tituloValido = "Clean Code";
        autorValido = "Robert C. Martin";
        anoPublicacaoValido = 2008;
        isbnValido = "978-0132350884";
    }

    @Test
    void deveCriarLivroQuandoDadosSaoValidos() {
        Livro livro = new Livro(tituloValido, autorValido, anoPublicacaoValido, isbnValido);

        assertEquals(tituloValido, livro.getTitulo());
        assertEquals(autorValido, livro.getAutor());
        assertEquals(anoPublicacaoValido, livro.getAnoPublicacao());
        assertEquals("9780132350884", livro.getIsbn());
        assertTrue(livro.isDisponivel());
    }

    @Test
    void deveLancarExcecaoQuandoTituloNuloOuVazio() {
        String tituloVazio = "";
        String tituloNulo = null;

        assertThrows(IllegalArgumentException.class, () -> {
            new Livro(tituloVazio, autorValido, anoPublicacaoValido, isbnValido);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Livro(tituloNulo, autorValido, anoPublicacaoValido, isbnValido);
        });
    }

    @Test
    void deveLancarExcecaoQuandoAutorNuloOuVazio() {
        String autorVazio = "";
        String autorNulo = null;

        assertThrows(IllegalArgumentException.class, () -> {
            new Livro(tituloValido, autorVazio, anoPublicacaoValido, isbnValido);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Livro(tituloValido, autorNulo, anoPublicacaoValido, isbnValido);
        });
    }

    @Test
    void deveLancarExcecaoQuandoIsbnNuloOuVazio() {
        String isbnVazio = "";
        String isbnNulo = null;

        assertThrows(IllegalArgumentException.class, () -> {
            new Livro(tituloValido, autorValido, anoPublicacaoValido, isbnVazio);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Livro(tituloValido, autorValido, anoPublicacaoValido, isbnNulo);
        });
    }

    @Test
    void deveLancarExcecaoQuandoIsbnInvalido() {
        String isbnInvalido = "12965";

        assertThrows(IllegalArgumentException.class, () -> {
            new Livro(tituloValido, autorValido, anoPublicacaoValido, isbnInvalido);
        });
    }

    @Test
    void deveLancarExcecaoQuandoAnoPublicacaoInvalido() {
        int anoPublicacaoNegativo = -5;
        int anoPublicacaoMaiorQueAtual = 2030;

        assertThrows(IllegalArgumentException.class, () -> {
            new Livro(tituloValido, autorValido, anoPublicacaoNegativo, isbnValido);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Livro(tituloValido, autorValido, anoPublicacaoMaiorQueAtual, isbnValido);
        });
    }

    @Test
    void deveAlterarTituloQuandoValido() {
        Livro livro = new Livro(tituloValido, autorValido, anoPublicacaoValido, isbnValido);
        String novoTitulo = "Clean Arquiteture";

        livro.alterarTitulo(novoTitulo);

        assertEquals(novoTitulo, livro.getTitulo());
    }

    @Test
    void deveLancarExcecaoAoAlterarTituloInvalido() {
        Livro livro = new Livro(tituloValido, autorValido, anoPublicacaoValido, isbnValido);
        String novoTituloVazio = "";
        String novoTituloNulo = null;

        assertThrows(IllegalArgumentException.class, () -> {
           livro.alterarTitulo(novoTituloVazio);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            livro.alterarTitulo(novoTituloNulo);
        });
    }

    @Test
    void deveAlterarAutorQuandoValido() {
        Livro livro = new Livro(tituloValido, autorValido, anoPublicacaoValido, isbnValido);
        String novoAutor = "Sebesta";

        livro.alterarAutor(novoAutor);

        assertEquals(novoAutor, livro.getAutor());
    }

    @Test
    void deveLancarExcecaoAoAlterarAutorInvalido() {
        Livro livro = new Livro(tituloValido, autorValido, anoPublicacaoValido, isbnValido);
        String novoAutorVazio = "";
        String novoAutorNulo = null;

        assertThrows(IllegalArgumentException.class, () -> {
            livro.alterarAutor(novoAutorVazio);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            livro.alterarAutor(novoAutorNulo);
        });
    }

    @Test
    void deveAlterarAnoPublicacaoQuandoValido() {
        Livro livro = new Livro(tituloValido, autorValido, anoPublicacaoValido, isbnValido);
        int novoAnoPublicacaoValido = 2015;

        livro.alterarAnoPublicacao(novoAnoPublicacaoValido);

        assertEquals(novoAnoPublicacaoValido, livro.getAnoPublicacao());
    }

    @Test
    void deveLancarExcecaoAoAlterarAnoPublicacaoInvalido() {
        Livro livro = new Livro(tituloValido, autorValido, anoPublicacaoValido, isbnValido);
        int novoAnoPublicacaoNegativo = -2000;
        int novoAnoPublicacaoMaiorQueAtual = 2050;

        assertThrows(IllegalArgumentException.class, () -> {
            livro.alterarAnoPublicacao(novoAnoPublicacaoNegativo);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            livro.alterarAnoPublicacao(novoAnoPublicacaoMaiorQueAtual);
        });

    }

    @Test
    void deveMarcarComoIndisponivelQuandoDisponivel() {
        Livro livro = new Livro(tituloValido, autorValido, anoPublicacaoValido, isbnValido);

        livro.marcarComoIndisponivel();

        assertFalse(livro.isDisponivel());
    }

    @Test
    void deveMarcarComoDisponivelQuandoIndisponivel() {
        Livro livro = new Livro(tituloValido, autorValido, anoPublicacaoValido, isbnValido);

        livro.marcarComoIndisponivel();
        livro.marcarComoDisponivel();
        assertTrue(livro.isDisponivel());
    }

    @Test
    void deveLancarExcecaoAoMarcarComoDisponivelQuandoJaDisponivel() {
        Livro livro = new Livro(tituloValido, autorValido, anoPublicacaoValido, isbnValido);

        assertThrows(IllegalArgumentException.class, livro::marcarComoDisponivel);
    }

    @Test
    void deveLancarExcecaoAoMarcarComoIndisponivelQuandoJaIndisponivel() {
        Livro livro = new Livro(tituloValido, autorValido, anoPublicacaoValido, isbnValido);

        livro.marcarComoIndisponivel();

        assertThrows(IllegalArgumentException.class, livro::marcarComoIndisponivel);
    }

}
