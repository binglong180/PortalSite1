<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <title>后台管理员登录页面</title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico"/>
    <link rel="bookmark" href="${pageContext.request.contextPath}/favicon.ico"/>
    <script type="application/javascript" src="${pageContext.request.contextPath}/webjars/jquery/jquery.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/private/css/admin/login.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/private/css/loading/load.css" media="all">
    <script type="application/javascript">
        $(function () {
            $(document).ajaxStart(function(){
                $('#test11').css('display','block');
            });

            $(document).ajaxStop(function(){
                $('#test11').css('display','none');
            });
        });
    </script>
</head>
<body>
<div id="test11" class="mask" style="width: 100%;height: 100%;z-index: 999999;background-color: rgba(242,242,242, 0.5);position: absolute;display: none;">
    <div>数据加载中...</div>
</div>
<div id="wrapper" class="login-page">
    <div id="login_form" class="form">
        <form class="login-form">
            <input type="text" placeholder="用户名" id="user_name"/>
            <span style="font-size: 12px;color: red;padding-right: 65%;display: none;" id="usernameerro">用户名不能为空</span>
            <input type="password" placeholder="密码" id="password"/>
            <span style="font-size: 12px;color: red;padding-right: 65%;display: none;" id="pwderro">密码不能为空</span>
            <button id="login">登　录</button>
        </form>
    </div>
</div>
</body>
<script type="application/javascript">
    function check_login() {
        $("#usernameerro").css("display","none");
        $("#pwderro").css("display","none");
        var name = $("#user_name").val();
        var pass = $("#password").val();
        if(name=='') {
            shakeform();
            $("#usernameerro").css("display","block");
            return;
        }
        if(pass=='') {
            shakeform();
            $("#pwderro").css("display","block");
            return;
        }
        $.ajax({
            url:'${pageContext.request.contextPath}/eyas/admin/login',
            async:true,
            data:{username:name,password:pass},
            dataType:'json',
            type:'POST',
            success:function(xhrdata) {
                if(!xhrdata.success) {
                    alert("用户名或密码错误");
                    return;
                }
                alert("登录成功");
                setTimeout(function(){
                    window.location.href = '${pageContext.request.contextPath}/eyas/admin/index';
                },500);
            },
            error:function(errodata) {
                alert(errodata.responseText.message);
                $("#user_name").val('');
                $("#password").val('');
            }
        });
    }

    function shakeform() {
        $("#login_form").removeClass('shake_effect');
        setTimeout(function () {
            $("#login_form").addClass('shake_effect')
        }, 1);
    }

    $(function () {
        $("#login").click(function () {
            check_login();
            return false;
        });
    })

</script>
</html>
