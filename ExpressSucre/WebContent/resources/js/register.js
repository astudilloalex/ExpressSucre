/**
 * 
 */
$(document).ready(function() {
	var id = $('#register\\:id-card').val();
	var user = $('#register\\:username').val();
	var password = $('#register\\:password').val();
	var resetpassword = $('#register\\:reset-password').val();

	if (id == "") {
		$('#register\\:id-card').css({
			'border' : '1px solid red'
		});
	}
	if (user == "") {
		$('#register\\:username').css({
			'border' : '1px solid red'
		});
	}
	if (password == "") {
		$('#register\\:password').css({
			'border' : '1px solid red'
		});
	}
	if (resetpassword == "") {
		$('#register\\:reset-password').css({
			'border' : '1px solid red'
		});
	}
	$('#register\\:id-card').keyup(function() {
		var field = $(this).val();
		var input = $(this);
		if (field != "") {
			input.css({
				'border' : '0'
			});
		} else {
			input.css({
				'border' : '1px solid red'
			});
		}
	});
	$('#register\\:username').keyup(function() {
		var field = $(this).val();
		var input = $(this);
		if (field != "") {
			input.css({
				'border' : '0'
			});
		} else {
			input.css({
				'border' : '1px solid red'
			});
		}
	});
	$('#register\\:password').keyup(function() {
		var field = $(this).val();
		var input = $(this);
		if (field != "") {
			input.css({
				'border' : '0'
			});
		} else {
			input.css({
				'border' : '1px solid red'
			});
		}
	});
	$('#register\\:reset-password').keyup(function() {
		var field = $(this).val();
		var input = $(this);
		if (field != "") {
			input.css({
				'border' : '0'
			});
		} else {
			input.css({
				'border' : '1px solid red'
			});
		}
	});
});