package com.jobify.user.controller;

import com.jobify.offer_user.dto.FormResponseApplyOffer;
import com.jobify.offer_user.dto.FormUserApplyOffer;
import com.jobify.user.dto.FormUpdateUser;
import com.jobify.user.dto.MyUserDto;
import com.jobify.user.dto.MyUserResponseDto;
import com.jobify.shared.exception.exceptions.MyUserNotFoundException;
import com.jobify.shared.exception.exceptions.OfferIsDesactiveException;
import com.jobify.shared.exception.exceptions.OfferNotFoundException;
import com.jobify.user.service.IMyUserService;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/${app.version}/users")
public class MyUserController {

    private final IMyUserService myUserService;

    @GetMapping
    public ResponseEntity<List<MyUserResponseDto>> findAllUsers() {
        return ResponseEntity.ok(myUserService.allUser());
    }

    @GetMapping("/applications")
    public ResponseEntity<List<FormResponseApplyOffer>> findAllApplications(@RequestParam String email) throws MyUserNotFoundException {
        return ResponseEntity.ok(myUserService.findApplicationByEmail(email));
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
        Either<String, String> userUploadImgProfile = myUserService.uploadImgProfile(file, emailUser);
        if(userUploadImgProfile.isLeft()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to upload profile");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userUploadImgProfile.get());
    }

    @PatchMapping("/update-bio")
    public ResponseEntity<?> updateUserBio(@RequestParam String emailUser, @RequestParam String bio) throws MyUserNotFoundException {
        return ResponseEntity.ok(myUserService.updateBiographyUserByEmail(emailUser, bio));
    }


    @PutMapping
    public ResponseEntity<MyUserResponseDto> updateUserByEmail(@RequestBody FormUpdateUser formUpdateUser) throws MyUserNotFoundException {
        return ResponseEntity.ok(myUserService.updateUserByEmail(formUpdateUser));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@RequestBody String emailUser) throws MyUserNotFoundException {
        boolean result = myUserService.deleteUser(emailUser);
        return result ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}