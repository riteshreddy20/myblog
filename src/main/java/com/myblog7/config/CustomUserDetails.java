package com.myblog7.config;

import com.myblog7.entity.Role;
import com.myblog7.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class CustomUserDetails implements UserDetails {
    private User user;

    public CustomUserDetails(User user) {
        this.user= user;
    }


//        @Override
//    public Collection<? extends GrantedAuthority> getAuthorities(){
//        return ( Collection<? extends GrantedAuthority>)user.getRoles();
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return (Collection<? extends GrantedAuthority>) user.getRoles();

    }

    @Override
    public String getPassword(){
        return user.getPassword();
    }
    @Override
    public String getUsername(){
        return  user.getUsername();
    }
    @Override
    public boolean isAccountNonExpired(){
        return true;
    }
    @Override
    public boolean isAccountNonLocked(){
        return true;//customize as needed

    }
    @Override
    public boolean isCredentialsNonExpired(){
        return true;//customize as needed
    }
    @Override
    public boolean isEnabled(){
        return true;//customize as needed
    }
}
