
function countDownTimer(hours, minutes, seconds, elem) {
    var element = document.getElementById(elem);
    element.innerHTML = hours + ":" + minutes + ":" + seconds;
    if (hours == 0 && minutes == 0 && seconds < 1) {
        clearTimeout(timer);
        location.reload();
    }

    if (seconds > 0) {
        seconds--;
    } else {
        seconds = 59;
        if (minutes > 0) {
            minutes--;
        } else {
            minutes = 59;
            hours--;
        }
    }
    var timer = setTimeout('countDownTimer('+hours+','+minutes+','+seconds+',"'+elem+'")',1000);
}
