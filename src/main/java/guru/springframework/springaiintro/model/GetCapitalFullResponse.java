package guru.springframework.springaiintro.model;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public record GetCapitalFullResponse(
        @JsonPropertyDescription("This is the city name") String cityName,
    @JsonPropertyDescription("This is the city has a population") String population,
    @JsonPropertyDescription("This is the city is located in") String region,
    @JsonPropertyDescription("This is the primary language spoken") String language,
    @JsonPropertyDescription("This is the currency used") String currency
) {
}
