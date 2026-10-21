package co.edu.uis.taller.ej4;

import java.util.ArrayList;
import java.util.List;

/**
 * Ejercicio 4 - Desarrollo guiado por pruebas (TDD).
 * <p>
 * Esta clase está VACÍA a propósito.
 * La irás construyendo SOLO con el código necesario para que pase
 * cada prueba que escribas en CarritoComprasTest (Rojo -> Verde -> Refactor).
 * <p>
 * Requisitos (se implementan de uno en uno, en este orden):
 * 1. Un carrito nuevo no tiene productos y su total es 0.
 * 2. Se puede agregar un producto con nombre, precio unitario (pesos) y cantidad.
 * El total es precio x cantidad.
 * 3. Con varios productos, el total es la suma de los subtotales.
 * 4. Precio negativo o cantidad menor o igual a 0 -> IllegalArgumentException.
 * 5. Si el total supera $100.000, se aplica un 10% de descuento.
 */
public class CarritoCompras {
    public static final long UMBRAL_DESCUENTO = 100_000;
    public static final int PORCENTAJE_DESCUENTO = 10;

    private record Item(String nombre, long precioUnitario, int cantidad) {
        long subtotal() {
            return precioUnitario * cantidad;
        }
    }

    private final List<Item> items = new ArrayList<>();

    public int cantidadProductos() {
        return items.size();
    }

    public void agregar(String nombre, long precioUnitario, int cantidad) {
        if (precioUnitario < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que 0");
        }
        
        items.add(new Item(nombre, precioUnitario, cantidad));
    }

    public long total() {
        long suma = 0;

        for (Item item : items) {
            suma += item.subtotal();
        }
        if (suma > UMBRAL_DESCUENTO) {
            suma = suma * (100 - PORCENTAJE_DESCUENTO) / 100;
        }

        return suma;
    }
}
