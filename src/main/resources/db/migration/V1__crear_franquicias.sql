CREATE TABLE IF NOT EXISTS franquicias
(
    id_franquicia bigserial NOT NULL,
    nombre_franquicia character varying(100) NOT NULL,
    fecha_franquicia timestamp without time zone NOT NULL,
    CONSTRAINT franquicias_pkey PRIMARY KEY (id_franquicia)
);

CREATE UNIQUE INDEX IF NOT EXISTS franquicias_nombre_franquicia_idx
    ON franquicias USING btree
        (nombre_franquicia ASC NULLS LAST)
    WITH (deduplicate_items=True);