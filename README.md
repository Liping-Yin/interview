# interview
### Main Tasks:
        0. receive a link with Swagged Doc
        1. Call endpoint
        2. find the most recent item
        3. Call another endpoint using the id
        
### Some explanations for the solutions:  
I didn't override previous solution, instead I put them in different classes

###  phase 1: solution is implemented by  class JasonNodeSolution 
       Tasks: 
       0. Finish it with current approach. Use JsonNode and its API to do the job
       0.1. Now we got a date needed. Another field is "ïd" (very long one)
       0.2. You iterate over the List<> and do the job.
       0.3. Call https://datasearch.elsevier.com/api/docs#/search/getContainer
### phase 2 : solution is implemented by  class StreamSolution 
       Taks:
       1. Get rid of JsonNode and go with POJO (DTO) objects
       1.1. You can also iterate
       1.2. Use Streams
### Phase 3: For Spring DI: only Controller and Service is used, and a Container POJO is created with only attributes of id and updatedDate 
       Taks:
       2. Utilize Spring DI framework
       2.1. Introduce Components (@Service, @Component, etc.)
       2.2. Use actual dependency injection
       Related apis created in this application: (when run in localhost: 8080)
        -> Get the most recent container : Http://localhost:8080/api/container?query=spring  
        (since different search query result in different most recent container record, thus a request parameter is considered )
        ->  // Get the container based on containerId: Http://localhost:8080/api/container/some_id
        -> //  Get all containers based on path variable: http://localhost:8080/api/containers?query=spring
        
       
