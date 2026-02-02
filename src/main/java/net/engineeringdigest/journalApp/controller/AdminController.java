package net.engineeringdigest.journalApp.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import net.engineeringdigest.journalApp.Cache.AppCache;
import net.engineeringdigest.journalApp.Service.UserService;
import net.engineeringdigest.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin Api's" , description = "Get User Details")
public class AdminController {

    @Autowired
    private AppCache appCache;

    @Autowired
    private UserService userService;
  @GetMapping("/clear-appCache")
    public void AppCache(){
        appCache.init();

    }


    @GetMapping("/all-users")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> all = userService.getAll();
        if(all !=null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
    @PostMapping("/create-admin")
    public void createUser(@RequestBody User user){
        userService.saveAdmin(user);
    }
}
