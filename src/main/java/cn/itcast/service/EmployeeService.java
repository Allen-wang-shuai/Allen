package cn.itcast.service;

import cn.itcast.domain.Employee;
import cn.itcast.domain.EmployeeExample;
import cn.itcast.domain.SelectedId;
import cn.itcast.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    EmployeeMapper employeeMapper;

    /**
     * 查询所有员工
     * @return
     */
    public List<Employee> findAll() {
        return employeeMapper.selectByExampleWithDept(null);
    }


    /**
     * 按照条件员工
     * @return
     */
    public List<Employee> findLike(String empName,String email,Integer did) {
        EmployeeExample employeeExample = new EmployeeExample();
        EmployeeExample.Criteria criteria = employeeExample.createCriteria();
        criteria.andDIdEqualTo(did);
        return employeeMapper.selectByExampleWithDept(employeeExample);
    }


    /**
     * 根据ID查询单个员工
     * @param id
     * @return
     */
    public Employee findById(String id){
        EmployeeExample employeeExample = new EmployeeExample();
        EmployeeExample.Criteria criteria = employeeExample.createCriteria();
        criteria.andEmpIdEqualTo(Integer.parseInt(id));
        List<Employee> employeeList = employeeMapper.selectByExample(employeeExample);
        Employee employee = employeeList.get(0);
        return employee;
    }

    /**
     * 删除单个员工
     * @param id
     */
    public void deleteEmpById(String id){
        EmployeeExample employeeExample = new EmployeeExample();
        EmployeeExample.Criteria criteria = employeeExample.createCriteria();
        criteria.andEmpIdEqualTo(Integer.parseInt(id));
        employeeMapper.deleteByExample(employeeExample);
    }


    /**
     * 删除选中的员工
     * @param selectedId
     */
    public void deleteSelected(List<SelectedId> selectedId){
        for (SelectedId id : selectedId) {
            System.out.println(id);
            if (id.getEmpId()!=null){
                EmployeeExample employeeExample = new EmployeeExample();
                EmployeeExample.Criteria criteria = employeeExample.createCriteria();
                criteria.andEmpIdEqualTo(Integer.parseInt(id.getEmpId()));
                employeeMapper.deleteByExample(employeeExample);
            }

            /*
                对于input标签的type的checkbox的值如果选中的话在提交时才会被提交，不选中不会被提交，一定要指定其name和value
                name可用于参数绑定，value是真正需要用到的值。
                对于这里为什么会出现选中两个输出了多于两个的selectedId并且只有两个有值其他的为Null是因为你在选的时候从第几个开始选的原因，
                我们在input的name属性中指定了集合的角标并且从零开始，如果你不从零开始选那么参数绑定集合类型时也会跳过该集合的第零个，放到你
                指定的角标那里，这样的话对于集合来说它是从零开始的输出的时候肯定是从零开始，这样的话你指定角标的前几个你没赋值肯定会输出，
                并且值肯定是Null。要想得到选中的和输出的一样那就从角标从零开始的开始选，并且中间不能间隔这样就一样了。
                这里涉及到了集合的知识。集合和checkbox选没选中放在一起就可以理解了
             */
        }
    }

    /**
     * 添加员工
     * @param employee
     */
    public void addEmp(Employee employee){

        employeeMapper.insertSelective(employee);
    }


    /**
     * 修改员工
     * @param employee
     */
    public void updateEmp(Employee employee){
        EmployeeExample employeeExample = new EmployeeExample();
        EmployeeExample.Criteria criteria = employeeExample.createCriteria();
        criteria.andEmpIdEqualTo(employee.getEmpId());
        employeeMapper.updateByExampleSelective(employee,employeeExample);
    }


}
