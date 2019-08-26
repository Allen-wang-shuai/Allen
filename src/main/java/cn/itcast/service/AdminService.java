package cn.itcast.service;

import cn.itcast.domain.Admin;
import cn.itcast.domain.AdminExample;
import cn.itcast.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    AdminMapper adminMapper;

    public Admin login(String username, String password) {
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andUsernameEqualTo(username);
        criteria.andPasswordEqualTo(password);
        List<Admin> selectByExample = adminMapper.selectByExample(adminExample);
        if (selectByExample != null && selectByExample.size() > 0) {
            return selectByExample.get(0);
        } else {
            return null;
        }
    }


}
