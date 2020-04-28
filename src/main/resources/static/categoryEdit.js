$(function(){

	if($("#parentCategory").val()=='-Category1-'){
		$("#changedParentCategory").hide();
	}
	
	if($("#childCategory").val()=='-Category2-'){
		$("#changedChildCategory").hide();
	}
	if($("#grandChildCategory").val()=='- Category3 -'){
		$("#changedGrandChildCategory").hide();
	}
	
	
	
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
		var parentCategory =escape($("#parentCategory"));
		var childSelectedName =escape($("#childCategory"));
		var grandChildSelectedName =$("#grandChildCategory").val();
		var newGrandChildCategory=$("#newGrandChildCategory").val();
		var newChildCategory=$("#newChildCategory").val();
		var newParentCategory=$("#newParentCategory").val();
		if(parentCategory!='-Category1-'){
			$("#changedParentCategory").show();
		}
		
		if(childSelectedName!='-Category2-'){
			$("#changedChildCategory").show();
		}
		
		if(grandChildSelectedName!='- Category3 -'){
			$("#changedGrandChildCategory").show();
		}
		
		location.href = '/toEditCategory?parentSelectedName='  + parentCategory+ '&childSelectedName=' + childSelectedName + '&grandChildSelectedName=' + grandChildSelectedName+ '&newParentCategory=' + newParentCategory+ '&newGrandChildCategory=' + newGrandChildCategory+ '&newChildCategory=' + newChildCategory;
	};
	
});