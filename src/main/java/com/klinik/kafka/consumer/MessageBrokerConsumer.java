package com.klinik.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import com.klinik.kafka.message.SendMessageBroker;
import com.klinik.kafka.serializer.MessageBrokerDeserializer;
import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class MessageBrokerConsumer {
    
    @Value( value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    public ConsumerFactory<String, SendMessageBroker> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put( ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress );
        props.put( ConsumerConfig.GROUP_ID_CONFIG, "12345");
        props.put( ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put( ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, MessageBrokerDeserializer.class );
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, SendMessageBroker> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, SendMessageBroker> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory( consumerFactory() );
        return factory;
    }

}