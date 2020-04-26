<?php
$conn = mysqli_connect("localhost", "nobles1030", "hero!0628", "nobles1030");
$day=$_POST['Day'];
$number=$_POST['Number'];
$word=$_POST['Word'];
$pronunciation=$_POST['pronunciation'];
$partSpeech=$_POST['$partSpeech'];
$meaning=$_POST['meaning'];

$db_sql = "SELECT * FROM English_words WHERE Day='".$day."' AND Number='".$number."' AND Word='".$word."' AND meaning='".$meaning."' AND pronunciation='".$pronunciation"' AND partSpeech='".$partSpeech"'";
$result = mysqli_query($conn, $db_sql);

$data = mysqli_fetch_array($result);

$array = array(
  'day' => $data["Day"],
  'number' => $data["Number"],
  'word' => $data["Word"],
  'pronunciation' => $data["pronunciation"],
  'partSpeech' => $data['partSpeech'],
  'meaning' => $data["meaning"]
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
