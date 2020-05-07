<?php
$con = mysqli_connect("localhost", "nobles1030", "hero!0628", "nobles1030");

$id = $_POST['id'];
$password = $_POST['password'];
$name = $_POST['name'];
$mail = $_POST['mail'];

<<<<<<< HEAD
$sql = "UPDATE PaceMakerMembers SET password = '".$password."', userName = '".$name."', userEmail = '".$mail."' WHERE id = '".$id."';";

=======

$sql = "UPDATE PaceMakerMembers SET password = '".$password."', userName = '".$name."', userEmail = '".$mail."' WHERE id = '".$id."';";
>>>>>>> 35fdea7995e102c5f7a7a88184d9d91bc06bbe5b
if(mysqli_query($con,$sql)){
  echo json_encode(success, JSON_UNESCAPED_UNICODE);
} else {
  echo json_encode(failure, JSON_UNESCAPED_UNICODE);
}
mysqli_close($con);
?>
