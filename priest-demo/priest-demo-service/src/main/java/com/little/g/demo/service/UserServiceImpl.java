package com.little.g.demo.service;

import com.little.g.common.dto.ListResultDTO;
import com.little.g.common.params.TimeQueryParam;
import com.little.g.demo.api.UserService;
import com.little.g.demo.dto.UserDTO;
import com.little.g.common.web.mapper.UserMapper;
import com.little.g.common.web.model.User;
import com.little.g.common.web.model.UserExample;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by lengligang on 2019/3/9.
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public boolean add(UserDTO entity) {
        User user=new User();
        BeanUtils.copyProperties(entity,user);
        return userMapper.insertSelective(user)>0;
    }

    @Override
    public UserDTO get(Integer id) {
        User user=userMapper.selectByPrimaryKey(id);
        if(user == null){
            return null;
        }
        UserDTO dto=new UserDTO();
        BeanUtils.copyProperties(user,dto);
        return dto;
    }

    @Override
    public boolean update(UserDTO entity) {
        if(entity.getId() == null) return false;
        User user=new User();
        BeanUtils.copyProperties(entity,user);
        return userMapper.updateByPrimaryKeySelective(user)>0;
    }

    @Override
    public boolean delete(Integer id) {
        return userMapper.deleteByPrimaryKey(id)>0;
    }

    @Override
    public ListResultDTO<UserDTO> list(TimeQueryParam param) {
        ListResultDTO<UserDTO> result=param.getResult(ListResultDTO.class);

        UserExample example = new UserExample();
        example.or().andCreateTimeLessThan(param.getLast());
        example.setOrderByClause(String.format("create_time desc limit %d",result.getLimit()));
        List<User> list= userMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(list)){
            return result;
        }
        result.setLast(list.get(list.size()-1).getCreateTime());
        result.setList(list.stream().map(entity->{
            UserDTO dto=new UserDTO();
            BeanUtils.copyProperties(entity,dto);
            return dto;
        }).collect(Collectors.toList()));

        return result;
    }


}
