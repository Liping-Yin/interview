package com.example.interview;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JasonNodeSolution {

    public void testMethod() throws IOException {
        //Method 1: JasonNode
        //Retrieve search data
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl
                = "https://api.datasearch.elsevier.com/api/v4/search?query=spring";
        ResponseEntity<String> response
                = restTemplate.getForEntity(resourceUrl, String.class);
        ObjectMapper mapper = new ObjectMapper();

        //Get the most recent date and its id
        JsonNode root = null;
        Instant maxDateTime = null;
        String maxDateId = null;
        {
            try {

                root = mapper.readTree(response.getBody());
                JsonNode results = root.path("results");
                List<JsonNode> z = new ObjectMapper().readerForListOf(JsonNode.class).readValue(results);
                Map<Instant, String> dates = new HashMap<>();
                z.forEach(item -> {
                    dates.put(Instant.parse(item.path("updatedDate").asText()), item.path("id").asText());
                });

                maxDateTime = Collections.max(dates.keySet());
                maxDateId = dates.get(maxDateTime);

//                System.out.println(dates);
                //System.out.println(results);

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        System.out.println("===================================================");
        System.out.println("Most recent date and id : " );
        System.out.println(maxDateTime);
        System.out.println(maxDateId);
        System.out.println("===================================================");
        // Get container based on the id
        String containerUrl = "https://api.datasearch.elsevier.com/api/v4/container/051057050053054049048048048045097045122104116101047050051057051046048049:ethz.epics-ba";
        ResponseEntity<String> containerResponse
                = restTemplate.getForEntity(containerUrl, String.class);
        root = mapper.readTree(containerResponse.getBody());


        // print out result as key value pairs
        System.out.println("===================================================");
        System.out.println("The container with most recent date: ");
        Map<String, Object> containerResult = mapper.convertValue(root, new TypeReference<Map<String, Object>>() {
        });
        for (Map.Entry<String, Object> entry : containerResult.entrySet()) {
            System.out.println(entry);
        }

//        Method2 : Stream





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
