<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>首页</title>
    <script src="js/jquery.min.js"></script>   <!--引入外部js资源-->
    <script>
        //页面的资源加载完成后，绑定单击事件
    $(function () {
        $("#btn").click(function () {
            //发送ajax请求
            $.ajax({
                url:"test/ajax",
                contentType:"application/json;charset=UTF-8",//传入的数据类型与编码格式
                data:'{"name":"黄海","sex":"男"}',  //请求数据,格式必须这么写，否则就报错
                dataType:"json",  //响应数据的类型
                type:"post",
                success:function (data) {   //响应成功的回调函数
                    alert(data.name+"  "+data.sex)
                },
                error:function () {  //响应失败的回调函数
                    alert("出错啦！。。。。。")
                },
            })
        })
    });

    //第二种ajax方式（更简单）
/*        $(function () {
            $("#btn").click(function () {
                $.post("test/ajax",{"name":"黄海","sex":"男"},function (data) {
                        alert(data.name);
                },"json")
            })
        })*/
    </script>
</head>
<body>
<h2>Hello World!</h2>
<!--这里所有的路径都不能在前面写“/”，否则报错，因为视图解析器会给你解析到jsp去-->
<a href="test/hello?username=hh&password=123">简单的参数（基本类型）绑定</a>
<br/>
<form action="test/account" method="post">
    姓名：<input type="text" name="username"/> <br/>  <!--所有的数据都会自动匹配封装，要求是必须参数名称一样-->
    密码：<input type="text" name="password"/> <br/>
    资金：<input type="text" name="money"/> <br/>
    他爸爸：<input type="text" name="user.name"/> <br/> <!--springMVC会自动识别对象中有的对象，并将数据自动封装，名称必须一样-->
    性别：<input type="text" name="user.sex"/> <br/>
    <%--list:<input type="text" name="list[0].name"/> <br/>
    map1:<input type="text" name="map.one.name"/> <br/>
    map2:<input type="text" name="map['one'].name"/> <br/>--%>  <!--将数据封装到创建的一个键为one的map集合中去-->
    <input type="submit" value="提交" /> <br/>
</form>
<a href="test/getRequest">获取Servlet原生的API</a><br/>
<a href="test/testRequestParam?name=黄海">测试注解@RequestParam</a><br/>

<form action="test/testRequestBody" method="post">
    姓名：<input type="text" name="username"/> <br/>
    密码：<input type="text" name="password"/> <br/>
    <input type="submit" value="提交" /> <br/>
</form>
<a href="test/testPathVariable/sss傻子">测试注解@PathVariable</a><br/>
<a href="test/requestHeader">测试注解@RquestHeader</a><br/>
<a href="test/cookieValue">测试注解@CookieValue</a><br/>
<a href="test/modelAttribute?uname=人生">测试注解@ModelAttribute</a><br/>
<a href="test/sessionAttribute">测试注解@sessionAttribute</a><br/>
<a href="test/getSessionAttribute">用ModelMap的get方法取出域中的值</a><br/>
<a href="test/deleteSessionAttribute">清除session域中的值</a><br/>
<a href="test/ForwardOrRedirectAndRedirect">测试字符串形式的转发或重定向</a><br/>
<a href="test/ModelAndView">测试ModelAndView类</a><br/>

<center><button id="btn">发送ajax请求</button></center><br>

<a href="test/myExceptionResolver">测试异常处理器</a><br/><br/><br/>

<form action="test/fileUpload" method="post" enctype="multipart/form-data">
    选择要上传的文件：<input type="file" name="upload"/>
                <input type="submit" value="上传"/>

</form>

</body>
</html>
