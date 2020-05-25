<?php
$conn = mysqli_connect("localhost", "nobles1030", "hero!0628", "nobles1030");

$subject = $_POST['subject'];

$db_sql = "SELECT address FROM math_formula WHERE subject = '".$subject."';";
$result = mysqli_query($conn, $db_sql);

$data = mysqli_fetch_array($result);

$array = array(
  'address' => $data["address"],
);

if(mysqli_num_rows($result) > 0){
    echo json_encode(success, JSON_UNESCAPED_UNICODE);
    echo json_encode($array, JSON_UNESCAPED_UNICODE);
}
else{
    echo json_encode(failure, JSON_UNESCAPED_UNICODE);
}

mysqli_close($conn);
?>
