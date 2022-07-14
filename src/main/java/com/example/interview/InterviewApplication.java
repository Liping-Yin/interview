package com.example.interview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class InterviewApplication {

    public static void main(String[] args) throws IOException {
//        SpringApplication.run(InterviewApplication.class, args);
		JasonNodeSolution solution = new JasonNodeSolution();
		solution.testMethod();
    }


}
