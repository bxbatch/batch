package de.bredex.batch.batch;

import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import de.bredex.batch.model.Input;
import de.bredex.batch.repository.InputRepository;

@Component
@StepScope
public class InputReader implements ItemStreamReader<Input> {

    private static final Logger LOG = LoggerFactory.getLogger(InputReader.class);

    @Autowired
    private InputRepository repo;

    private LinkedList<Input> items;

    private Pageable pageable;

    @BeforeStep
    public void beforeStep(final StepExecution stepExecution) {
        JobParameters parameters = stepExecution.getJobExecution().getJobParameters();

        LOG.info("testparameter = {}", parameters.getString("testparameter"));
    }

    @Override
    public void close() throws ItemStreamException {
        LOG.info("closing item stream");
    }

    private void fetchNextPage() {
        LOG.info("fetching page {}", pageable.getPageNumber());
        Page<Input> page = repo.findAll(pageable);
        items.addAll(page.getContent());
        if (page.hasNext()) {
            LOG.info("still more pages...");
            pageable = page.nextPageable();
            return;
        }
        LOG.debug("no more pages...");
        pageable = null;
    }

    @Override
    public void open(final ExecutionContext executionContext) throws ItemStreamException {
        LOG.info("opening item stream");
        pageable = new PageRequest(0, 10);
        items = new LinkedList<>();
    }

    @Override
    public Input read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        LOG.info("reading input item");
        if (items.isEmpty()) {
            if (pageable != null) {
                fetchNextPage();
            }
            if (items.isEmpty() && pageable == null) {
                return null;
            }
        }
        return items.pop();
    }

    @Override
    public void update(final ExecutionContext executionContext) throws ItemStreamException {
        LOG.info("updating item stream");
    }
}
