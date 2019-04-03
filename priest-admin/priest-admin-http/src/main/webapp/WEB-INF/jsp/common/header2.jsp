<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link rel="stylesheet" href="/layui/css/layui.css"  media="all">
<link rel="stylesheet" href="/css/table.css" />
<link rel="stylesheet" href="/css/global.css" media="all">
<link href="/startbootstrap/css/bootstrap.min.css" rel="stylesheet">
<%--<link rel="stylesheet" href="/css/font-awesome.min.css">--%>

<%--<link rel="shortcut icon" href="/images/favicon.ico"/>--%>
<script src="/layui/layui.js"></script>
<script src="/startbootstrap/js/jquery.js"></script>
<%--<script src="/js/jquery.searchselect.min.js"></script>--%>
<%--<script src="/js/jquery.slimscroll.min.js"></script>--%>
<%--<script src="/js/bootstrap-addtabs.js"></script>--%>
<script src="/js/jquery.twbsPagination.js"></script>
<script src="/startbootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script type="text/javascript">
    $(function(){

        layui.use('laydate', function(){
            var laydate = layui.laydate;

            var start = {
                min: 0
                ,max: '2099-06-16 23:59:59'
                ,istoday: false
                ,choose: function(datas){
                    end.min = datas; //开始日选好后，重置结束日的最小日期
                    end.start = datas //将结束日的初始值设定为开始日
                }
            };

            var end = {
                min: 0
                ,max: '2099-06-16 23:59:59'
                ,istoday: false
                ,choose: function(datas){
                    start.max = datas; //结束日选好后，重置开始日的最大日期
                }
            };
            $("#LAY_demorange_s").click(function(){
                start.elem = this;
                laydate(start);
            });
            $("#LAY_demorange_e").click(function(){
                end.elem = this
                laydate(end);
            });

        });
        layui.use('element', function(){
            var element = layui.element;
        });

        layui.use(['form', 'layedit', 'laydate'], function(){
            var form = layui.form
                ,layer = layui.layer
                ,layedit = layui.layedit
                ,laydate = layui.laydate;

            //创建一个编辑器
            var editIndex = layedit.build('LAY_demo_editor');

            //自定义验证规则
            form.verify({
                title: function(value){
                    if(value.length < 5){
                        return '标题至少得5个字符啊';
                    }
                }
                ,pass: [/(.+){6,12}$/, '密码必须6到12位']
                ,content: function(value){
                    layedit.sync(editIndex);
                }
            });

            form.on('select(provCode)', function(data){
                $('#cityCode').find('option').remove();
                $('#areaCode').html('<option value="">请选择县/区</option>');
                $.ajax({
                    url:'/admin/area/city',
                    type:'post',
                    data:{pId:data.value},
                    dataType:'json',
                    success:function(data){
                        if(data.c == 0){
                            var appendHtml ='';
                            $.each(data.d.areaList, function(i, field){
                                appendHtml+=' <option value="'+field.id+'">'+field.areaName+'</option>';
                            });
                            areaChange("#areaCode", data.d.areaList[0].id);
                            $('#cityCode').append(appendHtml);
                        }

                        form.render('select'); //刷新select选择框渲染
                    },
                    error:function(){

                    }
                });
            });

            form.on('select(cityCode)', function(data){
                areaChange(data.value);
            });


            function areaChange(areaEleId,cityId){
                $(areaEleId).empty();
                $.ajax({
                    url:'/admin/area/city',
                    type:'post',
                    data:{pId:cityId},
                    dataType:'json',
                    success:function(data){
                        if(data.c == 0){
                            var appendHtml ='';
                            $.each(data.d.areaList, function(i, field){
                                appendHtml+=' <option value="'+field.id+'">'+field.areaName+'</option>';
                            });

                            $(areaEleId).append(appendHtml);
                        }

                        form.render('select'); //刷新select选择框渲染
                    },
                    error:function(){

                    }
                });
            }

            form.on('select(provId)', function(data){
                $('#cityId').find('option').remove();
                $('#areaId').html('<option value="">请选择县/区</option>');
                $.ajax({
                    url:'/admin/area/city',
                    type:'post',
                    data:{pId:data.value},
                    dataType:'json',
                    success:function(data){
                        if(data.c == 0){
                            var appendHtml ='';
                            $.each(data.d.areaList, function(i, field){
                                appendHtml+=' <option value="'+field.id+'">'+field.areaName+'</option>';
                            });
                            areaChange("#areaId", data.d.areaList[0].id);
                            $('#cityId').append(appendHtml);
                        }

                        form.render('select'); //刷新select选择框渲染
                    },
                    error:function(){

                    }
                });
            });

            form.on('select(cityId)', function(data){
                areaChange("#areaId", data.value);
            });

        });
    });

    function commonProcessConfirm(url) {
        layer.confirm('处理后不可恢复，确定处理？', {icon: 3, title:'提示'}, function(index){
            //do something
            layer.close(index);
            var myForm = document.getElementById("commonPostForm");
            if(myForm == null){
                //兼容老的删除方式。
                window.location.href=url
            }else{//新当页刷新。
                myForm.action = url;myForm.submit();
            }
        });
    }

    //sys confirm for globel javascript.
    function commonDeleteConfirm(url) {
        layer.confirm('删除后不可恢复，确定删除？', {icon: 3, title:'提示'}, function(index){
            //do something
            layer.close(index);
            var myForm = document.getElementById("commonPostForm");
            if(myForm == null){
                //兼容老的删除方式。
                window.location.href=url
            }else{//新当页刷新。
                myForm.action = url;myForm.submit();
            }
        });
    }
    //商品上下架操作.
    function commonMarktableConfirm(url) {
        layer.confirm('确定该商品上下架吗？', {icon: 3, title:'提示'}, function(index){
            //do something
            layer.close(index);
            var myForm = document.getElementById("commonPostForm");
            if(myForm == null){
                //兼容老的删除方式。
                window.location.href=url
            }else{//新当页刷新。
                myForm.action = url;myForm.submit();
            }
        });
    }

    function commonShangxianStatusConfirm(url) {
        layer.confirm('确认上线吗？', {icon: 4, title:'提示'}, function(index){
            //do something
            layer.close(index);
            var myForm = document.getElementById("commonPostForm");
            if(myForm == null){
                //兼容老的方式。
                window.location.href=url
            }else{//新当页刷新。
                myForm.action = url;myForm.submit();
            }
        });
    }
    // 赛果审核
    function commonUpdateAuditStatusConfirm(url) {
        layer.confirm('确认审核？', {icon: 3, title:'提示'}, function(index){
            //do something
            layer.close(index);
            var myForm = document.getElementById("commonPostForm");
            if(myForm == null){
                //兼容老的删除方式。
                window.location.href=url
            }else{//新当页刷新。
                myForm.action = url;myForm.submit();
            }
        });
    }

    //确认操作
    function msgConfirm(url,msg) {
        layer.confirm(msg, {icon: 3, title:'提示'}, function(index){
            //do something
            layer.close(index);
            var myForm = document.getElementById("commonPostForm");
            if(myForm == null){
                //兼容老的删除方式。
                window.location.href=url
            }else{//新当页刷新。
                myForm.action = url;myForm.submit();
            }
        });
    }

    /**
     * excel导出专用
     * @param url
     * @param msg
     */
    function msgConfirmExcel(url,msg) {
        layer.confirm(msg, {icon: 3, title:'提示'}, function(index){
            //do something
            layer.close(index);
            $.ajax({
                url: url,
                type: "POST",
                processData: false,
                contentType: false,
//            contentType: 'multipart/form-data',
                cache: false,
                data: new FormData($('#excelId')[0]),
//            dataType: "json",
                async: false,
                success: function(data) {
                    layer.closeAll();
                    if(data.c==0){
                        layer.open({
                            content: data.m
                            ,skin: 'msg'
                        });
                    }else{
                        layer.open({
                            content: data.m
                            ,skin: 'msg'
                        });
                    }
                }
            });
        });
    }

    function commonReceiveConfirm(url) {
        layer.confirm('确定已到账？', {icon: 3, title:'提示'}, function(index){
            //do something
            layer.close(index);
            var myForm = document.getElementById("commonPostForm");
            if(myForm == null){
                //兼容老的删除方式。
                window.location.href=url
            }else{//新当页刷新。
                myForm.action = url;myForm.submit();
            }
        });
    }
    function commonSubmit(formId,url) {
        if(!confirm('确定保存')){ return; };
        var myForm = document.getElementById(formId);//按照form进行提交.
        myForm.action = url;myForm.submit();
    }
    function commonConfirm(url,message) {
        if(!confirm(message)){ return; };window.location.href=url;
    }
    function addRecommendConfirm(url,message) {
        if(!confirm(message)){ return; };window.location.href=url;" target='_blank'";
    }
    function commonCheckAll(){
        var obj = document.getElementsByName("checkid");var len = obj.length ;
        for(var i=0;i<len;i++){obj[i].checked = document.getElementById("checkall").checked;}
    }
    //deleteAll
    function commonDeleteAll(url){
        var objCheck = document.getElementsByName("checkid") ;
        var len = objCheck.length ;var canDeleteAll = false;var url = url;
        for( var i = 0; i < len; i ++){if(objCheck[i].checked){
            canDeleteAll = true;url= url +"&ids=" + objCheck[i].value;}}
        if(canDeleteAll){
            if(!confirm('确定删除？')){ return false;}
            window.location.href=url;
        }else{alert('没有选择删除数据。');return null;}
    }
    function formatSeconds(value) {
        var theTime = parseInt(value);// 秒
        var theTime1 = 0;// 分
        var theTime2 = 0;// 小时
        if(theTime > 60) {
            theTime1 = parseInt(theTime/60);
            theTime = parseInt(theTime%60);
            if(theTime1 > 60) {
                theTime2 = parseInt(theTime1/60);
                theTime1 = parseInt(theTime1%60);
            }
        }
        var result = ""+parseInt(theTime)+"秒";
        if(theTime1 > 0) {
            result = ""+parseInt(theTime1)+"分"+result;
        }
        if(theTime2 > 0) {
            result = ""+parseInt(theTime2)+"小时"+result;
        }
        return result;
    }
    //-->
</script>