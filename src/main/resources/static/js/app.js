function reloadWallet(){
	const xhr = new XMLHttpRequest();			    	
	xhr.open("GET", "http://localhost:8080/wallet", true);
			    		xhr.addEventListener("load", function() {
			    			let response=JSON.parse(xhr.response);
			    			let wallets=response.userWallets;
			    			for(let i in wallets){
			    				let row=document.querySelector('tr[wallet='+wallets[i].currency+']');
			    				row.children[1].innerText=wallets[i].unitPrice;
			    				row.children[2].innerText=wallets[i].amount;
			    				row.children[3].innerText=wallets[i].value;
			    			}
			    			document.querySelector('.settlementAmount').innerText=response.settlementAmount;
			    		    
			    		});
			    		xhr.addEventListener("error", function() {
			    		    alert("Something went wrong");
			    		});
			    		xhr.send();
}
function addButtonsListener(className,endPoint){
	var buttons=document.querySelector(className).querySelectorAll('.btn')
	
	buttons.forEach( 
			  function(currentValue, currentIndex, listObj) { 
			    currentValue.addEventListener('click',function(e){
			    	var currency=this.getAttribute('currency');
			    	if(confirm("Are you sure?")){
			    		const xhr = new XMLHttpRequest();
			    		xhr.open("POST", "http://localhost:8080/transaction/"+endPoint+"?currencyType="+currency+"&amount=1", true);
			    		xhr.addEventListener("load", function() {
			    			let status=JSON.parse(xhr.response);
			    		    alert(status.reason);
			    		    if(status.isOK){
			    		    	reloadWallet();
			    		    }
			    		});
			    		xhr.addEventListener("error", function() {
			    		    alert("Something went wrong");
			    		});
			    		xhr.send();
			    	}
			    }); 
			  });
}

addButtonsListener('.currencyTable','buying');
addButtonsListener('.walletTable','selling');