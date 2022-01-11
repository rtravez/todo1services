package com.todo1.hulkstore.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todo1.hulkstore.entity.Empleado;


/**
 * <b> Descripcion de la clase, interface o enumeracion. </b>
 * 
 * @author rene.travez
 * @version $1.0$
 */
public interface IEmpleadoDao extends JpaRepository<Empleado, Long> {
	
}


	
