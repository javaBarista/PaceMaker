<?php
$con = mysqli_connect("localhost", "nobles1030", "hero!0628", "nobles1030");

$id = $_POST['id'];
$password = $_POST['password'];
$name = $_POST['name'];
$mail = $_POST['mail'];

$sql = "UPDATE PaceMakerMembers SET password = HEX(AES_ENCRYPT('".$password."', 'pwkey')), userName = '".$name."', userEmail = '".$mail."' WHERE id = '".$id."';";

if(mysqli_query($con,$sql)){
  echo json_encode(success, JSON_UNESCAPED_UNICODE);
} else {
  echo json_encode(failure, JSON_UNESCAPED_UNICODE);
}
mysqli_close($con);
?>
