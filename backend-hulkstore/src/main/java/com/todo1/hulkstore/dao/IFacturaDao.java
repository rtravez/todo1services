package com.todo1.hulkstore.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todo1.hulkstore.entity.Factura;

public interface IFacturaDao extends JpaRepository<Factura, Long> {

}
