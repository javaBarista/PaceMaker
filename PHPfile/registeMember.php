<?php
$conn = mysqli_connect("localhost", "nobles1030", "hero!0628", "nobles1030");

$id = $_POST['id'];
$password = $_POST['password'];
$name = $_POST['name'];
$mail = $_POST['mail'];
$college1 = $_POST['college1'];
$college2 = $_POST['college2'];
$college3 = $_POST['college3'];

$db_sql = "INSERT INTO PaceMakerMembers(id, password, userName, userEmail) values('".$id."', HEX(AES_ENCRYPT('".$password."', 'pwkey')), '".$name."', '".$mail."') ON DUPLICATE KEY UPDATE id = '".$id."';";
mysqli_query($conn, $db_sql);

$db_sql = "INSERT INTO PaceMaker_Target_University(userId, num, university_name) values('".$id."', 1, '".$college1."');";
mysqli_query($conn, $db_sql);
$db_sql = "INSERT INTO PaceMaker_Target_University(userId, num, university_name) values('".$id."', 2, '".$college2."');";
mysqli_query($conn, $db_sql);
$db_sql = "INSERT INTO PaceMaker_Target_University(userId, num, university_name) values('".$id."', 3, '".$college3."');";
mysqli_query($conn, $db_sql);

mysqli_close($conn);
?>
