package kafka.ProcessorApi;

import org.apache.kafka.streams.processor.api.Processor;
import org.apache.kafka.streams.processor.api.ProcessorContext;
import org.apache.kafka.streams.processor.api.Record;

public class CustomProcessor <kin,Vin,Kout,Vout> implements Processor<kin,Vin,Kout,Vout> {
    @Override
    public void init(ProcessorContext context) {
        Processor.super.init(context);
    }

    @Override
    public void process(Record record) {

    }

    @Override
    public void close() {
        Processor.super.close();
    }
}
