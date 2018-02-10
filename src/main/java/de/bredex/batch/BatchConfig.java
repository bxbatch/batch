package de.bredex.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.bredex.batch.batch.InputProcessor;
import de.bredex.batch.batch.InputReader;
import de.bredex.batch.batch.JobCompletionNotificationListener;
import de.bredex.batch.batch.OutputWriter;
import de.bredex.batch.model.Input;
import de.bredex.batch.model.Output;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private InputProcessor processor;

    @Autowired
    private InputReader reader;

    @Autowired
    private OutputWriter writer;

    @Bean
    public Job importJob(final JobCompletionNotificationListener listener) {
        return jobBuilderFactory.get("importJob").incrementer(new RunIdIncrementer()).listener(listener).flow(step1())
                .end().build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1").<Input, Output>chunk(10).reader(reader).processor(processor)
                .writer(writer).build();
    }
}
