<?php
$conn = mysqli_connect("localhost", "nobles1030", "hero!0628", "nobles1030");

$college = $_POST['college'];
$year = $_POST['year'];
$id=$_POST['id'];

$db_sql = "SELECT score FROM PaceMaker_PreviousTests_analysis WHERE college = '".$college."' AND year = '".$year."' AND id = '".$id."';";
$result = mysqli_query($conn, $db_sql);

if(mysqli_num_rows($result) > 0){
    $sql = "UPDATE PaceMaker_PreviousTests_analysis SET score = '".$score."' WHERE college = '".$college."' AND year = '".$year."' AND id = '".$id."';";
    while($row = mysqli_fetch_assoc($sql)){
      $array[] = $row;
    }
}
else{
    $sql = "INSERT INTO PaceMaker_PreviousTests_analysis(college, year, id, score) values('".$college."', '".$year."', '".$id."', '".$score."') ON DUPLICATE KEY UPDATE id = '".$id."';";
    while($row = mysqli_fetch_assoc($sql)){
      $array[] = $row;
    }
}

echo json_encode($array, JSON_UNESCAPED_UNICODE);

mysql_free_result($data);
mysqli_close($conn);
?>
