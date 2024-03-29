package app.Weather.app.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MResponse {

    private String status;

    private String response;
}
