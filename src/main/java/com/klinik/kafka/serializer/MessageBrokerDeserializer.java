package com.klinik.kafka.serializer;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.klinik.kafka.message.SendMessageBroker;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageBrokerDeserializer implements Deserializer<SendMessageBroker> {

    @Override
    public SendMessageBroker deserialize(String topic, byte[] data) {
        try {
            if (data == null){
                log.info("Null received at deserializing");
                return null;
            }
            return new ObjectMapper().registerModule(new JavaTimeModule())
                                     .readValue( new String( data, "UTF-8" ), SendMessageBroker.class );
        } catch ( Exception e ) {
            throw new SerializationException( "Error when deserializing byte[] to SendMessageBroker" );
        }
    }
    
}
