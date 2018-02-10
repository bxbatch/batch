package de.bredex.batch.batch;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import de.bredex.batch.model.Input;
import de.bredex.batch.model.Output;

@Component
public class InputProcessor implements ItemProcessor<Input, Output> {

    @Override
    public Output process(final Input item) throws Exception {
        Output output = new Output();
        output.setValue((int) item.getValue());
        return output;
    }
}
