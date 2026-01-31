package net.engineeringdigest.journalApp.Service;


import net.engineeringdigest.journalApp.Api.Response.WeatherResponse;
import net.engineeringdigest.journalApp.Cache.AppCache;
import net.engineeringdigest.journalApp.constants.Placeholders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {
    @Value("${weather.api.key}")
    private String Key;

    @Autowired
    private AppCache appCache;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisService redisService;

    public WeatherResponse getWeather(String city){
       WeatherResponse weatherResponse =  redisService.get( city,WeatherResponse.class);
       if(weatherResponse!=null)return weatherResponse;
       else{
           String FinalApi =   appCache.appCache.get(AppCache.Keys.WEATHER_API.toString()).replace(Placeholders.CITY, city).replace(Placeholders.API_KEY, Key);
           ResponseEntity<WeatherResponse> resp = restTemplate.exchange(FinalApi, HttpMethod.GET,null, WeatherResponse.class);
           resp.getStatusCode();
           WeatherResponse body = resp.getBody();
           if(body!=null){
               redisService.set(city, body , 240L);
           }
           return body;

       }


    }




}
