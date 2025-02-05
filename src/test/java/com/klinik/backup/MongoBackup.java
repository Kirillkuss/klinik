package com.klinik.backup;

import java.io.IOException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Disabled
@DisplayName("Dump and restore data in mongodb in docker")
public class MongoBackup {

    private final String containerName = "mongodb"; 
    private final String backupPathOnHost = "./src/main/resources/db/backup/mongo"; 
    private final String backupPathInContainer = "/backup"; 
    private final String uri = "mongodb://admin:password@localhost:27017/mongo?authSource=admin";
    
    @Test
    @DisplayName("Создание Backup")
    public void testCreateBackupInDockerAndCopyToHost() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("docker", "exec", containerName,"mongodump", "--uri", uri, "--out=" + backupPathInContainer);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Backup created successfully inside the container.");
                ProcessBuilder copyProcessBuilder = new ProcessBuilder("docker", "cp", "mongodb" + ":" + backupPathInContainer, backupPathOnHost );
                Process copyProcess = copyProcessBuilder.start();
                int copyExitCode = copyProcess.waitFor();
                if (copyExitCode == 0) {
                    System.out.println("Backup copied to host successfully.");
                } else {
                    System.err.println("Failed to copy backup to host with exit code: " + copyExitCode);
                }
            } else {
                System.err.println("Backup creation failed with exit code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Выполение Restore MongoDb")
    public void testRestoreBackupFromHostToDockerMongoDb() {
        try {
            ProcessBuilder copyProcessBuilder = new ProcessBuilder("docker", "cp", backupPathOnHost, containerName + ":" + backupPathInContainer );
            Process copyProcess = copyProcessBuilder.start();
            int copyExitCode = copyProcess.waitFor();
            if (copyExitCode == 0) {
                System.out.println("Backup copied to container successfully.");
                ProcessBuilder restoreProcessBuilder = new ProcessBuilder("docker", "exec", containerName, "mongorestore", "--uri", uri, backupPathInContainer);
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
