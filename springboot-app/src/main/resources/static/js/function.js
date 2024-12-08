// 問い合わせ内容を送信時、二重送信防止。
// 送信ボタンを非活性にする。
$(function() {
  $("#contact-button").on('click', function() {
    $(this).prop('disabled', true);
    $("#contact-form").submit();
  });
});

$(function() {
  $("#update-button").on('click', function() {
    $(this).prop('disabled', true);
    $("#update-form").submit();
  });
});

// 各htmlにheader.hmtlとfooter.htmlを挿入する。
fetch("/html/header.html")
.then((response) => response.text())
.then((data) => document.querySelector("body").insertAdjacentHTML('afterbegin', data));
fetch("/html/footer.html")
.then((response) => response.text())
.then((data) => document.querySelector("main").insertAdjacentHTML('afterend', data));

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