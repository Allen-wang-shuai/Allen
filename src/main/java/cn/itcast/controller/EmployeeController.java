package cn.itcast.controller;

import cn.itcast.domain.Employee;
import cn.itcast.domain.SelectedId;
import cn.itcast.service.EmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *处理员工增删改查请求
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;


    /**
     * 查询员工数据，分页查询
     * @retur
     */
    @RequestMapping("/emps")
    public ModelAndView getEmps(@RequestParam(value = "pageNumber",defaultValue = "1") Integer pageNumber){

        ModelAndView modelAndView = new ModelAndView();

        //引入pageHelper分页插件
        //查询之前调用startPage()方法，传入页码和每页显示多少条数据
        PageHelper.startPage(pageNumber,8);
        //执行startPage()方法之后的查询就是一个分页查询
        List<Employee> employeeList = employeeService.findAll();
        //使用PageInfo包装查询后的结果，里面含有当前页、页码总数等详细页面信息，包括我们查询出来的数据
        // 只需将PageInfo交给页面就行，传入连续显示的页数
        PageInfo page = new PageInfo(employeeList,6);

        modelAndView.addObject("pageInfo",page);
        modelAndView.setViewName("views/list");

        return modelAndView;

    }


    /**
     * 查询员工数据，按条件、分页查询
     * @retur
     */
    @RequestMapping("/getLikeEmps")
    public ModelAndView getLikeEmps(@RequestParam(value = "pageNumber",defaultValue = "1") Integer pageNumber,
                                    @RequestParam(value = "empName",defaultValue = "null") String empName,
                                    @RequestParam(value = "email",defaultValue = "null") String email,
                                    @RequestParam(value = "deptName",defaultValue = "1") Integer did){

        ModelAndView modelAndView = new ModelAndView();

        //引入pageHelper分页插件
        //查询之前调用startPage()方法，传入页码和每页显示多少条数据
        PageHelper.startPage(pageNumber,8);

        //执行startPage()方法之后的查询就是一个分页查询
        List<Employee> employeeList = employeeService.findLike(empName,email,did);

        //使用PageInfo包装查询后的结果，里面含有当前页、页码总数等详细页面信息，包括我们查询出来的数据
        // 只需将PageInfo交给页面就行，传入连续显示的页数
        PageInfo page = new PageInfo(employeeList,6);

        modelAndView.addObject("pageInfo",page);
        modelAndView.setViewName("views/list");

        return modelAndView;

    }



    /**
     * 删除单个员工
     * @param request
     * @param response
     * @param id
     * @throws Exception
     */
    @RequestMapping("/deleteEmpoyee")
    public void deleteEmpoyee(HttpServletRequest request,
                              HttpServletResponse response,
                              @RequestParam("empId") String id) throws Exception{
        employeeService.deleteEmpById(id);
        request.getRequestDispatcher("/employee/emps").forward(request,response);
    }

    /**
     * 删除选中的
     * @param request
     * @param response
     * @param employee
     * @throws Exception
     */
    @RequestMapping("/deleteSelected")
    public void deleteSelected(HttpServletRequest request,
                               HttpServletResponse response,
                               Employee employee) throws Exception {
        List<SelectedId> list = employee.getList();
//        System.out.println(list);
        employeeService.deleteSelected(list);
        request.getRequestDispatcher("/employee/emps").forward(request,response);
    }

    /**
     * 处理跳转到添加页面的请求
     * @param model
     * @return
     */
    @RequestMapping("toAddJsp")
    public String toAddJsp(Model model){
        return "views/add";
    }

    @RequestMapping("addEmp")
    public void addEmp(HttpServletRequest request,
                       HttpServletResponse response,
                       Employee employee) throws Exception{
        employeeService.addEmp(employee);
        request.getRequestDispatcher("/employee/emps").forward(request,response);
    }


    /**
     * 跳转到修改员工信息页面
     * @param request
     * @param response
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping("/toUpdateJsp")
    public ModelAndView toUpdateJsp(HttpServletRequest request,
                              HttpServletResponse response,
                              @RequestParam("empId") String id) throws Exception{
        ModelAndView modelAndView = new ModelAndView();
        Employee employee = employeeService.findById(id);
        modelAndView.addObject("emp",employee);
        modelAndView.setViewName("views/update");
        return modelAndView;
    }

    /**
     * 修改员工
     * @param request
     * @param response
     * @param employee
     * @throws Exception
     */
    @RequestMapping("updateEmp")
    public void updateEmp(HttpServletRequest request,
                       HttpServletResponse response,
                       Employee employee) throws Exception{
        employeeService.updateEmp(employee);
        request.getRequestDispatcher("/employee/emps").forward(request,response);
    }


}
//路径问题：
//对于用ModelAndView和Model跳转到页面时用的是视图解析器

//对于转发的如：
// (request.getRequestDispatcher("/employee/emps").forward(request,response);)
//<jsp:forward page="/admin/login"></jsp:forward>
//自带的有当前项目路径，也只写一个当前项目路径，所以直接写/employee/emps就行

//对于jsp页面中的表单提交、超链接需要考虑带不带/，带斜杠就是当前项目的根路径即直接在8080后面
//不带/就是相对于当前页面所在路径
//web的路径问题：
//    不以/开始的相对路径，找资源，以当前资源的路径为基准，的判断当前资源和目标资源的位置关系，比较容易出问题，比如本项目的list页面当点击分页后
//    当前资源所在路径就变了，所以容易出错
//    以/开始的相对路径，找资源以服务器的路径为标准(http://localhost:8080)需要加上项目名
//                http://localhost:8080
//    如果以/开始的话要在/前加上项目路径
//所以对于jsp中的路径统一用：${APP_PATH}/employee/deleteEmpoyee绝不会错