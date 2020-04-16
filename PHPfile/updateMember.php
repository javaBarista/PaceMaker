<?php
$con = mysqli_connect("localhost", "nobles1030", "hero!0628", "nobles1030");

$id = $_POST['id'];
$password = $_POST['password'];
$major = $_POST['major'];
$mail = $_POST['mail'];


$sql = "UPDATE ChajAjUmMembers SET password = '".$password."', major = '".$major."', mail = '".$major."' WHERE id = '".$id."';";
mysqli_query($con, $sql);

mysqli_close($con);
?>
