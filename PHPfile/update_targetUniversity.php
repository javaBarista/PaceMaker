<?php
$con = mysqli_connect("localhost", "nobles1030", "hero!0628", "nobles1030");

$id = $_POST['id'];
$college1 = $_POST['college1'];
$college2 = $_POST['college2'];
$college3 = $_POST['college3'];

$sql1 = "UPDATE PaceMaker_Target_University SET university_name = '".$college1."' WHERE userId = '".$id."' AND num = 1;";
$sql2 = "UPDATE PaceMaker_Target_University SET university_name = '".$college2."' WHERE userId = '".$id."' AND num = 2;";
$sql3 = "UPDATE PaceMaker_Target_University SET university_name = '".$college3."' WHERE userId = '".$id."' AND num = 3;";

if(mysqli_query($con,$sql1) && mysqli_query($con,$sql2) && mysqli_query($con,$sql3)){
  echo json_encode(success, JSON_UNESCAPED_UNICODE);
} else {
  echo json_encode(failure, JSON_UNESCAPED_UNICODE);
}
mysqli_close($con);
?>
