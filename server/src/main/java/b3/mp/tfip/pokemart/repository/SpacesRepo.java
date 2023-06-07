package b3.mp.tfip.pokemart.repository;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

@Repository
public class SpacesRepo {

    public static final String type = "image";
    public static final String bucket = "tofu-pokemart";
    public static final String dirName = "tofu-pokemart";

    @Autowired
    private AmazonS3 s3;

    public String uploadSprite(Map<String, String> prodData, byte[] file, String fileName, String productID,
            String fileType) throws IOException {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(type + fileType);
        metadata.setContentLength(file.length);
        metadata.setUserMetadata(prodData);

        String key = dirName + "/" + productID;
        PutObjectRequest putReq = new PutObjectRequest(bucket, key,
                new ByteArrayInputStream(file), metadata);
        putReq = putReq.withCannedAcl(CannedAccessControlList.PublicRead);
        PutObjectResult result = s3.putObject(putReq);
        System.out.printf(">> [INFO] Uploaded:" + result.toString());

        return s3.getUrl(bucket, key).toString();
    }
}