INSERT INTO regiones (id, nombre) VALUES(1, 'Sudamérica');
INSERT INTO regiones (id, nombre) VALUES(2, 'Centroamérica');
INSERT INTO regiones (id, nombre) VALUES(3, 'Norteamérica');
INSERT INTO regiones (id, nombre) VALUES(4, 'Europa');
INSERT INTO regiones (id, nombre) VALUES(5, 'Asia');
INSERT INTO regiones (id, nombre) VALUES(6, 'África');
INSERT INTO regiones (id, nombre) VALUES(7, 'Oceanía');
INSERT INTO regiones (id, nombre) VALUES(8, 'Antártida');

INSERT INTO empleados (id, nombre, apellido, email, create_at,  cedula, region_id) VALUES(1, 'Jorge', 'López', 'jorge.lopez@gmail.com', '2017-08-28',  '1717172637', 1);
INSERT INTO empleados (id, nombre, apellido, email, create_at,  cedula, region_id) VALUES(2, 'Luis', 'Gordon', 'luis.gordon@gmail.com', '2017-08-28',  '1717172636', 1);

INSERT INTO usuarios (username, password, enabled, nombre, apellido, email) VALUES ('rene','$2a$10$ykhXmCAam5FUEF9GN.4Z8OwwWJidvMii6VFYe77cmS2X6oF6p4W86',true, 'René', 'Trávez','rene.travez@gmail.com');
INSERT INTO usuarios (username, password, enabled, nombre, apellido, email) VALUES ('admin','$2a$10$qGyDfZLBB.SgLv7GCP3uZe3oM38fVtr58T1iZ1LNOvO61loNUAAaK',true, 'Administrador', 'Administrador','admin@todo1.com');

INSERT INTO roles (nombre) VALUES ('ROLE_USER');
INSERT INTO roles (nombre) VALUES ('ROLE_ADMIN');

INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (1, 1);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (2, 2);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (2, 1);

INSERT INTO tipo_operaciones(descripcion, nombre) VALUES ('Entrada', 'Entrada');
INSERT INTO tipo_operaciones(descripcion, nombre) VALUES ('Salida', 'Salida');

INSERT INTO marcas (descripcion, nombre) VALUES ('Marvel', 'Marvel');
INSERT INTO marcas (descripcion, nombre) VALUES ('Dc Comics', 'Dc Comics');
INSERT INTO marcas (descripcion, nombre) VALUES ('Comunidad', 'Comunidad');

/* Populate tabla productos */
INSERT INTO productos (descripcion, nombre, precio, marca_id) VALUES ('Camiseta de Spiderman', 'Camiseta de Spiderman', 10, 1);
INSERT INTO productos (descripcion, nombre, precio, marca_id) VALUES ('Vaso de Spiderman', 'Vaso de Spiderman', 10, 1);
INSERT INTO productos (descripcion, nombre, precio, marca_id) VALUES ('Comics de Spiderman', 'Comics de Spiderman', 10, 1);
INSERT INTO productos (descripcion, nombre, precio, marca_id) VALUES ('Muñeco de Spiderman', 'Muñeco de Spiderman', 10, 1);
INSERT INTO productos (descripcion, nombre, precio, marca_id) VALUES ('Llavero de Spiderman', 'Llavero de Spiderman', 10, 1);

INSERT INTO productos (descripcion, nombre, precio, marca_id) VALUES ('Camiseta de Batman', 'Camiseta de Batman', 10, 2);
INSERT INTO productos (descripcion, nombre, precio, marca_id) VALUES ('Vaso de Batman', 'Vaso de Spiderman', 10, 2);
INSERT INTO productos (descripcion, nombre, precio, marca_id) VALUES ('Comics de Batman', 'Comics de Batman', 10, 2);
INSERT INTO productos (descripcion, nombre, precio, marca_id) VALUES ('Muñeco de Batman', 'Muñeco de Batman', 10, 2);
INSERT INTO productos (descripcion, nombre, precio, marca_id) VALUES ('Llavero de Batman', 'Llavero de Batman', 10, 2);

INSERT INTO productos (descripcion, nombre, precio, marca_id) VALUES ('Camiseta de Joker', 'Camiseta de Joker', 10, 3);
INSERT INTO productos (descripcion, nombre, precio, marca_id) VALUES ('Vaso de Joker', 'Vaso de Joker', 10, 3);
INSERT INTO productos (descripcion, nombre, precio, marca_id) VALUES ('Comics de Joker', 'Comics de Joker', 10, 3);
INSERT INTO productos (descripcion, nombre, precio, marca_id) VALUES ('Muñeco de Joker', 'Muñeco de Joker', 10, 3);
INSERT INTO productos (descripcion, nombre, precio, marca_id) VALUES ('Llavero de Joker', 'Llavero de Joker', 10, 3);

/* Creamos algunas facturas */
INSERT INTO facturas (descripcion, observacion, empleado_id, create_at) VALUES('Productos de Spiderman', 'Alguna nota importante!', 1, NOW());
INSERT INTO items_facturas (cantidad, factura_id, producto_id) VALUES(1, 1, 1);
INSERT INTO items_facturas (cantidad, factura_id, producto_id) VALUES(2, 1, 4);
INSERT INTO items_facturas (cantidad, factura_id, producto_id) VALUES(1, 1, 5);
INSERT INTO items_facturas (cantidad, factura_id, producto_id) VALUES(1, 1, 7);

INSERT INTO facturas (descripcion, observacion, empleado_id, create_at) VALUES('Productos de Batman', 'Alguna nota importante!', 1, NOW());
INSERT INTO items_facturas (cantidad, factura_id, producto_id) VALUES(3, 2, 6);








