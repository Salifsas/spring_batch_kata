package fr.audensiel.kata;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KataApplication implements CommandLineRunner {

	private final JobLauncher jobLauncher;

	private final Job importTondeuseJob;

	public KataApplication(JobLauncher jobLauncher, Job importTondeuseJob){
		this.jobLauncher = jobLauncher;
		this.importTondeuseJob = importTondeuseJob;
	}

	public static void main(String[] args) {
		SpringApplication.run(KataApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		JobParameters jobParameters = new JobParametersBuilder()
				.addLong("time", System.currentTimeMillis())
				.toJobParameters();
		jobLauncher.run(importTondeuseJob, jobParameters);
	}
}
