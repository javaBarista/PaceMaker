<?php
$conn = mysqli_connect("localhost", "nobles1030", "hero!0628", "nobles1030");
$start=$_POST['start'];
$end=$_POST['end'];

$db_sql = "SELECT * FROM English_words WHERE Id BETWEEN '".$start."' AND '".$end."' ORDER BY (Id) ASC;";
$data = mysqli_query($conn, $db_sql);

if(mysqli_num_rows($data) > 0 ){
  while($row = mysqli_fetch_assoc($data)){
    $array[] = $row;
  }
}

echo json_encode($array, JSON_UNESCAPED_UNICODE);
mysql_free_result($data);

mysqli_close($conn);
?>
