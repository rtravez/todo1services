package com.todo1.hulkstore.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todo1.hulkstore.entity.Factura;
import com.todo1.hulkstore.entity.Producto;
import com.todo1.hulkstore.service.IEmpleadoService;

/**
 * @author renetravez
 *
 */
@RestController()
@RequestMapping("/api")
public class FacturaController {

	private static final Logger LOG = LoggerFactory.getLogger(FacturaController.class);
	@Autowired
	private IEmpleadoService clienteService;
	private Map<String, Object> response;
	private Factura factura;

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@GetMapping("/facturas/{id}")
	public ResponseEntity<?> obtenerFacturaById(@PathVariable Long id) {
		response = new HashMap<>();
		try {
			this.factura = clienteService.findFacturaById(id).orElse(null);
		} catch (DataAccessException e) {
			LOG.error("obtenerFacturaById () {}", e);
			response.put("mensaje", "Error al realizar la consulta");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

		if (this.factura == null) {
			response.put("mensaje", "La factura no existe");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok().body(this.factura);
	}

	@Secured({ "ROLE_ADMIN" })
	@DeleteMapping("/facturas/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {
		response = new HashMap<>();
		this.factura = null;
		try {

			this.factura = clienteService.findFacturaById(id).orElse(null);

			if (this.factura != null) {
				clienteService.deleteFacturaById(id);
			}
		} catch (DataAccessException e) {
			LOG.error("deleteById () {}", e);
			response.put("mensaje", "Error al eliminar el registro");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "la factura ha sido eliminado con éxito");
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@Secured({ "ROLE_ADMIN" })
	@GetMapping("/facturas/filtrar-productos/{term}")
	public ResponseEntity<?> findProductoByNombre(@PathVariable("term") String term) {
		List<Producto> productos = clienteService.findProductoByNombre(term);
		return ResponseEntity.ok().body(productos);
	}

	@Secured({ "ROLE_ADMIN" })
	@PostMapping("/facturas")
	public ResponseEntity<?> create(@Valid @RequestBody Factura factura, BindingResult result) {
		response = new HashMap<>();
		this.factura = null;

		if (result.hasErrors()) {

			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);

		}

		try {
			this.factura = clienteService.saveFactura(factura);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al guardar el registro");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "La factura ha sido creado con éxito");
		response.put("factura", this.factura);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
}
