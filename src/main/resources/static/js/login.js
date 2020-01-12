var person = {
    firstName : "John",
    lastName : "Doe",
    id : 5566
};
function myFunction() {
   return person.lastName;
};
function f() {
    var time = new Date().getHours();
    if(time<20){
        alert(555);
    }else {
        alert(777);
    }
    cars=["BMW","Volvo","Saab","Ford"];
    list:{
        document.write(cars[0] + "<br>");
        document.write(cars[1] + "<br>");
        document.write(cars[2] + "<br>");
        document.write(cars[3] + "<br>");
        document.write(cars[4] + "<br>");
        document.write(cars[5] + "<br>");
    }
};
function f1() {
    var z, text;

    // 获取 id="numb" 的值
    z = document.getElementById("password_text").value;

    // 如果输入的值 x 不是数字或者小于 1 或者大于 10，则提示错误 Not a Number or less than one or greater than 10
    if (isNaN(z) || z <=1 || z >= 10) {
        text = "输入错误";
    } else {
        text = "输入正确";
    }
    window.alert(text);
};
function validateForm(){
    var text = '{ "sites" : [' +
        '{ "name":"Runoob" , "url":"www.runoob.com" },' +
        '{ "name":"Google" , "url":"www.google.com" },' +
        '{ "name":"Taobao" , "url":"www.taobao.com" } ]}';

    obj = JSON.parse(text);
    document.getElementById("password").innerHTML = obj.sites[1].name + " " + obj.sites[1].url;
};