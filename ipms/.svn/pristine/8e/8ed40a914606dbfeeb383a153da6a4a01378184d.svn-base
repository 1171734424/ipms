var width = 150; // 桌布宽度
var height= 150; // 桌布高度
var dot = {
    x : width / 2,
    y : height / 2,
    radius : 2
}; // 圆点位置、半径
var radius = 72; // 圆半径
var borderWidth = 2; // 圆边框宽度

var clock = document.getElementById('clock');
var clockBg = document.createElement('canvas');
var clockPointers = document.createElement('canvas');

clockPointers.width = clockBg.width = width;
clockPointers.height = clockBg.height = height;
clockPointers.style.position = 'absolute';
clockPointers.style.left = 0;
clockPointers.style.right = 0;

clock.appendChild(clockBg);
clock.appendChild(clockPointers);

var bgCtx = clockBg.getContext('2d');
bgCtx.beginPath();
bgCtx.lineWidth = borderWidth;
bgCtx.strokeStyle = '#000';
bgCtx.arc(dot.x, dot.y, radius, 0, 2 * Math.PI, true);
bgCtx.stroke();
bgCtx.closePath();

bgCtx.beginPath();
bgCtx.fillStyle = '#000';
bgCtx.arc(dot.x, dot.y, dot.radius, 0, 2 * Math.PI, true);
bgCtx.fill();
bgCtx.closePath();

for (var i = 0, angle = 0, tmp, len; i < 60; i++) {
    bgCtx.beginPath();

    // 突出显示能被5除尽的刻度
    if (0 === i % 5) {
        bgCtx.lineWidth = 4;
        len = 10;
        bgCtx.strokeStyle = '#000';
    } else {
        bgCtx.lineWidth = 2;
        len = 5;
        bgCtx.strokeStyle = '#999';
    }

    tmp = radius - borderWidth / 2; // 因为圆有边框，所以要减去边框宽度
    bgCtx.moveTo(
        dot.x + tmp * Math.cos(angle),
        dot.y + tmp * Math.sin(angle)
    );
    tmp -= len;
    bgCtx.lineTo(dot.x + tmp * Math.cos(angle), dot.y + tmp * Math.sin(angle));
    bgCtx.stroke();
    bgCtx.closePath();

    angle += Math.PI / 30; // 每次递增1/30π
}

var ptCtx = clockPointers.getContext('2d');

function updatePointers() {
    ptCtx.clearRect(0, 0, width, height);　　// 清掉原来的指针

    // 获取当前时间
    var now = new Date();
    var h = now.getHours();
    var m = now.getMinutes();
    var s = now.getSeconds();

    // 算出时分秒指针现在应指向圆的几分之几处
    h = h > 12 ? h - 12 : h;
    h = h + m / 60;
    h = h / 12;
    m = m / 60;
    s = s / 60;

    drawPointers(s, 2, 62); // 画秒针
    drawPointers(m, 4, 57); // 画分针
    drawPointers(h, 6, 48); // 画时针
}

function drawPointers(angle, lineWidth, length) {
    angle = angle * Math.PI * 2 - Math.PI / 2;

    ptCtx.beginPath();
    ptCtx.lineWidth = lineWidth;
    ptCtx.strokeStyle = "#000";
    ptCtx.moveTo(dot.x, dot.y);
    ptCtx.lineTo(dot.x + length * Math.cos(angle), dot.y + length * Math.sin(angle));
    ptCtx.stroke();
    ptCtx.closePath();
}

setInterval(updatePointers, 1000);
updatePointers();


