package br.com.vinicius.biblioteca.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class EmprestimoTest {
    private String isbnValido;
    private String nomeUsuarioValido;

    @BeforeEach
    void setUp(){
        isbnValido = "9780132350884";
        nomeUsuarioValido = "Vinícius";
    }

    @Test
    void deveCriarEmprestimoQuandoDadosValidos() {
        LocalDate antes = LocalDate.now();
        Emprestimo emprestimo = new Emprestimo(isbnValido, nomeUsuarioValido);
        LocalDate depois = LocalDate.now();

        assertEquals(nomeUsuarioValido, emprestimo.getNomeUsuario());
        assertEquals(StatusEmprestimo.ATIVO, emprestimo.getStatus());
        assertTrue(!emprestimo.getDataEmprestimo().isBefore(antes) && !emprestimo.getDataEmprestimo().isAfter(depois));
    }

    @Test
    void deveLancarExcecaoAoCriarEmprestimoComIsbnInvalido() {
        String isbnInvalido = "123456";

        assertThrows(IllegalArgumentException.class, () -> {
           new Emprestimo(isbnInvalido, nomeUsuarioValido);
        });
    }

    @Test
    void deveLancarExcecaoAoCriarEmprestimoComNomeUsuarioInvalido() {
        String nomeVazio = "";
        String nomeNulo = null;

        assertThrows(IllegalArgumentException.class, () -> {
           new Emprestimo(isbnValido, nomeVazio);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Emprestimo(isbnValido, nomeNulo);
        });
    }

    @Test
    void deveFinalizarEmprestimoAtivo() {
        Emprestimo emprestimo = new Emprestimo(isbnValido, nomeUsuarioValido);
        emprestimo.finalizar();
        assertEquals(StatusEmprestimo.FINALIZADO, emprestimo.getStatus());
    }

    @Test
    void deveLancarExcecaoAoFinalizarEmprestimoFinalizado() {
        Emprestimo emprestimo = new Emprestimo(isbnValido, nomeUsuarioValido);
        emprestimo.finalizar();
        assertThrows(IllegalStateException.class, emprestimo::finalizar);
    }
}
