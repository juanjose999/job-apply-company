package com.job.controller;

import com.job.entities.offer_apply_user.OfferApplyUser;
import com.job.entities.offer_apply_user.dto.FormResponseApplyOffer;
import com.job.entities.offer_apply_user.dto.FormUserApplyOffer;
import com.job.entities.user.MyUser;
import com.job.entities.user.dto.FormUpdateUser;
import com.job.entities.user.dto.MyUserDto;
import com.job.entities.user.dto.MyUserResponseDto;
import com.job.exception.exceptions.MyUserNotFoundException;
import com.job.exception.exceptions.OfferIsDesactiveException;
import com.job.exception.exceptions.OfferNotFoundException;
import com.job.repository.myuser.IMyUserRepository;
import com.job.service.cloudinary.CloudinaryService;
import com.job.service.myuser.IMyUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/${app.version}/users")
public class MyUserController {

    private final IMyUserService myUserService;
    private final CloudinaryService cloudinaryService;
    private final IMyUserRepository myUserRepository;

    @GetMapping
    public ResponseEntity<List<MyUserResponseDto>> findAll() {
        return ResponseEntity.ok(myUserService.allUser());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<MyUserResponseDto> findUserByEmail(@PathVariable String email) throws MyUserNotFoundException {
        return ResponseEntity.ok(myUserService.findUserByEmail(email));
    }

    @PostMapping
    public ResponseEntity<MyUserResponseDto> saveUser(@RequestBody MyUserDto myUserDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(myUserService.saveUser(myUserDto));
    }

    @PostMapping("/apply-to-offer")
    public ResponseEntity<FormResponseApplyOffer> userApplyOffer(@RequestBody FormUserApplyOffer formUserApplyOffer)
            throws OfferIsDesactiveException, MyUserNotFoundException, OfferNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(myUserService.userApplyOffer(formUserApplyOffer));
    }

    @PostMapping("/upload/img/profile")
    public ResponseEntity<?> uploadImgProfile(@RequestParam("file") MultipartFile file, @RequestParam String emailUser) throws MyUserNotFoundException, IOException {
        Optional<MyUser> user = myUserRepository.findUserByEmail(emailUser);
        if(user.isPresent()) {
            try{
                MyUser myUser = user.get();
                String imgUrl = cloudinaryService.uploadImg(file.getBytes());
                myUser.setUrlImgProfile(imgUrl);
                myUserRepository.saveUser(myUser);
                return ResponseEntity.ok().body(imgUrl);
            }catch (Exception e){
                return ResponseEntity.status(500).body("Error in uploading img profile user");
            }
        }else{
            return ResponseEntity.status(404).body("User not found");
        }
    }


    @GetMapping("/applications")
    public ResponseEntity<List<FormResponseApplyOffer>> findAllApplications(@RequestParam String email) throws MyUserNotFoundException {
        return ResponseEntity.ok(myUserService.findApplicationByEmail(email));
    }

    @PutMapping
    public ResponseEntity<MyUserResponseDto> updateUser(@RequestBody FormUpdateUser formUpdateUser) throws MyUserNotFoundException {
        return ResponseEntity.ok(myUserService.updateUser(formUpdateUser));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@RequestBody String emailUser) throws MyUserNotFoundException {
        boolean result = myUserService.deleteUser(emailUser);
        return result ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
