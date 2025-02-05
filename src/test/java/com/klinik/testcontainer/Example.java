package com.klinik.testcontainer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import com.klinik.service.DocumentService;
import com.klinik.testcontainer.datasource.PostgresContainer;

@SpringBootTest
//@ContextConfiguration(classes = {PostgresContainer.class, PostgresContainer.TestConnectDataBase.class})
@ContextConfiguration(classes = { PostgresContainer.TestConnectDataBase.class})
public class Example {

    @Autowired
    private  DocumentService documentService;

    @Test
    public void testFirst(){
        System.out.println( "TES>>>>>>>>>>>>>>>>>>>>");
    }

    /**@Test
    public void secondTest(){
        System.out.println( "2> \n" + documentService.getLazyDocuments( 1, 3 ));
    }*/
    
}
