package com.jobify.shared.cloudinary.service;

import java.io.IOException;

public interface ICloudinaryService {
    String uploadImg(byte[] imgBytes) throws IOException;
}
