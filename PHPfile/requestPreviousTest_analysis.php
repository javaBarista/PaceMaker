<?php
$conn = mysqli_connect("localhost", "nobles1030", "hero!0628", "nobles1030");

$college = $_POST['college'];
$year = $_POST['year'];
$id=$_POST['id'];

$db_sql = "SELECT score FROM PaceMaker_PreviousTests_analysis WHERE college = '".$college."' AND year = '".$year."' AND id = '".$id."';";
$result = mysqli_query($conn, $db_sql);

if(mysqli_query($con,$result)){
    $sql = "UPDATE PaceMaker_PreviousTests_analysis SET score = '".$score."' WHERE college = '".$college."' AND year = '".$year."' AND id = '".$id."';";
    mysqli_query($conn, $sql);
}
else{
    $sql = "INSERT INTO PaceMaker_PreviousTests_analysis(college, year, id, score) values('".$college."', '".$year."', '".$id."', '".$score."') ON DUPLICATE KEY UPDATE id = '".$id."';";
    mysqli_query($conn, $sql);
}

$select_query = "SELECT COUNT(*) FROM `PaceMaker_PreviousTests_analysis` WHERE `college` = '".$college."' AND '".$year."';";
$result_set = mysqli_query($conn, $select_query);

for($i=0;$row1=mysqli_fetch_array($result_set);$i++){
  $rank="SELECT (SELECT COUNT(*) + 1 FROM `PaceMaker_PreviousTests_analysis` WHERE score > a.score) AS rank
  FROM `PaceMaker_PreviousTests_analysis` AS a
  WHERE `college` = '".$college."' AND `year` = '".$year."' AND `id` = '".$id."'
  ORDER BY a.score;";
}

if(mysqli_query($con,$rank)){
  echo json_encode(success, JSON_UNESCAPED_UNICODE);
} else {
  echo json_encode(failure, JSON_UNESCAPED_UNICODE);
}

mysqli_close($conn);
?>
