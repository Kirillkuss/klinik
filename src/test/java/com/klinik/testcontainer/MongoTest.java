package com.klinik.testcontainer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Disabled
@Testcontainers
@DisplayName("Загрузка mongorestore в testcontainer mongo_test ")
public class MongoTest {

    @SuppressWarnings("resource")
    @Container
    private static GenericContainer<?> mongoContainer = new GenericContainer<>("mongo:latest")
                                                            .withExposedPorts(27017)
                                                            .withEnv("MONGO_INITDB_ROOT_USERNAME", "admin")
                                                            .withEnv("MONGO_INITDB_ROOT_PASSWORD", "password")
                                                            .withCreateContainerCmdModifier(cmd -> cmd.withName("mongo_test"));
    @BeforeAll
    public static void setUpClass() throws Exception{
        mongoContainer.start();
        /**
         * Для загрузки mongodb нужно подождать пока бд созд в докере 
         */
        Thread.sleep( 6000);
    }

    @AfterAll
    public static void tearDownClass() throws InterruptedException {
        Thread.sleep( 180000 );
        mongoContainer.stop();
    }

    private final String containerName = "mongo_test"; 
    private final String backupPathOnHost = "./src/main/resources/db/backup/mongo"; 
    private final String backupPathInContainer = "/backup"; 
    private final String uri = "mongodb://admin:password@localhost:27017/mongo?authSource=admin";

    @Test
    @DisplayName("Выполение Restore MongoDb")
    public void testRestoreBackupFromHostToDockerMongoDb() throws InterruptedException {
        try {
            ProcessBuilder copyProcessBuilder = new ProcessBuilder("docker", "cp", backupPathOnHost, containerName + ":" + backupPathInContainer );
            Process copyProcess = copyProcessBuilder.start();
            int copyExitCode = copyProcess.waitFor();
            if (copyExitCode == 0) {
                System.out.println("Backup copied to container successfully.");
                ProcessBuilder restoreProcessBuilder = new ProcessBuilder("docker", "exec", containerName, "mongorestore", "--uri", uri,  backupPathInContainer);
                               restoreProcessBuilder.redirectErrorStream(true);
                Process restoreProcess = restoreProcessBuilder.start();
                StringBuilder output = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(restoreProcess.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        output.append(line).append("\n");
                    }
                }
                int restoreExitCode = restoreProcess.waitFor();
                if (restoreExitCode == 0) {
                    System.out.println("Restore completed successfully.");
                } else {
                    System.err.println("Restore failed with exit code: " + restoreExitCode);
                    System.err.println("Output: " + output.toString());
                }
            } else {
                System.err.println("Failed to copy backup to container with exit code: " + copyExitCode);
            }
            System.out.println( "port: " + mongoContainer.getFirstMappedPort());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
