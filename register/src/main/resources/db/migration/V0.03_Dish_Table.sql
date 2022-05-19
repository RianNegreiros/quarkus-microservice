-- public.dish definition

-- Drop table

-- DROP TABLE public.dish;

CREATE TABLE public.dish (
                             id int8 NOT NULL,
                             description varchar(255) NULL,
                             "name" varchar(255) NULL,
                             price numeric(19, 2) NULL,
                             restaurant_id int8 NULL,
                             CONSTRAINT dish_pkey PRIMARY KEY (id)
);


-- public.dish foreign keys

ALTER TABLE public.dish ADD CONSTRAINT fk2vogfxta1enaixbdpno6pb1rd FOREIGN KEY (restaurant_id) REFERENCES public.restaurant(id);
