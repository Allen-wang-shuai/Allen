package cn.itcast.test;

import cn.itcast.domain.Department;
import cn.itcast.domain.Employee;
import cn.itcast.mapper.DepartmentMapper;
import cn.itcast.mapper.EmployeeMapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

/**
 * 测试dao层
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( locations = "classpath:applicationContext.xml")
public class MapperTest {

    @Autowired
    DepartmentMapper departmentMapper;

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    SqlSession sqlSession;

    @Test
    public void testCRUD(){

        //检测是否自动注入成功
//        System.out.println(departmentMapper);
//        System.out.println(employeeMapper);

        //部门测试
//        departmentMapper.insertSelective(new Department(null,"开发部"));
//        departmentMapper.insertSelective(new Department(null,"测试部"));

        //员工测试
//        employeeMapper.insertSelective(new Employee(null,"紫霞仙子","F","onelife@xiyou.com",1));
//        employeeMapper.insertSelective(new Employee(null,"至尊宝","M","onelove@xiyou.com",2));
        //批量插入多个员工，使用可以执行批量操作的SqlSession获得EmployeeMapper对象执行循环操作
        //如果不用SqlSession获得EmployeeMapper对象来执行循环操作因为不是批量的，会很慢
        EmployeeMapper employeeMapper2 = sqlSession.getMapper(EmployeeMapper.class);
        for (int i=0;i<500;i++){
            String name = UUID.randomUUID().toString().substring(0, 5)+i;
//            employeeMapper2.insertSelective(new Employee(null,name,"M",name+"@shuai.com",2));
        }


    }
}
