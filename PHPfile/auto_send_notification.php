<?php
function send_notification_android ($tokens, $data){
	$url = 'https://fcm.googleapis.com/fcm/send';
	//어떤 형태의 data/notification payload를 사용할것인지에 따라 폰에서 알림의 방식이 달라 질 수 있다.
	$msg = array(
		'title'	=> $data["title"],
		'body' 	=> $data["body"],
		'imgurl' => $data["imgurl"],
		'request_code' => $data["code"],
	);

	$fields = array(
		'registration_ids'		=> $tokens,
		'data'	=> $msg
	);

	$headers = array(
		'Authorization:key =' . "AAAA6__Vnsg:APA91bHwIkjeMsfY5dZyYKy11P_qUC0ISpFHCFFh9tzQDVzJ_YxHNNE5OZGs_YNpitM4a8590XPolMtNdcT88p8LXDcs2fyeb3PWGo7u6zcGgUA7-AmtfikziAu4rmnqewbxajs3Z6kc",
		'Content-Type: application/json'
	);

	$ch = curl_init();
	curl_setopt($ch, CURLOPT_URL, ($url));
	curl_setopt($ch, CURLOPT_POST, true);
	curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
	curl_setopt ($ch, CURLOPT_SSL_VERIFYHOST, 0);
	curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
	curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));
	$result = curl_exec($ch);
	if ($result === FALSE) {
		die('Curl failed: ' . curl_error($ch));
	}
	curl_close($ch);
	return $result;
}

//데이터베이스 저장된 토큰을 array변수에 모두 담는다.
$conn = mysqli_connect("localhost", "nobles1030", "hero!0628", "nobles1030");

$sql = "SELECT * FROM PaceMakerFCM;";

$result = mysqli_query($conn,$sql);
$androidTokens = array();

if(mysqli_num_rows($result) > 0 ){
	//DB에서 가져온 토큰을 모두 $tokens에 담아 둔다.	while ($row = mysqli_fetch_assoc($result)) {
	while ($row = mysqli_fetch_assoc($result)) {
      $androidTokens[] = $row["token"];
	}
}

$today = date("Y/m/d");
$msql = "SELECT * FROM Schedule WHERE startDate = '".$today."' OR endDate = '".$today."';";
$data = mysqli_query($conn, $msql);

$title_array = array();
$message_array = array();
if(mysqli_num_rows($data) > 0 ){
  while ($row = mysqli_fetch_assoc($data)) {
      $title_array[] = $row["college"];
      $message_array[] = '오늘은 '.$row["college"].' 의 '.$row["todo"].'일 입니다.';
	}
}

for($i = 0; $i < count($title_array); $i++){
  $mTitle = $title_array[$i];
  $mMessage = $message_array[$i];
  $img_sql = "SELECT Address FROM College_Image WHERE College_name = '".$mTitle."';";
  $temp = mysqli_query($conn, $img_sql);
  $sds = mysqli_fetch_array($temp);
  $ImgUrl = $sds['Address'];

  $inputData = array("title" => $mTitle, "body" => $mMessage, "imgurl" => $ImgUrl, "code" => $i);

  //마지막에 알림을 보내는 함수를 실행하고 그 결과를 화면에 출력해 준다.
  $result = send_notification_android($androidTokens, $inputData);
}
mysqli_close($conn);
?>
