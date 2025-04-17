package com.app.service;

import com.app.models.MyUserPrincipal;
import com.app.pojos.User;
import com.app.repo.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo.findByUserName(username);

        if(user == null){
            System.out.println("User "+ username+" not found");
            throw new UsernameNotFoundException("User "+ username+" not found");
        }

        System.out.println(user);
        return new MyUserPrincipal(user);
    }
}
