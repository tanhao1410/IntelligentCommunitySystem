function login() {

    var param = {
        userName:$("#loginUserNameInput").val(),
        password:$("#loginPasswordInput").val(),
    };

    $.ajax({
        type : "Post",
        dataType : "json",
        url : "/login",
        data : JSON.stringify(param),
        contentType:"application/json;charset=utf-8",
        success : function(data) {
            if(data){
                window.location.href="/index.html";
            }else{
                alert("密码错误！")
            }

        },
        error : function(data) {
            console.info(data);
        }
    });
}