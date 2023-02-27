package com.yuelimin.controller;

import com.yuelimin.common.rest.RestBaseResponse;
import com.yuelimin.minio.service.MinioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author YueLiMin
 * @version 1.0.0
 */
@Slf4j
@Tag(name = "MinioController", description = "minio web接口")
@RestController
@RequestMapping("/minio")
public class MinioController {
    @Value("${minio.endpoint}")
    private String endpoint;
    @Resource
    private MinioService minioService;

    @Operation(summary = "创建桶")
    @PostMapping("/bucket/{bucketName}")
    public RestBaseResponse<?> createBucket(@PathVariable("bucketName") String bucketName) {
        minioService.createBucket(bucketName);

        return RestBaseResponse.success();
    }

    @Operation(summary = "删除桶")
    @DeleteMapping("/bucket/{bucketName}")
    public RestBaseResponse<?> deleteBucket(@PathVariable("bucketName") String bucketName) {
        minioService.removeBucket(bucketName);

        return RestBaseResponse.success();
    }


    @Operation(summary = "获取桶策略")
    @GetMapping("/bucket/policy{bucketName}")
    public RestBaseResponse<?> getBucketPolicy(@PathVariable("bucketName") String bucketName) {
        return RestBaseResponse.success(minioService.getBucketPolicy(bucketName));
    }

    @Operation(summary = "上传文件")
    @PostMapping("/file/private/{bucketName}")
    public RestBaseResponse<?> uploadPrivateFile(@PathVariable("bucketName") String bucketName, @RequestParam("file") MultipartFile file) {
        if (file.getSize() == 0 || file.isEmpty()) {
            return RestBaseResponse.fail("文件内容为空");
        }

        if (!minioService.bucketExists(bucketName)) {
            return RestBaseResponse.fail(bucketName + "桶不存在");
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            return RestBaseResponse.fail("文件名称为空");
        }

        String newFileName = UUID.randomUUID().toString().replaceAll("-", "")
                + originalFilename.substring(originalFilename.lastIndexOf("."));

        log.info("文件{}生成唯一编码 -> {}", originalFilename, newFileName);

        try {
            minioService.uploadFile(bucketName, newFileName, file.getInputStream());
        } catch (Exception e) {
            log.error("{}文件上传失败", originalFilename, e);
            return RestBaseResponse.fail(originalFilename + "文件上传失败");
        }

        Map<String, String> result = new HashMap<>();
        result.put("endpoint", endpoint);
        result.put("file", newFileName);

        return RestBaseResponse.success(result);
    }

    @Operation(summary = "删除文件")
    @DeleteMapping("/file/{bucketName}/{fileName}")
    public RestBaseResponse<?> deleteFile(@PathVariable("bucketName") String bucketName, @PathVariable("fileName") String fileName) {
        minioService.removeFile(bucketName, fileName);

        return RestBaseResponse.success();
    }

    @Operation(summary = "获取文件信息")
    @GetMapping("/file/{bucketName}/{fileName}")
    public RestBaseResponse<?> getFileStatus(@PathVariable("bucketName") String bucketName, @PathVariable("fileName") String fileName) {
        return RestBaseResponse.success(minioService.getFileStatusInfo(bucketName, fileName));
    }

    @Operation(summary = "获取文件(默认时效30秒)")
    @PostMapping("/file/private/{bucketName}/{fileName}")
    public RestBaseResponse<?> getPrivateFile(@PathVariable("bucketName") String bucketName, @PathVariable("fileName") String fileName) {
        return RestBaseResponse.success(minioService.getPresignedObjectUrl(bucketName, fileName, null));
    }

    @Operation(summary = "文件下载")
    @GetMapping("/file/download/{bucketName}/{fileName}")
    public void downloadFile(@PathVariable("bucketName") String bucketName,
                             @PathVariable("fileName") String fileName,
                             HttpServletResponse response) {
        InputStream fileInputStream;

        try {
            fileInputStream = minioService.getObject(bucketName, fileName);
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setContentType("application/force-download");
            response.setCharacterEncoding("UTF-8");
            IOUtils.copy(fileInputStream, response.getOutputStream());
        } catch (Exception e) {
            log.error(fileName + "文件下载失败");
        }

    }
}

