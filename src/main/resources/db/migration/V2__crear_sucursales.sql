CREATE TABLE IF NOT EXISTS sucursales
(
    id_sucursal bigserial NOT NULL,
    nombre_sucursal character varying(100) NOT NULL,
    fecha_sucursal timestamp without time zone NOT NULL,
    id_franquicia bigint NOT NULL,
    CONSTRAINT sucursales_pkey PRIMARY KEY (id_sucursal),
    CONSTRAINT sucursales_id_franquicia_fkey FOREIGN KEY (id_franquicia)
        REFERENCES public.franquicias (id_franquicia) MATCH SIMPLE
);

CREATE UNIQUE INDEX IF NOT EXISTS sucursales_nombre_sucursal_id_franquicia_idx
    ON sucursales USING btree
        (nombre_sucursal ASC NULLS LAST, id_franquicia ASC NULLS LAST)
    WITH (deduplicate_items=True);