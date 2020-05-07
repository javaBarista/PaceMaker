<?php
$conn = mysqli_connect("localhost", "nobles1030", "hero!0628", "nobles1030");
$day=$_POST['Day'];

$db_sql = "SELECT Number, Word, pronunciation, partSpeech, meaning FROM English_words WHERE Day = '".$day."' ORDER BY (Number) ASC;";
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
