package com.klinik.testcontainer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import org.junit.jupiter.api.DisplayName;

public class RestoreMongodb {

    private static final String backupPathOnHost      = "./src/main/resources/db/backup/mongo"; 
    private static final String backupPathInContainer = "/backup"; 
    private static final String uri                   = "mongodb://admin:password@localhost:27017/mongo?authSource=admin";

    @DisplayName("Загрузка данных в бд")
    public static void getRestoreDataBaseMongo(  String containerName ) throws Exception {
        List<String> restore = List.of("docker", "cp", backupPathOnHost, containerName + ":" + backupPathInContainer);
        processBuilder( restore, containerName );
    }
    
    @DisplayName("Загрузка данных")
    private static void processBuilder( List<String> commnds, String containerName ) throws Exception{
        try {
            ProcessBuilder copyProcessBuilder = new ProcessBuilder( commnds );
            Process copyProcess = copyProcessBuilder.start();
            int copyExitCode = copyProcess.waitFor();
            if (copyExitCode == 0) {
                System.out.println("Backup copied to container successfully.");
                ProcessBuilder restoreProcessBuilder = new ProcessBuilder("docker", "exec", containerName, "mongorestore", "--uri", uri, backupPathInContainer );
                restoreProcessBuilder.redirectErrorStream(true);
                Process restoreProcess = restoreProcessBuilder.start();
                int restoreExitCode = restoreProcess.waitFor();
                if (restoreExitCode == 0) {
                    System.out.println("Restore completed successfully.");
                } else {
                    System.err.println("Restore failed with exit code: " + restoreExitCode);
                }
            } else {
                System.err.println("Failed to copy backup to container with exit code: " + copyExitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
