package com.pm.ecommerce.product_service.services;

import com.pm.ecommerce.entities.User;
import com.pm.ecommerce.product_service.models.UserResponse;
import com.pm.ecommerce.product_service.repositories.UserRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    @Autowired
    UserRepositories userRepositories;

    public UserResponse updateuserinformation(User user, int userid) throws Exception{

        User finduser=userRepositories.findById(userid).orElse(null);
        if(finduser==null){

            throw new Exception("user not found");
        }
        if(user.getPassword()==null){

            throw new Exception("you did not fill the pssword");
        }
        if(!finduser.getPassword().equals(user.getPassword())){

               finduser.setPassword(user.getPassword());

        }
        else{
            throw new Exception("you insert the previous password");
        }
          userRepositories.save(finduser);


         return new UserResponse(finduser);
    }


}
