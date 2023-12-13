package com.PicPayTest.repositories;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.PicPayTest.domain.user.User;
import com.PicPayTest.domain.user.UserType;
import com.PicPayTest.dtos.UserDTO;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManager;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {
    
    @Autowired
    UserRepository repository;

    @Autowired
    EntityManager entityManeger;

    @Test
    @DisplayName("Should get user sucessfully")
    void findUserByDocumentCase1(){
        String document = "0000099999";
        UserDTO data = new UserDTO("Natan", "Lucena", document, new BigDecimal(1000), "email@test.com", "1234", UserType.COMMOM);
        this.createUser(data);

        Optional<User> result = this.repository.findUserByDocument(document);

        assertThat(result.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should get user when us does not exists")
    void findUserByDocumentCase2(){
        String document = "0000099999";

        Optional<User> result = this.repository.findUserByDocument(document);

        assertThat(result.isEmpty()).isTrue();
    }

    private User createUser(UserDTO data){
        User newUser = new User(data);
        this.entityManeger.persist(newUser);
        return newUser;
    }

}
