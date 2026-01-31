package net.engineeringdigest.journalApp.Scheduler;

import net.engineeringdigest.journalApp.Cache.AppCache;
import net.engineeringdigest.journalApp.Repository.userRepositoryImpl;
import net.engineeringdigest.journalApp.Service.EmailService;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.enums.Sentiment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
@Component
public class UserScheduler {
    @Autowired
    private EmailService emailService;

    @Autowired
    private userRepositoryImpl userRepository;



    @Autowired
    private AppCache appCache;

    @Scheduled(cron = " 0 0/10 *  ? * *")
    private void clearCache(){
        appCache.init();
    }

    @Scheduled(cron = "0 0 9 * * SUN ")
    public void fetchUserAndSendSaMail(){
      List<User> users =   userRepository.getUsersforSa();
        for(User user : users) {
            List<JournalEntry>  journalEntries=user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment,Integer> sentimentCounts = new HashMap<>();
            for(Sentiment sentiment : sentiments){
                if(sentiment!=null){
                    sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment,0)+1);
                }
            }
            Sentiment mostFrequentSentiment =null;
            int maxCount= 0;
            for(Map.Entry<Sentiment,Integer> entry : sentimentCounts.entrySet()){
                if(entry.getValue()>maxCount){
                    mostFrequentSentiment=entry.getKey();
                    maxCount = entry.getValue();
                }

            }
            if(mostFrequentSentiment!=null){
                emailService.sendEmail(user.getEmail(),"Sentiment for last 7 days" , mostFrequentSentiment.toString());

            }

        }
    }
}
