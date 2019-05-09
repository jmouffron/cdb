$(function() {
	let regexDate = /[0-9]{4}-[0-1]{1}[0-9]{1}-[0-2]{1}[0-9]{1}/
	let regexWord = /[\w-\d]+/
			
	$("form input[type='submit']").first().addClass('disabled')
	$("#introduced").val() == "" ? $("#discontinued").attr('readonly', true): $("#discontinued").attr("readonly", false);
	$("#computerName").attr("required", true);
	
	$("div #computerName").on("focus blur", function(){
		if ( $(this).val() == "" || !$(this).val().match(regexWord) ){
			invalidate($(this))
		} else {
			validate($(this))
		}
	})
	
	$("div #introduced").on("focus blur", function( ){
		let intro = new Date($(this).val())
		let disco = new Date($("#discontinued").val())

		if ( !$(this).val() == '' || intro == "Invalid Date" ){
			invalidate($(this))
			$("#discontinued").attr('readonly', true)
			console.log("invalid date")
		} else if ( !$(this).val() == '' || !$(this).val().match(regexDate) ) {
			invalidate( $(this) )
			console.log("invalid regexp")
		} else if ( !$(this).val() == '' || (  $("#discontinued").val() != "" && intro >= disco ) ){
			invalidate($(this))
			$("#discontinued").attr('readonly', true)
			console.log("invalid value or invalid compared to disco")
		} else {
			validate($(this))
			$("#discontinued").attr('readonly', false)
		}
	})
	
	$("div #discontinued").on("focus blur", function( ){
		let intro = new Date($("#introduced").val())
		let disco = new Date($(this).val())
		
		if ( $(this).val() == '' || intro == "Invalid Date" || disco == "Invalid Date" ){
			invalidate($(this))
		} else if ( $(this).val() == '' || !$(this).val().match(regexDate) ) {
			invalidate( $(this) )
		} else if ( $(this).val() == '' || $(this).val() <= $('#introduced').val() ){
			invalidate($(this))
		} else if ( $(this).val() == '' || !($("#discontinued").val() instanceof Date) ){
			invalidate($(this))
		} else {
			validate($(this))
			$(this).attr('readonly', false)
		}
	})
	
	$("div #companyId").on("focus blur", function( ) {
		if ( $(this).val() == '' || !$("#companyId option").toArray().map(option => option.value).includes( $("#companyId").val() ) ){
			invalidate($(this))
		} else {
			validate($(this))
		}
	})
	
	$('form[action="/cdb/addComputer"]').first().on("submit", function(){
		let ctx = $(this)
		$(this).find("fieldset div").forEach( _ => {
			if ( $(this).hasClass('has-error') || !$(this).hasClass('has-success') ){
				ctx.preventDefault()
			}
		})
	})
	
	function invalidate(obj){
		obj.parent().addClass('has-error').addClass('tootltip')
		obj.parent().removeClass('has-success')
		obj.prev().addClass('danger')
		$("form input[type='submit']").first().addClass('disabled')
	}
	
	function validate(obj){
		obj.parent().removeClass('has-error').removeClass('tooltip')
		obj.parent().addClass('has-success')
		obj.prev().removeClass('danger')
		$("form input[type='submit']").first().removeClass('disabled')
	}
	
})

