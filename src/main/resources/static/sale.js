$(function(){
		$("#year").on("change",function(){
			location.href = '/showSalesGraph?year=' + $("#year").val();
			});
});