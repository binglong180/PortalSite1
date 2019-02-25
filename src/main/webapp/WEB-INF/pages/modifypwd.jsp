<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta charset="utf-8">
    <title>修改密码</title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico"/>
    <link rel="bookmark" href="${pageContext.request.contextPath}/favicon.ico"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/layui/css/layui.css" media="all">
    <script type="application/javascript" src="${pageContext.request.contextPath}/webjars/layui/layui.js"
            charset="utf-8"></script>
    <script type="application/javascript" src="${pageContext.request.contextPath}/webjars/jquery/jquery.js"></script>
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
<fieldset class="layui-elem-field">
    <legend>修改密码</legend>
    <div class="layui-form-item">
        <label class="layui-form-label">旧密码：</label>
        <div class="layui-input-inline">
            <input type="password" id="password_old" required lay-verify="required" placeholder="请输入旧密码" autocomplete="off"
                   class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">新密码：</label>
        <div class="layui-input-inline">
            <input type="password" id="password_new" required lay-verify="required" placeholder="请输入新密码" autocomplete="off"
                   class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button type="button" class="layui-btn" onclick="fn_modifypwd()">更改密码</button>
        </div>
    </div>
</fieldset>
</body>
<script type="application/javascript">
    layui.use('element', function () {
        var element = layui.element;
    });

    function fn_modifypwd() {
        var pwd_old = $('#password_old').val();
        var pwd_new = $('#password_new').val();
        if(pwd_old=='') {
            alert("请录入旧密码");
            return;
        }

        if(pwd_new=='') {
            alert("请录入新密码");
            return;
        }

        $.ajax({
            url:'${pageContext.request.contextPath}/eyas/admin/modifypwd',
            async:true,
            data:{pwdold:pwd_old,pwdnew:pwd_new},
            dataType:'json',
            type:'PUT',
            success:function(xhrdata) {
                if(!xhrdata.success) {
                    alert(xhrdata.errormsg);
                    return;
                }
                alert("密码修改成功，请重新登录");
                setTimeout(function(){
                    window.parent.location.href = '${pageContext.request.contextPath}/eyas/admin/logout';
                    <%--window.location.href = '${pageContext.request.contextPath}/eyas/admin/index';--%>
                },500);
            },
            error:function(errodata) {
                alert(errodata.responseText.message);
            }
        });
    }
</script>
</html>
