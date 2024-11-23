package com.job.service.myuser;

import com.job.entities.apply.dto.FormResponseApplyOffer;
import com.job.entities.apply.dto.FormUserApplyOffer;
import com.job.entities.offer.Offer;
import com.job.entities.user.MyUser;
import com.job.entities.user.dto.FormUpdateUser;
import com.job.entities.user.dto.MyUserDto;
import com.job.entities.user.dto.MyUserMapper;
import com.job.entities.user.dto.MyUserResponseDto;
import com.job.exception.MyUserNotFoundException;
import com.job.exception.OfferIsDesactiveException;
import com.job.exception.OfferNotFoundException;
import com.job.repository.apply.IApplyOfferJpa;
import com.job.repository.myuser.IMyUserRepository;
import com.job.repository.offer.IOfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyUserServiceImpl implements IMyUserService {

    private final IMyUserRepository myUserRepository;
    private final IOfferRepository offerRepository;
    private final IApplyOfferJpa applyOfferJpa;

    @Override
    public List<MyUserResponseDto> allUser() {
        return myUserRepository.allUser().stream()
                .map(MyUserMapper::UserToUserDto)
                .toList();
    }

    @Override
    public MyUserResponseDto findUserByEmail(String email) throws MyUserNotFoundException{
        return myUserRepository.findUserByEmail(email).map(MyUserMapper::UserToUserDto)
                .orElseThrow(()->new MyUserNotFoundException("User not found with email : " + email ));
    }

    @Override
    public MyUserResponseDto saveUser(MyUserDto userDto) {
        return MyUserMapper.UserToUserDto(
                myUserRepository.saveUser(MyUserMapper.UserDtoToUser(userDto))
        );
    }

    @Override
    public FormResponseApplyOffer userApplyOffer(FormUserApplyOffer formUserApplyOffer) throws OfferNotFoundException, MyUserNotFoundException, OfferIsDesactiveException {
        MyUser findUser = myUserRepository.findUserByEmail(formUserApplyOffer.emailUser())
                .orElseThrow(() -> new MyUserNotFoundException("User not found with email : " + formUserApplyOffer.emailUser() ));
        Offer findOffer = offerRepository.findOfferById(formUserApplyOffer.idOffer()).orElseThrow(() -> new OfferNotFoundException("Offer not found with id : " + formUserApplyOffer.idOffer() ));
        if(findOffer.isActive()){
            var apply = myUserRepository.myUserApplyOffer(findUser, findOffer);
            return new FormResponseApplyOffer(findOffer.getTitle(), apply.getDate_apply(), String.valueOf(apply.getStatus()));
        }
        throw new OfferIsDesactiveException("this offer not available");
    }

    @Override
    public MyUserResponseDto updateUser(FormUpdateUser formUpdateUser) throws MyUserNotFoundException {
        MyUserDto userDto = MyUserDto.builder()
                .first_name(formUpdateUser.first_name())
                .last_name(formUpdateUser.last_name())
                .email(formUpdateUser.email())
                .password(formUpdateUser.password())
                .build();
        return MyUserMapper.UserToUserDto(
                myUserRepository.updateUser(formUpdateUser.emailFindUser(), MyUserMapper.UserDtoToUser(userDto))
                        .orElseThrow(()->new MyUserNotFoundException("User not found with email: " + formUpdateUser.email() ))
        );
    }

    @Override
    public boolean deleteUser(String email) throws MyUserNotFoundException {
        if(myUserRepository.deleteUser(email)){
            return true;
        }
        throw new MyUserNotFoundException("User not found with email : " + email);
    }
}
