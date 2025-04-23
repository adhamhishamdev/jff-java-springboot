package com.justforfun.app.api.users.shared;

import com.justforfun.app.api.users.data.creditcard.CreditCardEntity;
import com.justforfun.app.api.users.data.transaction.TransactionEntity;
import com.justforfun.app.api.users.ui.model.AlbumResponseModel;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class UserDTO implements Serializable {

    private static final long serialVersionUID = -953297098295050686L;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String userId;
    private String encryptedPassword;
    private Set<CreditCardEntity> creditCards;
    private Set<TransactionEntity> transactions;
    // added for the albums collections
    private List<AlbumResponseModel> albums;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public List<AlbumResponseModel> getAlbums() {
        return albums;
    }

    public void setAlbums(List<AlbumResponseModel> albums) {
        this.albums = albums;
    }

    public Set<CreditCardEntity> getCreditCards() {
        return creditCards;
    }

    public void setCreditCards(Set<CreditCardEntity> creditCards) {
        this.creditCards = creditCards;
    }

    public Set<TransactionEntity> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<TransactionEntity> transactions) {
        this.transactions = transactions;
    }
}
