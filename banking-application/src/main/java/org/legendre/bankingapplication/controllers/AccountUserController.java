package org.legendre.bankingapplication.controllers;

import jakarta.validation.Valid;
import org.legendre.bankingapplication.models.AccountResource;
import org.legendre.bankingapplication.models.AccountUser;
import org.legendre.bankingapplication.services.AccountUserService;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
public class AccountUserController {

    private final AccountUserService accountUserService;
    public AccountUserController(AccountUserService accountUserService) {
        this.accountUserService = accountUserService;
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<AccountUser>> getAllUsers() {
        return accountUserService.getAllUsers();
    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<Optional<AccountUser>> getUserById(@PathVariable long id) {
        return accountUserService.getById(id);
    }

    @GetMapping("/searchUser")
    public ResponseEntity<AccountUser> getUserByUsername(@RequestParam String username) {
        return accountUserService.getByUsername(username);
    }

    @PostMapping("/createNewUser")
    public ResponseEntity<AccountUser> addNewAccountUser(@RequestBody @Valid AccountUser user){
        return accountUserService.createNewAccountUser(user);
    }

    @PutMapping("/updateUserDetails")
    public ResponseEntity<AccountUser> updateAccountUser(@RequestParam long id, @RequestBody @Valid AccountUser user){
        return accountUserService.updateUserDetails(id, user);
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<AccountUser> deleteUser(@PathVariable long id){
        return accountUserService.deleteAccountUser(id);
    }

    @GetMapping("/resources/{id}")
    public ResponseEntity<AccountResource> getAccountUserResource(@PathVariable long id){
        Optional<AccountUser> user = accountUserService.getById(id).getBody();
        AccountResource accountResource = new AccountResource();

        assert Objects.requireNonNull(user).isPresent();
        user.ifPresent(accountResource::setUser);

        Link get = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AccountUserController.class).getUserById(id)).withRel("get");
        Link update = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AccountUserController.class).updateAccountUser(id,user.get())).withRel("update");
        Link delete = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AccountUserController.class).deleteUser(id)).withRel("delete");
        accountResource.add(get, update, delete);

        return new ResponseEntity<>(accountResource, HttpStatus.OK);
    }
}
