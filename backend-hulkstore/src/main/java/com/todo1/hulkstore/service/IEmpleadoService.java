package com.todo1.hulkstore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.todo1.hulkstore.entity.Empleado;
import com.todo1.hulkstore.entity.Factura;
import com.todo1.hulkstore.entity.Producto;
import com.todo1.hulkstore.entity.Region;

/**
 * <b> Descripcion de la clase, interface o enumeracion. </b>
 * 
 * @author rene.travez
 * @version $1.0$
 */
public interface IEmpleadoService {

	public List<Empleado> findAll();

	public Page<Empleado> findAll(Pageable pageable);

	public Empleado save(Empleado cliente);

	public Optional<Empleado> findById(Long id);

	public void deleteById(Long id);

	public List<Region> findAllRegiones();

	public Optional<Factura> findFacturaById(Long id);

	public Factura saveFactura(Factura factura);

	public void deleteFacturaById(Long id);

	public List<Producto> findProductoByNombre(String term);

}
