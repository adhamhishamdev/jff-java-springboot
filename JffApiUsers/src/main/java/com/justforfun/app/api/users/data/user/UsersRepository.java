package com.justforfun.app.api.users.data.user;

import com.justforfun.app.api.users.shared.UserTransactionDetailsDTO;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

// UsersRepository is used for managing instances of "UserEntity"
// UserEntity has a primary key of type "Long"
public interface UsersRepository extends CrudRepository<UserEntity, Long> {

    // added as no predefined method
    UserEntity findByEmail(String email);
    UserEntity findByUserId(String userId);

    @Query("SELECT new com.justforfun.app.api.users.shared.UserTransactionDetailsDTO(u.userId, u.firstName, u.lastName, u.email, " +
            "cc.cardNumber, cc.expirationDate, cc.cardHolderName, t.amount, t.transactionDate, t.transactionId) " +
            "FROM TransactionEntity t " +
            "LEFT JOIN t.creditCard cc " +
            "LEFT JOIN t.user u " +
            "WHERE u.userId = :userId")
    List<UserTransactionDetailsDTO> findUserTransactionDetailsByUserId(@Param("userId") String userId);

    @Modifying
    @Query("DELETE FROM UserEntity user " +
            "WHERE user.id IN " +
            "( SELECT userToBeDeleted.id FROM " +
            "( SELECT userAtBottom.id AS id FROM UserEntity userAtBottom ORDER BY userAtBottom.id DESC ) " +
            "userToBeDeleted WHERE ROWNUM <= :numberOfUsers)" )
    void deleteUsersByAmount(@Param("numberOfUsers") int numberOfUsers);

    // https://stackoverflow.com/questions/34725802/why-select-c-instead-of-select-in-jpql
    @Query("SELECT user.userId FROM UserEntity user")
    List<String> findAllUserIds();
}
