$(function(){
	$("#parentCategory").on("change",function(){
	var parentSelectedName =escape($("#parentCategory").val());
	location.href = '/toAddItem?parentSelectedName=' + parentSelectedName+ '&name=' + $("#inputName").val()+ '&price=' + $("#price").val() + '&brand=' + $("#brand").val()+ '&condition=' + $(".condition:checked").val()+ '&description=' + $("#description").val();
	});
	
	$("#childCategory").on("change",function(){
		var parentSelectedName =escape($("#parentCategory").val());
		var childSelectedName =escape($("#childCategory").val());
		location.href = '/toAddItem?parentSelectedName=' + parentSelectedName + '&childSelectedName=' + childSelectedName +  '&name=' + $("#inputName").val()+ '&price=' + $("#price").val() + '&brand=' + $("#brand").val()+ '&condition=' + $(".condition:checked").val()+ '&description=' + $("#description").val();
	});
	

	
});