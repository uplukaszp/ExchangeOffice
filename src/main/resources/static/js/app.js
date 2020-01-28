function addButtonsListener(className,endPoint){
	var buttons=document.querySelector(className).querySelectorAll('.btn')
	
	buttons.forEach( 
			  function(currentValue, currentIndex, listObj) { 
			    currentValue.addEventListener('click',function(e){
			    	var currency=this.getAttribute('currency');
			    	if(confirm("Are you sure?")){
			    		const xhr = new XMLHttpRequest();
			    		xhr.open("POST", location.origin+"transaction/"+endPoint+"?currencyType="+currency+"&amount=1", true);
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
	let addres=location.origin;
	if(addres.startsWith('https')){
		addres=address.replace('https','ws');
	}else{
		addres=addres.replace('http','ws');
	}
	xhr.open("GET", addres+"/wallet", true);
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
	var socket = new WebSocket('ws://localhost:8080/stomp');
	ws = Stomp.over(socket);

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
		alert("Websocket error " + error);
	});




//	var socket = new WebSocket('ws://localhost:8080/stomp');
//	ws = Stomp.over(socket);
//	socket.onopen = () =>{console.log('aaa');
//	ws.subscribe("/currencies/", function(message) {
//		let rates=JSON.parse(xhr.response);
//		console.log('aa');
//		for(let i in rates){
//			let row=document.querySelector('tr[rate='+rates[i].code+']');
//			row.children[1].innerText=rates[i].unit;
//			row.children[2].innerText=rates[i].purchasePrice;
//		}
//		document.querySelector('.settlementAmount').innerText=response.settlementAmount;
//	}, function(error) {
//		alert("STOMP error " + error);
//	});}
}

addButtonsListener('.currencyTable','buying');
addButtonsListener('.walletTable','selling');

addServiceListener();