CREATE TABLE IF NOT EXISTS public.franquicias
(
    uuid_franquicia uuid NOT NULL,
    nombre_franquicia character varying(100) NOT NULL,
    fecha_franquicia timestamp without time zone NOT NULL,
    CONSTRAINT franquicias_pkey PRIMARY KEY (uuid_franquicia)
);

CREATE UNIQUE INDEX IF NOT EXISTS franquicias_nombre_franquicia_idx
    ON franquicias USING btree
        (nombre_franquicia ASC NULLS LAST)
    WITH (deduplicate_items=True);