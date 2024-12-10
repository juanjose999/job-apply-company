package com.job.service.myuser;

import com.job.entities.offer_apply_user.OfferApplyUser;
import com.job.entities.offer_apply_user.dto.FormResponseApplyOffer;
import com.job.entities.offer_apply_user.dto.FormUserApplyOffer;
import com.job.entities.offer.Offer;
import com.job.entities.offer_apply_user.dto.OfferApplyUserMapper;
import com.job.entities.user.MyUser;
import com.job.entities.user.dto.FormUpdateUser;
import com.job.entities.user.dto.MyUserDto;
import com.job.entities.user.dto.MyUserMapper;
import com.job.entities.user.dto.MyUserResponseDto;
import com.job.exception.exceptions.MyUserNotFoundException;
import com.job.exception.exceptions.OfferIsDesactiveException;
import com.job.exception.exceptions.OfferNotFoundException;
import com.job.repository.apply.IApplyOfferJpa;
import com.job.repository.myuser.IMyUserRepository;
import com.job.repository.offer.IOfferRepository;
import com.job.service.cloudinary.ICloudinaryService;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyUserServiceImpl implements IMyUserService {

    private final IMyUserRepository myUserRepository;
    private final IOfferRepository offerRepository;
    private final IApplyOfferJpa applyOfferJpa;
    private final ICloudinaryService cloudinaryService;

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
    public List<FormResponseApplyOffer> findApplicationByEmail(String emailUser) throws MyUserNotFoundException {
        MyUser findUser = myUserRepository.findUserByEmail(emailUser)
                .orElseThrow(() -> new MyUserNotFoundException("User not found with email : " + emailUser ));

        return applyOfferJpa.findByUserId(findUser.getId())
                .stream()
                .map(OfferApplyUserMapper::offerApplyToResponse)
                .toList();
    }

    @Override
    public Either<String, String> uploadImgProfile(MultipartFile file, String emailUser) {
        if(file==null || file.isEmpty()) return Either.left("File is empty, please load file");
        if(emailUser == null || emailUser.isEmpty()) return Either.left("Email user is empty");
        Optional<MyUser> myUser = myUserRepository.findUserByEmail(emailUser);
        if(myUser.isEmpty()){
            return Either.left("User not found with email : " + emailUser);
        }
        try {
            String urlImg = cloudinaryService.uploadImg(file.getBytes());
            myUser.get().setUrlImgProfile(urlImg);
            myUserRepository.saveUser(myUser.get());
            return Either.right("url image profile = " + urlImg);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

        Offer findOffer = offerRepository.findOfferById(formUserApplyOffer.idOffer())
                .orElseThrow(() -> new OfferNotFoundException("Offer not found with id : " + formUserApplyOffer.idOffer() ));

        boolean userIsApplyPrevious = findUser.getUserOffer().stream().anyMatch(u -> u.getUser().equals(findUser));
        if(userIsApplyPrevious){
            throw new OfferNotFoundException("You cannot apply again for a vacancy for which you are already registered.");
        }

        if(findOffer.isActive()){
            var apply = myUserRepository.myUserApplyOffer(findUser, findOffer);
            return new FormResponseApplyOffer(findOffer.getTitle(), apply.getDate_apply(), String.valueOf(apply.getStatus()));
        }
        throw new OfferIsDesactiveException("this offer not available");
    }

    @Override
    public MyUserResponseDto updateUserByEmail(FormUpdateUser formUpdateUser) throws MyUserNotFoundException {
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
    public String updateBiographyUserByEmail(String bio, String email) throws MyUserNotFoundException {
        if(bio == null || bio.isEmpty()) throw new MyUserNotFoundException("User not found with email : " + email);
        MyUser findUser = myUserRepository.findUserByEmail(email)
                .orElseThrow(() -> new MyUserNotFoundException("User not found with email : " + email ));
        findUser.setBiography(bio);
        myUserRepository.saveUser(findUser);
        return "Biography update is successfully";
    }

    @Override
    public boolean deleteUser(String email) throws MyUserNotFoundException {
        if(myUserRepository.deleteUser(email)){
            return true;
        }
        throw new MyUserNotFoundException("User not found with email : " + email);
    }
}
