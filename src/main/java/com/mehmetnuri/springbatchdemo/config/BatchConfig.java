package com.mehmetnuri.springbatchdemo.config;

import java.io.File;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class BatchConfig {

    @Autowired
    private JobBuilderFactory jobFactory;

    @Autowired
    private StepBuilderFactory stepFactory;


    @Bean
    public Step step1() {
        return stepFactory.get("step1").tasklet(helloWorldTasklet()).build();
    }

    private Tasklet helloWorldTasklet() {
        return (new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
                System.out.println("Deleting File");
                try {
                    File f = new File("/home/mehmet/Desktop/abc.txt");
                    if (f.delete()) {
                        System.out.println(f.getName() + " deleted");
                    } else {
                        System.out.println("failed");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return RepeatStatus.FINISHED;
            }
        });

    }

    @Bean
    public Job helloWorldJob() {
        return jobFactory.get("helloworld").flow(step1()).end().build();
    }
}
