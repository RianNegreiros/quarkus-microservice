package com.example.dto;

import entities.Restaurant;
import infra.dto.DTO;
import infra.dto.ValidDTO;

import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@ValidDTO
public class RestaurantDTO implements DTO {

    @NotBlank
    public String owner;

    @Pattern(regexp = "/^\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}$/")
    @NotBlank
    public String cnpj;

    @Size(min = 5, max = 50)
    public String name;

    public LocalizationDTO localization;

    @Override
    public boolean isValid(ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        if(Restaurant.find("cnpj", cnpj).count() > 0) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("Company already registered")
                .addPropertyNode("cnpj")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
