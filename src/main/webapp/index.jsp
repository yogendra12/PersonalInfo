<html>
<head>
<script type="text/javascript" src="/js/jquery-1.12.4.min.js"></script>
<script type="text/javascript">
function importCsv(){
	
	var ext = $("#import_csv").val().split(".").pop().toLowerCase();

	if($.inArray(ext, ["csv"]) == -1) {
	/* 	$('#smsnchat_error  > p').text(SELECT_CSV_FILE);
		$('#smsnchat_error').css("display","block");
		setInterval(function(){$("#smsnchat_error").fadeOut();}, 5000); */
		alert('choose csv file only..');
		return false;
	}
	
	var csvval= "";
	var reader = new FileReader();
	reader.readAsText($("#import_csv")[0].files[0]);
	
	reader.onload = function(event) {
		var size = $("#import_csv")[0].files[0].size/(1024*1024);
		//console.log("csv file size   "+size);
		if(parseFloat(size) > 0.5){
			/* $('#smsnchat_error  > p').text(BIG_CSV);
			$('#smsnchat_error').css("display","block");
			setInterval(function(){$("#smsnchat_error").fadeOut();}, 5000); */
			alert('chose small csv files..');
			return false;
		}
		var toPhones = [];
		csvval=event.target.result.split("\n");
		for(var i=1;i<csvval.length;i++) {
			var csvvalue=csvval[i].split(",");
			var csvvalue = csvval[i].split(",");
			var phone = $.trim(csvvalue[0]);
			var name = $.trim(csvvalue[1]);
			toPhones.push({"phoneNum":phone,"name":name});
			}
		if (toPhones.length > 0){
			$.ajax({   
				method: 'PSOT',			
				   url: '/importcsv',
				   data: toPhones,
				   success: function(data) {
			/* jQuery.fn.makeRequestWithContent('POST', 'application/json',  "rest/twilio/add/number",
					JSON.stringify( {"username":sessionContactObj.userName, "password":sessionContactObj.password,"ToPhones":toPhones}), function (data) { */
				if (data != null && data != ""){
					if(data.statusCode == 200) {
						var iCount =  data.success[0].added_phone_numbers_count;
						if(iCount > 0){
							var niConunt = toPhones.length - iCount;
							$.each(data.success[0].address_book, function(i, val){
								validContactsToDisplay.push(val.phone);
								var contact = chooseBusinessContactsWithSMS(val.phone,val.name, true);
								$("#loadSMSContacts").prepend(contact);
							});		
						}
					}
					}
				}
			});
		}else{
			//no valid contacts.
		}
	};
}
</script>
</head>
<body>
	<!--  -->
	<b>Import CSV: </b>
	<input type='file' id='import_csv' name='csv' onchange='importCsv();'>
</body>
</html>