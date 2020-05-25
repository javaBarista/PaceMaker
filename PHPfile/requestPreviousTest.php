<?php
$conn = mysqli_connect("localhost", "nobles1030", "hero!0628", "nobles1030");

$year = $_POST['year'];
$college = $_POST['college'];

$db_sql = "SELECT num, address, main_text, part, ans FROM PaceMaker_PreviousTests WHERE year = '".$year."' AND College_name = '".$college."';";
$data = mysqli_query($conn, $db_sql);

if(mysqli_num_rows($data) > 0 ){
  while($row = mysqli_fetch_assoc($data)){
    $array[] = $row;
  }
}

echo json_encode($array, JSON_UNESCAPED_UNICODE);

mysql_free_result($data);
mysqli_close($conn);
?>
