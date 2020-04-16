<?php
$conn = mysqli_connect("localhost", "nobles1030", "hero!0628", "nobles1030");

$id = $_POST['id'];

$db_sql = "SELECT * FROM PaceMakerMembers WHERE id = '".$id."';";
$result = mysqli_query($conn, $db_sql);

if(mysqli_num_rows($result) > 0){
  echo json_encode(failure, JSON_UNESCAPED_UNICODE);
}
else{
  echo json_encode(success, JSON_UNESCAPED_UNICODE);
}

mysqli_close($conn);
?>
