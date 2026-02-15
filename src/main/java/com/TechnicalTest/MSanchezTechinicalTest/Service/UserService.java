
package com.TechnicalTest.MSanchezTechinicalTest.Service;

import com.TechnicalTest.MSanchezTechinicalTest.DTO.UserUpdate;
import com.TechnicalTest.MSanchezTechinicalTest.Model.Address;
import com.TechnicalTest.MSanchezTechinicalTest.Model.Result;
import com.TechnicalTest.MSanchezTechinicalTest.Model.User;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private int TotalAddresses = 1;
    private final List<User> usersList = new ArrayList<>();

    public Result GetAllSortedBy(List<String> sortedBy) {
        Result result = new Result();

        try {

            List<User> sortedList = new ArrayList<>(usersList);

            if (sortedBy != null && !sortedBy.isEmpty()) {

                Comparator<User> comparator = null;

                for (String field : sortedBy) {

                    Comparator<User> current = switch (field.toLowerCase()) {
                        case "email" ->
                            Comparator.comparing(User::getEmail);
                        case "id" ->
                            Comparator.comparing(User::getId);
                        case "name" ->
                            Comparator.comparing(User::getName);
                        case "phone" ->
                            Comparator.comparing(User::getPhone);
                        case "tax_id" ->
                            Comparator.comparing(User::getTax_id);
                        case "created_at" ->
                            Comparator.comparing(User::getCreated_at);
                        default ->
                            null;
                    };

                    if (current != null) {
                        comparator = (comparator == null)
                                ? current
                                : comparator.thenComparing(current);
                    }
                }

                if (comparator != null) {
                    sortedList.sort(comparator);
                }
            }

            result.object = sortedList;
            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    public Result GetAllFilter(String filter) {
        Result result = new Result();

        try {
            String[] parts = filter.split("\\s");

            if (parts.length != 3) {
                result.correct = false;
                result.errorMessage = "invalid format. it must be: attributte+operator+value";
                return result;
            }

            String attribute = parts[0].toLowerCase();
            String operator = parts[1].toLowerCase();
            String value = parts[2];

            List<User> usersFiltered = usersList.stream()
                    .filter(user -> {
                        String attrValue;

                        switch (attribute) {
                            case "email":
                                attrValue = user.getEmail();
                                break;
                            case "id":
                                attrValue = user.getId();
                                break;
                            case "name":
                                attrValue = user.getEmail();
                                break;
                            case "phone":
                                attrValue = user.getPhone();
                                break;
                            case "tax_id":
                                attrValue = user.getTax_id();
                                break;
                            default:
                                return false;
                        }

                        if (attrValue == null) {
                            return false;
                        }

                        return switch (operator) {
                            case "eq" ->
                                attrValue.equals(value);
                            case "co" ->
                                attrValue.contains(value);
                            case "sw" ->
                                attrValue.startsWith(value);
                            case "ew" ->
                                attrValue.endsWith(value);
                            default ->
                                false;
                        };
                    })
                    .collect(Collectors.toList());

            result.object = usersFiltered;
            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    public Result Add(User user) {
        Result result = new Result();

        try {

            boolean phoneExists = usersList.stream()
                    .anyMatch(u -> u.getPhone().equalsIgnoreCase(user.getPhone()));

            if (phoneExists) {
                result.correct = false;
                result.errorMessage = "Phone number already exists.";
                return result;
            }

            boolean tax_idExists = usersList.stream()
                    .anyMatch(u -> u.getTax_id().equalsIgnoreCase(user.getTax_id()));

            if (tax_idExists) {
                result.correct = false;
                result.errorMessage = "Tax_id already exists.";
            }

            String idUUID = UUID.randomUUID().toString();
            user.setId(idUUID);

            String passwordEncypted = EncryptService.encrypt(user.getPassword());
            user.setPassword(passwordEncypted);

            user.setCreated_at(getMadagascarDate());

            if (user.getAddresses() != null) {
                for (Address address : user.getAddresses()) {
                    address.setId(TotalAddresses++);
                }
            }

            usersList.add(user);

            result.object = user;
            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    public Result Update(UserUpdate user, String id) {
        Result result = new Result();

        try {
            User userFind = null;

            for (User userFor : usersList) {
                if (userFor.getId().equals(id)) {
                    userFind = userFor;
                    break;
                }
            }

            if (userFind != null) {
                if (user.getEmail() != null) {
                    userFind.setEmail(user.getEmail());
                }

                if (user.getName() != null) {
                    userFind.setName(user.getName());
                }

                if (user.getPhone() != null) {
                    userFind.setPhone(user.getPhone());
                }

                if (user.getPassword() != null) {
                    userFind.setPassword(EncryptService.encrypt(user.getPassword()));
                }

                if (user.getTax_id() != null) {
                    userFind.setTax_id(user.getTax_id());
                }

                result.object = userFind;
                result.correct = true;
            }

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    public Result Delete(String id) {
        Result result = new Result();

        try {

            User userFind = null;

            for (User user : usersList) {
                if (user.getId().equals(id)) {
                    userFind = user;
                    break;
                }
            }

            if (userFind != null) {
                usersList.remove(userFind);
                result.correct = true;
            }

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    private Date getMadagascarDate() {
        ZoneId madagascarZone = ZoneId.of("Indian/Antananarivo");
        ZonedDateTime madagascarTime = ZonedDateTime.now(madagascarZone);
        return Date.from(madagascarTime.toInstant());
    }
}
