package S3MultipartUpload;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class AwsClient {
    private final S3Client s3Client;
    AwsClient(){
        String accessKey = System.getenv("accessKey");
        String secretKey = System.getenv("secretKey");
        AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(accessKey, secretKey);
        this.s3Client = S3Client.builder()
                .region(Region.AP_SOUTH_1)
                .credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials))
                .build();
    }
    public PutObjectResponse putObject(String bucket, String key, Path path){
        PutObjectRequest request = PutObjectRequest
                .builder()
                .bucket(bucket)
                .key(key)
                .build();
        PutObjectResponse response = this.s3Client.putObject(request,path);
        return response;
    }
    public CompleteMultipartUploadResponse uploadInMultiplePart(String bucket,String key, Path path) {
        // Initiate a multipart upload
        CreateMultipartUploadRequest createRequest = CreateMultipartUploadRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();
        CreateMultipartUploadResponse createResponse = this.s3Client.createMultipartUpload(createRequest);
        String uploadId = createResponse.uploadId();
        // upload

        // Prepare the parts to be uploaded
        List<CompletedPart> completedParts = new ArrayList<>();
        int partNumber = 1;
        ByteBuffer buffer = ByteBuffer.allocate(5 * 1024 * 1024); // 10 bytes // Set your preferred part size (5 MB in this example)

        // Read the file and upload each part
        try (RandomAccessFile file = new RandomAccessFile(path.toFile(), "r")) {
            long fileSize = file.length();
            long position = 0;
            while (position < fileSize) {
                file.seek(position);
                int bytesRead = file.getChannel().read(buffer);
                buffer.flip();
                UploadPartRequest uploadPartRequest = UploadPartRequest.builder()
                        .bucket(bucket)
                        .key(key)
                        .uploadId(uploadId)
                        .partNumber(partNumber)
                        .contentLength((long) bytesRead)
                        .build();

                UploadPartResponse response = this.s3Client.uploadPart(uploadPartRequest, RequestBody.fromByteBuffer(buffer));

                completedParts.add(CompletedPart.builder()
                        .partNumber(partNumber)
                        .eTag(response.eTag())
                        .build());
                buffer.clear();
                position += bytesRead;
                partNumber++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Complete the multipart upload
        CompletedMultipartUpload completedUpload = CompletedMultipartUpload.builder()
                .parts(completedParts)
                .build();

        CompleteMultipartUploadRequest completeRequest = CompleteMultipartUploadRequest.builder()
                .bucket(bucket)
                .key(key)
                .uploadId(uploadId)
                .multipartUpload(completedUpload)
                .build();
        CompleteMultipartUploadResponse completeResponse = this.s3Client.completeMultipartUpload(completeRequest);
        return completeResponse;
    }
    private static byte[] readPartFromFile(Path fileToUpload, long position, long length) throws IOException {
        try (java.nio.channels.FileChannel fileChannel = java.nio.channels.FileChannel.open(fileToUpload, StandardOpenOption.READ)) {
            ByteBuffer buffer = ByteBuffer.allocate((int) length);
            fileChannel.read(buffer, position);
            return buffer.array();
        }
    }
}
