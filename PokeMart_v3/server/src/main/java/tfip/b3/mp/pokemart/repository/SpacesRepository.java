package tfip.b3.mp.pokemart.repository;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;


@Repository
public class SpacesRepository {

    private static final String type = "image";
    @Value("${s3.bucket}")
    private String bucket;
    @Value("${s3.dir.name}")
    private String dirName;

    @Autowired
    private AmazonS3 s3;

    public String uploadSprite(Map<String, String> prodData, byte[] file, String fileName,
            String fileType) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(type + fileType);
        metadata.setContentLength(file.length);
        metadata.setUserMetadata(prodData);

        String key = dirName + "/sprite/" + fileName;
        PutObjectRequest putReq = new PutObjectRequest(bucket, key,
                new ByteArrayInputStream(file), metadata);
        putReq = putReq.withCannedAcl(CannedAccessControlList.PublicRead);
        PutObjectResult result = s3.putObject(putReq);
        System.out.printf(">> [INFO] Uploaded:" + result.toString());

        return s3.getUrl(bucket, key).toString();
    }

        public String uploadImage(Map<String, String> prodData, MultipartFile file, String fileName,
            String fileType) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(type + fileType);
        metadata.setContentLength(file.getSize());
        metadata.setUserMetadata(prodData);

        String key = dirName + "/image/" + fileName;
        PutObjectRequest putReq = new PutObjectRequest(bucket, key,
                file.getInputStream(), metadata);
        putReq = putReq.withCannedAcl(CannedAccessControlList.PublicRead);
        PutObjectResult result = s3.putObject(putReq);
        System.out.printf(">> [INFO] Uploaded:" + result.toString());

        return s3.getUrl(bucket, key).toString();
    }
}

