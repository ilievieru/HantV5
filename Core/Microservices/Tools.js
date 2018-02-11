exports.rand = (max) => Math.floor(Math.random() * max);

exports.rand2 = (min, max) => Math.floor(Math.random() * (max - min)) + min;

exports.getRandomInterval = (min, max) => {
	if(max > min){
		var temp = max;
		max = min;
		min = temp;
	}

	return this.rand2(min, max);
}

exports.SubscriberContains = function (val) {
    for (var i = 0; i < this.length; i++)
        if (this[i].address === val.address && this[i].port === val.port)
            return i;

    return -1;
}

exports.trunc = (number, noOfDigits) => {
	var s = number + '';
    return s.substring(0, s.indexOf('.') + noOfDigits);
}