package com.justforfun.app.api.users.data.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.justforfun.app.api.users.data.creditcard.CreditCardEntity;
import com.justforfun.app.api.users.data.transaction.TransactionEntity;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name="users")
public class UserEntity implements Serializable {

    private static final long serialVersionUID = -2731425678149216053L;

    @Id
    @GeneratedValue
    private long id;

    // notice that the fields below is the same the UserDTO
    @Column(nullable=false, length=50)
    private String firstName;

    @Column(nullable=false, length=50)
    private String lastName;

    @Column(nullable=false, length=120, unique=true)
    private String email;

    @Column(nullable=false, unique=true)
    private String userId;

    @Column(nullable=false, unique=true)
    @JsonIgnore
    private String encryptedPassword;

    // added for the relation with the CreditCardEntity
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<CreditCardEntity> creditCards;

    // added for the relation with the TransactionEntity
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<TransactionEntity> transactions;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
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
