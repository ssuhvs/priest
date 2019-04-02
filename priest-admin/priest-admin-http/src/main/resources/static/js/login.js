;$(function() {
    $("form").submit(function() {
        var url = "/h5/login";
        var username = $('#inputUsername').val();
        if (!username) {
            alert('用户名为空');
            return false;
        }

        var password = $('#inputPassword').val();
        if (!password) {
            alert('密码为空');
            return false;
        }

        var loginObject = {
            username : username,
            password : password
        };
        $.post(url,loginObject,function(jqXHR) {
            if (!!jqXHR) {
                if (jqXHR) {
                    window.location.href = "/admin/index";
                }else {
                    switch (jqXHR.code) {
                        case 103 :
                            // duplicate login
                            window.location.href = "/admin/index";
                            break;
                        default :
                            //
                            window.location.href = "/admin/login";
                    }
                }
            }
        });
    });
});