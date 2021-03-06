<?php
function send_notification_android ($tokens, $data){
	$url = 'https://fcm.googleapis.com/fcm/send';
	//어떤 형태의 data/notification payload를 사용할것인지에 따라 폰에서 알림의 방식이 달라 질 수 있다.
	$msg = array(
		'title'	=> $data["title"],
		'body' 	=> $data["body"],
		'imgurl' => $data["imgurl"],
		'iconurl' => $data["iconurl"],
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

$sql = "SELECT * FROM PaceMakerFCM";

$result = mysqli_query($conn,$sql);
$androidTokens = array();

if(mysqli_num_rows($result) > 0 ){
	//DB에서 가져온 토큰을 모두 $tokens에 담아 둔다.
	while ($row = mysqli_fetch_assoc($result)) {
      $androidTokens[] = $row["token"];
	}
}
mysqli_close($conn);

//관리자페이지 폼에서 입력한 내용들을 가져와 정리한다.
$mTitle = $_POST['title'];
$mMessage = $_POST['message'];
if($_POST['chk_info'] != 'nouse'){
	$IconUrl = $_POST['imgurl']; //폼에서 image url link를 받음
	if($_POST['chk_info'] == 'bothuse'){
		$ImgUrl = $_POST['imgurl']; //폼에서 image url link를 받음
	}
}
$userUrl = $_POST['userurl'];
//알림할 내용들을 취합해서 $data에 모두 담는다.
$inputData = array("title" => $mTitle, "body" => $mMessage, "imgurl" => $ImgUrl, "iconurl" => $IconUrl, 'userurl' => $userUrl);

//마지막에 알림을 보내는 함수를 실행하고 그 결과를 화면에 출력해 준다.
$result = send_notification_android($androidTokens, $inputData);
echo 'android: '.$result.'<br>';

echo '
<br><br>
<button><a href="/PaceMakerIndex.php">돌아가기</a></button>
';

?>
