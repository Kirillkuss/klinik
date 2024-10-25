package com.klinik.kafka.serializer;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.klinik.entity.Document;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DocumentSerializer implements Serializer<Document>{

    @Override
    public byte[] serialize( String topic, Document document) {
        try {
            if ( document == null ){
                log.info("Null received at serializing");
                return null;
            }
            //log.info( "Serializing entity Document..." );
            return new ObjectMapper().writeValueAsBytes( document );
        } catch ( Exception e ) {
            throw new SerializationException( "Error when serializing Document to byte[] \n" + e.getMessage() );
        }
    }
    
}
