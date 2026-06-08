package com.hivex.campusconnect.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.hivex.campusconnect.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;

    @Override
    public String uploadFile(MultipartFile file) {

        try {

            Map<?, ?> uploadResult =
                    cloudinary.uploader().upload(
                            file.getBytes(),
                            ObjectUtils.asMap(
                                    "resource_type",
                                    "auto"
                            )
                    );

            return uploadResult
                    .get("secure_url")
                    .toString();

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException(
                    "Upload failed : "
                            + e.getMessage()
            );
        }
    }
}