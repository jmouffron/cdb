$(function() {
	$("#computerName").on("keyup focus", function(e){
		if( !$(this).val().match(/\d{0,255}/) || $(this).val() == ''){
			invalidate()
			$(this).toggleClass('.is-invalid').toggleClass('.is-invalid-feedback')
		$("form input[submit]").first().toggleClass('disabled')
		}else{
			validate()
		}
	})
	
	$("#introduced").on("keyup focus", function(e){
		if( $(e.target).val() >= $('#discontinued').val() || $(this).val() == ''){
			invalidate()
		}else{
			validate()
		}
		if(!$("#introduced").val() instanceof Date){
			invalidate()
		}else{
			validate()
		}
	})
	
	$("#discontinued").on("keyup focus", function(e){
		if( $(e.target).val() <= $('#introduced').val() || $(this).val() == ''){
			invalidate()
		}else{
			validate()
		}
		
		if(!$("#discontinued").val() instanceof Date){
			invalidate()
		}else{
			validate()
		}
	})
	
	$("#companyId").on("keyup focus", function(e) {
		if ( !$("#companyId option").contains( $("#companyId").val() || $(this).val() == '') ){
			invalidate()
		}else{
			validate()
		}
	})
	
	
	function invalidate(){
		$(this).toggleClass('.is-invalid').toggleClass('.is-invalid-feedback')
		$("form input[submit]").first().toggleClass('disabled')
	}
	
	function validate(){
		$(this).toggleClass('.is-valid')
	}
})
