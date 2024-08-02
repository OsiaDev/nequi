CREATE TABLE IF NOT EXISTS productos
(
    uuid_producto uuid NOT NULL,
    nombre_producto character varying(100) NOT NULL,
    stock_producto bigint NOT NULL DEFAULT 0,
    fecha_producto timestamp without time zone NOT NULL,
    uuid_sucursal uuid NOT NULL,
    CONSTRAINT productos_uuid_sucursal_fkey FOREIGN KEY (uuid_sucursal)
        REFERENCES sucursales (uuid_sucursal) MATCH SIMPLE
);

CREATE UNIQUE INDEX IF NOT EXISTS productos_nombre_producto_uuid_sucursal_idx
    ON productos USING btree
        (nombre_producto ASC NULLS LAST, uuid_sucursal ASC NULLS LAST)
    WITH (deduplicate_items=True);