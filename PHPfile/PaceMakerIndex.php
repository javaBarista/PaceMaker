<!doctype html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, user-scalable=no">
  <meta name="Generator" content="EditPlus®">
  <meta name="Author" content="">
  <meta name="Keywords" content="">
  <meta name="Description" content="">
  <style>
  li { list-style: none }

  input[type=submit] {
    padding:5px 15px;
    color: #FFFAFA;
    background:#6A5ACD;
    border:0 none;
    cursor:pointer;
    -webkit-border-radius: 5px;
    border-radius: 5px;
}
button[type=button] {
  padding:5px 15px;
  background:#87CEFA;
  border:0 none;
  cursor:pointer;
  -webkit-border-radius: 5px;
  border-radius: 5px;
}
</style>
  <title>Firebase Cloud Messaging</title>

  <script type="text/javascript">

  function reply_click_img(clicked_id, clicked_url)
  {
    var imgs = document.getElementsByTagName('img');
    for(var i = 0; i < imgs.length; i++){
      imgs[i].style.border='1px solid #ffffff';
    }

    document.getElementById('imgurl').value = clicked_url;
    document.getElementById(clicked_id).style.border = '1px solid #ff0000';
    //window.scrollTo(0,0);
  }

</script>

<link rel="stylesheet" href="styles.css">
</head>
<body>
  <div class="container">
    <div style="border: 1px solid gold; float: left; height:330px; width: 49%;">
      <div class="messageWrapper" align=center border-collapse style="table-layout: fixed">
        <h2>FCM-관리자페이지</h2>
        <h4>푸시알림 보내기</h4>
        <form action="send_notification.php" method="post">
          <div class="textbox" align=left>
            <label for="title">제목</label>
            <input type="text" id="title" name="title" size="38" required><br>
            <div style = "padding: 5px 0px 8px 0px;">
            &nbsp;<textarea name="message" rows="3" cols="40" placeholder="메세지를 입력하세요"  required></textarea><br></div>

            <input type="radio" name="chk_info" value="bothuse">이미지&아이콘 사용
            <input type="radio" name="chk_info" value="onlyicon">아이콘만 사용
            <input type="radio" name="chk_info" value="nouse" checked="checked">둘다 사용안함<br>

            <label for="imgurl">이미지 URL<span style="font-size:0.8em"> : </span></label>
            <input type="text" id="imgurl" name="imgurl" size="55"><br><br>

          </div>
          <input type="submit" name="submit" value="알림보내기" id="submitButton">
        </form>
      </div>
    </div>
    <div style="border: 1px solid gray; float: left; height:330px; width: 50%;">
      <div id="tokenList">
        <?php
        $conn = mysqli_connect("localhost", "nobles1030", "hero!0628", "nobles1030");
        if(mysqli_connect_errno()){
          echo "연결실패! ".mysqli_connect_error();
        }
        $query = "SELECT * FROM PaceMakerFCM";
        $result = mysqli_query($conn, $query);

        echo '<br>
        <table border=1 background=#cc99ff width=70% align=center border-collapse style="table-layout: fixed">
        <thead>
        <th>유저ID</th>
        </thead>';
        while($data = mysqli_fetch_array($result)){

          echo '<tr><td align=left style="text-overflow:ellipsis; overflow:hidden; white-space:nowrap;">';
          echo ($data['token']);
          echo '</td></tr>';

        }
        echo '</table>';

        mysqli_close($conn);
        ?>
      </div>
    </div>

    <div style="border: 1px solid red; float: left; width: 99%;">
      <div id="">
        <h3>이미지 선택
        <button type="button" onclick="location.href='PaceMakerFCM.php'">이미지 갱신</button>

        <ul id="imgList">
          <?php
          $conn = mysqli_connect("localhost", "nobles1030", "hero!0628", "nobles1030");
          if(mysqli_connect_errno()){
            echo "연결실패! ".mysqli_connect_error();
          }

          $query = "SELECT * FROM College_Image";
          $result = mysqli_query($conn, $query);

          while($data = mysqli_fetch_array($result)){
            ?>

            <li style='float:left; margin: 2px;'>
              <img src=<?=$data['Address']?> vspace=30 hspace=20 width=300 height=150 onClick='reply_click_img(this.id, this.src);' style="border:1px solid #ffffff; cursor:pointer" id=<?=$data['College_name']?>><br>
            </li>

            <?php
          }
          mysqli_close($conn);
          ?>
        </ul>
      </div>
    </div>

  </div>

</body>
</html>
