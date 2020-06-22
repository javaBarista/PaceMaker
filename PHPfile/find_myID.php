<?php
$conn = mysqli_connect("localhost", "nobles1030", "hero!0628", "nobles1030");

$name = $_POST['name'];
$mail = $_POST['mail'];

$sql = "SELECT id FROM PaceMakerMembers WHERE userName = '".$name."' AND userEmail = '".$mail."' ;";
$result = mysqli_query($conn, $sql);

if(mysqli_num_rows($result) > 0){
  while($row = mysqli_fetch_assoc($result)){
    $arr[] = $row;
  }
  echo json_encode($arr, JSON_UNESCAPED_UNICODE);
}
else{
    echo json_encode(failure, JSON_UNESCAPED_UNICODE);
}

mysqli_close($conn);
?>
