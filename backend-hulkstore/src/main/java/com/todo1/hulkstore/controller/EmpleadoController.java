package com.todo1.hulkstore.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.todo1.hulkstore.entity.Empleado;
import com.todo1.hulkstore.entity.Region;
import com.todo1.hulkstore.service.IEmpleadoService;
import com.todo1.hulkstore.service.IUploadFileService;

/**
 * <b> Descripcion de la clase, interface o enumeracion. </b>
 * 
 * @author rene.travez
 * @version $1.0$
 */
@RestController()
@RequestMapping("/api")
public class EmpleadoController {

	private static final Logger LOG = LoggerFactory.getLogger(EmpleadoController.class);

	@Autowired
	private IEmpleadoService empleadoService;

	@Autowired
	private IUploadFileService uploadService;

	private Empleado empleado;
	private Map<String, Object> response;

	public EmpleadoController() {
		empleado = new Empleado();
		response = new HashMap<>();
	}

	@GetMapping("/empleados")
	public ResponseEntity<?> listar() {
		List<Empleado> empleados = empleadoService.findAll();
		return ResponseEntity.ok().body(empleados);
	}

	@GetMapping("/empleados/page/{page}")
	public ResponseEntity<?> listar(@PathVariable Integer page) {
		Page<Empleado> empleados = empleadoService.findAll(PageRequest.of(page, 4));
		return ResponseEntity.ok().body(empleados);
	}

	@GetMapping("/empleados/{id}")
	public ResponseEntity<?> obtenerEmpleadoById(@PathVariable Long id) {
		response = new HashMap<>();
		try {
			this.empleado = empleadoService.findById(id).orElse(null);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

		if (this.empleado == null) {
			response.put("mensaje", "El empleado no existe");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok().body(this.empleado);
	}

	@Secured({ "ROLE_ADMIN" })
	@PostMapping("/empleados")
	public ResponseEntity<?> create(@Valid @RequestBody Empleado empleado, BindingResult result) {
		response = new HashMap<>();
		this.empleado = null;

		if (result.hasErrors()) {

			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);

		}

		try {
			this.empleado = empleadoService.save(empleado);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al guardar el registro");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El empleado ha sido creado con éxito");
		response.put("empleado", this.empleado);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@Secured({ "ROLE_ADMIN" })
	@PutMapping("/empleados/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Empleado empleado, BindingResult result, @PathVariable Long id) {
		response = new HashMap<>();
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		try {
			this.empleado = empleadoService.findById(id).orElse(null);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (this.empleado == null) {
			response.put("mensaje", "El empleado no existe");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			if (this.empleado != null) {
				this.empleado.setNombre(empleado.getNombre());
				this.empleado.setApellido(empleado.getApellido());
				this.empleado.setEmail(empleado.getEmail());
				this.empleado.setRegion(empleado.getRegion());

				this.empleado = empleadoService.save(this.empleado);
			}
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al atualizar el registro");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		response.put("mensaje", "El empleado ha sido actualizado  con éxito");
		response.put("empleado", this.empleado);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@Secured({ "ROLE_ADMIN" })
	@DeleteMapping("/empleados/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		response = new HashMap<>();
		this.empleado = null;
		try {

			this.empleado = empleadoService.findById(id).orElse(null);

			if (this.empleado != null) {
				if (this.empleado.getFoto() != null && this.empleado.getFoto().length() > 0) {
					uploadService.eliminar(this.empleado.getFoto());
				}

				empleadoService.deleteById(id);
			}
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al eliminar el registro");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El empleado ha sido eliminado con éxito");
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	@PostMapping("/empleados/upload")
	public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Long id) {
		response = new HashMap<>();
		this.empleado = null;
		try {
			this.empleado = empleadoService.findById(id).orElse(null);

			if (this.empleado != null) {
				if (!archivo.isEmpty()) {
					String nombreArchivo = null;

					try {
						// Copia el archivo subido al servidor a la ruta escojida
						nombreArchivo = uploadService.copiar(archivo);

						if (empleado.getFoto() != null && empleado.getFoto().length() > 0) {
							uploadService.eliminar(empleado.getFoto());
						}

						this.empleado.setFoto(nombreArchivo);

						empleadoService.save(this.empleado);
						response.put("empleado", this.empleado);
						response.put("mensaje", "La imagen ha sido cargada con éxito");
					} catch (IOException e) {
						LOG.error("upload () {}", e);
						response.put("mensaje", "Error al subir la imagen " + nombreArchivo);
						response.put("error", e.getMessage().concat(": ").concat(e.getCause().getMessage()));
						return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
					}
				}
			} else {
				response.put("mensaje", "El empleado no existe con id " + id);
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);

			}
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al subir la imagen");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(response);

	}

	@GetMapping("/uploads/img/{nombreArchivo:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable("nombreArchivo") String nombreArchivo) {
		response = new HashMap<>();
		this.empleado = null;
		Resource recurso = null;
		try {
			recurso = uploadService.cargar(nombreArchivo);
		} catch (MalformedURLException e) {
			LOG.error("verFoto () {}", e);
		}
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
				.body(recurso);
	}

	@Secured({ "ROLE_ADMIN" })
	@GetMapping("/regiones")
	public ResponseEntity<?> listarRegiones() {
		List<Region> regiones = empleadoService.findAllRegiones();
		return ResponseEntity.ok().body(regiones);
	}
}
