// 更新ボタンを非活性にする。
$(function() {
  $("#updateButton").on('click', function() {
    $(this).prop('disabled', true);
    $("#updateForm").submit();
  });
});

// ログアウト管理
$(function() {
  $("#logout").on('click', function() {
    var result = confirm("ログアウトしますか。");
    if (result) {
      window.location.href = "/logout";
    }
  });
});

// 「イメージを追加する」ボタンを押下時に
// さらに外部ファイルを読み取れるようにする
var counter = 0;
$(document).on("click", "#addImage", function (e) {
  e.preventDefault();
  counter++;
   var newRow = jQuery(
              "<div id='image-repeat'>"+
              "  <div class='row pt-2'>"+
              "    <div class='col-md-4 pr-1'>"+
              "      <input"+
              "        type='file'"+
              "        placeholder='イメージ画像'"+
              "        name='imagesFile'"+
              "      />"+                   
              "    </div>"+
              "  </div>"+
              "</div>");
   console.log(newRow);
  $('#image-repeat')
    .parent('div.parent')
    .append(newRow);

});

// 郵便番号から住所を表示
var formElements = document.forms.orderForm;
$(function() {
  $("#searchAddress").on('click', function() {
    const postalCode = document.getElementById("postalCode").value;
    $.getJSON(`https://jp-postal-code-api.ttskch.com/api/v1/${postalCode}.json`, (data) => {
      formElements.prefecture.value = data["addresses"][0]["ja"]["prefecture"];
      formElements.city.value = data["addresses"][0]["ja"]["address1"];
      formElements.address.value = data["addresses"][0]["ja"]["address2"];
    });
  });
});

// ポップアップ画面の表示、非表示
function deleteCart(num) {
  $('.popup-wrapper' + num).show();
};
function popupClose() {
  $('.popup-wrapper').hide();
};

// 作品画面で他の画像を押下するとメイン画像が切り替わる
function makeMainLgImage(workId,imageName) {
  let img = document.getElementById("mainLgImage");
  img.src = `/image/works/${workId}/${imageName}`;
};
function makeMainMdImage(workId,imageName) {
  let img = document.getElementById("mainMdImage");
  img.src = `/image/works/${workId}/${imageName}`;
};

// 問い合わせ画面でのバリデーションチェック
document.addEventListener('DOMContentLoaded', function() {
  var targets = document.getElementById("contactForm");
  // ▼送信直前で全項目を再度チェックしてエラーを数える：
  targets.onsubmit = function (event) {

    // 問い合わせ内容を送信時、二重送信防止。
    // 送信ボタンを非活性にする。
    const contactButton = document.getElementById("contactButton");
    contactButton.disabled = true;

    const blankCheck = /\S/g;
    const phoneNumberPattern = /^(0{1}\d{9,10})$/;
    // 入力情報を取得
    const emailPattern = /^[a-zA-Z0-9_+-]+(\.[a-zA-Z0-9_+-]+)*@([a-zA-Z0-9][a-zA-Z0-9-]*[a-zA-Z0-9]*\.)+[a-zA-Z]{2,}$/;
    const name = document.getElementById('name').value;
    const phoneNumber = document.getElementById('phoneNumber').value;
    const email = document.getElementById('email').value;
    const subject = document.getElementById('subject').value;
    const content = document.getElementById('content').value;

    var validation = true;

    if(!name || !name.match(blankCheck)){
      document.getElementById('nameMessage').innerHTML = "お名前を入力して下さい";
      validation = false;
    } else {
      document.getElementById('nameMessage').innerHTML = "";
    }
    if(!(!phoneNumber || phoneNumber.match(phoneNumberPattern))){
      document.getElementById('phoneNumberMessage').innerHTML = "0から始まる10桁又は11桁の半角数字で入力して下さい";
      validation = false;
    } else {
      document.getElementById('phoneNumberMessage').innerHTML = "";
    }
    if(!email || !email.match(emailPattern)){
      document.getElementById('emailMessage').innerHTML = "メールアドレスを入力して下さい";
      validation = false;
    } else {
      document.getElementById('emailMessage').innerHTML = "";
    }
    if(!subject || !subject.match(blankCheck)){
      document.getElementById('subjectMessage').innerHTML = "件名を入力して下さい";
      validation = false;
    } else {
      document.getElementById('subjectMessage').innerHTML = "";
    }
    if(!content || !content.match(blankCheck)){
      document.getElementById('contentMessage').innerHTML = "お問い合わせ内容を入力して下さい";
      validation = false;
    } else {
      document.getElementById('contentMessage').innerHTML = "";
    }
    if(!validation){
      event.preventDefault();
      // バリデーションチェックでエラーが出た際は、もう一度送信できるようボタンを活性化させる
      contactButton.disabled = false;
      return false;
    } else {
      return true;
    }
  }
});

// 購入画面でのバリデーションチェック
document.addEventListener('DOMContentLoaded', function() {
  var targets = document.getElementById("orderForm");
  // ▼送信直前で全項目を再度チェックしてエラーを数える：
  targets.onsubmit = function (event) {

    // 購入者情報を送信時、二重送信防止。
    // 送信ボタンを非活性にする。
    const orderButton = document.getElementById("orderButton");
    orderButton.disabled = true;

    const blankCheck = /\S/g;
    const emailPattern = /^[a-zA-Z0-9_+-]+(\.[a-zA-Z0-9_+-]+)*@([a-zA-Z0-9][a-zA-Z0-9-]*[a-zA-Z0-9]*\.)+[a-zA-Z]{2,}$/;
    const postalCodePattern = /^\d{7}$/;
    // 入力情報を取得
    const email = document.getElementById('email').value;
    const lastName = document.getElementById('lastName').value;
    const firstName = document.getElementById('firstName').value;
    const postalCode = document.getElementById('postalCode').value;
    const prefecture = document.getElementById('prefecture').value;
    const city = document.getElementById('city').value;
    const address = document.getElementById('address').value;
    const building = document.getElementById('building').value;

    var validation = true;

    if(!email || !email.match(emailPattern)){
      document.getElementById('emailMessage').innerHTML = "メールアドレスを入力して下さい";
      validation = false;
    } else {
      document.getElementById('emailMessage').innerHTML = "";
    }
    if(!lastName || !lastName.match(blankCheck)){
      document.getElementById('lastNameMessage').innerHTML = "性を入力して下さい";
      validation = false;
    } else {
      document.getElementById('lastNameMessage').innerHTML = "";
    }
    if(!firstName || !firstName.match(blankCheck)){
      document.getElementById('firstNameMessage').innerHTML = "名を入力して下さい";
      validation = false;
    } else {
      document.getElementById('firstNameMessage').innerHTML = "";
    }
    if(!postalCode || !postalCode.match(postalCodePattern)){
      document.getElementById('postalCodeMessage').innerHTML = "7桁の半角数字で入力して下さい";
      validation = false;
    } else {
      document.getElementById('postalCodeMessage').innerHTML = "";
    }
    if(!prefecture){
      document.getElementById('prefectureMessage').innerHTML = "都道府県を選択して下さい";
      validation = false;
    } else {
      document.getElementById('prefectureMessage').innerHTML = "";
    }
    if(!city || !city.match(blankCheck)){
      document.getElementById('cityMessage').innerHTML = "市区町村を入力して下さい";
      validation = false;
    } else {
      document.getElementById('cityMessage').innerHTML = "";
    }
    if(!address || !address.match(blankCheck)){
      document.getElementById('addressMessage').innerHTML = "丁目・番地・号を入力して下さい";
      validation = false;
    } else {
      document.getElementById('addressMessage').innerHTML = "";
    }
    if(!(!building || building.match(blankCheck))){
      document.getElementById('buildingMessage').innerHTML = "建物名を入力して下さい";
      validation = false;
    } else {
      document.getElementById('buildingMessage').innerHTML = "";
    }

    if(!validation){
      event.preventDefault();
      // バリデーションチェックでエラーが出た際は、もう一度送信できるようボタンを活性化させる
      orderButton.disabled = false;
      return false;
    } else {
      return true;
    }
  }
});