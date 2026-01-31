package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.Repository.UserRepo;
import net.engineeringdigest.journalApp.Service.UserDetailsServiceImpl;
import net.engineeringdigest.journalApp.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
  @SpringBootTest
public class UserDetailsServiceImplTest {


     @Autowired
    private UserDetailsServiceImpl userDetailsService;

     @MockBean
     private UserRepo userRepo;



     @Test
    void loadUserByUserName(){

//        when(userRepo.findByUserName(ArgumentMatchers.anyString())).thenReturn(User.builder().userName("Hunny1").password("Hunny1").roles(new ArrayList<>()).build());
//        UserDetails user = userDetailsService.loadUserByUsername("Hunny");
//        assertNotNull(user);
    }
}
