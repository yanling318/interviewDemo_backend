package com.mercury.interview.service;

import com.mercury.interview.bean.User;
import com.mercury.interview.dao.RoleDao;
import com.mercury.interview.dao.UserDao;
import com.mercury.interview.http.AuthenticationSuccessResponse;
import com.mercury.interview.http.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleDao roleDao;

    public Response register(User user) {
        //encode password by autowired in SecurityConfig method
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User user1 = userDao.findByUsername(user.getUsername());
        System.out.println();
        try {
            if (user1 == null) {
                roleDao.getById(1);

                user.setRole(roleDao.getById(1));

                userDao.save(user);
                return new Response(true);

            } else {
                return new Response(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(false);
        }
    }
//    public Response findUser(User user) {
//
//    };
   public List<User> getAll() {
    return userDao.findAll();
}

    public Response checklogin(Authentication authentication) {
        System.out.print(userDao.findByUsername(authentication.getName()));
        System.out.print("-----------------------------------------");

        if (authentication != null) {
            Response response = new AuthenticationSuccessResponse(true, 200, "Logged In!", userDao.findByUsername(authentication.getName()));
            return response;
        } else {
            return new Response(false);
        }
    }

    public boolean isAdmin(Collection<? extends GrantedAuthority> profiles) {
        boolean isAdmin = false;
        for (GrantedAuthority profle : profiles) {
            if (profle.getAuthority().equals("ROLE_ADMIN")) {
                isAdmin = true;
            }
        }
        ;
        return isAdmin;
    }
    public String findByUsername(User user) {
//        System.out.println(username);
//        System.out.println(userDao.findByUsername(username) );
        if (userDao.findByUsername(user.getUsername()) != null) {

            String AlphaNumericString =
                    //"ABCDEFGHIJKLMNOPQRSTUVWXYZ"+
                    "0123456789"
                            + "abcdefghijklmnopqrstuvxyz";

            StringBuilder sb = new StringBuilder(6);
            for (int i = 0; i < 6; i++) {
                int index
                        = (int) (AlphaNumericString.length()
                        * Math.random());
                sb.append(AlphaNumericString
                        .charAt(index));
            }
            return sb.toString();
        } else {
            return null;
        }

    }

}
