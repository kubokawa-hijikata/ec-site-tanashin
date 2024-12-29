// 問い合わせ内容を送信時、二重送信防止。
// 送信ボタンを非活性にする。
$(function() {
  $("#contact-button").on('click', function() {
    $(this).prop('disabled', true);
    $("#contactForm").submit();
  });
});

$(function() {
  $("#update-button").on('click', function() {
    $(this).prop('disabled', true);
    $("#update-form").submit();
  });
});

// 各htmlにheader.hmtlとfooter.htmlを挿入する。
// fetch("/html/header.html")
// .then((response) => response.text())
// .then((data) => document.querySelector("body").insertAdjacentHTML('afterbegin', data));
// fetch("/html/footer.html")
// .then((response) => response.text())
// .then((data) => document.querySelector("main").insertAdjacentHTML('afterend', data));

// 「イメージを追加する」ボタンを押下時に
// さらに外部ファイルを読み取れるようにする
var counter = 0;
$(document).on("click", "#addImage", function (e) {
  e.preventDefault();
  counter++;
   var newRow = jQuery(
              "<div id='image-repeat'>"+
              "  <div class='row'>"+
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

// // カートの中身を削除する
// const clickBtn = document.getElementById('deleteCart');
// const popupWrapper = document.getElementById('popup-wrapper');
// const close = document.getElementById('close');
// // ボタンをクリックしたときにポップアップを表示させる
// clickBtn.addEventListener('click', () => {
//   popupWrapper.style.display = "block";
// });

// // ポップアップの外側又は「x」のマークをクリックしたときポップアップを閉じる
// popupWrapper.addEventListener('click', e => {
//   if (e.target.id === popupWrapper.id || e.target.id === close.id) {
//     popupWrapper.style.display = 'none';
//   }
// });

// ポップアップ画面の表示、非表示
function deleteCart(num) {
  $('.popup-wrapper' + num).show();
}
function popupClose(e) {
  $('.popup-wrapper').hide();
}