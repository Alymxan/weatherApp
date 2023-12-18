package app.Weather.app.controller;

import app.Weather.app.db.entity.Weather;
import app.Weather.app.dto.MResponse;
import app.Weather.app.dto.MWeather;
import app.Weather.app.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MyFirstController {

    private final WeatherService weatherService;

    @GetMapping("testFirstEndpoint")
    public MResponse testFirstEndpoint() {
        return MResponse.builder()
                .response("tested first endpoint successfully")
                .status("200")
                .build();
    }

    @GetMapping("/getWeather")// READ
    public MWeather getWeather(@RequestParam String city) {
        return weatherService.getWeather(city);
    }

    @PostMapping("/saveWeather")
    public MResponse saveWeather(@RequestBody Weather weather) {
        return weatherService.saveWeather(weather);
    }

    @PutMapping("/updateWeather")
    public MResponse updateWeather(@RequestBody Weather weather) {
        return weatherService.updateWeather(weather);
    }

    @DeleteMapping("/deleteWeather")
    public MResponse deleteWeather(@RequestParam String city) {
        return weatherService.deleteWeather(city);
    }
}
