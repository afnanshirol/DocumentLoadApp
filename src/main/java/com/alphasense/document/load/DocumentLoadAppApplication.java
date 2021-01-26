package com.alphasense.document.load;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class DocumentLoadAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(DocumentLoadAppApplication.class, args);
	}

	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	Job job;

	/**
	 * This method is used to trigger the job based on the cron expression configured.
	 *
	 * @throws Exception
	 */
	@Scheduled(cron = "0 */5 * * * ?")
	public void schedule() throws Exception {
		JobParameters params = new JobParametersBuilder()
				.addString("JobID", String.valueOf(System.currentTimeMillis()))
				.toJobParameters();
		jobLauncher.run(job, params);
	}
}