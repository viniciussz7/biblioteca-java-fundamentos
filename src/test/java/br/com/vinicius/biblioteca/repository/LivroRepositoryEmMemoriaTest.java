package br.com.vinicius.biblioteca.repository;

import br.com.vinicius.biblioteca.model.Livro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class LivroRepositoryEmMemoriaTest {
    private LivroRepositoryEmMemoria repository;

    @BeforeEach
    void setUp() {
        repository = new LivroRepositoryEmMemoria();
    }

    @Test
    void deveSalvarELocalizarLivroPorIsbn() {
        //Arrange
        Livro livro = new Livro("Clean Code", "Robert C. Martin", 2010, "978-0132350884");
        //Act
        repository.salvar(livro);
        Optional<Livro> resultado = repository.buscarPorIsbn(livro.getIsbn());
        //Assert
        assertTrue(resultado.isPresent());
        assertEquals("Clean Code", resultado.get().getTitulo());
    }

    @Test
    void deveLancarExcecaoAoSalvarIsbnDuplicado() {
        //Arrange
        Livro livro1 = new Livro("Clean Code", "Robert C. Martin", 2010, "978-0132350884");
        Livro livro2 = new Livro("Clean Code 2", "Robert C. Martin", 2015, "978-0132350884");
        //Act
        repository.salvar(livro1);
        //Assert
        assertThrows(IllegalArgumentException.class, () -> {
            repository.salvar(livro2);
        });
    }

    @Test
    void deveLancarExcecaoAoRemoverLivroInexistente() {
        assertThrows(IllegalArgumentException.class, () -> {
           repository.remover("178-0132350884");
        });
    }

    @Test
    void deveListarTodosOsLivrosSalvos() {
        //Arrange
        Livro livro1 = new Livro("Livro 1", "Autor", 2020, "978-0321125217");
        Livro livro2 = new Livro("Livro 2", "Autor", 1999, "978-0132350884");
        //Act
        repository.salvar(livro1);
        repository.salvar(livro2);
        List<Livro> livros = repository.listarTodos();
        //Assert
        assertEquals(2, livros.size());
    }
}
