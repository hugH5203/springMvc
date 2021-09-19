package in2021winter.controller;

import com.sun.jersey.api.client.WebResource;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author HuangHai
 * @date 2021/2/19 22:42
 */
//控制器类
    @Controller   //声明这是一个控制器实例
    @RequestMapping("test")  //该注解可以在方法上与类上，方法上为资源路径，类上为父文件夹路径，后面的路径加不加/都一样
    @SessionAttributes(value = {"user","user2"},types = {User.class,String.class})//value属性是将该数组的键中
    // modelAttribute底层map也有的值复制并存入session域。type指的是可以存入值的类型
public class HellowController {


        @RequestMapping(value = "hello",method =RequestMethod.GET,params = {"username=hh"})  //该注解有几个参数：value为路径，和path的作用一模一样，method用于指定的请求方式，
                                  // params用于限制指定请求参数的条件，且支持简单的表达式，headers用于限制指定请求头的条件
        public String sayHello(String username,String password){
            System.out.println("用户名："+username+"密码："+password);
            System.out.println("晚上哈哈"); //我也不知道为什么非要在runner里配置-Dfile.encoding=GBK才不会控制台乱码，为什么是gbk呢
            return "hellow";  //所有的controller方法内部跳到视图解析器的方式都是转发

        }

        @RequestMapping(value = "account",method = RequestMethod.POST,params = {"money"}) //必须的有money这个参数，且必须是post请求
        public String testJavaBean(Account account){
            System.out.println(account);
            return "hellow";
        }

        @RequestMapping("getRequest")
        public String testGetRequest(HttpServletRequest request, HttpServletResponse response){
            System.out.println(request);
            System.out.println(response);
            System.out.println(request.getContextPath());//  /springMVC
            System.out.println(request.getSession());//org.apache.catalina.session.StandardSessionFacade@59c6746
            System.out.println(request.getServletPath());//  /test/getRequest
            System.out.println(request.getSession().getServletContext());//org.apache.catalina.core.ApplicationContextFacade@c158287
            System.out.println(request.getRealPath("/"));// C:\IntelliJ IDEA 2019.3.3\idealProject\springMVC\src\main\webapp\

            return "hellow";
        }

        @RequestMapping("testRequestParam")
        public String testRequestParam(@RequestParam(name = "name" ,required = true) String username){
            //@RequestParam注解的作用是匹配相应的参数，防止请求中的参数与方法中的参数不同，将请求中的参数对应方法中参数.
            //required属性的作用表示该参数在请求中是否是必须的，否则报错，默认值为true.
            System.out.println(username);
            return "hellow";
        }

        @RequestMapping("testRequestBody")
        public String testRequestBody(@RequestBody String requestBody){
            //@RequestBody注解用于获取所有请求体，get请求方式不适用，因为get请求方式没有请求体
            System.out.println(requestBody);//打印出来的中文会使用urlEncode编码，要使用urlDecode方法解码成utf-8中文形式
            return "hellow";
        }

        @RequestMapping("testPathVariable/sss{theName}")
        public String testPathVariable(@PathVariable(name = "theName") String username){
            //@PathVariable注解将请求中该占位符所代表的地址绑定到操作方法的传入参数中，这是一种restful风格写法
            System.out.println(username);
            return "hellow";
        }

        @RequestMapping("requestHeader")
        public String requestHeader(@RequestHeader("Accept") String header){
            //该注解表示绑定请求中该请求头的信息
            System.out.println(header);
            return "hellow";
        }

    @RequestMapping("cookieValue")
    public String cookieValue(@CookieValue("JSESSIONID") String  cookie){
            //该注解表示绑定cookie域中键为JSESSIONID的值到方法参数cookie中
        System.out.println(cookie);
        return "hellow";
    }

    /**
     * modelAttribute注解，很重要，一般配合下面的testModelAttribute方法使用
     * @param uname
     * @param map
     */
    @ModelAttribute   //该注解会让该方法在所有方法执行前执行，一般用于防止表单提交时有些数据没有传值，为了防止没传值的变量不为null，就让它使用之前的值
    public void init(String uname, Map<String,User> map){  //这里的uname也可以绑定请求中的uname
        System.out.println("controller的所有方法中，我@ModelAttribute在最前面执行");
        User user = new User();
        user.setName(uname);
        user.setSex("男");
        System.out.println(user);
        map.put("user",user);  //这里的map就是一个域对象，map就是它的底层实现，优先存入request域中
    }

    @RequestMapping("modelAttribute")
    public String testModelAttribute(@ModelAttribute("user") User user){
            //@ModelAttribute作用于参数里时，表示将键中的值取出来并赋值到旁边的user里
        System.out.println(user);
            return "hellow";
    }

    /**
     * 将数据存入session域中
     * @param model
     * @return
     */
    @RequestMapping("sessionAttribute")
    public String testSessionAttribute(Model model){
        User user = new User();
        user.setName("大哥");
        user.setSex("男");
        model.addAttribute("user",user);//底层也是一个map实现的，request域就是这个modelAttribute域，
        //用了@sessionAttribute才会从这个域中将需要存入的值复制到session域
        return "attribute";
    }


    /**
     * 用model接口的实现类ModelMap来获取域中的值
     * @param modelMap
     * @return
     */
    @RequestMapping("getSessionAttribute")
    public String getSessionAttribute(ModelMap modelMap){
        User user = (User) modelMap.get("user");//modelMap是Model接口的实现类，用它可以获取modelAttribute的值，也就是所有域的值
        //用之前得先存值才行
        System.out.println(user);
        return "hellow";
    }


    /**
     * 去除session域中所有的值
     * @param sessionStatus
     * @return
     */
    @RequestMapping("deleteSessionAttribute")
    public String deleteSessionAttribute(SessionStatus sessionStatus,ModelMap modelMap){
        System.out.println((User) modelMap.get("user"));//现在还有
        sessionStatus.setComplete();  //用sessionStatus的setComplete方法清空session中的值
        System.out.println((User) modelMap.get("user"));//session域已经没了，但是modelAttribute域中还有，打印的是后者域中的
        return "hellow";
    }

    /**
     * 测试字符串的转发或者重定向，当SpringMVC识别这种特殊的字符串返回值，就好自动执行相应的操作
     * @return
     */
    @RequestMapping("ForwardOrRedirect")
    public String testForwardOrRedirect(){
        //return "forward:/WEB-INF/jsp/hellow.jsp";//springMVC的返回逻辑视图的字符串就转发其实就是这个转发，但此时就不能再写逻辑视图必须写实际视图
        //return "redirect:/jsp/hellow.jsp";这种写法是错误的，因为重定向是不能访问WEB-INF文件夹下的资源的，只有服务器内部才能访问，即servlet或转发
        return "redirect:/index.jsp";
    }


    /**
     * 测试一个重要的类：ModelAndView
     * @return
     */
    @RequestMapping("ModelAndView")
    public ModelAndView testModelAndView(){
        //创建modelAndView对象
        ModelAndView mv= new ModelAndView();//这个实例既可以存值到域中又可以返回视图
        User user = new User();
        user.setName("二弟");
        user.setSex("女");
        mv.addObject("user2",user);//将值存储到该次请求对应的域中，一般是request域
        mv.setViewName("attribute");
        return mv;//一次返回该请求的model与view，这也是ModelAndView这个类所存在的意义，因为一般的return只能返回一个相同类型对象
    }


    /**
     * 测试@RequestBody与ajax的配合用法
     * @param user
     * @return
     */
    @RequestMapping("ajax")
    @ResponseBody  //该注解将方法的返回值并且转成json对象绑定到响应体中即response中
    public User testAjax(@RequestBody User user){
        user.setName("刘备");
        user.setSex("女");
        System.out.println(user);
        return user;
    }


    /**
     * 测试一下异常处理器的使用
     * @return
     */
    @RequestMapping("myExceptionResolver")
    public String testMyException() throws MyException {
        try {
            int a=3/0;
        } catch (Exception e) {
            e.printStackTrace();
            //抛出自定义异常
            throw new MyException("出现异常！！");
        }
        return "hellow";
    }


    /**
     * 测试springMVC的文件上传
     * 要配置文件解析器才行
     * 注意：这里的参数变量必须与表单中的上传的name的变量名一模一样，否则就不能上传成功
     * @return
     */
    @RequestMapping("fileUpload")
    public String testFileUpload(HttpServletRequest request, MultipartFile upload) throws IOException {
        //获取要上传到服务器的文件目录（这里的"/upload"目录在src/main/webapp/下）
        String path = request.getSession().getServletContext().getRealPath("/upload");
        //创建文件对象,对象在这个目录里上传文件
        File file = new File(path);
        //判断该路径是否存在，不存在就要去创建
        if (!file.exists()){
            file.mkdirs();
        }
        //从提交的表单中获取上传的文件名称
        String filename = upload.getOriginalFilename();
        //使用UUID类（唯一识别码）获取一个随机的不会重复的识别码，并且将“-”替换成“空格”，再忽略大小写
        String uuid = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
        //将上传的原文件名与UUID码拼接在一起形成新的文件名，把文件名改成唯一的是为了防止同名字文件的覆盖
        filename=filename+"_"+uuid;
        //最后一步也是最重要一步，就是：上传文件
        upload.transferTo(new File(file,filename));//上传到一个路径中去（即src/main/webapp/upload/），新new的file对象由父路径与新文件名决定
        System.out.println("文件上传成功");
        return "hellow";
    }


/*    *//**
     * 传统的web项目文件上传方式
     * @param request
     * @return
     * @throws Exception
     *//*
    @RequestMapping("fileupload2")
    public String fileupload(HttpServletRequest request) throws Exception {
// 先获取到要上传的文件目录
        String path = request.getSession().getServletContext().getRealPath("/uploads");
// 创建File对象，一会向该路径下上传文件
        File file = new File(path);
// 判断路径是否存在，如果不存在，创建该路径
        if(!file.exists()) {
            file.mkdirs();
        }
// 创建磁盘文件项工厂
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload fileUpload = new ServletFileUpload(factory);
// 解析request对象
        List<FileItem> list = fileUpload.parseRequest(request);
// 遍历
        for (FileItem fileItem : list) {
// 判断文件项是普通字段，还是上传的文件
            if(fileItem.isFormField()) {
            }else {
// 上传文件项
               *//* 2. SpringMVC传统方式文件上传
                1. SpringMVC框架提供了MultipartFile对象，该对象表示上传的文件，要求变量名称必须和表单file标签的
                name属性名称相同。
                2. 代码如下
                3. 配置文件解析器对象*//*
// 获取到上传文件的名称
                String filename = fileItem.getName();
// 上传文件
                fileItem.write(new File(file, filename));
// 删除临时文件
                fileItem.delete();
            }
        }
        return "hellow";
    }*/


/*    *//**
     * SpringMVC跨服务器方式的文件上传
     *
     * @param
     * @return
     * @throws Exception
     *//*
    @RequestMapping(fileupload3")

*//*1.异常处理思路
1.Controller调用service，service调用dao，异常都是向上抛出的，最终有DispatcherServlet找异常处理器进行异常的处理。*//*
    public String fileupload3(MultipartFile upload) throws Exception {
        System.out.println("SpringMVC跨服务器方式的文件上传...");
// 定义图片服务器的请求路径
        String path = "http://localhost:9090/day02_springmvc5_02image/uploads/";
// 获取到上传文件的名称
        String filename = upload.getOriginalFilename();
        String uuid = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
// 把文件的名称唯一化
        filename = uuid+"_"+filename;
// 向图片服务器上传文件
// 创建客户端对象
        Client client = Client.creat();
// 连接图片服务器
        WebResource webResource = client.resource(path+filename);
// 上传文件
        webResource.put(upload.getBytes());
        return "hellow";
    }*/




}
