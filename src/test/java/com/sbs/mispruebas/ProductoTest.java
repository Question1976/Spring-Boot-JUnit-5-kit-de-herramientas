package com.sbs.mispruebas;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.sbs.modelos.ProductoModel;
import com.sbs.repositorio.IProductoRepositorio;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)	// Para que trabaje con la bbdd real
@TestMethodOrder(OrderAnnotation.class)	// Ordenar la ejecución de los métodos de pruebas
public class ProductoTest {

	@Autowired
	private IProductoRepositorio repositorio;
	
	@Test
	@Rollback(false)	// Habilita la creación del registro en la bbdd.
	@Order(1)
	public void crearProducto() {
		// Guardar registro | Lo creo pare hace rollback,
		// no lo guarda en la bbdd, porque lo borra automáticamente.
		// Solo se crea para probar.
		ProductoModel guardar = this.repositorio.save(new ProductoModel("Six pack", 300));
		//System.out.println(guardar);
		// Comprobar que guarda los registros, si lo creó o no.
		assertNotNull(guardar);
	}
	
	@Test
	@Order(2)
	public void buscarPorNombre() {		
		String nombre = "Producto 1";
		ProductoModel producto = this.repositorio.findByNombre(nombre);
		// Si existe por el nombre, correcto, sino, incorrecto.
		assertThat(producto.getNombre()).isEqualTo(nombre);
	}
	
	@Test
	@Order(3)
	public void buscarPorNombreNoExistente() {
		String nombre = "Producto gluglupug";
		ProductoModel producto = this.repositorio.findByNombre(nombre);
		// Buscar valor por nombre pero que NO exista | llega nulo.
		assertNull(producto);
	}
	
	@Test
	@Rollback(false)	// Habilita la actualización del registro en la bbdd.
	@Order(4)
	public void updateProducto() {
		String nombreProducto = "Mi producto modificado";
		ProductoModel producto = new ProductoModel(nombreProducto, 456);
		producto.setId(4);	// Id del producto en la bbdd.
		this.repositorio.save(producto);
		ProductoModel productoModificado = this.repositorio.findByNombre(nombreProducto);
		// Actualizar registro
		assertThat(productoModificado.getNombre()).isEqualTo(nombreProducto);
	}
	
	@Test
	@Order(5)
	public void listarProductos() {
		List<ProductoModel> productos = this.repositorio.findAll();
		for(ProductoModel producto: productos) {
			System.out.println(producto.getNombre());
		}
		// Listar los registros
		assertThat(productos).size().isGreaterThan(0);
	}
	
	@Test
	@Rollback(false)
	@Order(6)
	public void eliminarProducto() {
		Integer id = 4;	// ID del registro en la bbdd.
		boolean existe = this.repositorio.findById(id).isPresent();
		this.repositorio.deleteById(id);
		boolean noExiste = this.repositorio.findById(id).isPresent();
		// Eliminar registro.
		assertTrue(existe);
		assertFalse(noExiste);
	}
	
}
