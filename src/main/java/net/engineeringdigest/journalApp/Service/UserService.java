package net.engineeringdigest.journalApp.Service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.Repository.UserRepo;
import net.engineeringdigest.journalApp.Repository.userRepositoryImpl;
import net.engineeringdigest.journalApp.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class UserService {
    @Autowired
    private UserRepo userRepo;



    private static  final PasswordEncoder passwordencoder = new BCryptPasswordEncoder();






    public boolean saveNewUser(User user){

        try{
            user.setPassword(passwordencoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepo.save(user);
            return true;

        }catch (Exception e){
            log.error("Hunny");
            log.info("hh");
            return false;
        }

    }
    public void saveUser(User user){

        userRepo.save(user);
    }
    public List<User> getAll(){
        return userRepo.findAll();
    }


    public  User findByUserName(String username){
        return userRepo.findByUserName(username);
    }

    public void saveAdmin(User user){
        user.setPassword(passwordencoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER" , "ADMIN"));
        userRepo.save(user);

    }


}
