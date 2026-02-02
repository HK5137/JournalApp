package net.engineeringdigest.journalApp.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import net.engineeringdigest.journalApp.Service.JournalEntryService;
import net.engineeringdigest.journalApp.Service.UserService;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
@Tag(name="Journal-entry Api's" , description = "Get , create & update entries")
public class JournalEntryController {
    @Autowired
    private JournalEntryService journalEntryService;

     @Autowired
     private UserService userService;




 @PostMapping()
 public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){

     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
     String username = authentication.getName();

       try {
           myEntry.setDate(LocalDateTime.now());
           journalEntryService.saveEntry(myEntry,username);
           return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
       }catch (Exception e){
           return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
       }
    }


    @GetMapping()
    public ResponseEntity<List<JournalEntry>> getAllEntriesofUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
     User user = userService.findByUserName(username);
        List<JournalEntry> all = user.getJournalEntries();
        if(!all.isEmpty() && all!=null){
            return new ResponseEntity<>(all, HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable String myId){
     ObjectId objId = new ObjectId(myId);


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUserName(username);
        List<JournalEntry> collect= user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).toList();
        if(!collect.isEmpty()){
            Optional<JournalEntry>  journalEntry= journalEntryService.findbyId(objId);
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);

        }

      return new ResponseEntity<>( HttpStatus.NOT_FOUND);
 }


    @DeleteMapping("/del/{Id}")
    public ResponseEntity<?>  delete(@PathVariable ObjectId Id ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();


       boolean removed=   journalEntryService.deleteById(Id , username);
       if(removed){
           return new ResponseEntity<>(HttpStatus.NO_CONTENT);

       }else{
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);

       }

    }


    @PutMapping("/upd/{Id}")
    public ResponseEntity<JournalEntry> update(@PathVariable ObjectId Id , @RequestBody  JournalEntry myEntry){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userService.findByUserName(username);
        List<JournalEntry> collect= user.getJournalEntries().stream().filter(x -> x.getId().equals(Id)).toList();
        if(!collect.isEmpty()){
            Optional<JournalEntry> journalentry = journalEntryService.findbyId(Id);
            if(journalentry.isPresent()){
                JournalEntry old = journalentry.get();
                old.setContent(myEntry.getContent()!=null && !myEntry.getContent().equals("")? myEntry.getContent() : old.getContent());
                old.setTitle(myEntry.getTitle()!=null && !myEntry.getTitle().equals("")? myEntry.getTitle() : old.getTitle());
                journalEntryService.saveEntry(old);
                return new ResponseEntity<>(old , HttpStatus.OK);

            }


        }


        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

}
