import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.Future;

public class OrderProducer {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers","localhost:9092");
        properties.setProperty("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        properties.setProperty("value.serializer","org.apache.kafka.common.serialization.IntegerSerializer");

        KafkaProducer<String,Integer> producer = new KafkaProducer<String, Integer>(properties);
        ProducerRecord<String,Integer> record = new ProducerRecord<>("OrderTopic","Macbook Pro" , 13);
        try {
            /*Future<RecordMetadata> recordMetadataFuture = producer.send(record);
            RecordMetadata recordMetadata = recordMetadataFuture.get();
            System.out.println(recordMetadata.partition());
            System.out.println(recordMetadata.serializedValueSize());
            System.out.println(recordMetadata.offset());*/
            // this makes future object handle async
            producer.send(record, new OrderCallBack());
            System.out.println("Order Send");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            producer.close();
        }
    }
}
