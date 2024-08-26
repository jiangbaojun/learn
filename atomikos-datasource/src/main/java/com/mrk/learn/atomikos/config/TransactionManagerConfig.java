package com.mrk.learn.atomikos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.jta.JtaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.atomikos.icatch.jta.UserTransactionManager;
import com.atomikos.icatch.jta.UserTransactionImp;

import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

@Configuration
public class TransactionManagerConfig {

    @Bean
    public UserTransaction userTransaction() throws Throwable {
        UserTransactionImp userTransaction = new UserTransactionImp();
        userTransaction.setTransactionTimeout(300);
        return userTransaction;
    }

    @Bean
    public TransactionManager atomikosTransactionManager() throws Throwable {
        UserTransactionManager manager = new UserTransactionManager();
        manager.setForceShutdown(true);
        return manager;
    }

    @Bean
    public PlatformTransactionManager transactionManager(UserTransaction userTransaction, TransactionManager atomikosTransactionManager) {
        return new JtaTransactionManager(userTransaction, atomikosTransactionManager);
    }
}
