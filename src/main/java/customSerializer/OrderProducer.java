package customSerializer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class OrderProducer {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        properties.setProperty("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        properties.setProperty("value.serializer","customSerializer.OrderSerializer");
        properties.setProperty("partitioner.class",VIPPartitioner.class.getName());
        properties.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG , "true");

        KafkaProducer<String,Order> producer = new KafkaProducer<String, Order>(properties);
        Order order = new Order();
        order.setCustomerName("veli");
        order.setProduct("iphone");
        order.setQuantity(1);
        //ProducerRecord<String,Order> record = new ProducerRecord<>("OrderTopicCS",order.getCustomerName(), order);
        ProducerRecord<String,Order> record = new ProducerRecord<>("OrderTopicCScmd",order.getCustomerName(), order);
        try {
            producer.send(record);
            System.out.println("Order Send");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            producer.close();
        }
    }
}
