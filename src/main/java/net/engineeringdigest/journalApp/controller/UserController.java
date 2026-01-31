package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.Api.Response.WeatherResponse;
import net.engineeringdigest.journalApp.Repository.UserRepo;
import net.engineeringdigest.journalApp.Repository.userRepositoryImpl;
import net.engineeringdigest.journalApp.Service.UserService;
import net.engineeringdigest.journalApp.Service.WeatherService;
import net.engineeringdigest.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
   private UserService userService;

    @Autowired
    private UserRepo UserRepo;

    @Autowired
      private WeatherService weatherService;

    @Autowired
    private userRepositoryImpl userRepositoryImpl;



    @GetMapping("/all-SA")
    public List<User> getSA(){
     List<User> all =   userRepositoryImpl.getUsersforSa();
        return all;
    }


   @PutMapping()
    public ResponseEntity<User> updateUser( @RequestBody User user ){
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String username = authentication.getName();
       User userInDb = userService.findByUserName(username);
       if(userInDb!=null){
           userInDb.setUserName(user.getUserName());
           userInDb.setPassword(user.getPassword());
           userService.saveNewUser(userInDb);
           return  new ResponseEntity<>(HttpStatus.OK);

       }else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
   }
    @DeleteMapping
    public ResponseEntity<User> deleteUserById( @RequestBody User user ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserRepo.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<?> greetings( ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse resp =  weatherService.getWeather("Mumbai");
        String greeting  = "";
        if(resp!=null){
            greeting = " Weather feels like " +  resp.getCurrent().getFeelslike();
        }

        return new ResponseEntity<>("Hi " + authentication.getName() + greeting , HttpStatus.OK );
    }

}
