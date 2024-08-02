CREATE TABLE IF NOT EXISTS sucursales
(
    uuid_sucursal uuid NOT NULL,
    nombre_sucursal character varying(100) NOT NULL,
    fecha_sucursal timestamp without time zone NOT NULL,
    uuid_franquicia uuid NOT NULL,
    CONSTRAINT sucursales_pkey PRIMARY KEY (uuid_sucursal),
    CONSTRAINT sucursales_uuid_franquicia_fkey FOREIGN KEY (uuid_franquicia)
        REFERENCES franquicias (uuid_franquicia) MATCH SIMPLE
);

CREATE UNIQUE INDEX IF NOT EXISTS sucursales_nombre_sucursal_uuid_franquicia_idx
    ON sucursales USING btree
        (nombre_sucursal ASC NULLS LAST, uuid_franquicia ASC NULLS LAST)
    WITH (deduplicate_items=True);