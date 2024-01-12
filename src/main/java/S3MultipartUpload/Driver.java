package S3MultipartUpload;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;

public class Driver {
    private static final int PART_SIZE = 10;
    public static void main(String []args){

        AwsClient awsClient = new AwsClient();
        String bucketName = System.getenv("bucketName");
        Path absolutePath = Paths.get("src/main/java/S3MultipartUpload/IO/input.txt").toAbsolutePath();
        Path bigFile = Paths.get("").toAbsolutePath();

        String objectKey = absolutePath.getFileName().toString();
        String bigFileKey = bigFile.getFileName().toString();
        PutObjectResponse response = awsClient.putObject(bucketName,objectKey,absolutePath);
//        CompleteMultipartUploadResponse response = awsClient.uploadInMultiplePart(bucketName,"io/"+bigFileKey,bigFile);
        System.out.println("File uploaded successfully!");
    }
    private static void createDirectoryIfNotExists(String directory) throws IOException {
        Path directoryPath = Paths.get(directory);
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }
    }
    private static void splitFile(String inputFile, String outputDirectory) throws IOException {
        File file = new File(inputFile);
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] buffer = new byte[PART_SIZE];
            int bytesRead;
            int partNumber = 1;

            while ((bytesRead = fis.read(buffer)) > 0) {
                String partFileName = outputDirectory + File.separator + "part" + partNumber + ".dat";
                try (FileOutputStream fos = new FileOutputStream(partFileName)) {
                    fos.write(buffer, 0, bytesRead);
                }
                partNumber++;
            }
        }
    }
    private static void assembleFile(String outputDirectory) throws IOException {
        Comparator<File> comparator = (File s1,File s2) -> {
            if (s1.getPath().length() < s2.getPath().length()) return -1;
            else if  (s1.getPath().length() > s2.getPath().length()) return 1;
            for (int i=0;i<s1.getPath().length();i++){
                if (s1.getPath().charAt(i) > s2.getPath().charAt(i)) return 1;
                if (s1.getPath().charAt(i) < s2.getPath().charAt(i)) return -1;
            }
            return 1;
        };
        File[] parts = new File(outputDirectory).listFiles((dir, name) -> name.startsWith("part"));
        assert parts != null;
        Arrays.sort(parts,comparator);
        if (parts.length > 0) {
            Path assembledFilePath = Path.of(outputDirectory, "assembled.txt");
            if (!Files.exists(assembledFilePath)) {
                Files.createDirectories(assembledFilePath);
            }
            try (FileOutputStream fos = new FileOutputStream(assembledFilePath.toFile())) {
                for (File part : parts) {
                    Files.copy(part.toPath(), fos);
                }
            }
        }
    }
}