package cn.itcast.test;

import cn.itcast.domain.Employee;
import com.github.pagehelper.PageInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

/**
 * 用spring测试模块提供的测试请求功能，测试CRUD请求的正确性
 * 注意：spring4测试的时候，需要servlet3.0或以上的支持！！！
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration( locations = {"classpath:applicationContext.xml","classpath:springmvc.xml"})
public class MVCTest {

    //获取IOC容器
    @Autowired
    WebApplicationContext context;

    //虚拟MVC请求，获取处理结果
    MockMvc mockMvc;

    @Before
    public void initMockMvc(){
      mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }


    @Test
    public void testPage() throws Exception{
        //用perform模拟发送请求
        //这里get()方法中的路径开始一定要加上/否则会空指针异常，不加/代表请求的资源和当前文件在同一个文件
        //系统里，显然这不是，所以要加/否则报错
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/employee/emps")
                .param("pageNumber", "5"))
                .andReturn();

        //请求成功以后在请求域中有PageInfo，取出PageInfo进行验证
        MockHttpServletRequest request = result.getRequest();

        //测试能否拿出pageInfo
        PageInfo pageInfo = (PageInfo) request.getAttribute("pageInfo");

        System.out.println("当前页码："+pageInfo.getPageNum());
        System.out.println("总页码："+pageInfo.getPages());
        System.out.println("总记录数：："+pageInfo.getTotal());
        System.out.println("连续显示几页：：");
        int[] nums = pageInfo.getNavigatepageNums();
        for (int num : nums) {
            System.out.println(" "+num);
        }

        //获取员工数据
        List<Employee> list = pageInfo.getList();
        for (Employee employee : list) {
            System.out.println("姓名："+employee.getEmpName());
        }

    }

}
