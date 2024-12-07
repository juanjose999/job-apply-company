package com.job.service.myuser;

import com.job.entities.offer.Offer;
import com.job.entities.offer_apply_user.OfferApplyUser;
import com.job.entities.offer_apply_user.dto.FormResponseApplyOffer;
import com.job.entities.offer_apply_user.dto.FormUserApplyOffer;
import com.job.entities.user.dto.FormUpdateUser;
import com.job.entities.user.dto.MyUserDto;
import com.job.entities.user.dto.MyUserResponseDto;
import com.job.exception.exceptions.MyUserNotFoundException;
import com.job.exception.exceptions.OfferIsDesactiveException;
import com.job.exception.exceptions.OfferNotFoundException;

import java.util.List;

public interface IMyUserService {
    List<MyUserResponseDto> allUser();
    MyUserResponseDto findUserByEmail(String email) throws MyUserNotFoundException;
    List<FormResponseApplyOffer> findApplicationByEmail(String emailUser) throws MyUserNotFoundException;
    MyUserResponseDto saveUser(MyUserDto userDto);
    FormResponseApplyOffer userApplyOffer(FormUserApplyOffer formUserApplyOffer) throws MyUserNotFoundException, OfferNotFoundException, OfferIsDesactiveException;
    MyUserResponseDto updateUser(FormUpdateUser formUpdateUser) throws MyUserNotFoundException;
    boolean deleteUser(String email) throws MyUserNotFoundException;
}
