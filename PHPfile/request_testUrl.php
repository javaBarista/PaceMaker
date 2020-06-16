<?php
$conn = mysqli_connect("localhost", "nobles1030", "hero!0628", "nobles1030");

$year = $_POST['year'];
$college = $_POST['college'];
$num = $_POST['num'];

if($year == "영어"){
  $db_sql = "SELECT address, main_text FROM PaceMaker_EngMockupTest WHERE round = '".$college."' AND num = '".$num."';";
}
else if($year == "수학"){
    $db_sql = "SELECT address, main_text FROM PaceMaker_MathMockupTest WHERE round = '".$college."' AND num = '".$num."';";
}
else{
  $db_sql = "SELECT address, main_text FROM PaceMaker_PreviousTests WHERE year = '".$year."' AND College_name = '".$college."' AND num = '".$num."';";
}
$res = mysqli_query($conn, $db_sql);
$data = mysqli_fetch_array($res);

$array = array(
  'address' => $data["address"],
  'main_text' => $data["main_text"]
);

echo json_encode($array, JSON_UNESCAPED_UNICODE);

mysql_free_result($res);
mysqli_close($conn);
?>
