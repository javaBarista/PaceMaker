<?php
$conn = mysqli_connect("localhost", "nobles1030", "hero!0628", "nobles1030");

$qnum = $_POST['qnum'];
$id = $_POST['id'];
$body = $_POST['body'];
$today = date("Y/m/d");

$sql = "SELECT MAX(anum)as max FROM AnswerBoard WHERE qnum = '".$qnum."';";
$result = mysqli_query($conn,$sql);
$data = mysqli_fetch_array($result);
$cnt = (int)$data["max"] + 1;

$db_sql = "INSERT INTO AnswerBoard(anum, qnum, userID, body, uploadDate) values('".$cnt."', '".$qnum."', '".$id."', '".$body."','".$today."');";

if(mysqli_query($conn,$db_sql)){
  echo json_encode(success, JSON_UNESCAPED_UNICODE);
  echo json_encode($cnt, JSON_UNESCAPED_UNICODE);
} else {
  echo json_encode(failure, JSON_UNESCAPED_UNICODE);
}

mysqli_close($conn);
?>
