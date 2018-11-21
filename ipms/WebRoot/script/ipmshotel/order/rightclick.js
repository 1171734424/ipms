// 屏蔽浏览器自带的右键
$(document).bind("contextmenu",
    function(){
        return false;
    }
);