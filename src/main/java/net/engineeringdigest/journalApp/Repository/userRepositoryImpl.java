package net.engineeringdigest.journalApp.Repository;

import net.engineeringdigest.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class userRepositoryImpl {
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> getUsersforSa(){
        Query query = new Query();
        query.addCriteria(Criteria.where("email").exists(true));

        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));
        List<User> all = mongoTemplate.find(query , User.class);
        return all;

    }
}
