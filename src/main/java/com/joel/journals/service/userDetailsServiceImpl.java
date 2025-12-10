package com.joel.journals.service;

import com.joel.journals.entity.UserEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.joel.journals.repositary.UserEntryRepo;

@Component
public class userDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserEntryRepo userrepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        UserEntry user =userrepo.findByUsername(username);
        if(user!=null){
            UserDetails userdetails= org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRoles().toArray(new String[0]))
                    .build();
            return userdetails;
        }
        throw new UsernameNotFoundException("user not found" + username);
    }

}
