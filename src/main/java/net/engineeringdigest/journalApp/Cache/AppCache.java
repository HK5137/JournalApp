package net.engineeringdigest.journalApp.Cache;

import jakarta.annotation.PostConstruct;
import net.engineeringdigest.journalApp.Repository.ConfigJournalAppRepo;
import net.engineeringdigest.journalApp.entity.ConfigJournalApp;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {
    public enum  Keys{
        WEATHER_API;

    }

    public Map<String, String> appCache;
    @Autowired
    private ConfigJournalAppRepo configJournalAppRepo;


    @PostConstruct
    public void init(){
        appCache=  new HashMap<>();
        List<ConfigJournalApp> all = configJournalAppRepo.findAll();
        for(ConfigJournalApp configJournalApp : all){
            appCache.put(configJournalApp.getKey(), configJournalApp.getValue());
        }

    }
}
