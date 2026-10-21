package co.edu.uis.taller.ej3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Ejercicio 3 - Aislar dependencias con Mockito.
 * Sigue las instrucciones de la presentación paso a paso.
 */
class ServicioEstudiantesTest {

    private RepositorioEstudiantes repositorio;   // el mock
    private ServicioEstudiantes servicio;         // la clase que probamos

    @BeforeEach
    void setUp() {
        // PASO 2: creamos un objeto SIMULADO del repositorio
        repositorio = mock(RepositorioEstudiantes.class);
        // y se lo inyectamos al servicio por el constructor
        servicio = new ServicioEstudiantes(repositorio);
    }

    // ---------------------------------------------------------------
    // PASO 3: Ejemplo completo - STUBBING con when(...).thenReturn(...)
    // ---------------------------------------------------------------
    @Test
    @DisplayName("Promedio 4.2 está en estado NORMAL")
    void estudianteConBuenPromedioEsNormal() {
        // Arrange: le "enseñamos" al mock qué responder
        Estudiante ana = new Estudiante("2201234", "Ana", 4.2);
        when(repositorio.buscarPorCodigo("2201234")).thenReturn(Optional.of(ana));

        // Act
        String estado = servicio.consultarEstado("2201234");

        // Assert
        assertEquals(ServicioEstudiantes.NORMAL, estado);
    }

    // ---------------------------------------------------------------
    // PASO 4: Estudiante con promedio 2.5 -> BAJO_RENDIMIENTO
    // ---------------------------------------------------------------
    @Test
    @DisplayName("Promedio 2.5 está en BAJO RENDIMIENTO")
    void estudianteConPromedioBajo() {
        Estudiante luis = new Estudiante("2205678", "Luis", 2.5);
        when(repositorio.buscarPorCodigo("2205678")).thenReturn(Optional.of(luis));

        String estado = servicio.consultarEstado("2205678");

        assertEquals(ServicioEstudiantes.BAJO_RENDIMIENTO, estado);
    }


    // ---------------------------------------------------------------
    // PASO 5: Estudiante inexistente -> NoSuchElementException
    //         (el mock debe devolver Optional.empty())
    // ---------------------------------------------------------------
    @Test
    @DisplayName("Estudiante inexistente lanza excepción")
    void estudianteInexistenteLanzaExcepcion() {
        when(repositorio.buscarPorCodigo("0000000")).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> servicio.consultarEstado("0000000"));
    }


    // ---------------------------------------------------------------
    // PASO 6: promedioGeneral() con 3 estudiantes (listarTodos)
    // ---------------------------------------------------------------
    @Test
    @DisplayName("El promedio general de 3.0, 4.0 y 5.0 es 4.0")
    void promedioGeneralDeTresEstudiantes() {
        when(repositorio.listarTodos()).thenReturn(List.of(
                new Estudiante("1", "Ana", 3.0),
                new Estudiante("2", "Luis", 4.0),
                new Estudiante("3", "Sofía", 5.0)
        ));

        double promedio = servicio.promedioGeneral();

        assertEquals(4.0, promedio, 0.001);
    }


    // ---------------------------------------------------------------
    // PASO 7: VERIFICACIÓN con verify(...):
    //   a) registrar() con código válido llama a repositorio.guardar(...)
    //   b) registrar() con código vacío lanza excepción y NO llama a nada
    //      (usa verifyNoInteractions)
    // ---------------------------------------------------------------
    @Test
    @DisplayName("Registrar un estudiante válido lo guarda")
    void registrarGuardaEnRepositorio() {
        Estudiante nuevo = new Estudiante("2209999", "Camila", 0.0);

        servicio.registrar(nuevo);

        verify(repositorio).guardar(nuevo);
    }

    @Test
    @DisplayName("Código vacío: lanza excepción y no guarda")
    void registrarSinCodigoNoGuarda() {
        Estudiante invalido = new Estudiante("", "Sin código", 0.0);

        assertThrows(IllegalArgumentException.class,
                () -> servicio.registrar(invalido));

        verifyNoInteractions(repositorio);
    }
}
