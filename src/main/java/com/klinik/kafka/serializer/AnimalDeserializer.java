package com.klinik.kafka.serializer;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.klinik.kafka.message.Animal;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class AnimalDeserializer implements Deserializer<Animal> {

    @Override
    public Animal deserialize(String topic, byte[] data) {
        try {
            if (data == null){
                log.info("Null received at deserializing");
                return null;
            }
            //log.info(" Deserializer  Animal !!! ");
            return new ObjectMapper().readValue( new String( data, "UTF-8" ), Animal.class);
        } catch ( Exception e ) {
            throw new SerializationException( "Error when deserializing byte[] to Animal" );
        }
    }
    
}
