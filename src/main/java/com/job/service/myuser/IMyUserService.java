package com.job.service.myuser;

import com.job.entities.offer.Offer;
import com.job.entities.user.MyUser;
import com.job.entities.user.dto.FormUpdateUser;
import com.job.entities.user.dto.MyUserDto;
import com.job.entities.user.dto.MyUserResponseDto;
import com.job.exception.MyUserNotFoundException;
import com.job.exception.OfferIsDesactiveException;
import com.job.exception.OfferNotFoundException;

import java.util.List;

public interface IMyUserService {
    List<MyUserResponseDto> allUser();
    MyUserResponseDto findUserByEmail(String email) throws MyUserNotFoundException;
    MyUserResponseDto saveUser(MyUserDto userDto);
    String userApplyOffer(String emailUser, Long idOffer) throws MyUserNotFoundException, OfferNotFoundException, OfferIsDesactiveException;
    MyUserResponseDto updateUser(FormUpdateUser formUpdateUser) throws MyUserNotFoundException;
    boolean deleteUser(String email) throws MyUserNotFoundException;
}
