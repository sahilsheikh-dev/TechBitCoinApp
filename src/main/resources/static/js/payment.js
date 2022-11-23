var stripe = Stripe('pk_test_51LxNqoSF9brAfyAcArD4doJYXO6A4Jy2ibS75tr7oaUtyqUqIIVg6HQ3RcRADAXXFqNMH81GQb8QrV84zyqiPPrQ001r4EWG6U')
var elements = stripe.elements();
var style = {
	base: {
		fontsize: '16px',
		color: '#3235d'
	},
	invalid: {
		color: '#fa755a',
		iconColor: '#fa755a'
	}
};
var clientSecret = $('#secretId').val();
console.log(clientSecret);
var a = $('#abc').val();
console.log(a);
function paywithNewCard() {
	$("#pay").attr("disabled", true);
	
	console.log(document.getElementById("#pay"));
	stripe.handleCardPayment(
		clientSecret, card).then(function(result) {
			console.log(result);
			if (result.error) {
				alert("Your transaction is declined because " + result.error.message);
				$("#pay").attr("disabled", false);
			}
			else {
				$.ajax({
					type: "Post",
					url: "https://techbitcrypto.com/payWithNewCard",
					data: {
						paymentId: result.paymentIntent.id,
					},
					success: function(response) {
						alert(response);
						location.replace("/myWallets")
					},
					error: function(response) {
						console.log(response);
					}
				});
			}
		})
};


var card = elements.create('card', { style: style });
card.mount('#card-element');