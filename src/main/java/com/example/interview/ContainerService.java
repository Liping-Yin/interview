package com.example.interview;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.Instant;
import java.util.*;

@Service
public class ContainerService {
    private Container recentContainer;
    private List<Container> containers = new ArrayList<>();

    public Container getRecentContainer(String query) {
        getContainers(query);
        return this.recentContainer;
    }

    public Container getContainerById(String id) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String containerUrl = "https://api.datasearch.elsevier.com/api/v4/container/" + id;
        ResponseEntity<String> containerResponse
                = restTemplate.getForEntity(containerUrl, String.class);
        ObjectMapper mapper = new ObjectMapper();
        //Get the most recent date and its id
        JsonNode root = null;
        root = mapper.readTree(containerResponse.getBody());

        // print out result as key value pairs
        System.out.println("===================================================");
        System.out.println("The container with most recent date: ");
        Map<String, Object> containerResult = mapper.convertValue(root, new TypeReference<Map<String, Object>>() {
        });
        for (Map.Entry<String, Object> entry : containerResult.entrySet()) {
            System.out.println(entry);
        }
        Instant updatedDate = Instant.parse(root.path("updatedDate").asText());
        // it seems that the containers received in the beginning already includes the data from the new url
        // Here I pretend it was different
        return new Container(updatedDate,id);
    }

    public List<Container> getContainers(String query) {
        // For simplicity the Container class only has two attributes <- different from the source data
        //Retrieve search data
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl
                = "https://api.datasearch.elsevier.com/api/v4/search?query="+query;
        ResponseEntity<String> response
                = restTemplate.getForEntity(resourceUrl, String.class);
        ObjectMapper mapper = new ObjectMapper();

        //Get the most recent date and its id
        JsonNode root = null;
        {
            try {

                root = mapper.readTree(response.getBody());
                JsonNode results = root.path("results");
                List<JsonNode> z = new ObjectMapper().readerForListOf(JsonNode.class).readValue(results);
                z.forEach(item -> {
                    this.containers.add(new Container(Instant.parse(item.path("updatedDate").asText()), item.path("id").asText()));
                });
                Comparator<Container> cmp = Comparator.comparing(Container::getUpdatedDate);
                Optional<Container> optionalContainer= this.containers.stream().max(cmp);
                this.recentContainer= optionalContainer.isPresent() ? optionalContainer.get():null;

                System.out.println("===================================================");
                System.out.println("Most recent date and id : " );
                System.out.println(this.recentContainer.getUpdatedDate());
                System.out.println(this.recentContainer.getId());
                System.out.println("===================================================");

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return this.containers;
    }
}
