package com.job.service.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public String uploadImg(byte[] imgBytes) throws IOException {
        Map uploadParams = cloudinary.uploader().upload(imgBytes, ObjectUtils.emptyMap());
        return uploadParams.get("url").toString();
    }

}
