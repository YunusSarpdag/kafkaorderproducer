import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class TransactionalOrderProducer {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers","localhost:9092");
        properties.setProperty("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        properties.setProperty("value.serializer","org.apache.kafka.common.serialization.IntegerSerializer");
        properties.setProperty(ProducerConfig.TRANSACTIONAL_ID_CONFIG , "consumer-tr1");

        KafkaProducer<String,Integer> producer = new KafkaProducer<String, Integer>(properties);
        producer.initTransactions();
        ProducerRecord<String,Integer> record = new ProducerRecord<>("OrderTopic","Macbook Pro" , 13);
        ProducerRecord<String,Integer> record2 = new ProducerRecord<>("OrderTopic","iphone" , 3);
        try {
            producer.beginTransaction();
            producer.send(record);
            producer.send(record2);
            producer.commitTransaction();
            System.out.println("Order Send");
        }catch (Exception e){
            producer.abortTransaction();
            e.printStackTrace();
        }finally {
            producer.close();
        }
    }
}
