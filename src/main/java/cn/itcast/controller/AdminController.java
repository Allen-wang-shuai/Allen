package cn.itcast.controller;

import cn.itcast.domain.Admin;
import cn.itcast.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    /**
     * 根据用户名查询账户信息是否存在
     * @return
     */
    @RequestMapping("/checkUsername")
    public @ResponseBody void checkUsername(@RequestBody Admin admin){
        System.out.println("checkUsername方法执行了。。。");
        System.out.println(admin.getUsername());

    }


    //登陆页面
    @RequestMapping("/login")
    public String login(Model model) throws Exception {
        return "views/login";
    }

    //退出
    @RequestMapping("/logout")
    public void logout(HttpSession session,
                         HttpServletRequest request,
                         HttpServletResponse response) throws Exception {
        //session过期
        session.invalidate();
        request.getRequestDispatcher("/admin/login").forward(request,response);
    }

    // 管理员登录逻辑的编写
    @RequestMapping("/loginsubmit")
    public void loginSystem(HttpServletRequest request,
                            HttpServletResponse response,
                            HttpSession session,
                            @RequestParam(value = "username",required = false) String username,
                            @RequestParam(value = "password",required = false) String password,
                            @RequestParam(value = "verifycode",required = false) String verifycode) throws Exception{

        //3.验证码校验
        session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");//确保验证码一次性

        //别忘了忽略大小写
        //这里需要注意当你登陆后返回不刷新那么此时session域中已经没有值了，
        //那么取出来的checkcode_server就为Null所以当把!checkcode_server.equalsIgnoreCase(verifycode)
        //放到前面时就会报空指针异常可以在前面加一个checkcode_server==null
        if (checkcode_server==null||!checkcode_server.equalsIgnoreCase(verifycode)){
            //验证码不正确
            //提示信息
            request.setAttribute("error", "验证码错误");

            request.getRequestDispatcher("/admin/login").forward(request,response);
            return;
        }

        Admin admin = adminService.login(username, password);

        if (admin != null) {
            request.getSession().setAttribute("admin",admin);
            request.getRequestDispatcher("/employee/emps").forward(request,response);
            return;

        } else {
            // 登录失败
            // System.out.println("用户名或密码不正确。。。");
            request.setAttribute("error", "用户名或密码错误");
            request.getRequestDispatcher("/admin/login").forward(request,response);
        }
    }

    //验证码的生成
    @RequestMapping("/checkCode")
    public void CheckCode(HttpServletRequest request, HttpServletResponse response) throws Exception{

        //服务器通知浏览器不要缓存
        response.setHeader("pragma","no-cache");
        response.setHeader("cache-control","no-cache");
        response.setHeader("expires","0");

        //在内存中创建一个长80，宽30的图片，默认黑色背景
        //参数一：长
        //参数二：宽
        //参数三：颜色
        int width = 80;
        int height = 30;
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);

        //获取画笔
        Graphics g = image.getGraphics();
        //设置画笔颜色为灰色
        g.setColor(Color.GRAY);
        //填充图片
        g.fillRect(0,0, width,height);

        //产生4个随机验证码，12Ey
        String checkCode = getCheckCode();
        //将验证码放入HttpSession中
        request.getSession().setAttribute("CHECKCODE_SERVER",checkCode);

        //设置画笔颜色为黄色
        g.setColor(Color.YELLOW);
        //设置字体的小大
        g.setFont(new Font("黑体",Font.BOLD,24));
        //向图片上写入验证码
        g.drawString(checkCode,15,25);

        //将内存中的图片输出到浏览器
        //参数一：图片对象
        //参数二：图片的格式，如PNG,JPG,GIF
        //参数三：图片输出到哪里去
        ImageIO.write(image,"PNG",response.getOutputStream());

    }

    /**
     * 产生4位随机字符串
     */
    private String getCheckCode() {
        String base = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        int size = base.length();
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        for(int i=1;i<=4;i++){
            //产生0到size-1的随机值
            int index = r.nextInt(size);
            //在base字符串中获取下标为index的字符
            char c = base.charAt(index);
            //将c放入到StringBuffer中去
            sb.append(c);
        }
        return sb.toString();
    }

}
