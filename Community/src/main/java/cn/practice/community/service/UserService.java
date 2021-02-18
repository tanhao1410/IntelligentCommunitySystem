package cn.practice.community.service;

import cn.practice.community.mapper.UserMapper;
import cn.practice.community.model.User;
import cn.practice.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by tanhao on 2020/5/23.
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User getUserByUserName(String userName){
        UserExample example = new UserExample();
        example.createCriteria().andNameEqualTo(userName);
        List<User> users = userMapper.selectByExample(example);
        if(users.size() < 1){
            return null;
        }else{
            return users.get(0);
        }
    }

    public void createOrUpdate(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andNameEqualTo(user.getName());
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size() == 0) {
            // 插入
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }
    }
}
