function addButtonsListener(className,endPoint){
	var buttons=document.querySelector(className).querySelectorAll('.btn')
	
	buttons.forEach( 
			  function(currentValue, currentIndex, listObj) { 
			    currentValue.addEventListener('click',function(e){
			    	var currency=this.getAttribute('currency');
			    	if(confirm("Are you sure?")){
			    		const xhr = new XMLHttpRequest();
			    		xhr.open("POST", location.origin+"/transaction/"+endPoint+"?currencyType="+currency+"&amount=1", true);
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

function reloadWallet(){
	const xhr = new XMLHttpRequest();

	xhr.open("GET", location.origin+"/wallet", true);
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

function addServiceListener(){
	let addres=location.origin;
	if(addres.startsWith('https')){
		addres=addres.replace('https','wss');
	}else{
		addres=addres.replace('http','ws');
	}
	var socket = new WebSocket(addres+'/stomp');
	ws = Stomp.over(socket);
	ws.debug = null
	ws.reconnect_delay = 5000;

	ws.connect({}, function(frame) {
	ws.subscribe("/currencies/", function(message) {
		let rates=JSON.parse(message.body);
		console.log('aa');
		for(let i in rates){
			let row=document.querySelector('tr[rate='+rates[i].code+']');
			row.children[1].innerText=rates[i].unit;
			row.children[2].innerText=rates[i].purchasePrice;
		}
		let data=rates[0].date.replace("T"," ");
		data=data.substring(0, data.length - 5);
		document.querySelector('.pubDate').innerText=data;	

	});
	}, function(error) {
		console.log(error);
		setTimeout(addServiceListener,1000);
	});


}

addButtonsListener('.currencyTable','buying');
addButtonsListener('.walletTable','selling');

addServiceListener();