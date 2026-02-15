package com.TechnicalTest.MSanchezTechinicalTest;

import com.TechnicalTest.MSanchezTechinicalTest.DTO.UserUpdate;
import com.TechnicalTest.MSanchezTechinicalTest.Model.Address;
import com.TechnicalTest.MSanchezTechinicalTest.Model.Result;
import com.TechnicalTest.MSanchezTechinicalTest.Model.User;
import com.TechnicalTest.MSanchezTechinicalTest.Service.EncryptService;
import com.TechnicalTest.MSanchezTechinicalTest.Service.UserService;
import jakarta.mail.internet.ParseException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UsuarioJUnit {

    @Autowired
    UserService userService;

    @Test
    public void GetAllSortedBy() {

        List<String> sortedBy = List.of("name");

        Result result = userService.GetAllSortedBy(sortedBy);

        Assertions.assertTrue(result.correct);
        Assertions.assertNotNull(result.object);
        Assertions.assertInstanceOf(Result.class, result);
    }

    @Test
    public void GetAllFilter() {

        String filter = "email ew gmail.com";

        Result result = userService.GetAllFilter(filter);

        Assertions.assertTrue(result.correct);
        Assertions.assertNotNull(result.object);
        Assertions.assertInstanceOf(Result.class, result);
    }

    @Test
    public void AddUser() throws ParseException, Exception {
        User user = new User();

        String idUUID = UUID.randomUUID().toString();
        user.setId(idUUID);
        user.setEmail("junit@junit.com");
        user.setName("JUnit Test");
        user.setPhone("5587654321");
        user.setPassword(EncryptService.encrypt("user123"));
        user.setTax_id("JJUU310402F9E");
        user.setCreated_at(getMadagascarDate());

        user.addresses = new ArrayList<>();
        Address address = new Address();
        address.setId(23);
        address.setName("Street JUnit");
        address.setStreet("23");
        address.setCountry_code("US");

        user.addresses.add(address);

        Result result = userService.Add(user);

        Assertions.assertTrue(result.correct);
        Assertions.assertNull(result.errorMessage);
        Assertions.assertNotNull(result.object);

    }

    @Test
    public void Delete() {
        String tax_id = "MALR920315XYZ";
        String id = null;

        List<User> allUsers = (List<User>) userService.GetAllSortedBy(List.of("name")).object;

        for (User user : allUsers) {
            if (tax_id.equals(user.getTax_id())) {
                id = user.getId();
                break;
            }
        }

        Result result = userService.Delete(id);

        Assertions.assertTrue(result.correct);
        Assertions.assertNull(result.errorMessage);
        Assertions.assertNull(result.object);
    }

    @Test
    public void Update() throws Exception {
        String tax_id = "MALR920315XYZ";
        String id = null;

        List<User> allUsers = (List<User>) userService.GetAllSortedBy(List.of("name")).object;

        for (User user : allUsers) {
            if (tax_id.equals(user.getTax_id())) {
                id = user.getId();
                break;
            }
        }

        Assertions.assertNotNull(id);

        UserUpdate userUpdate = new UserUpdate();
        userUpdate.setEmail("userJUnit@gmail.com");
        userUpdate.setName("user Actualizada");
        userUpdate.setPhone("5551122334");
        userUpdate.setPassword(EncryptService.encrypt("admin123"));
        userUpdate.setTax_id("MALR921315XYZ");
        
        Result result = userService.Update(userUpdate, id);
        
        Assertions.assertTrue(result.correct);
        Assertions.assertNotNull(result.object);
    }

    private Date getMadagascarDate() {
        ZoneId madagascarZone = ZoneId.of("Indian/Antananarivo");
        ZonedDateTime madagascarTime = ZonedDateTime.now(madagascarZone);
        return Date.from(madagascarTime.toInstant());
    }
}
