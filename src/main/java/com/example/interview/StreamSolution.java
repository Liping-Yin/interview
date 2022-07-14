package com.example.interview;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

public class StreamSolution {

    public void testMethod() throws IOException {
        // Method2 : Stream and POJO
        // For simplicity the Container class only has two attributes <- different from the source data
        //Retrieve search data

        List<Container> containers = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl
                = "https://api.datasearch.elsevier.com/api/v4/search?query=spring";
        ResponseEntity<String> response
                = restTemplate.getForEntity(resourceUrl, String.class);
        ObjectMapper mapper = new ObjectMapper();

        //Get the most recent date and its id
        JsonNode root = null;
        Container mostRecentContainer = null;
        {
            try {

                root = mapper.readTree(response.getBody());
                JsonNode results = root.path("results");
                List<JsonNode> z = new ObjectMapper().readerForListOf(JsonNode.class).readValue(results);
                z.forEach(item -> {
                    containers.add(new Container(Instant.parse(item.path("updatedDate").asText()), item.path("id").asText()));
                });
                Comparator<Container> cmp = Comparator.comparing(Container::getUpdatedDate);
                Optional<Container> optionalContainer= containers.stream().max(cmp);
                mostRecentContainer= optionalContainer.isPresent() ? optionalContainer.get():null;

                System.out.println("===================================================");
                System.out.println("Most recent date and id : " );
                System.out.println(mostRecentContainer.getUpdatedDate());
                System.out.println(mostRecentContainer.getId());
                System.out.println("===================================================");

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        // Get container based on the id

        String containerUrl = "https://api.datasearch.elsevier.com/api/v4/container/";
        ResponseEntity<String> containerResponse
                = restTemplate.getForEntity(containerUrl + mostRecentContainer.getId(), String.class);
        root = mapper.readTree(containerResponse.getBody());

        // print out result as key value pairs
        System.out.println("===================================================");
        System.out.println("The container with most recent date: ");
        Map<String, Object> containerResult = mapper.convertValue(root, new TypeReference<Map<String, Object>>() {
        });
        for (Map.Entry<String, Object> entry : containerResult.entrySet()) {
            System.out.println(entry);
        }

       /*
       emails: vasylievd@science.regn.net; dmytro@vasyliev.nl

        0. receive a link with Swagged Doc
        1. Call endpoint
        2. find the most recent item
        3. Call another endpoint using

       It would be great if you put this into accessible Git repo (GitHub or GitLab) for me to track progress.

       0. Finish it with current approach. Use JsonNode and its API to do the job
       0.1. Now we got a date needed. Another field is "ïd" (very long one)
       0.2. You iterate over the List<> and do the job.
       0.3. Call https://datasearch.elsevier.com/api/docs#/search/getContainer

       1. Get rid of JsonNode and go with POJO (DTO) objects
       1.1. You can also iterate
       1.2. Use Streams

       TOP
       2. Utilize Spring DI framework
       2.1. Introduce Components (@Service, @Component, etc.)
       2.2. Use actual dependency injection
        */

    }
}
