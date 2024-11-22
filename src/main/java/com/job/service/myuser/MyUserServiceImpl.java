package com.job.service.myuser;

import com.job.entities.company.Company;
import com.job.entities.offer.Offer;
import com.job.entities.user.MyUser;
import com.job.entities.user.dto.FormUpdateUser;
import com.job.entities.user.dto.MyUserDto;
import com.job.entities.user.dto.MyUserMapper;
import com.job.entities.user.dto.MyUserResponseDto;
import com.job.exception.MyUserNotFoundException;
import com.job.exception.OfferIsDesactiveException;
import com.job.exception.OfferNotFoundException;
import com.job.repository.myuser.IMyUserRepository;
import com.job.repository.offer.IOfferRepository;
import com.job.service.company.CompanyServiceImpl;
import com.job.service.company.ICompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyUserServiceImpl implements IMyUserService {

    private final IMyUserRepository myUserRepository;
    private final IOfferRepository offerRepository;

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
    public String userApplyOffer(String emailUser, Long idOffer) throws OfferNotFoundException, MyUserNotFoundException, OfferIsDesactiveException {
        MyUser findUser = myUserRepository.findUserByEmail(emailUser)
                .orElseThrow(() -> new MyUserNotFoundException("User not found with email : " + emailUser ));
        Offer findOffer = offerRepository.findOfferById(idOffer).orElseThrow(() -> new OfferNotFoundException("Offer not found with id : " + idOffer ));
        if(findOffer.isActive()){
            return myUserRepository.myUserApplyOffer(findUser, findOffer);
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
