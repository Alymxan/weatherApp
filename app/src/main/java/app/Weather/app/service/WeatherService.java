package app.Weather.app.service;

import app.Weather.app.db.entity.Weather;
import app.Weather.app.db.repository.WeatherRepository;
import app.Weather.app.dto.MResponse;
import app.Weather.app.dto.MWeather;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;
import app.Weather.app.dto.MWeather.Response;

import java.util.Optional;
@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherService {

    private final WeatherRepository weatherRepository;

    private RestTemplate restTemplate;

    @Value("${weather.api.apiKey}")
    private String apiKey;


    public MWeather getWeather(String city){
        if (city.equals("")) return null;
        try{
            restTemplate = new RestTemplate();
            String apiURL = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;
            ResponseEntity<Response> response = restTemplate.getForEntity(apiURL, Response.class);

            if (response.getStatusCode() == HttpStatus.OK){
                Response dataResponse = response.getBody();
                log.info("dataResponse {}", dataResponse);
                if (dataResponse != null)
                    return MWeather.create(dataResponse);
            }

        }catch (Exception ex){
            log.error("Exception with 3rd party service {}", ex.getMessage());
        }
        return null;
    }

    public MResponse saveWeather(Weather weather){
        try{
            weatherRepository.save(weather);
        }catch (Exception ex){
            log.error("Exception with {}", ex.getMessage());
        }
        return MResponse.builder()
                .status("200")
                .response("successfully created")
                .build();
    }

    public MResponse updateWeather(Weather weather){
        try{
            Optional<Weather> dataResponse = weatherRepository.findBycity(weather.getCity());
            if (dataResponse.isPresent()){
                Weather dataWeather = dataResponse.get();
                dataWeather.setDescription(weather.getDescription());
                dataWeather.setCity((weather.getCity()));
                dataWeather.setTemperature(weather.getTemperature());
                weatherRepository.save(dataWeather);
            }
        }catch (Exception ex){
            log.error("Exception with {}", ex.getMessage());
        }
        return MResponse.builder()
                .status("200")
                .response("successfully created")
                .build();
    }
    public MResponse deleteWeather(String city){
        try{
            Optional<Weather> dataResponse = weatherRepository.findBycity(city);
            dataResponse.ifPresent(weather -> weatherRepository.deleteById(weather.getId()));
        }catch (Exception ex){
            log.error("Exception with {}", ex.getMessage());
        }
        return MResponse.builder()
                .status("200")
                .response("successfully created")
                .build();
    }
}