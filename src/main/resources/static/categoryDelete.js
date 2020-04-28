$(function(){
	
	$("#parentCategory").on("change",function(){
		change();
	});
	
	$("#childCategory").on("change",function(){
		change();
	});
	
	$("#grandChildCategory").on("change",function(){
		change();
	});
	
	function change(){
		var parentCategory =escape($("#parentCategory").val());
		var childSelectedName =escape($("#childCategory").val());
		var grandChildSelectedName =$("#grandChildCategory").val();
		
		location.href = '/toDeleteCategory?parentSelectedName='  + parentCategory+ '&childSelectedName=' + childSelectedName + '&grandChildSelectedName=' + grandChildSelectedName ;
	};
	
});