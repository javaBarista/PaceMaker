<?php
$conn = mysqli_connect("localhost", "nobles1030", "hero!0628", "nobles1030");

$id = $_POST['id'];
$year = $_POST['year'];
$name = $_POST['name'];
$num = $_POST['testNum'];
$today = date("Y/m/d");

$db_sql = "INSERT INTO QuestionBoard(userID, year, name, testNum, uploadDate) values('".$id."', '".$year."', '".$name."', '".$num."' ,'".$today."');";
mysqli_query($conn, $db_sql);

$sql = "SELECT num, year, name, testNum, uploadDate FROM QuestionBoard WHERE userId='".$id."' AND year='".$year."' AND name='".$name."' AND testNum='".$num."' AND uploadDate='".$today."';";
$result = mysqli_query($conn, $sql);
$data = mysqli_fetch_array($result);

$array = array(
  'num' => $data["num"],
  'year' => $data["year"],
  'name' => $data["name"],
  'testNum' => $data["testNum"],
  'uploadDate' => $data["uploadDate"]
);

echo json_encode($array, JSON_UNESCAPED_UNICODE);

mysqli_close($conn);
?>
