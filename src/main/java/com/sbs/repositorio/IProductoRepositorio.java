package com.sbs.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sbs.modelos.ProductoModel;

public interface IProductoRepositorio extends JpaRepository<ProductoModel, Integer> {

	public ProductoModel findByNombre(String nombre);
}
