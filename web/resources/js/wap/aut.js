//上传图片
function c () {
    var r = new FileReader();
    imgOne = document.getElementById('icardone').files[0];
     
    r.readAsDataURL(imgOne);
    r.onload=function (e) {
      document.getElementById('blah').style.backgroundImage='url('+this.result+')';
    };
};

function b() {
    var r= new FileReader();
    f = document.getElementById('icardtwo').files[0];
     
    r.readAsDataURL(f);
    r.onload=function (e) {
      document.getElementById('blahtwo').style.backgroundImage= 'url('+this.result+')';
    };
}