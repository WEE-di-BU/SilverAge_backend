package ynu.edu.security.service;

import ynu.edu.security.entity.User;

public interface UserService {
    User findByUsername(String username);
}