package com.job.controller;

import com.job.entities.offer_apply_user.OfferApplyUser;
import com.job.entities.offer_apply_user.dto.FormResponseApplyOffer;
import com.job.entities.offer_apply_user.dto.FormUserApplyOffer;
import com.job.entities.user.dto.FormUpdateUser;
import com.job.entities.user.dto.MyUserDto;
import com.job.entities.user.dto.MyUserResponseDto;
import com.job.exception.exceptions.MyUserNotFoundException;
import com.job.exception.exceptions.OfferIsDesactiveException;
import com.job.exception.exceptions.OfferNotFoundException;
import com.job.service.myuser.IMyUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/${app.version}/users")
public class MyUserController {

    private final IMyUserService myUserService;

    @GetMapping("/all")
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
