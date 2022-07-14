package com.example.interview;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ContainerController {

    private final ContainerService containerService;

    @Autowired
    public ContainerController(ContainerService containerService) {
        this.containerService = containerService;
    }

    // Get the most recent container
    // Http://localhost:8080/api/container?query=spring
    @GetMapping("/container")
    public Container getRecentContainer(@RequestParam String query) {
        // For a different query , the most recent container should be different
        return containerService.getRecentContainer(query);
    }

//    Path variable
    // Get the container based on containerID
    // Http://localhost:8080/api/container/some_id
    @GetMapping("/container/{containerId}")
    public Container getContainerById(@PathVariable("containerId") String id) throws JsonProcessingException {
        return containerService.getContainerById(id);
    }


//    Request Param
//    Get all containers based on path variable
//   http://localhost:8080/api/containers?query=spring
    @GetMapping("/containers")
    public List<Container> getContainers(@RequestParam String query) {
        return containerService.getContainers(query);
    }

}
