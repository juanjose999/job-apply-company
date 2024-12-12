package com.job.user.service;

import com.job.offer_user.dto.FormResponseApplyOffer;
import com.job.offer_user.dto.FormUserApplyOffer;
import com.job.user.dto.FormUpdateUser;
import com.job.user.dto.MyUserDto;
import com.job.user.dto.MyUserResponseDto;
import com.job.shared.exception.exceptions.MyUserNotFoundException;
import com.job.shared.exception.exceptions.OfferIsDesactiveException;
import com.job.shared.exception.exceptions.OfferNotFoundException;
import io.vavr.control.Either;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IMyUserService {
    List<MyUserResponseDto> allUser();
    MyUserResponseDto findUserByEmail(String email) throws MyUserNotFoundException;
    List<FormResponseApplyOffer> findApplicationByEmail(String emailUser) throws MyUserNotFoundException;
    Either<String, String> uploadImgProfile(MultipartFile file, String emailUser);
    MyUserResponseDto saveUser(MyUserDto userDto);
    FormResponseApplyOffer userApplyOffer(FormUserApplyOffer formUserApplyOffer) throws MyUserNotFoundException, OfferNotFoundException, OfferIsDesactiveException;
    MyUserResponseDto updateUserByEmail(FormUpdateUser formUpdateUser) throws MyUserNotFoundException;
    String updateBiographyUserByEmail(String bio, String email) throws MyUserNotFoundException;
    boolean deleteUser(String email) throws MyUserNotFoundException;
}
