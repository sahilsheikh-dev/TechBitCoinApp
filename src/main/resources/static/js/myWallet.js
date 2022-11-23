$('#exampleModal').on('show.bs.modal', function(e) {
	console.log("My modal", $(e.relatedTarget).data('value'));

	$(this).find('.btnStripe').attr('href', $(e.relatedTarget).data('href'));
	$(this).find('.btnCoin').attr('value', $(e.relatedTarget).data('value'));
});

$()

function paywithcoin() {
	var a = $('#payCoin').attr('value');
	$("#payCoin").attr("disabled", true);
	$("#payStripe").attr("disabled", true);
	var c = $.ajax({
		type: "post",
		url: "https://techbitcrypto.com/paywithcoin",
		data: {
			from:"package",
			amount: a
		},
		success: function(response) {
			location.replace(response)
		}
	})
}function disableButtons(){
	console.log("func");
	$("#payCoin").attr("disabled", true);
	$("#payStripe").attr("disabled", true);
}