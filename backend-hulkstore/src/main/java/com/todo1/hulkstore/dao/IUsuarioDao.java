package com.todo1.hulkstore.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.todo1.hulkstore.entity.Usuario;

/**
 * <b> Descripcion de la clase, interface o enumeracion. </b>
 * 
 * @author renetravez
 * @version $1.0$
 */
public interface IUsuarioDao extends JpaRepository<Usuario, Long> {

	@Query("select u from Usuario u where u.username = ?1")
	public Optional<Usuario> findByUsername(String username);
}
