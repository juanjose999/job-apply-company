package com.jobify.user.controller;

import com.jobify.company.dto.FormLogin;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final AuthenticationManager authenticationManager;

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

    @PostMapping("/register")
    public ResponseEntity<MyUserResponseDto> saveUser(@RequestBody MyUserDto myUserDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(myUserService.saveUser(myUserDto));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody FormLogin formLogin) throws MyUserNotFoundException {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(formLogin.username(), formLogin.password()));
        if(auth.isAuthenticated()){
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            System.out.println("el usuario con el nombre " + userDetails.getUsername() + " es " + userDetails.getAuthorities());
            return ResponseEntity.ok(userDetails);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
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
