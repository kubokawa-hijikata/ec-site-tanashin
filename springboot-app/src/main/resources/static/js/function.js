// 問い合わせ内容を送信時、二重送信防止。
// 送信ボタンを非活性にする。
$(function() {
    $("#submit-button").on('click', function() {
       $(this).prop('disabled', true);
       $("#contact-form").submit();
    });
  });

// 各htmlにheader.hmtlとfooter.htmlを挿入する。
fetch("/html/header.html")
.then((response) => response.text())
.then((data) => document.querySelector("body").insertAdjacentHTML('afterbegin', data));
fetch("/html/footer.html")
.then((response) => response.text())
.then((data) => document.querySelector("main").insertAdjacentHTML('afterend', data));