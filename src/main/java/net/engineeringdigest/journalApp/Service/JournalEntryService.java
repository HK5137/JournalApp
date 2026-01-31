package net.engineeringdigest.journalApp.Service;

import net.engineeringdigest.journalApp.Repository.JournalEntryRepo;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepo journalEntryRepo;
    @Autowired
    private UserService userService;


   @Transactional
    public void saveEntry(JournalEntry journalEntry, String username){
       try {
           User user = userService.findByUserName(username);
           journalEntry.setDate(LocalDateTime.now());
           JournalEntry  saved =  journalEntryRepo.save(journalEntry);
           user.getJournalEntries().add(saved);

           userService.saveUser(user);

       }catch (Exception e){
           System.out.print(e);
           throw new RuntimeException("There has been some error while saving entry" , e);
       }

    }
    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepo.save(journalEntry);
    }
    public List<JournalEntry> getAll(){
        return journalEntryRepo.findAll();
    }


    public Optional<JournalEntry> findbyId(ObjectId myId){
        return journalEntryRepo.findById(myId);

    }
    @Transactional
    public boolean deleteById(ObjectId myId, String username){
       boolean removed= false;
       try {
           User user = userService.findByUserName(username);
            removed = user.getJournalEntries().removeIf(x -> x.getId().equals(myId));
           if(removed){
               userService.saveUser(user);
               journalEntryRepo.deleteById(myId);

           }
           return removed;

       } catch (Exception e) {
           System.out.println(e);
           throw new RuntimeException(" Error Occured",e);
       }


    }


}
