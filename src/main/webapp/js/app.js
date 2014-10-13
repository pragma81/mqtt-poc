$(document).ready(function() {
		var destination = "event";
		var host;
		var connected = false;
		
		 var userLang = navigator.language || navigator.userLanguage; 
		
		$('#datetimepicker1').datetimepicker({
			language : 'en'
		});	
		
		 $('#connect-btn').click(function () {
			    var btn = $(this)
			    btn.button('loading')
			    
			    host = $("#connect_host").val();    
			    var port = $("#connect_port").val();
			    var clientId = "browser";
			    

			    
			    client = new Messaging.Client(host, Number(port), clientId);

			    client.onConnect = onConnect;
			  
			    client.onMessageArrived = onMessageArrived;
			    client.onConnectionLost = onConnectionLost;            

			    client.connect({onSuccess:onConnect, onFailure:onFailure}); 
			    return false;
			  });  
			    
		    // the client is notified when it is connected to the server.
		    var onConnect = function(frame) {
		       $('#connect-btn').fadeOut({ duration: 'fast' });
		      client.subscribe(destination);
		      $('#success-alert').text("Successfully connected to "+host);
		      $('#success-alert').removeClass( "hidden" ).addClass( "show" );
		      //$('#success-alert').fadeIn({ duration: 'fast' });
		      connected = true;
		    };  

		    
		  function onFailure(failure) {
		    $('#connect-btn').fadeIn({ duration: 'fast' });
		    $('#error-alert').text("Error while establishing web socket connection: "+failure.errorMessage);
		    $('#error-alert').removeClass( "hidden" ).addClass( "show" );
		    //$('#error-alert').fadeIn({ duration: 'fast' });
		    $('#connect-btn').button('reset');
		    connected = false;
		  }  

		  function onMessageArrived(message) {
		    var event = $.parseJSON(message.payloadString);
		    $('#calendar').fullCalendar('renderEvent', event, true);
		  }

		  function onConnectionLost(responseObject) {
		    if (responseObject.errorCode !== 0) {
		       $('#error-alert').text("Connection lost from server: "+responseObject.errorCode);
		       $('#error-alert').removeClass( "hidden" ).addClass( "show" );
		       $('#connect-btn').fadeIn({ duration: 'fast' });
		       $('#connect-btn').button('reset');
		       connected = false;
		    }
		    
		  }
		 
		
		$('#send-btn').click(function(){
			var eventText = $("#eventText").val();
			var eventDate = $("#eventDate").val();
			
			if (!connected) {
          		 $('#error-alert').text("You must first connect to web socket server");
		  		 $('#error-alert').removeClass( "hidden" ).addClass( "show" );
            	return false;
        	}
			var payload = {
					"title" : eventText,
					"start" : eventDate
			};
        	
			var message = new Messaging.Message(JSON.stringify(payload));
			//var message = new Messaging.Message("prova");
        	message.destinationName = "eventInboundTopic";
      	  	client.send(message);
        
			}); 
			
		$('#calendar').fullCalendar({
			header : {
				left : 'prev,next today',
				center : 'title',
				right : 'month,agendaWeek,agendaDay'
			},
			timezone : false,
			editable : true,
			slotDuration: '00:15:00',
			eventRender: function(event, el) {
				// render the timezone offset below the event title
				if (event.start.hasZone()) {
					el.find('.fc-title').after(
						$('<div class="tzo"/>').text(event.start.format('Z'))
					);
				}
			}
			
		});

	});