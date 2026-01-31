package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.Repository.UserRepo;
import net.engineeringdigest.journalApp.Service.UserService;
import net.engineeringdigest.journalApp.entity.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class userServiceTests {
   @Autowired
  private  UserRepo userRepo;

   @Autowired
   private UserService userService;

   @Disabled
   @ParameterizedTest
   @ArgumentsSource(userArgumentProvider.class)
    public void testFindByUserName(User user){

    assertTrue(userService.saveNewUser(user));
    }


}
