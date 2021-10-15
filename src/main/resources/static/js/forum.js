function init(loginUserInfo) {
    let username, uid;
    let avatar = "../images/headimg.jpg";
    console.log(loginUserInfo);

    //用户登录信息
    if (loginUserInfo !== undefined && loginUserInfo !== null) {
        username = loginUserInfo.username;
        uid = loginUserInfo.uid;

        //获取用户头像
        $.ajax({
            type: "get",
            url: "/getavatar",
            data: {uid: uid},
            async: false,
            success: function (res) {
                if (res.status === 200) {
                    avatar = res.data;
                } else {
                    avatar = "../images/headimg.jpg";
                }
                $("#useravatar").attr("src", avatar);
            },
            error: function () {
                avatar = "../images/headimg.jpg";
                $("#useravatar").attr("src", avatar);
            }
        });

        //用户名和用户头像初始化
        $("#username").attr("value", username);
    }

    if (($.trim(username).length > 0)) {
        //将登录注册按钮隐藏
        $('.layui-login').attr('class', "layui-hide");
        $('.layui-register').attr('class', "layui-hide");
        //添加一段div
        let publishHtml = "<li id='title-publish' class=\"layui-nav-item\">\n" +
            "    <a href=\"/publish\" style='font-size: 17px'><i style='font-size: 17px' class=\"layui-icon layui-icon-release\"></i>&nbsp;&nbsp;发帖</a>\n" +
            "  </li>\n" +
            "  <li id='title-notice' class=\"layui-nav-item\">\n" +
            "    <a href=\"/notice\" style='font-size: 17px'><i style='font-size: 17px' class=\"layui-icon layui-icon-notice\"></i>&nbsp;&nbsp;消息通知</a>\n" +
            "  </li>";
        let insertHtml = "<li id='title-user' class=\"layui-nav-item\">\n" +
            "        <a href=\"javascript:;\">\n" +
            "          <img src='" + avatar + "' class=\"layui-nav-img user-small-avatar\">\n" + username +
            "        </a>\n" +
            "        <dl class=\"layui-nav-child\">\n" +
            "          <dd><a href=\"/user/" + uid + "\"><i class=\"layui-icon layui-icon-home\" style=\"color: #38941a;\"></i>&nbsp;&nbsp;我的主页</a></dd>\n" +
            "          <dd><a href=\"/settings\"><i class=\"layui-icon layui-icon-username\" style=\"color: #38941a;\"></i>&nbsp;&nbsp;基本资料</a></dd>\n" +
            "          <dd><a href=\"/safe\"><i class=\"layui-icon layui-icon-password\" style=\"color: #38941a;\"></i>&nbsp;&nbsp;安全设置</a></dd>\n" +
            "          <dd><a href=\"/logout?username=" + username + "\" onClick=\"return confirm('确定退出登录?');\"><i class=\"layui-icon layui-icon-vercode\" style=\"color: #38941a\"></i>&nbsp;&nbsp;退出</a></dd>\n" +
            "        </dl>\n" +
            "      </li>";
        $("#userinfo").html(publishHtml + insertHtml);
    }
}

//登录显示关注文章页面
function followPageInit(loginUserInfo) {
    if (loginUserInfo !== undefined && loginUserInfo !== null) {
        $.get("/getFollowArticleCount", function (res) {
            if (res.status === 200) {
                followArticleCount = res.data;
            }
        });
        $('.follow-item').css('display','block');
    }
}
