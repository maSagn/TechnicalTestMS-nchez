package com.TechnicalTest.MSanchezTechinicalTest.Component;

import com.TechnicalTest.MSanchezTechinicalTest.Model.Address;
import com.TechnicalTest.MSanchezTechinicalTest.Model.User;
import com.TechnicalTest.MSanchezTechinicalTest.Service.CustomerDetailsService;
import com.TechnicalTest.MSanchezTechinicalTest.Service.UserService;
import jakarta.annotation.PostConstruct;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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


        List<Address> addresses1 = new ArrayList<>();

        Address a1 = new Address();
       // a1.setId(1);
        a1.setName("Casa");
        a1.setStreet("Av. Real 123");
        a1.setCountry_code("MX");

        addresses1.add(a1);

        User u1 = new User(
                "laura.martinez@email.com",
                "Laura Martínez",
                "5522334455",
                "3e5p2B9wZfKZsLh7f2l6uQ==",
                "MALR920315XYZ",
                getMadagascarDate(),
                addresses1
        );

        List<Address> addresses2 = new ArrayList<>();

        Address a2 = new Address();
        //a2.setId(2);
        a2.setName("Sucursal Madrid");
        a2.setStreet("Calle Gran Vía 28");
        a2.setCountry_code("ES");
        
        Address a3 = new Address();
        //a3.setId(3);
        a3.setName("Oficina");
        a3.setStreet("Blvd. Cervantes 100");
        a3.setCountry_code("MX");

        addresses2.add(a2);
        addresses2.add(a3);

        User u2 = new User(
                "miguel.masg.2001@gmail.com",
                "Miguel Sanchez",
                "5912345678",
                "3e5p2B9wZfKZsLh7f2l6uQ==",
                "SAGM010331V6A",
                getMadagascarDate(),
                addresses2
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
