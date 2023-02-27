package com.imooc.mall.service;

import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.model.dao.UserMapper;
import com.imooc.mall.model.pojo.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class UserServiceTest {
    @Resource
    private UserService userService;


    @Test
    void register() throws ImoocMallException, NoSuchAlgorithmException {
        userService.register("德寶","12345678");
    }

    @Test
    void getUser() {
        User user=userService.getUser(1);
        System.out.println(user);
    }
}