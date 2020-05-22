<?php
$conn = mysqli_connect("localhost", "nobles1030", "hero!0628", "nobles1030");

$year = $_POST['year'];
$college = $_POST['College_name'];

$db_sql = "SELECT num, address, main_text, part, ans FROM PaceMaker_PreviousTests WHERE year = '".$year."' AND College_name = '".$college."';";
$result = mysqli_query($conn, $db_sql);

$data = mysqli_fetch_array($result);

$array = array(
  'num'=> $data["num"],
  'address' => $data["address"],
  'main_text' => $data["main_text"],
  'part' => $data["part"],
  'ans' => $data["ans"]
);

if(mysqli_num_rows($result) > 0){
    echo json_encode(success, JSON_UNESCAPED_UNICODE);
    echo json_encode($array, JSON_UNESCAPED_UNICODE);
}
else{
    echo json_encode(failure, JSON_UNESCAPED_UNICODE);
}

mysqli_close($conn);
?>
