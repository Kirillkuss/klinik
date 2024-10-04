package com.klinik.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class Topics {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic topicKllinikFirst() {
        return TopicBuilder.name( "topicKllinikFirst")
                           .partitions( 1 )
                           .replicas( 1 )
                           .build();
    }

    @Bean
    public NewTopic topicKlinnikSecond(){
        return TopicBuilder.name( "topicKlinnikSecond")
                           .partitions( 1 )
                           .replicas( 1 )
                           .build();
    }
}