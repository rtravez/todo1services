package com.todo1.hulkstore.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.todo1.hulkstore.entity.Region;

/**
 * <b> Descripcion de la clase, interface o enumeracion. </b>
 * 
 * @author renetravez
 * @version $1.0$
 */
public interface IRegionDao extends JpaRepository<Region, Long> {

	@Query("select r from Region r")
	public List<Region> findAllRegiones();

}
