<!doctype html>
<html>
<head>
<meta charset="utf-8">
<link rel="stylesheet" href="/login/css/base.css" />
<link rel="stylesheet" href="/login/css/info-reg.css" />
<title>基础结构平台</title>
</head>

<body>
<div class="title"><h2>新增投票</h2></div>
<div class="main">
	<p class="long-input ue-clear">
    	<label>名称：</label>
        <input type="text" placeholder="名称" />
    </p>
    <div class="short-input select ue-clear">
    	<label>类型：</label>
        <div class="select-wrap">
        	<div class="select-title ue-clear"><span>单选</span><i class="icon"></i></div>
            <ul class="select-list">
            	<li>单选</li>
                <li>双选</li>
                <li>三选</li>
                <li>四选</li>
                <li>五选</li>
                <li>六选</li>
                <li>七选</li>
                <li>八选</li>
                <li>多选</li>
            </ul>
        </div>
    </div>
    <div class="short-input select ue-clear">
    	<label>是否登录：</label>
        <div class="select-wrap">
        	<div class="select-title ue-clear"><span>否</span><i class="icon"></i></div>
            <ul class="select-list">
            	<li>否</li>
                <li>是</li>
            </ul>
        </div>
    </div>
    <p class="short-input ue-clear">
    	<label>每人限投：</label>
        <input type="text" placeholder="每人限投" />
        <span>次数</span>
    </p>
    <p class="short-input ue-clear">
    	<label>投票频率：</label>
        <input type="text" placeholder="投票频率"/>
        <span>时间以分钟为单位，例如每天一次 60*24</span>
    </p>
    <p class="short-input ue-clear">
    	<label>投票时间：</label>
        <input type="text" placeholder="开始时间" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width: 104px; margin-right: 10px;"/>
        <input type="text" placeholder="结束时间" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width: 104px;"/>
        <span><em>*</em>请输入正确的时间格式</span>
    </p>
    <div class="long-input select ue-clear" style="">
    	<label>选项：</label>
    	<div style="padding-left: 82px;">
    	<p name="chooseItem" class="short-input ue-clear" style="padding-left:0px">
	        <input type="text" placeholder="序号" style="width:25px;text-align:center;margin-right:10px"/><input type="text" placeholder="选项"style="width:414px;margin-right:10px" />
        	<img onclick="addItem(this)" src="/login/images/add.png" style="width: 20px; height: 20px; vertical-align: text-top;"/>
        	<img onclick="delItem(this)" src="/login/images/close4.png" style="width: 20px; height: 20px; vertical-align: text-top;"/>
	    </p>
	    <p name="chooseItem" class="short-input ue-clear" style="padding-left:0px">
	        <input type="text" placeholder="序号" style="width:25px;text-align:center;margin-right:10px"/><input type="text" placeholder="选项"style="width:414px;margin-right:10px" />
        	<img onclick="addItem(this)" src="/login/images/add.png" style="width: 20px; height: 20px; vertical-align: text-top;"/>
        	<img onclick="delItem(this)" src="/login/images/close4.png" style="width: 20px; height: 20px; vertical-align: text-top;"/>
	    </p>
	    </div>
    </div>
    <p class="short-input ue-clear">
    	<label>投票说明：</label>
        <textarea placeholder="请输入内容"></textarea>
    </p>
</div>
<div class="btn ue-clear">
	<a href="javascript:;" class="confirm">确定</a>
    <a href="javascript:;" class="clear">清空内容</a>
</div>
</body>
<script type="text/javascript" src="/login/js/jquery.js"></script>
<script type="text/javascript" src="/login/js/common.js"></script>
<script type="text/javascript" src="/login/js/WdatePicker.js"></script>
<script type="text/javascript">
$(".select-title").on("click",function(){
	$(this).next().toggle();
	return false;
});
$(".select-list").on("click","li",function(){
	var txt = $(this).text();
	$(this).parent().prev().find("span").text(txt);
});
function addItem(that_){
	var that = $(that_).parent();
	that.after(that.prop("outerHTML"));
}
function delItem(that_){
 	if($("p[name='chooseItem']").length>2){
 		$(that_).parent().remove();
 	}else{
 		alert("选项至少保留2个");
 	}
}


showRemind('input[type=text], textarea','placeholder');
</script>
</html>
