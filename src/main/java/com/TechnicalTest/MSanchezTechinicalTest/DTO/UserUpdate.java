package com.TechnicalTest.MSanchezTechinicalTest.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;

@Schema(description = "DTO for update endpoint")
public class UserUpdate {
    
    @Schema(hidden = true)
    private String id;
    private String email;
    private String name;
    
    @Pattern(regexp = "^\\+?\\d{10,13}$",
            message = "Phone number must be 10 digits and may include country code")
    private String phone;
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    
    @Pattern(regexp = "^[A-ZÑ&]{4}\\d{6}[A-Z0-9]{3}$",
            message = "Tax_id is not valid.")
    private String tax_id;

    public UserUpdate() {
    }
    
    public UserUpdate(String email, String name, String phone, String password, String tax_id) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.tax_id = tax_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTax_id() {
        return tax_id;
    }

    public void setTax_id(String tax_id) {
        this.tax_id = tax_id;
    }
}
