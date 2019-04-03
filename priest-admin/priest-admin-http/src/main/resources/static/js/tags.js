function isInt(value) {
    var x;
    return isNaN(value) ? !1 : (x = parseFloat(value), (0 | x) === x);
}

$(function() {
    $('.tag-new,.tag-update').click(function() {
        var id = $(this).parent().siblings(":first").text();

        if (!!id && isInt(id)) {
            window.location.href = "/tag/tag?tid="+id;
        }else{
            window.location.href = "/tag/tag";
        }
    });

    //
    $('.tag-delete').click(function() {
        var id = $(this).parent().siblings(":first").text();

        var url = '/tag/delete';
        var object = {
            "tid":id
        };

        $.post(url,object,function(jqXHR) {
            switch (jqXHR) {
                case 0 :
                    alert('操作失败');
                    break;
                case 1 :
                    alert('操作成功');
                    window.location.href = "/tag/tags";
                    break;
            }
        });
    });
});