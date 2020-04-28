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
		var searchName = $("#name").val();
		var brand = $("#brand").val();
		var offset = $("#offset").text();
		var parentCategory =escape($("#parentCategory").val());
		var childSelectedName =escape($("#childCategory").val());
		var grandChildSelectedName =escape($("#grandChildCategory").val());
		location.href = '/showItemList?searchName=' + searchName + '&offset=' + offset + '&brand=' + brand + '&parentSelectedName=' + parentCategory+ '&childSelectedName=' + childSelectedName + '&grandChildSelectedName=' + grandChildSelectedName;
	};
	
	$("#btn").on("click",function(){
		var page = $("#page").val();
		var searchName = $("#name").val();
		var brand = $("#brand").val();
		var offset = $("#offset").text();
		var parentCategory =escape($("#parentCategory").val());
		var childSelectedName =escape($("#childCategory").val());
		var grandChildSelectedName =escape($("#grandChildCategory").val());
		location.href = '/showItemList?searchName=' + searchName + '&offset=' + (offset+30) + '&brand=' + brand + '&parentSelectedName=' + parentCategory+ '&childSelectedName=' + childSelectedName + '&grandChildSelectedName=' + grandChildSelectedName + '&page='+ (page+1);
	});
	
});