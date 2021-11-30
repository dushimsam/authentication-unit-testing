package com.example.authenticationunittesting.dto.userdto;


import com.example.authenticationunittesting.model.User;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Data
public class UserDtoGet {
    private Long id;
    private String names;
    private String userName;
    private String email;
    public UserDtoGet(User user){
        BeanUtils.copyProperties(user,this);
    }
}
