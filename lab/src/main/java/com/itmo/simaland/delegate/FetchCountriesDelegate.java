package com.itmo.simaland.delegate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.spin.Spin;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class FetchCountriesDelegate implements JavaDelegate {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        String countriesApiUrl = "http://api.geonames.org/countryInfoJSON?username=nastyabeggin";

        ResponseEntity<String> response = restTemplate.getForEntity(countriesApiUrl, String.class);
        String countriesJson = response.getBody();

        Map<String, String> countryMap = parseCountries(countriesJson);
        log.info("countryMap");
        log.info(countryMap.toString());
        log.info("countryList");
        log.info(countryMap.keySet().toString());
//        execution.setVariable("countryMap", countryMap);
        execution.setVariable("countryList", new ArrayList<>(countryMap.keySet()));
    }

    private Map<String, String> parseCountries(String countriesJson) throws Exception {
        Map<String, String> countryMap = new HashMap<>();
        JsonNode countriesObject = objectMapper.readTree(countriesJson);
        JsonNode countriesArray = countriesObject.get("geonames");

        for (JsonNode countryNode : countriesArray) {
            String countryName = countryNode.get("countryName").asText();
            String countryCode = countryNode.get("countryCode").asText();
            countryMap.put(countryName, countryCode);
        }

        return countryMap;
    }}
