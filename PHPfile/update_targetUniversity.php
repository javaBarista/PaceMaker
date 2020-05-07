<?php
$con = mysqli_connect("localhost", "nobles1030", "hero!0628", "nobles1030");

$id = $_POST['id'];
$college1 = $_POST['college1'];
$college2 = $_POST['college2'];
$college3 = $_POST['college3'];

<<<<<<< HEAD
=======

>>>>>>> 35fdea7995e102c5f7a7a88184d9d91bc06bbe5b
$sql = "UPDATE PaceMakerMembers SET targetUniversity1 = '".$college1."', targetUniversity2 = '".$college2."', targetUniversity3 = '".$college3."' WHERE id = '".$id."';";
if(mysqli_query($con,$sql)){
  echo json_encode(success, JSON_UNESCAPED_UNICODE);
} else {
  echo json_encode(failure, JSON_UNESCAPED_UNICODE);
}
mysqli_close($con);
?>
