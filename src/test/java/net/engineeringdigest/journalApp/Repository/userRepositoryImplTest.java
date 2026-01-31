package net.engineeringdigest.journalApp.Repository;

import net.engineeringdigest.journalApp.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class userRepositoryImplTest {
    @Autowired
    private userRepositoryImpl userRepository;

    @Test
    public void testSaveNewUser(){
        userRepository.getUsersforSa();

    }
}
