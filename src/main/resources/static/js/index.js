function myFunction() {
	var x = document.getElementById("amountTbc").value;
	console.log(x);
	//var x=Math.floor(x);
	console.log(x);
	document.getElementById("amountTbc").value = x;
	document.getElementById("priceTbc").value = x * 5;
	console.log(x / 5);
	console.log(x);
}