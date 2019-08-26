package cn.itcast.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.itcast.domain.Admin;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


public class AdminLoginInterceptor implements HandlerInterceptor {


	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {  
		// TODO Auto-generated method stub 

	}


	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub

	}
 
	
	public boolean preHandle(HttpServletRequest request,
							 HttpServletResponse response,
							 Object arg2) throws Exception {
		// TODO Auto-generated method stub

		//对于拦截器一定要注意，其方法形式是固定的，除了方法体可以随便写东西外，其他的比如方法的参数，声明
		//都不能动

		Admin admin = (Admin) request.getSession().getAttribute("admin");
		if(admin == null){
			request.setAttribute("error", "您还没有登录请先登录");
			request.getRequestDispatcher("/admin/login").forward(request,response);
			return false;
		}else{
			return true;
		}
	}

}
