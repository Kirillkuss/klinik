package com.klinik.service.backup;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class PostgresBackupService {


    @Value("${spring.datasource.username}")
    private String dbUsername;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    public void backupDatabase(String backupFilePath) throws Exception {
        List<String> dump = List.of("pg_dump", "--host=localhost","--port=5432","--username=" + dbUsername,
                                       "--file=" + "./src/main/resources/db/backup/klinika.backup","--no-password",
                                       "--format=c","--encoding=UTF8","--verbose","Klinika");
        processBuilder( dump );
        
    }

    public void getRestore() throws Exception {
        List<String> restore = List.of("pg_restore", "--host=localhost", "--port=5432","--username=" + dbUsername,
                                       "--dbname=Klinika",  "--no-password","--format=c","--verbose",
                                          "./src/main/resources/db/backup/klinika.backup");
        processBuilder( restore );
    }

    private void processBuilder( List<String>  commnds ) throws Exception{
        ProcessBuilder processBuilder = new ProcessBuilder( commnds );
        processBuilder.environment().put("PGPASSWORD", dbPassword );
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                log.info(line);
            }
        }
        int exitCode = process.waitFor();
        if (exitCode == 0) {
            log.info("Success " );
        } else {   
            log.warn("Code >>> " + exitCode);
        }

    }


}