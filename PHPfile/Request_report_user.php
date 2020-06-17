<?php
$conn = mysqli_connect("localhost", "nobles1030", "hero!0628", "nobles1030");

$id = $_POST['userId'];
$body = $_POST['body'];

$db_sql = "INSERT INTO PaceMaker_Report(id, body) values('".$id."', '".$body."');";
$res1=mysqli_query($conn, $db_sql);

$sql = "SELECT report FROM PaceMakerMembers WHERE id = '".$id."';";
$result = mysqli_query($conn,$sql);
$data = mysqli_fetch_array($result);
$cnt = (int)$data["report"] + 1;

$sql_user = "UPDATE PaceMakerMembers SET report = '".$cnt."' WHERE id = '".$id."';";
$res2=mysqli_query($conn, $sql_user);

mysqli_close($conn);
?>
