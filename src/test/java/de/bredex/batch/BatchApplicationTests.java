package de.bredex.batch;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import de.bredex.batch.model.Input;
import de.bredex.batch.repository.InputRepository;
import de.bredex.batch.repository.OutputRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BatchApplicationTests {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    @Qualifier("importJob")
    Job job;

    @Autowired
    private InputRepository inRepo;

    @Autowired
    private OutputRepository outRepo;

    @Before
    public void setup() {
        for (int i = 0; i < 100; i++) {
            inRepo.save(new Input());
        }
    }

    @Test
    public void testJob() throws JobExecutionAlreadyRunningException, JobRestartException,
            JobInstanceAlreadyCompleteException, JobParametersInvalidException {

        Map<String, JobParameter> parameters = new HashMap<>();
        parameters.put("testparameter", new JobParameter("yolo!"));

        JobParameters jobParameters = job.getJobParametersIncrementer().getNext(new JobParameters(parameters));
        jobLauncher.run(job, jobParameters);

        jobParameters = job.getJobParametersIncrementer().getNext(jobParameters);
        jobLauncher.run(job, jobParameters);

        assertEquals(200, outRepo.count());
    }
}
