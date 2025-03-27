package ynu.edu.security.service.impl;

import org.springframework.stereotype.Service;
import ynu.edu.security.entity.User;
import ynu.edu.security.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    // 这里应该注入你的用户Mapper/Repository
    // 示例数据，需根据实际数据库操作修改
    @Override
    public User findByUsername(String username) {
        if ("admin".equals(username)) {
            User user = new User();
            user.setId(1L);
            user.setUsername("admin");
            user.setPassword("$2a$10$你的加密密码");
            return user;
        }
        return null;
    }
}