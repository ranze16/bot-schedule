package com.ranze.schedule.service;

import com.ranze.schedule.mapper.UserInfoMapper;
import com.ranze.schedule.pojo.UserInfo;
import com.ranze.schedule.pojo.UserInfoExample;
import com.ranze.schedule.util.UniqueIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserInfoService {
    @Autowired
    UniqueIDUtil uniqueIDUtil;

    @Autowired
    UserInfoMapper userInfoMapper;

    public void insertUserInfo(String userId, String nickName) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(uniqueIDUtil.nextId());
        userInfo.setUserId(userId);
        userInfo.setNickName(nickName);
        userInfoMapper.insertSelective(userInfo);
    }

    public UserInfo getUserInfo(String userId) {
        UserInfoExample userInfoExample = new UserInfoExample();
        UserInfoExample.Criteria criteria = userInfoExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        List<UserInfo> userInfos = userInfoMapper.selectByExample(userInfoExample);
        if (userInfos.isEmpty()) {
            return null;
        }
        return userInfos.get(0);
    }
}
