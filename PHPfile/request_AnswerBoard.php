<?php
$conn = mysqli_connect("localhost", "nobles1030", "hero!0628", "nobles1030");

$num = $_POST['num'];

$sql = "SELECT userID, body, uploadDate FROM AnswerBoard WHERE qnum = '".$num."' ORDER BY anum ASC;";
$result = mysqli_query($conn,$sql);

if(mysqli_num_rows($result) > 0 ){
  while($row = mysqli_fetch_assoc($result)){
    $arr[] = $row;
  }
}

echo json_encode($arr, JSON_UNESCAPED_UNICODE);
mysql_free_result($result);
mysqli_close($conn);
?>
