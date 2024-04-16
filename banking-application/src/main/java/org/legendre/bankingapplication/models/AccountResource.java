package org.legendre.bankingapplication.models;

import org.springframework.hateoas.RepresentationModel;

public class AccountResource extends RepresentationModel<AccountResource> {

    private AccountUser user;

    public AccountUser getUser() {
        return user;
    }

    public void setUser(AccountUser user) {
        this.user = user;
    }
}