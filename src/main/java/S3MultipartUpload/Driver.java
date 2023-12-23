package S3MultipartUpload;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Driver {

    private static final int PART_SIZE = 10;
    public static void main(String []args){
        String accessKey = System.getenv("accessKey");
        String secreteKey = System.getenv("secretKey");
        TestInputStream();

        String inputFile = "IO/input.txt"; // Replace with your input file path
        String outputDirectory = "IO/output"; // Replace with your desired output directory

        try {
            splitFile(inputFile, outputDirectory);
            assembleFile(outputDirectory);
        } catch (IOException e) {
            e.printStackTrace();
        }


//        AWSCredentials credentials = new BasicAWSCredentials(accessKey,secreteKey);
//
//        AmazonS3 amazonS3 = AmazonS3ClientBuilder
//                .standard()
//                .withCredentials(new AWSStaticCredentialsProvider(credentials))
//                .withRegion(Regions.AP_SOUTH_1)
//                .build();
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
        File[] parts = new File(outputDirectory).listFiles((dir, name) -> name.startsWith("part"));

        if (parts != null && parts.length > 0) {
            try (FileOutputStream fos = new FileOutputStream("assembled.txt")) {
                for (File part : parts) {
                    Files.copy(part.toPath(), fos);
                }
            }
        }
    }


    public static void TestInputStream() {
        try {
            // Specify the path of the file to be read
//            String filePath = "F1/f2/input.txt";

            String filePath = "S3MultipartUpload/IO/input.txt";
            Path absolutePathFile1 = Paths.get(filePath).toAbsolutePath();

            File file = new File(filePath);
            // relative path S3MultipartUpload/IO/input.txt (1)
            // relative path S3MultipartUpload/IO/Driver.java  (2)
            // above mentione are relative path i want to read file (1) from (2) how to do that
            // Create a FileInputStream object
            FileInputStream fis = new FileInputStream(absolutePathFile1.toFile());
            // Read bytes from the file
            int byteData;
            while ((byteData = fis.read()) != -1) {
                // Process the byte (you can do whatever you need here)
                System.out.print((char) byteData);
            }
            // Close the FileInputStream
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("DONE");
    }
}