
package com.TechnicalTest.MSanchezTechinicalTest.Service;

import com.TechnicalTest.MSanchezTechinicalTest.Model.User;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CustomerDetailsService {

    private final List<User> usersList = new ArrayList<>();

    public User findByTaxId(String tax_id) {
        for (User u : usersList) {
            if (u.getTax_id().equalsIgnoreCase(tax_id)) {
                return u;
            }
        }
        return null;
    }
    
    public void Add(User user) {
        usersList.add(user);
    }

}
