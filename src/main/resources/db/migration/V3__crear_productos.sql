CREATE TABLE IF NOT EXISTS productos
(
    id_producto bigserial NOT NULL,
    nombre_producto character varying(100) NOT NULL,
    stock_producto bigint NOT NULL DEFAULT 0,
    fecha_producto timestamp without time zone NOT NULL,
    id_sucursal bigint NOT NULL,
    CONSTRAINT productos_id_sucursal_fkey FOREIGN KEY (id_sucursal)
        REFERENCES sucursales (id_sucursal) MATCH SIMPLE
);

CREATE UNIQUE INDEX IF NOT EXISTS productos_nombre_producto_id_sucursal_idx
    ON productos USING btree
        (nombre_producto ASC NULLS LAST, id_sucursal ASC NULLS LAST)
    WITH (deduplicate_items=True);