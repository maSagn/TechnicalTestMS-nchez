
package com.TechnicalTest.MSanchezTechinicalTest.RestController;

import com.TechnicalTest.MSanchezTechinicalTest.Model.Result;
import com.TechnicalTest.MSanchezTechinicalTest.Model.User;
import com.TechnicalTest.MSanchezTechinicalTest.Service.UserService;
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

@RestController
@RequestMapping("users")
public class UserRestController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping
    public ResponseEntity GetAllSortedBy(@RequestParam(required = false) List<String> sortedBy) {
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
    
    @GetMapping("/filter")
    public ResponseEntity GetAllFilter(@RequestParam String filter) {
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
    
    @PostMapping
    public ResponseEntity Add(@Valid @RequestBody User user, BindingResult bindingResult) {
        
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        
        Result result = userService.Add(user);
        
        if (result.correct) {
            return ResponseEntity.status(201).body(result.object); // CREATED
        }else {
            return ResponseEntity.badRequest().body(result.errorMessage);
        }
        
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity Update(@Valid @RequestBody User user, BindingResult bindingResult, @PathVariable String id) {
        
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        
        Result result = userService.Update(user, id);
        if (result.correct) {
            return ResponseEntity.status(201).body(result.object);
        }else {
            return ResponseEntity.badRequest().body(result.errorMessage);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity Delete(@PathVariable String id) {
        Result result = userService.Delete(id);
        if (result.correct) {
            return ResponseEntity.status(200).body(result.object);
        }else {
            return ResponseEntity.badRequest().body(result.errorMessage);
        }
    }
}
