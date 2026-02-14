
package com.TechnicalTest.MSanchezTechinicalTest.Model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class Address {
    private int id;
    
    @NotBlank(message = "The address name is required")
    private String name;
    
    @NotBlank(message = "The street is required")
    private String street;
    
    @Pattern(regexp = "^[A-Z]{2,3}$", message = "Invalid country code.")
    private String country_code;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }
    
    
    
}
