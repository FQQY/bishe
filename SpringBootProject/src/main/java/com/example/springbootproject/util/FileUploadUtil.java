package com.example.springbootproject.util;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fqgxing
 * @create 2023-03-05-15:27
 */
public class FileUploadUtil {


    private static String saveFile(String pathSuffix, MultipartFile multipartFile) throws IOException {
        String fileName = multipartFile.getOriginalFilename();
        File f = new File("E:\\bishe\\Code\\Upload" + File.separator + pathSuffix );

        if(!f.isDirectory()) {
            f.mkdirs();
        }

        // 生成一个存放在数据库中的名字
        String fileCode = UUIDUtil.getUUID() + "-" + fileName;
        System.out.println(f.getAbsolutePath());
        try {
            multipartFile.transferTo(new File(f.getAbsolutePath() + File.separator + fileCode));
        } catch (IOException e) {
            throw new IOException("Could not save file: " + fileName, e);
        }

        return fileCode;
    }

    public static String uploadVideo(MultipartFile multipartFile) throws IOException {
        return saveFile("Video", multipartFile);
    }

    public static String uploadFile(MultipartFile multipartFile) throws IOException {
        return saveFile("File", multipartFile);
    }

    private static String saveFileByAliyun(String pathSuffix, MultipartFile file) {
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = "oss-cn-beijing.aliyuncs.com";
        // LTAI5tCxJdfN1sxvhaSZD9We
        // B0fh72u0io701eNTA7Xxxq00kOAOtH
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = "LTAI5tCxJdfN1sxvhaSZD9We";
        String accessKeySecret = "B0fh72u0io701eNTA7Xxxq00kOAOtH";
        // 填写Bucket名称，例如examplebucket。
        String bucketName = "fqiegxing";
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);


        try {
            // 上传文件流
            InputStream inputStream = file.getInputStream();
            String fileCode =
                    "Upload" + pathSuffix + "/" + UUIDUtil.getUUID() + "-" + file.getOriginalFilename();
            long fileSize = file.getSize();
            System.out.println(fileSize);
            if(fileSize < 30 * 1024 * 1024L) {
                ossClient.putObject(bucketName, fileCode, inputStream);
                ossClient.shutdown();
            }else {
                // 创建InitiateMultipartUploadRequest对象。
                InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(bucketName, fileCode);
                // 初始化分片。
                InitiateMultipartUploadResult upresult = ossClient.initiateMultipartUpload(request);
                // 返回uploadId，它是分片上传事件的唯一标识。您可以根据该uploadId发起相关的操作，例如取消分片上传、查询分片上传等。
                String uploadId = upresult.getUploadId();
                // partETags是PartETag的集合。PartETag由分片的ETag和分片号组成。
                List<PartETag> partETags =  new ArrayList<PartETag>();
                // 每个分片的大小，用于计算文件有多少个分片。单位为字节。
                final long partSize = 2 * 1024 * 1024L;   //1 MB。

                // 填写本地文件的完整路径。

                long fileLength = file.getSize();
                int partCount = (int) (fileLength / partSize);
                if (fileLength % partSize != 0) {
                    partCount++;
                }
                // 遍历分片上传。
                for (int i = 0; i < partCount; i++) {
                    long startPos = i * partSize;
                    long curPartSize = (i + 1 == partCount) ? (fileLength - startPos) : partSize;
                    InputStream instream = file.getInputStream();
                    // 跳过已经上传的分片。
                    instream.skip(startPos);
                    UploadPartRequest uploadPartRequest = new UploadPartRequest();
                    uploadPartRequest.setBucketName(bucketName);
                    uploadPartRequest.setKey(fileCode);
                    uploadPartRequest.setUploadId(uploadId);
                    uploadPartRequest.setInputStream(instream);
                    // 设置分片大小。除了最后一个分片没有大小限制，其他的分片最小为100 KB。
                    uploadPartRequest.setPartSize(curPartSize);
                    // 设置分片号。每一个上传的分片都有一个分片号，取值范围是1~10000，如果超出此范围，OSS将返回InvalidArgument错误码。
                    uploadPartRequest.setPartNumber( i + 1);
                    // 每个分片不需要按顺序上传，甚至可以在不同客户端上传，OSS会按照分片号排序组成完整的文件。
                    UploadPartResult uploadPartResult = ossClient.uploadPart(uploadPartRequest);
                    // 每次上传分片之后，OSS的返回结果包含PartETag。PartETag将被保存在partETags中。
                    partETags.add(uploadPartResult.getPartETag());
                }

                // 创建CompleteMultipartUploadRequest对象。
                // 在执行完成分片上传操作时，需要提供所有有效的partETags。OSS收到提交的partETags后，会逐一验证每个分片的有效性。当所有的数据分片验证通过后，OSS将把这些分片组合成一个完整的文件。
                CompleteMultipartUploadRequest completeMultipartUploadRequest =
                        new CompleteMultipartUploadRequest(bucketName, fileCode, uploadId, partETags);
                // 完成分片上传。
                CompleteMultipartUploadResult completeMultipartUploadResult = ossClient.completeMultipartUpload(completeMultipartUploadRequest);
                System.out.println(completeMultipartUploadResult.getETag());
            }



            String url = "https://" + bucketName + "." + endpoint + "/" + fileCode;
            return url;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static String uploadVideoByAliyun(MultipartFile file) throws IOException {
        return saveFileByAliyun("/Video", file);
    }

    public static String uploadFileByAliyun(MultipartFile file) throws IOException {
        return saveFileByAliyun("/File", file);
    }
}
