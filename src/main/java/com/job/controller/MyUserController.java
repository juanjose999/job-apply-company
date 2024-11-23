package com.job.controller;

import com.job.entities.apply.dto.FormResponseApplyOffer;
import com.job.entities.apply.dto.FormUserApplyOffer;
import com.job.entities.user.dto.FormUpdateUser;
import com.job.entities.user.dto.MyUserDto;
import com.job.entities.user.dto.MyUserResponseDto;
import com.job.exception.MyUserNotFoundException;
import com.job.exception.OfferIsDesactiveException;
import com.job.exception.OfferNotFoundException;
import com.job.service.myuser.IMyUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class MyUserController {

    private final IMyUserService myUserService;

    @GetMapping("/all")
    public ResponseEntity<List<MyUserResponseDto>> findAll() {
        return ResponseEntity.ok(myUserService.allUser());
    }

    @GetMapping("/email")
    public ResponseEntity<MyUserResponseDto> getUserByEmail(@RequestBody String emailUser) throws MyUserNotFoundException {
        return ResponseEntity.ok(myUserService.findUserByEmail(emailUser));
    }

    @PostMapping
    public ResponseEntity<MyUserResponseDto> saveUser(@RequestBody MyUserDto myUserDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(myUserService.saveUser(myUserDto));
    }

    @PostMapping("/apply-offer")
    public ResponseEntity<FormResponseApplyOffer> userApplyOffer(@RequestBody FormUserApplyOffer formUserApplyOffer)
            throws OfferIsDesactiveException, MyUserNotFoundException, OfferNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(myUserService.userApplyOffer(formUserApplyOffer));
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
