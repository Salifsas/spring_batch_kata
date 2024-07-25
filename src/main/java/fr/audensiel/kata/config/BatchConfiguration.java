package fr.audensiel.kata.config;

import fr.audensiel.kata.model.Tondeuse;
import fr.audensiel.kata.model.TondeuseCommande;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.IOException;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration extends BatchAutoConfiguration {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    @Value("${input.filename}")
    private String fileName;
    @Autowired
    public BatchConfiguration(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
    }

    @Bean
    public ItemReader<TondeuseCommande> reader() throws IOException {
        return new TondeuseItemReader(new ClassPathResource(fileName));
    }

    @Bean
    public ItemProcessor<TondeuseCommande, Tondeuse> processor() {
        return new TondeuseItemProcessor();
    }

    @Bean
    public ItemWriter<Tondeuse> writer() {
        return new TondeuseItemWriter();
    }

    @Bean
    public Job importTondeuseJob(Step step1) {
        return new JobBuilder("TondeuseJob", jobRepository)
                .start(step1)
                .build();
    }

    @Bean
    public Step step1(ItemReader<TondeuseCommande> reader,
                      ItemProcessor<TondeuseCommande, Tondeuse> processor,
                      ItemWriter<Tondeuse> writer) {
        return new StepBuilder("step1", jobRepository)
                .<TondeuseCommande, Tondeuse>chunk(1, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
