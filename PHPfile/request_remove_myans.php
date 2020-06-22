<?php
$conn = mysqli_connect("localhost", "nobles1030", "hero!0628", "nobles1030");

$id = $_POST['userId'];
$anum = $_POST['anum'];
$qnum = $_POST['qnum'];

$sql = "DELETE FROM AnswerBoard WHERE qnum = '".$qnum."' AND anum = '".$anum."';";
$res=mysqli_query($conn,$sql);

echo json_encode(success, JSON_UNESCAPED_UNICODE);
mysqli_close($conn);
?>
