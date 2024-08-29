package com.klinik.controller.backup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import com.klinik.response.BaseResponse;
import com.klinik.rest.backup.IBackUp;
import com.klinik.service.backup.PostgresBackupService;
import java.io.IOException;

@RestController
public class BackupController implements IBackUp {

    @Autowired
    private PostgresBackupService postgresBackupService;

    @SuppressWarnings("rawtypes")
    @Override
    public ResponseEntity<BaseResponse> getDump() throws Exception {
        try {
            postgresBackupService.backupDatabase("./src/main/resources/db/backup/klinika.backup");;
            return new ResponseEntity<BaseResponse>( new BaseResponse<>( 200, "success"), HttpStatus.OK );
        } catch (IOException | InterruptedException e) {
            return new ResponseEntity<BaseResponse>( new BaseResponse<>( 500, e.getMessage()), HttpStatus.OK );
        }
        
    }
    @SuppressWarnings("rawtypes")
    @Override
    public ResponseEntity<BaseResponse> getRestore() throws Exception {
        try {
            postgresBackupService.getRestore();
            return new ResponseEntity<BaseResponse>( new BaseResponse<>( 200, "success"), HttpStatus.OK );
        } catch (IOException | InterruptedException e) {
            return new ResponseEntity<BaseResponse>( new BaseResponse<>( 500, e.getMessage()), HttpStatus.OK );
        }
    }
}
