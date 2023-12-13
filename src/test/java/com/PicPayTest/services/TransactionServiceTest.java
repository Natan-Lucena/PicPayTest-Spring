package com.PicPayTest.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.PicPayTest.domain.user.User;
import com.PicPayTest.domain.user.UserType;
import com.PicPayTest.dtos.TransactionDTO;
import com.PicPayTest.repositories.TransactionRepository;

public class TransactionServiceTest {
    
    @Mock
    private UserService userService;
    @Mock
    private TransactionRepository repository;
    @Mock
    private AuthorizationService authService;
    @Mock
    private NotificationService notificationService;
    
    @Autowired
    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should create transaction sucessfully")
    void createTransactionCase1() throws Exception {
        User sender = new User(1l,"Natan","Lucena","999991","emaail@test.com","12345", new BigDecimal(100),UserType.COMMOM);
        User receiver = new User(2l,"Julinho","Carcar","24","emaailde@teste.com","12345", new BigDecimal(100),UserType.COMMOM);
       
        when(userService.findUserById(1l)).thenReturn(sender);
        when(userService.findUserById(2l)).thenReturn(receiver);

        when(authService.authorizeTransaction(any(),any())).thenReturn(true);
        TransactionDTO request = new TransactionDTO(new BigDecimal(10), 1l, 2l);
        transactionService.createTransaction(request);

        verify(repository, times(1)).save(any());

        sender.setBalance(new BigDecimal(90)); 
        verify(userService,times(1)).saveUser(sender);

        receiver.setBalance(new BigDecimal(110)); 
        verify(userService,times(1)).saveUser(receiver);
    
        verify(notificationService,times(1)).sendNotification(sender, "You have send a transaction");
        
    }

    @Test
    @DisplayName("Should throw a excepetion when transaction is not allowerd")
    void createTransactionCase2() throws Exception{
        User sender = new User(1l,"Natan","Lucena","999991","emaail@test.com","12345", new BigDecimal(100),UserType.COMMOM);
        User receiver = new User(2l,"Julinho","Carcar","24","emaailde@teste.com","12345", new BigDecimal(100),UserType.COMMOM);
       
        when(userService.findUserById(1l)).thenReturn(sender);
        when(userService.findUserById(2l)).thenReturn(receiver);

        when(authService.authorizeTransaction(any(),any())).thenReturn(false);
        Exception thrown = Assertions.assertThrows(Exception.class, () -> {
            TransactionDTO request = new TransactionDTO(new BigDecimal(10), 1l, 2l);
            transactionService.createTransaction(request);
        });
        Assertions.assertEquals("Not authorized transaction", thrown.getMessage() );
    }
}
