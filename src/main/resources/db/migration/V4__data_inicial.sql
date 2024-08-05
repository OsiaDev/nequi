INSERT INTO franquicias(nombre_franquicia, fecha_franquicia) VALUES ('Franquicia Pruebas 1', NOW());
INSERT INTO franquicias(nombre_franquicia, fecha_franquicia) VALUES ('Franquicia Pruebas 2', NOW());
INSERT INTO franquicias(nombre_franquicia, fecha_franquicia) VALUES ('Franquicia Pruebas 3', NOW());

INSERT INTO sucursales(nombre_sucursal, fecha_sucursal, id_franquicia) VALUES ('Sucursal 1', NOW(), 1);
INSERT INTO sucursales(nombre_sucursal, fecha_sucursal, id_franquicia) VALUES ('Sucursal 2', NOW(), 1);
INSERT INTO sucursales(nombre_sucursal, fecha_sucursal, id_franquicia) VALUES ('Sucursal 1', NOW(), 2);
INSERT INTO sucursales(nombre_sucursal, fecha_sucursal, id_franquicia) VALUES ('Sucursal 2', NOW(), 2);
INSERT INTO sucursales(nombre_sucursal, fecha_sucursal, id_franquicia) VALUES ('Sucursal 1', NOW(), 3);
INSERT INTO sucursales(nombre_sucursal, fecha_sucursal, id_franquicia) VALUES ('Sucursal 2', NOW(), 3);

INSERT INTO productos(nombre_producto, stock_producto, fecha_producto, id_sucursal) VALUES ('Producto 1', 20, NOW(), 1);
INSERT INTO productos(nombre_producto, stock_producto, fecha_producto, id_sucursal) VALUES ('Producto 2', 10, NOW(), 1);
INSERT INTO productos(nombre_producto, stock_producto, fecha_producto, id_sucursal) VALUES ('Producto 3', 50, NOW(), 1);
INSERT INTO productos(nombre_producto, stock_producto, fecha_producto, id_sucursal) VALUES ('Producto 1', 10, NOW(), 2);
INSERT INTO productos(nombre_producto, stock_producto, fecha_producto, id_sucursal) VALUES ('Producto 2', 50, NOW(), 2);
INSERT INTO productos(nombre_producto, stock_producto, fecha_producto, id_sucursal) VALUES ('Producto 3', 20, NOW(), 2);
INSERT INTO productos(nombre_producto, stock_producto, fecha_producto, id_sucursal) VALUES ('Producto 1', 30, NOW(), 3);
INSERT INTO productos(nombre_producto, stock_producto, fecha_producto, id_sucursal) VALUES ('Producto 2', 40, NOW(), 3);
INSERT INTO productos(nombre_producto, stock_producto, fecha_producto, id_sucursal) VALUES ('Producto 3', 80, NOW(), 3);
INSERT INTO productos(nombre_producto, stock_producto, fecha_producto, id_sucursal) VALUES ('Producto 1', 100, NOW(), 4);
INSERT INTO productos(nombre_producto, stock_producto, fecha_producto, id_sucursal) VALUES ('Producto 2', 90, NOW(), 4);
INSERT INTO productos(nombre_producto, stock_producto, fecha_producto, id_sucursal) VALUES ('Producto 3', 30, NOW(), 4);
INSERT INTO productos(nombre_producto, stock_producto, fecha_producto, id_sucursal) VALUES ('Producto 1', 50, NOW(), 5);
INSERT INTO productos(nombre_producto, stock_producto, fecha_producto, id_sucursal) VALUES ('Producto 2', 60, NOW(), 5);
INSERT INTO productos(nombre_producto, stock_producto, fecha_producto, id_sucursal) VALUES ('Producto 3', 70, NOW(), 5);
INSERT INTO productos(nombre_producto, stock_producto, fecha_producto, id_sucursal) VALUES ('Producto 1', 70, NOW(), 6);
INSERT INTO productos(nombre_producto, stock_producto, fecha_producto, id_sucursal) VALUES ('Producto 2', 20, NOW(), 6);
INSERT INTO productos(nombre_producto, stock_producto, fecha_producto, id_sucursal) VALUES ('Producto 3', 10, NOW(), 6);