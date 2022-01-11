package com.todo1.hulkstore.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todo1.hulkstore.dao.IEmpleadoDao;
import com.todo1.hulkstore.dao.IFacturaDao;
import com.todo1.hulkstore.dao.IProductoDao;
import com.todo1.hulkstore.dao.IRegionDao;
import com.todo1.hulkstore.entity.Empleado;
import com.todo1.hulkstore.entity.Factura;
import com.todo1.hulkstore.entity.Producto;
import com.todo1.hulkstore.entity.Region;
import com.todo1.hulkstore.service.IEmpleadoService;

/**
 * <b> Descripcion de la clase, interface o enumeracion. </b>
 * 
 * @author rene.travez
 * @version $1.0$
 */
@Service
public class EmpleadoServiceImpl implements IEmpleadoService {

	@Autowired
	private IEmpleadoDao empleadoDao;
	@Autowired
	private IFacturaDao facturaDao;
	@Autowired
	private IRegionDao regionDao;
	@Autowired
	private IProductoDao productoDao;

	@Override
	@Transactional(readOnly = true)
	public List<Empleado> findAll() {
		return empleadoDao.findAll();
	}

	@Override
	@Transactional
	public Empleado save(Empleado empleado) {
		return empleadoDao.save(empleado);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Empleado> findById(Long id) {
		return empleadoDao.findById(id);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		empleadoDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Empleado> findAll(Pageable pageable) {
		return empleadoDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Region> findAllRegiones() {
		return regionDao.findAllRegiones();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Factura> findFacturaById(Long id) {
		return facturaDao.findById(id);
	}

	@Override
	@Transactional
	public Factura saveFactura(Factura factura) {
		return facturaDao.save(factura);
	}

	@Override
	@Transactional
	public void deleteFacturaById(Long id) {
		facturaDao.deleteById(id);

	}

	@Override
	@Transactional(readOnly = true)
	public List<Producto> findProductoByNombre(String term) {
		return productoDao.findProductoByNombre(term);
	}
}
