appServices.factory('presenceStates', function($log, $presence) {
	$log.debug('presenceStates service started...');

	var states = [];

	states.push({
		ACTIVE : {
			enter : 0,
			text : "You are back!"
		},
		INACTIVE : {
			enter : 3000,
			text : "Your are away!"
		},
		TYPING : {
			accept : "KEYBOARD",
			text : "Hey, you are typing!"
		},
		IDLE : {
			enter : 2000,
			initial : true,
			text : "I know you're there, Why don't you type something?",
		},
		SHORTAWAY : {
			enter : 5000,
			text : "Ok, I think you're gone."
		},
		LONGAWAY : {
			enter : 10000,
			text : "You're definetly gone... But you should type something."
		}
	});

	states.push({
		ACTIVE : {
			enter : 0,
			text : "You are back!"
		},
		INACTIVE : {
			enter : 60 * 1000,
			text : "Your are away!"
		},
		TYPING : {
			accept : "KEYBOARD",
			text : "Hey, you are typing!"
		},
		IDLE : {
			enter : 30 * 1000,
			initial : true,
			text : "I know you're there, Why don't you type something?",
		},
		SHORTAWAY : {
			enter : 15 * 60 * 1000,
			text : "Ok, I think you're gone."
		},
		LONGAWAY : {
			enter : 25 * 60 * 1000,
			text : "You're definetly gone... But you should type something."
		}
	});

	states = $presence.init(states[1]);

	$log.debug('presenceStates service finished...');

	return states;

});
