package com.TechnicalTest.MSanchezTechinicalTest.RestController;

import com.TechnicalTest.MSanchezTechinicalTest.DTO.UserUpdate;
import com.TechnicalTest.MSanchezTechinicalTest.Model.Result;
import com.TechnicalTest.MSanchezTechinicalTest.Model.User;
import com.TechnicalTest.MSanchezTechinicalTest.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User RestController", description = "Controller focused on user methods")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("users")
public class UserRestController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Retrieve users sorted by different parameters", description = "Return a list of users stored in the array sorted by the attribute in the query parameter sortedBy")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "The users were successfully obtained"),
        @ApiResponse(responseCode = "403", description = "You are not authorized to make the request.")
    })
    @GetMapping
    public ResponseEntity GetAllSortedBy(
            @Parameter(
                description = "Select an option to sort",
                schema = @Schema(
                    allowableValues = {"email", "id", "name", "phone", "tax_id", "created_at"}
                )
            )
            @RequestParam(required = false) List<String> sortedBy) {
        Result result = userService.GetAllSortedBy(sortedBy);

        List<User> users = (List<User>) result.object;

        if (result.correct) {
            if (result.object != null && !users.isEmpty()) {
                return ResponseEntity.ok(result.object);
            } else {
                return ResponseEntity.noContent().build();
            }
        } else {
            return ResponseEntity.badRequest().body(result.errorMessage);
        }
    }

    @Operation(summary = "Retrieve users filtered by different parameters", description = "Return a list of users stored in the array filtered by the attribute in the query parameter filter")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "The users were successfully obtained"),
        @ApiResponse(responseCode = "403", description = "You are not authorized to make the request.")
    })
    @GetMapping("/filter")
    public ResponseEntity GetAllFilter(
            @Parameter(
                description = "Text used to filter users by [attribute]+[operator]+[value]",
                example = "email ew email.com"
            )
            @RequestParam String filter) {
        Result result = userService.GetAllFilter(filter);

        List<User> users = (List<User>) result.object;

        if (result.correct) {
            if (result.object != null && !users.isEmpty()) {
                return ResponseEntity.ok(result.object);
            } else {
                return ResponseEntity.badRequest().body(result.errorMessage);
            }
        } else {
            return ResponseEntity.badRequest().body(result.errorMessage);
        }

    }

    @Operation(summary = "Store a new user", description = "Store a new user in the array")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "The user was successfully added"),
        @ApiResponse(responseCode = "403", description = "You are not authorized to make the request.")
    })
    @PostMapping
    public ResponseEntity Add(@Valid @RequestBody User user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        Result result = userService.Add(user);

        if (result.correct) {
            return ResponseEntity.status(201).body(result.object);
        } else {
            return ResponseEntity.badRequest().body(result.errorMessage);
        }

    }

    @Operation(summary = "Update an user", description = "Update an user attribute by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "The user was successfully updated"),
        @ApiResponse(responseCode = "403", description = "You are not authorized to make the request.")
    })
    @PatchMapping("/{id}")
    public ResponseEntity Update(@Valid @RequestBody UserUpdate user, BindingResult bindingResult, @PathVariable String id) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        Result result = userService.Update(user, id);
        if (result.correct) {
            return ResponseEntity.status(201).body(result.object);
        } else {
            return ResponseEntity.badRequest().body(result.errorMessage);
        }
    }

    @Operation(summary = "Remove an user", description = "Remove an user from the array by Id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "The users were successfully removed"),
        @ApiResponse(responseCode = "403", description = "You are not authorized to make the request.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity Delete(@PathVariable String id) {
        Result result = userService.Delete(id);
        if (result.correct) {
            return ResponseEntity.status(200).body(result.object);
        } else {
            return ResponseEntity.badRequest().body(result.errorMessage);
        }
    }
}
