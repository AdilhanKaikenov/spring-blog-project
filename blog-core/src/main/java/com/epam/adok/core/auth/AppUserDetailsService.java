package com.epam.adok.core.auth;

import com.epam.adok.core.entity.Role;
import com.epam.adok.core.entity.User;
import com.epam.adok.core.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppUserDetailsService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(AppUserDetailsService.class);

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = this.userService.getUserByLogin(username);

        String login = user.getLogin();
        String password = user.getPassword();
        List<Role> roles = user.getRoles();

        List<GrantedAuthority> authorities = this.getAuthorities(roles);

        return new UserPrincipal(login, password, authorities);
    }

    private List<GrantedAuthority> getAuthorities(List<Role> roles) {
        List<GrantedAuthority> authList = new ArrayList<>();

        for (Role role : roles) {
            authList.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authList;
    }
}
