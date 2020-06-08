<?php
$Day1 = $_POST['Day1'];
$Day2 = $_POST['Day2'];
$conn = mysqli_connect("localhost", "nobles1030", "hero!0628", "nobles1030");

if (mysqli_connect_errno($conn)) {
	echo "Failed to connect to MySQL: " . mysqli_connect_error();
}
mysqli_set_charset($conn,"utf8");

$sql = "SELECT Word, meaning AS Mean, partSpeech FROM English_words WHERE Id BETWEEN ".$Day1." AND ".$Day2." ORDER BY RAND() LIMIT 20;";
$sql .= "SELECT meaning AS nA1, partSpeech AS ps FROM English_words WHERE Id NOT BETWEEN ".$Day1." AND ".$Day2." ORDER BY RAND() LIMIT 60;";
$sql .= "SELECT meaning AS nA1, partSpeech AS ps FROM English_words WHERE partSpeech='동사' ORDER BY RAND() LIMIT 3;";
$sql .= "SELECT meaning AS nA1, partSpeech AS ps FROM English_words WHERE partSpeech='명사' ORDER BY RAND() LIMIT 3;";
$sql .= "SELECT meaning AS nA1, partSpeech AS ps FROM English_words WHERE partSpeech='형용사' ORDER BY RAND() LIMIT 3;";
$sql .= "SELECT meaning AS nA1, partSpeech AS ps FROM English_words WHERE partSpeech='부사' ORDER BY RAND() LIMIT 3";

$data = array();

if (mysqli_multi_query($conn,$sql)) {
	do {
		if ($result=mysqli_store_result($conn)) {
			while ($row=mysqli_fetch_row($result)) {
				if ($row[2]!=null) {
					array_push($data, array('Word'=>$row[0],'Mean'=>$row[1],'partSpeech'=>$row[2]));
				} else {
					array_push($data, array('nA1'=>$row[0],'ps'=>$row[1]));
				}
			}
			mysqli_free_result($result);
		}
		if (mysqli_more_results($conn)) {
		}
	} while (mysqli_next_result($conn));

	header('Content-Type: application/json; charset=utf8');
	$json = json_encode(array("Word_test"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
	echo $json;
}

mysqli_close($conn);
?>
