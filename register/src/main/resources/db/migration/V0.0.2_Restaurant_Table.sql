-- public.restaurant definition

-- Drop table

-- DROP TABLE public.restaurant;

CREATE TABLE public.restaurant (
                                   id int8 NOT NULL,
                                   cnpj varchar(255) NULL,
                                   creationdate timestamp NULL,
                                   "name" varchar(255) NULL,
                                   "owner" varchar(255) NULL,
                                   updatedate timestamp NULL,
                                   localization_id int8 NULL,
                                   CONSTRAINT restaurant_pkey PRIMARY KEY (id)
);


-- public.restaurant foreign keys

ALTER TABLE public.restaurant ADD CONSTRAINT fkqe8wmqxulik6pdkvkeww4qrfn FOREIGN KEY (localization_id) REFERENCES public.localization(id);
