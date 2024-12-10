package com.job.service.cloudinary;

import java.io.IOException;

public interface ICloudinaryService {
    String uploadImg(byte[] imgBytes) throws IOException;
}
