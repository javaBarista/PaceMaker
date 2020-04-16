<?php
$con = mysqli_connect("localhost", "nobles1030", "hero!0628", "nobles1030");

if($con){
  echo "접속성공<br>";
}
else{
  echo "접속실패<br>";
}

$token = $_POST['token'];
$platform = $_POST['platform'];

$db_sql = "INSERT INTO PaceMakerFCM(token, platform) values('".$token."', '".$platform."') ON DUPLICATE KEY UPDATE token = '".$token."';";
mysqli_query($con, $db_sql);

mysqli_close($con);
?>
