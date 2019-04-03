;$(function() {
    $('.block,.recovery').click(function() {
        var block = $(this).hasClass('block');
        var id = $(this).parent().siblings(":first").text();

        var url = '/h5/user/status';
        var status = {
            "id":id,
            "status" :block ? 2 : 1};
        $.post(url,status,function(jqXHR) {
            switch (jqXHR) {
                case 0 :
                    alert('操作失败');
                    break;
                case 1 :
                    alert('操作成功');
                    window.location.href = "/admin/user/list";
                    break;
            }
        });
    });

    $('.resetPassword').click(function() {
        var id = $(this).parent().siblings(":first").text();
        window.location.href = "/h5/user/resetPassword?id="+id;
    });

    $("#user-psw").submit(function() {
        var id = $(this).find('input:hidden').val();
        var newPassword = $('#newPassword').val();

        var data = {
            "id" : id,
            "newPassword" : newPassword
        };

        var url = '/h5/user/resetPassword';
        $.post(url,data,function(jqXHR) {
            if (!!jqXHR) {
                if (jqXHR > 0) {
                    alert('操作成功');
                    window.location.href = "/admin/user/list";
                }else {
                    alert('操作失败');
                }
            }
        });

        return false;
    });

    $('#user-create').submit(function() {
        var mobile = $('#mobile').val();
        var username = $('#username').val();
        var password = $('#password').val();

        var data = {
            "mobile" : mobile,
            "username" : username,
            "password" : password
        };

        var url = '/h5/user/create';
        $.post(url,data,function(jqXHR) {
            if (!!jqXHR) {
                if (jqXHR > 0) {
                    alert('操作成功');
                    window.location.href = "/admin/user/list";
                }else {
                    alert('操作失败');
                }
            }
        });

        return false;
    });
})
;$('#pagination-demo').twbsPagination({
    totalPages: total,
    visiblePages: 8,
    href: '/h5/user/list?page={{number}}'
});