function addCurrenciesButtonsListener(){
	var buttons=document.querySelector('.currencyTable').querySelectorAll('.btn')
	
	buttons.forEach( 
			  function(currentValue, currentIndex, listObj) { 
			    currentValue.addEventListener('click',buy); 
			  });
}
function buy(e){
	var currency=this.getAttribute('currency');
	if(confirm("Are you sure?")){
		const xhr = new XMLHttpRequest();
		xhr.open("POST", "http://localhost:8080/transaction/buying?currencyType="+currency+"&amount=1", true);
		xhr.addEventListener("load", function() {
			let status=JSON.parse(xhr.response);
		    alert(status.reason);
		    if(status.isOK){
		    	Location.reload();
		    }
		    
		});

		xhr.addEventListener("error", function() {
		    alert("Something went wrong");
		});
		xhr.send();


	}
}
function addMyWalletButtonsListener(){
	var buttons=document.querySelector('.walletTable').querySelectorAll('.btn')
	
	buttons.forEach( 
			  function(currentValue, currentIndex, listObj) { 
			    currentValue.addEventListener('click',buy); 
			  });
}
function buy(e){
	var currency=this.getAttribute('currency');
	if(confirm("Are you sure?")){
		const xhr = new XMLHttpRequest();
		xhr.open("POST", "http://localhost:8080/transaction/selling?currencyType="+currency+"&amount=1", true);
		xhr.addEventListener("load", function() {
			let status=JSON.parse(xhr.response);
		    alert(status.reason);

		    if(status.isOK){
		    	Location.reload();
		    }
		    
		});

		xhr.addEventListener("error", function() {
		    alert("Something went wrong");
		});
		xhr.send();


	}
}
addCurrenciesButtonsListener();
addMyWalletButtonsListener();