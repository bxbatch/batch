package de.bredex.batch.batch;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.bredex.batch.model.Output;
import de.bredex.batch.repository.OutputRepository;

@Component
public class OutputWriter implements ItemWriter<Output> {

    private static final Logger LOG = LoggerFactory.getLogger(OutputWriter.class);

    @Autowired
    private OutputRepository repo;

    @Override
    public void write(final List<? extends Output> items) throws Exception {
        LOG.info("Writing chunk");
        repo.save(items);
    }
}
