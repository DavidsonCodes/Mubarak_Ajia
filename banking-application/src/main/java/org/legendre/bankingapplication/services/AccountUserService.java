package org.legendre.bankingapplication.services;

import jakarta.validation.Valid;
import org.legendre.bankingapplication.models.AccountUser;
import org.legendre.bankingapplication.repositories.AccountUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AccountUserService {

    @Autowired
    private AccountUserRepository accountUserRepository;

    public ResponseEntity<List<AccountUser>> getAllUsers() {
        return new ResponseEntity<>(accountUserRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Optional<AccountUser>> getById(long id) {
        return new ResponseEntity<>(accountUserRepository.findById(id), HttpStatus.OK);
    }

    public ResponseEntity<AccountUser> getByUsername(String username) {
        return new ResponseEntity<>(accountUserRepository.findByUsername(username), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<AccountUser> createNewAccountUser(AccountUser user){
        return new ResponseEntity<>(accountUserRepository.save(user), HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<AccountUser> updateUserDetails(long id, AccountUser user){
        AccountUser accountUser = accountUserRepository.findById(id).get();
            accountUser.setFirstName(user.getFirstName());
            accountUser.setMiddleName(user.getMiddleName());
            accountUser.setLastName(user.getLastName());
            accountUser.setUsername(user.getUsername());
            accountUser.setPhoneNumber(user.getPhoneNumber());
        return new ResponseEntity<>(accountUserRepository.save(accountUser), HttpStatus.ACCEPTED);
    }
    
    @Transactional
    public ResponseEntity<AccountUser> deleteAccountUser(long id){
        AccountUser user = accountUserRepository.findById(id).get();
        accountUserRepository.deleteById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
