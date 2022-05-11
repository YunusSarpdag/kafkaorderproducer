import org.apache.kafka.clients.producer.RecordMetadata;

public class OrderCallBack implements org.apache.kafka.clients.producer.Callback {
    @Override
    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println(recordMetadata.partition());
        System.out.println(recordMetadata.serializedValueSize());
        System.out.println(recordMetadata.offset());
        if(e != null){
            e.printStackTrace();
        }
    }
}
