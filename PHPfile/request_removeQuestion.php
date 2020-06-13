<?php
$conn = mysqli_connect("localhost", "nobles1030", "hero!0628", "nobles1030");

$num = $_POST['num'];

$sql = "DELETE FROM QuestionBoard WHERE num = '".$num."';";
mysqli_query($conn,$sql);
$sql = "DELETE FROM AnswerBoard WHERE qnum = '".$num."';";
mysqli_query($conn,$sql);

$db_sql = "SELECT * FROM QuestionBoard WHERE num = '".$num."';";
$result = mysqli_query($conn,$db_sql);
$aasql = "SELECT * FROM AnswerBoard WHERE qnum = '".$num."';";
$res = mysqli_query($conn,$aasql);

if(mysqli_num_rows($result) > 0 && mysqli_num_rows($res) > 0){
  echo json_encode(failure, JSON_UNESCAPED_UNICODE);
}
else{
  echo json_encode(success, JSON_UNESCAPED_UNICODE);
}
mysql_free_result($result);
mysql_free_result($res);
mysqli_close($conn);
?>
