<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta charset="utf-8">
    <title>Title</title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/favicon.ico"/>
    <link rel="bookmark" href="${pageContext.request.contextPath}/favicon.ico"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/layui/css/layui.css" media="all">
    <script type="application/javascript" src="${pageContext.request.contextPath}/webjars/layui/layui.js"
            charset="utf-8"></script>
    <script type="application/javascript" src="${pageContext.request.contextPath}/webjars/jquery/jquery.js"></script>
    <!-- include libraries(jQuery, bootstrap) -->
    <link href="${pageContext.request.contextPath}/webjars/bootstrap/css/bootstrap.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/webjars/bootstrap/js/bootstrap.js"></script>

    <!-- include summernote css/js -->
    <link href="${pageContext.request.contextPath}/webjars/summernote/summernote.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/webjars/summernote/summernote.js"></script>
    <script src="${pageContext.request.contextPath}/webjars/summernote/lang/summernote-zh-CN.js"></script>
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
<form id="form1" class="layui-form" action="">
    <fieldset class="layui-elem-field">
        <legend>标题内容部分</legend>
        <table width="96%" align="center">
            <colgroup>
                <col width="10%" align="center"/>
                <col width="50%" align="left"/>
            </colgroup>
            <tr>
                <td nowrap="nowrap">技术应用标题:</td>
                <td nowrap="nowrap">
                    <input type="hidden" id="technologyid" name="technologyid" value="${technology.technologyid}">
                    <input type="hidden" id="detailimgs" name="detailimgs">
                    <input id="title" name="title" class="layui-input" type="text" placeholder="请输入标题" autocomplete="off"
                           lay-verify="title" value="${technology.title}">
                </td>
            </tr>
            <tr>
                <td nowrap="nowrap">标题概述:</td>
                <td nowrap="nowrap">
                    <textarea name="comments" placeholder="请输入内容" class="layui-textarea" lay-verify="comments">${technology.comments}</textarea>
                </td>
            </tr>
            <tr>
                <td nowrap="nowrap">标题配图:</td>
                <td nowrap="nowrap">
                    <button class="layui-btn" id="uploadimg" type="button">上传图片(jpg|png)</button>
                    <input type="hidden" id="imgurl" name="imgurl" lay-verify="imgurl" value="${technology.imgurl}"/>
                </td>
            </tr>
            <tr>
                <td nowrap="nowrap">已上传配图展示:</td>
                <td nowrap="nowrap">
                    <img id="picid" src="${pageContext.request.contextPath}${technology.imgurl}" width="370" height="260" />
                </td>

            </tr>
        </table>
    </fieldset>
    <fieldset class="layui-elem-field">
        <legend>详细内容部分</legend>
        <table width="96%" align="center">
            <colgroup>
                <col width="60%" align="center"/>
            </colgroup>
            <tr>
                <td>
                    <textarea id="summernote" name="servicedetails" lay-verify="details">${technology.details}</textarea>
                </td>
            </tr>
            <tr>
                <td nowrap="nowrap">
                    <button id="formsubmit" class="layui-btn" lay-submit lay-filter="saveservice">保存</button>
                </td>
            </tr>
        </table>
    </fieldset>
</form>

</body>
<script type="application/javascript">
    layui.use(['form','upload'], function(){
        var form = layui.form;
        var upload = layui.upload;

        //表单验证规则自定义
        form.verify({
            title: function(value){
                if(!value) {
                    return '技术应用标题不能为空';
                }
                if(value.length>100) {
                    return '技术应用标题长度不能超过100[一个汉字算2个长度]';
                }
            },
            details: function(value) {
                if(!value) {
                    return '详细内容部分不能为空';
                }
            },
            imgurl: function(value) {
                /*if(!value) {
                 return '请上传标题配图';
                 }*/
            }
        });

        //监听提交
        form.on('submit(saveservice)', function(data){
            $.ajax({
                data : data.field,
                type : "POST",
                url : "${pageContext.request.contextPath}/eyas/admin/addTechnology", //图片上传出来的url，返回的是图片上传后的路径，http格式
                dataType : "json",
                success: function(data) {
                    if(data.code!=0) {
                        alert("保存失败");
                        return;
                    }
                    alert("保存成功");
                    window.close();
                },
                error:function(){
                    alert("保存失败");
                }
            });
            return false;
        });


        //图片上传
        upload.render({
            elem: '#uploadimg'
            ,url: '${pageContext.request.contextPath}/eyas/admin/uploadpic'
            ,exts: 'jpg|png' //只允许上传图片
            //,data: {serviceid:$('#serviceid').val()}
            ,size: 500 //限制文件大小，单位 KB
            ,before: function(obj){
                //预读本地文件示例，不支持ie8
                obj.preview(function(index, file, result){
                    $('#picid').attr('src', result); //图片链接（base64）
                });
            }
            ,done: function(res){
                //如果上传失败
                if(res.code > 0){
                    $('#picid').attr('src', ''); //图片链接（base64）
                    return layer.msg('上传失败');
                }
                //上传成功
                $('#imgurl').val(res.url);
            }
            ,error: function(){
                //演示失败状态，并实现重传
                $('#picid').attr('src', ''); //图片链接（base64）
                return layer.msg('上传失败');
            }
        });
    });

    $(document).ready(function() {
        $('#summernote').summernote({
            placeholder: '请编辑技术应用的详细内容...',
            lang: 'zh-CN',
            height:300,
            //调用图片上传
            callbacks: {
                onImageUpload: function (files) {
                    sendFile(files[0]);
                }
            }
        });

        function sendFile(file) {
            var formdata = new FormData();
            formdata.append("file", file);
            $.ajax({
                data : formdata,
                type : "POST",
                url : "${pageContext.request.contextPath}/eyas/admin/uploadpic", //图片上传出来的url，返回的是图片上传后的路径，http格式
                cache : false,
                contentType : false,
                processData : false,
                dataType : "json",
                success: function(data) {
                    if(data.code!=0) {
                        alert("上传失败");
                        return;
                    }
                    //data是返回的hash,key之类的值，key是定义的文件名
                    $('#summernote').summernote('insertImage', '${pageContext.request.contextPath}'+data.url,data.fileName);
                    $('#detailimgs').val($('#detailimgs').val()+','+data.fileName);
                },
                error:function(){
                    alert("上传失败");
                }
            });

        }
    });
</script>
</html>

