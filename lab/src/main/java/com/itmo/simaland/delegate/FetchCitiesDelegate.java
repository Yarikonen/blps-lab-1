package com.itmo.simaland.delegate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.Spin;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class FetchCitiesDelegate implements JavaDelegate {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String selectedCountryName = (String) execution.getVariable("select_country");
        Map<String, String> countryMap = (Map<String, String>) execution.getVariable("countryMap");
        String selectedCountryCode = countryMap.get(selectedCountryName);

        RestTemplate restTemplate = new RestTemplate();
        String citiesApiUrl = "http://api.geonames.org/searchJSON?country=" + selectedCountryCode + "&maxRows=10000&username=nastyabeggin";

        log.info("citiesApiUrl");
        log.info(citiesApiUrl);
        ResponseEntity<String> response = restTemplate.getForEntity(citiesApiUrl, String.class);
        String citiesJson = response.getBody();

        List<String> cityNames = parseCities(citiesJson);
        execution.setVariable("cityList", cityNames);
    }

    private List<String> parseCities(String citiesJson) throws Exception {
        List<String> cityNames = new ArrayList<>();
        JsonNode citiesObject = objectMapper.readTree(citiesJson);
        JsonNode citiesArray = citiesObject.get("geonames");

        for (JsonNode cityNode : citiesArray) {
            String cityName = cityNode.get("name").asText();
            cityNames.add(cityName);
        }

        return cityNames;
    }
}

