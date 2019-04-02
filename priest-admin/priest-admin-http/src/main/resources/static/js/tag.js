;$(function() {
    $("form").submit(function() {
        /*
         <input type="hidden" name="tid" value="${tid}">
         <input type="name" name="name" value="${name}">
         */
        // tid
        var tid = $(this).find('input:hidden').val();
        var type = $(this).find('select[name=type]').val();
        var name = $(this).find('input[name=name]').val();

        var data = {
            "tid" : tid,
            "type" : type,
            "name" : name
        };

        var update = !!tid && tid > 0;

        var url = update ? '/tag/update' : '/tag/tag';
        $.post(url,data,function(jqXHR) {
            if (!!jqXHR) {
                if (jqXHR > 0) {
                    alert('操作成功');
                    window.location.href = "/tag/tags";
                }else {
                    alert('操作失败');
                }
            }
        });

        return false;
    });
});