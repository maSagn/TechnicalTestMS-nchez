
package com.TechnicalTest.MSanchezTechinicalTest.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO for login endpoint")
public class Login {
    private String tax_id;
    private String password;

    public String getTax_id() {
        return tax_id;
    }

    public void setTax_id(String tax_id) {
        this.tax_id = tax_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
