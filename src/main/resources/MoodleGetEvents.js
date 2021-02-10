var callback = arguments[arguments.length - 1];

var http = new XMLHttpRequest();
var url = 'https://moodle.lafayette.edu/lib/ajax/service.php?sesskey='+ arguments[0] + '&info=core_calendar_get_action_events_by_timesort';

var dateNow = Math.floor(Date.now() / 1000);
var body = JSON.stringify([
    {
        "index": 0,
        "methodname": "core_calendar_get_action_events_by_timesort",
        "args": {
            "limitnum": 6,
            "timesortfrom": dateNow,
            "timesortto":   dateNow + 604800,
            "limittononsuspendedevents": true
        }
    }
]);

http.open('POST', url, true);

//Send the proper header information along with the request
http.setRequestHeader('Content-type', 'application/json');

http.onreadystatechange = function() {
    if(http.status === 200 && 4 === http.readyState) {
        callback(http.responseText);
    }
};

http.send(body);