package com.todo1.hulkstore.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.todo1.hulkstore.entity.Usuario;

/**
 * <b> Descripcion de la clase, interface o enumeracion. </b>
 * 
 * @author rene.travez
 * @version $1.0$
 */
public interface IUsuarioService extends UserDetailsService {

	public Optional<Usuario> findByUsername(String username);

}
