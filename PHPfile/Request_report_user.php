<?php
$conn = mysqli_connect("localhost", "nobles1030", "hero!0628", "nobles1030");

$qnum = $_POST['qnum'];
$anum = $_POST['anum'];
$id = $_POST['userId'];
$qbody = $_POST['qBody'];
$rbody = $_POST['rBody'];
$upload = $_POST['upload'];

$db_sql = "INSERT INTO PaceMaker_reports(qnum, userId, qBody, rBody, uploadDate) values('".$qnum."', '".$id."', '".$qbody."', '".$rbody."', '".$upload."');";
$res1=mysqli_query($conn, $db_sql);

$ddsql = "DELETE FROM AnswerBoard WHERE qnum = '".$qnum."' AND anum = '".$anum."';";
$ddres=mysqli_query($conn,$ddsql);

$sql = "SELECT report FROM PaceMakerMembers WHERE id = '".$id."';";
$result = mysqli_query($conn,$sql);
$data = mysqli_fetch_array($result);
$cnt = (int)$data["report"] + 1;

$sql_user = "UPDATE PaceMakerMembers SET report = '".$cnt."' WHERE id = '".$id."';";
$res2=mysqli_query($conn, $sql_user);

mysqli_close($conn);
?>
