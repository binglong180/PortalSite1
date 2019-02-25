<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta charset="utf-8">
    <title>技术应用</title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico"/>
    <link rel="bookmark" href="${pageContext.request.contextPath}/favicon.ico"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/layui/css/layui.css" media="all">
    <script type="application/javascript" src="${pageContext.request.contextPath}/webjars/layui/layui.js"
            charset="utf-8"></script>
    <script type="application/javascript" src="${pageContext.request.contextPath}/webjars/jquery/jquery.js"></script>
</head>
<body>
<fieldset class="layui-elem-field">
    <legend>查询条件</legend>
    <form id="form1" class="layui-form" action="">
        <table width="96%" align="center">
            <colgroup>
                <col width="10%" align="center"/>
                <col width="23%" align="left"/>
                <col width="10%" align="center"/>
                <col width="23%" align="left"/>
                <col width="10%" align="center"/>
                <col width="23%" align="left"/>
            </colgroup>
            <tr>
                <td nowrap="nowrap">技术标题:</td>
                <td nowrap="nowrap">
                    <input id="title" name="title" class="layui-input" type="text" placeholder="请输入标题"
                           autocomplete="off"
                           lay-verify="title">
                </td>
                <td nowrap="nowrap">编辑时间起:</td>
                <td nowrap="nowrap">
                    <input id="starttime" name="starttime" class="layui-input" type="text" placeholder="yyyy-MM-dd"
                           autocomplete="off"
                           lay-verify="starttime">
                </td>
                <td nowrap="nowrap">编辑时间止:</td>
                <td nowrap="nowrap">
                    <input id="endtime" name="endtime" class="layui-input" type="text" placeholder="yyyy-MM-dd"
                           autocomplete="off"
                           lay-verify="endtime">
                </td>
            </tr>
            <tr>
                <td nowrap="nowrap">显示状态</td>
                <td>
                    <input id="validflag" name="validflag" type="checkbox" checked="" lay-skin="switch" value="true"
                           lay-text="展示中|隐藏">
                </td>
            </tr>
            <tr>
                <td colspan="3" align="right">
                    <button class="layui-btn" lay-submit lay-filter="query">查询</button>
                </td>
                <td colspan="3" align="center">
                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </td>
            </tr>
        </table>
    </form>
</fieldset>

<fieldset class="layui-elem-field">
    <legend>查询结果</legend>
    <div class="layui-inline">
        <button class="layui-btn" onclick="fn_openaddTechnology()">新增技术应用</button>
    </div>
    <table class="layui-table" lay-filter="gridfilter" lay-data="{url:'',page:true, id:'gridresult',height:'330px'}">
        <thead>
        <tr>
            <th lay-data="{type:'checkbox', fixed: 'left',align:'center'}"></th>
            <th lay-data="{field:'technologyid', width:'5%',hide:true}">技术应用id</th>
            <th lay-data="{field:'title', width:'40%',align:'center'}">技术应用标题</th>
            <th lay-data="{field:'dmltime', width:'20%', sort: true,align:'center'}">最后一次编辑时间</th>
            <th lay-data="{field:'validflag', width:'15%',templet: '#switchTpl',align:'center'}">显示状态</th>
            <th lay-data="{fixed: 'right',align:'center', toolbar: '#barDemo'}">操作</th>
        </tr>
        </thead>
    </table>

</fieldset>
</body>
<script id="switchTpl" type="text/html">
    <input type="checkbox" name="validflag" value="true" lay-skin="switch" lay-text="展示中|隐藏" disabled {{
           d.validflag?'checked' : '' }}>
</script>
<script id="barDemo" type="text/html">
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">{{ d.validflag?'隐藏' : '显示' }}</a>
</script>
<script type="application/javascript">
    layui.use(['form', 'laydate', 'table'], function () {
        var form = layui.form;
        //表单验证规则自定义
        form.verify({
            starttime: function (value) {
            }
            , endtime: function (value) {
            }
        });

        //switch off状态赋值
        form.on('switch', function (data) {
            $(data.elem).attr('type', 'hidden').val(this.checked ? true : false);
        });

        //日期控件
        var laydate = layui.laydate;
        //执行一个laydate实例
        laydate.render({
            elem: '#starttime' //指定元素
        });
        laydate.render({
            elem: '#endtime' //指定元素
        });

        //数据表格模块
        var table = layui.table;
        //监听提交
        form.on('submit(query)', function (data) {
            table.reload('gridresult', {
                url: '${pageContext.request.contextPath}/eyas/admin/queryTechnology'
                , where: data.field //设定异步数据接口的额外参数
                , page: {
                    curr: 1
                }
            });
            return false;
        });
        //监听工具条
        table.on('tool(gridfilter)', function (obj) {
            var data = obj.data;
            if (obj.event === 'del') {
                $.ajax({
                    type: "DELETE",
                    url: "${pageContext.request.contextPath}/eyas/admin/deleteTechnology/" + data.technologyid, //图片上传出来的url，返回的是图片上传后的路径，http格式
                    dataType: "json",
                    success: function (data) {
                        layer.msg("保存成功");
                        obj.del();
                    },
                    error: function () {
                        alert("保存失败");
                    }
                });
            } else if (obj.event === 'edit') {
//                layer.alert('编辑行：<br>'+ JSON.stringify(data));
                fn_opendUpdateTechnology(data.technologyid);
            }
        });


    });

    function fn_opendUpdateTechnology(technologyid) {
        if (!technologyid) {
            layer.alert("失败,请重新打开这个菜单");
            return;
        }
        var url = '${pageContext.request.contextPath}/eyas/admin/editTechnology/' + technologyid;                             //转向网页的地址;
        var name = 'updatetechnology';                   //网页名称，可为空;
        var iWidth = 720;                          //弹出窗口的宽度;
        var iHeight = 600;                         //弹出窗口的高度;
        //获得窗口的垂直位置
        var iTop = (window.screen.availHeight - 30 - iHeight) / 2;
        //获得窗口的水平位置
        var iLeft = (window.screen.availWidth - 10 - iWidth) / 2;
        window.open(url, name, 'height=' + iHeight + ',,innerHeight=' + iHeight + ',width=' + iWidth + ',innerWidth=' + iWidth + ',top=' + iTop + ',left=' + iLeft + ',menubar=0,scrollbars=1, resizable=1,status=1,titlebar=0,toolbar=0,location=1');
    }

    function fn_openaddTechnology() {
        var url = '${pageContext.request.contextPath}/eyas/admin/pagegoto/addtechnology';                             //转向网页的地址;
        var name = 'addtechnology';                   //网页名称，可为空;
        var iWidth = 720;                          //弹出窗口的宽度;
        var iHeight = 600;                         //弹出窗口的高度;
        //获得窗口的垂直位置
        var iTop = (window.screen.availHeight - 30 - iHeight) / 2;
        //获得窗口的水平位置
        var iLeft = (window.screen.availWidth - 10 - iWidth) / 2;
        window.open(url, name, 'height=' + iHeight + ',,innerHeight=' + iHeight + ',width=' + iWidth + ',innerWidth=' + iWidth + ',top=' + iTop + ',left=' + iLeft + ',menubar=0,scrollbars=1, resizable=1,status=1,titlebar=0,toolbar=0,location=1');
    }

</script>
</html>
