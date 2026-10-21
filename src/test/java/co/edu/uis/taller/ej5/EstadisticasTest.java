package co.edu.uis.taller.ej5;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Ejercicio 5 - RETO: Cacería de bugs.
 * <p>
 * Escribe pruebas para cada método de Estadisticas.
 * Piensa en: casos normales, valores límite, números negativos,
 * cantidad par e impar de datos, el primer y el último elemento...
 * <p>
 * Cuando una prueba falle, ¡encontraste un bug! Corrígelo en Estadisticas.java.
 */
class EstadisticasTest {

    private Estadisticas estadisticas;

    @BeforeEach
    void setUp() {
        estadisticas = new Estadisticas();
    }

    // Pruebas para maximo()
    @Test
    @DisplayName("Caso normal: encuentra el máximo en un arreglo mixto")
    void maximoCasoNormal() {
        int[] datos = {3, 9, 4, 1, 8};

        assertEquals(9, estadisticas.maximo(datos));
    }

    @Test
    @DisplayName("El máximo de puros negativos devuelve el número correcto")
    void maximoConNumerosNegativos() {
        Estadisticas estadisticas = new Estadisticas();

        int[] datos = {-5, -2, -8};

        assertEquals(-2, estadisticas.maximo(datos));
    }

    @Test
    @DisplayName("Arreglo vacío o nulo lanza excepción en máximo")
    void maximoVacioONuloLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> estadisticas.maximo(new int[]{}));
        assertThrows(IllegalArgumentException.class, () -> estadisticas.maximo(null));
    }

    // Pruebas para minimo()
    @Test
    @DisplayName("Caso normal: encuentra el mínimo en un arreglo mixto")
    void minimoCasoNormal() {
        int[] datos = {3, 9, 4, 1, 8};
        assertEquals(1, estadisticas.minimo(datos));
    }

    @Test
    @DisplayName("Mínimo con un solo elemento devuelve ese elemento")
    void minimoUnSoloElemento() {
        int[] datos = {7};
        assertEquals(7, estadisticas.minimo(datos));
    }


    // Pruebas para mediana()
    @Test
    @DisplayName("La mediana con cantidad impar devuelve el valor central")
    void medianaCantidadImpar() {
        int[] datos = {3, 1, 2}; // Al ordenar queda: 1, 2, 3

        assertEquals(2.0, estadisticas.mediana(datos), 0.001);
    }

    @Test
    @DisplayName("La mediana de cantidad par calcula correctamente los decimales")
    void medianaCantidadParConDecimales() {
        int[] datos = {4, 1, 3, 2}; // Al ordenar queda: 1, 2, 3, 4

        assertEquals(2.5, estadisticas.mediana(datos), 0.001);
    }

    @Test
    @DisplayName("Arreglo vacío lanza excepción en mediana")
    void medianaVaciaLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> estadisticas.mediana(new int[]{}));
    }


    // Pruebas para contarAprobados()
    @Test
    @DisplayName("Caso normal: cuenta aprobados en una lista variada")
    void contarAprobadosCasoNormal() {
        double[] notas = {2.5, 3.0, 4.0, 1.0, 5.0};

        assertEquals(3, estadisticas.contarAprobados(notas));
    }

    @Test
    @DisplayName("Cuenta correctamente si solo hay un elemento aprobatorio")
    void contarAprobadosUnSoloElemento() {
        double[] notas = {4.5};

        assertEquals(1, estadisticas.contarAprobados(notas));
    }

    @Test
    @DisplayName("Valores límite: 2.9 reprueba, 3.0 aprueba exacto")
    void contarAprobadosValoresLimite() {
        double[] notas = {2.9, 3.0, 3.1};

        assertEquals(2, estadisticas.contarAprobados(notas));
    }

    @Test
    @DisplayName("Notas nulas lanzan excepción al contar aprobados")
    void contarAprobadosNuloLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> estadisticas.contarAprobados(null));
    }
}
