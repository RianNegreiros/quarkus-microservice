-- public.localization definition

-- Drop table

-- DROP TABLE public.localization;

CREATE TABLE public.localization (
                                     id int8 NOT NULL,
                                     latitude float8 NULL,
                                     longitude float8 NULL,
                                     CONSTRAINT localization_pkey PRIMARY KEY (id)
);