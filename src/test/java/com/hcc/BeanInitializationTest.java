/*package com.hcc;

import com.hcc.filters.JwtAuthenticationFilter;
import com.hcc.utils.JWTUtils;
import com.hcc.services.UserDetailServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest(classes = BackendApplication.class) // Specify your main application class here
public class BeanInitializationTest {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Test
    public void contextLoads() {
        Assert.notNull(jwtAuthenticationFilter, "JwtAuthenticationFilter should not be null");
        Assert.notNull(jwtUtils, "JWTUtils should not be null");
        Assert.notNull(userDetailService, "UserDetailServiceImpl should not be null");
    }
}*/


