
package com.TechnicalTest.MSanchezTechinicalTest.Component;

import com.TechnicalTest.MSanchezTechinicalTest.Model.User;
import com.TechnicalTest.MSanchezTechinicalTest.Service.CustomerDetailsService;
import com.TechnicalTest.MSanchezTechinicalTest.Service.UserService;
import jakarta.annotation.PostConstruct;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Starter {

    @Autowired
    private UserService userService;
    
    @Autowired
    private CustomerDetailsService customerDetailsService;

    @PostConstruct
    public void init() {

        User u1 = new User(
                "laura.martinez@email.com",
                "Laura Martínez",
                "5522334455",
                "3e5p2B9wZfKZsLh7f2l6uQ==",
                "MALR920315XYZ",
                getMadagascarDate()
        );

        User u2 = new User(
                "carlos.ramirez@email.com",
                "Carlos Ramírez",
                "5533445566",
                "Carlos#2026",
                "RACR880712ABC",
                getMadagascarDate()
        );

        User u3 = new User(
                "ana.lopez@gmail.com",
                "Ana López",
                "5544556677",
                "AnaSecure456",
                "LOAA950101DEF",
                getMadagascarDate()
        );
        
        // Agregar a CustomerDetailsService
        customerDetailsService.Add(u1);
        customerDetailsService.Add(u2);
        customerDetailsService.Add(u3);

        // Agregar a UserService
        userService.Add(u1);
        userService.Add(u2);
        userService.Add(u3);

        
        
    }

    private Date getMadagascarDate() {
        ZoneId madagascarZone = ZoneId.of("Indian/Antananarivo");
        ZonedDateTime madagascarTime = ZonedDateTime.now(madagascarZone);
        return Date.from(madagascarTime.toInstant());
    }

}
