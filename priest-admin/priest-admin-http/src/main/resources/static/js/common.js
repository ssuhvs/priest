/**
 * Created by  on 15/9/9.
 */
function checkClass(id){

    $("li").removeClass("active");
    $("#"+id).parent().addClass("active");
}
