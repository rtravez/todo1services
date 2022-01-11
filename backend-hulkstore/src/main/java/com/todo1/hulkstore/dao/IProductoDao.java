package com.todo1.hulkstore.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.todo1.hulkstore.entity.Producto;

/**
 * <b> Descripcion de la clase, interface o enumeracion. </b>
 * 
 * @author rene.travez
 * @version $1.0$
 */
public interface IProductoDao extends JpaRepository<Producto, Long> {

	@Query("select p from Producto p where p.nombre like  %?1%")
	public List<Producto> findProductoByNombre(String term);

	public List<Producto> findByNombreContainingIgnoreCase(String term);

	public List<Producto> findByNombreStartingWithIgnoreCase(String term);

}
