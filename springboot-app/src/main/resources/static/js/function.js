// 各htmlでヘッダー、フッターを読み込むための処理
$(function(){
    $("#header").load("/html/header.html");
    $("#footer").load("/html/footer.html");
});

$(function() {
    $("#submit-button").on('click', function() {
       $(this).prop('disabled', true);
       $("#contact-form").submit();
    });
  });