package app.Weather.app.service;

import app.Weather.app.db.entity.Weather;
import app.Weather.app.dto.MWeather;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ApplicationTest {
    @Mock
    private WeatherService weatherService;

    MWeather mWeather;

    @BeforeEach
    public void createObject(){
        mWeather = MWeather.builder()
                .city("Almaty")
                .temperature(213)
                .description("cold")
                .build();
    }

    @Test
    void shouldReturnWeather(){
        Mockito.when(weatherService.getWeather("Almaty")).thenReturn(mWeather);
        MWeather responseWeather = weatherService.getWeather("Almaty");
        assertEquals("Almaty", responseWeather.getCity());
        assertEquals(213, responseWeather.getTemperature());
        assertEquals("cold", responseWeather.getDescription());
    }

    @Test
    void shouldSaveWeather(){
        Weather weather = Weather.builder()
                .city("Shymkent")
                .temperature("250")
                .description("hot")
                .build();
        weatherService.saveWeather((weather));
        Mockito.verify(weatherService, Mockito.times(1)).saveWeather(weather);
    }
}
