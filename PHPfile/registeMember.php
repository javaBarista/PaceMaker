<?php
$conn = mysqli_connect("localhost", "nobles1030", "hero!0628", "nobles1030");

$id = $_POST['id'];
$password = $_POST['password'];
$name = $_POST['name'];
$mail = $_POST['mail'];
$college1 = $_POST['college1'];
$college2 = $_POST['college2'];
$college3 = $_POST['college3'];

$db_sql = "INSERT INTO PaceMakerMembers(id, password, userName, userEmail, targetUniversity1, targetUniversity2, targetUniversity3) values('".$id."', '".$password."', '".$name."', '".$mail."', '".$college1."', '".$college2."', '".$college3."') ON DUPLICATE KEY UPDATE id = '".$id."';";
mysqli_query($conn, $db_sql);

mysqli_close($conn);
?>
