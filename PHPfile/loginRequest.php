<?php
$conn = mysqli_connect("localhost", "nobles1030", "hero!0628", "nobles1030");

$id = $_POST['id'];
$password = $_POST['password'];

$db_sql = "SELECT * FROM PaceMakerMembers WHERE id='".$id."' AND AES_DECRYPT(UNHEX(password), 'pwkey')='".$password."'";
$result = mysqli_query($conn, $db_sql);

$uni_sql = "SELECT num, university_name FROM PaceMaker_Target_University WHERE userId = '".$id."'";
$uni_res = mysqli_query($conn, $uni_sql);

$today = date("Y/m/d");

$sql = "SELECT * FROM Schedule WHERE todo = '시험' AND endDate = (SELECT MIN(endDate) FROM Schedule WHERE todo = '시험' AND endDate > '".$today."');";
$res = mysqli_query($conn, $sql);

$data = mysqli_fetch_array($result);
$next = mysqli_fetch_array($res);

$array = array(
  'name' => $data["userName"],
  'mail' => $data["userEmail"],
  'college' => $next["college"],
  'endDate' => $next["endDate"]
);

if(mysqli_num_rows($uni_res) > 0 ){
  while($row = mysqli_fetch_assoc($uni_res)){
    $target[] = $row;
  }
}


if(mysqli_num_rows($result) > 0){
  echo json_encode(success, JSON_UNESCAPED_UNICODE);
  echo json_encode($array, JSON_UNESCAPED_UNICODE);
  echo json_encode($target, JSON_UNESCAPED_UNICODE);
}
else{
  echo json_encode(failure, JSON_UNESCAPED_UNICODE);
}

mysqli_close($conn);
?>
