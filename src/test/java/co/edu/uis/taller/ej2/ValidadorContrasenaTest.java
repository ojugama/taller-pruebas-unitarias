package co.edu.uis.taller.ej2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Ejercicio 2 - Excepciones y pruebas parametrizadas.
 * Sigue las instrucciones de la presentación paso a paso.
 */
class ValidadorContrasenaTest {

    private final ValidadorContrasena validador = new ValidadorContrasena();

    // ---------------------------------------------------------------
    // PASO 1: Ejemplo completo - una contraseña correcta es válida.
    // ---------------------------------------------------------------
    @Test
    @DisplayName("Contraseña válida: cumple todas las reglas")
    void contrasenaCorrectaEsValida() {
        assertTrue(validador.validar("Segura2026"));
    }

    // ---------------------------------------------------------------
    // PASO 2: Ejemplo completo - verificar excepción Y su mensaje.
    // ---------------------------------------------------------------
    @Test
    @DisplayName("Contraseña corta: excepción con mensaje correcto")
    void contrasenaCortaLanzaExcepcion() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> validador.validar("Ab1")
        );
        assertEquals(ValidadorContrasena.MSG_LONGITUD, ex.getMessage());
    }

    // ---------------------------------------------------------------
    // PASO 3: Escribe pruebas para:
    //   a) contraseña nula         -> MSG_NULA
    //   b) sin mayúscula           -> MSG_MAYUSCULA   (ej: "segura2026")
    //   c) sin número              -> MSG_NUMERO      (ej: "SeguraUIS")
    // ---------------------------------------------------------------
    @Test
    @DisplayName("Una contraseña nula lanza excepción")
    void contrasenaNulaLanzaExcepcion() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> validador.validar(null)
        );

        assertEquals(ValidadorContrasena.MSG_NULA, ex.getMessage());
    }

    @Test
    @DisplayName("Una contraseña sin mayúscula lanza excepción")
    void contrasenaSinMayusculaLanzaExcepcion() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> validador.validar("segura2026")
        );

        assertEquals(ValidadorContrasena.MSG_MAYUSCULA, ex.getMessage());
    }

    @Test
    @DisplayName("Una contrasseña sin número lanza excepción")
    void contrasenaSinNumeroLanzaExcepcion() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> validador.validar("SeguraUIS")
        );

        assertEquals(ValidadorContrasena.MSG_NUMERO, ex.getMessage());
    }


    // ---------------------------------------------------------------
    // PASO 4: Prueba PARAMETRIZADA con @ValueSource:
    //   varias contraseñas inválidas -> esValida(...) debe ser false
    // ---------------------------------------------------------------
    @ParameterizedTest(name = "\"{0}\" no es válida")
    @ValueSource(strings = {
            "", "Ab1", "segura2026", "SeguraUIS", "12345678"
    })
    @DisplayName("Contraseñas inválidas devuelven false")
    void contrasenasInvalidas(String contrasena) {
        assertFalse(validador.esValida(contrasena));
    }


    // ---------------------------------------------------------------
    // PASO 5: Prueba PARAMETRIZADA con @CsvSource:
    //   cada fila = contraseña, mensaje de error esperado
    // ---------------------------------------------------------------
    @ParameterizedTest(name = "\"{0}\" -> {1}")
    @CsvSource({
            "Ab1, Debe tener al menos 8 caracteres",
            "segura2026, Debe tener al menos una mayúscula",
            "SeguraUIS, Debe tener al menos un número"
    })
    @DisplayName("Cada regla incumplida produce su propio mensaje")
    void mensajeSegunRegla(String contrasena, String mensajeEsperado) {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> validador.validar(contrasena)
        );
        assertEquals(mensajeEsperado, ex.getMessage());
    }
}
