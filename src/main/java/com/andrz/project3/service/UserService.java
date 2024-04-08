package com.andrz.project3.service;

import com.andrz.project3.entity.Role;
import com.andrz.project3.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collection;
import java.util.List;

public interface UserService extends UserDetailsService {
    Collection<? extends GrantedAuthority> mapRolesToAuthorities(List<Role> list);
}