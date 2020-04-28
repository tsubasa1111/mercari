$(function(){
	$("input[name=parentCategory]").on("change",function(){
		change();
	});
	
	$("input[name=childCategory]").on("change",function(){
		change();
	});
	
	$("input[name=grandChildCategory]").on("change",function(){
		change();
	});
	
	function change(){
		var parentCategory =escape($("input[name=parentCategory]").val());
		var childSelectedName =escape($("input[name=childCategory]").val());
		var grandChildSelectedName =escape($("input[name=grandChildCategory]").val());
		location.href = '/toAddCategory?parentSelectedName='  + parentCategory+ '&childSelectedName=' + childSelectedName + '&grandChildSelectedName=' + grandChildSelectedName;
	};

});