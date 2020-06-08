<?php
$start = $_POST['start'];
$end = $_POST['end'];
$conn = mysqli_connect("localhost", "nobles1030", "hero!0628", "nobles1030");

if (mysqli_connect_errno($conn))
{
   echo "Failed to connect to MySQL: " . mysqli_connect_error();
}

mysqli_set_charset($conn,"utf8");


$query = "SELECT Word, meaning, partSpeech FROM English_words WHERE (Id BETWEEN '".$start."' AND '".$end."') ORDER BY RAND() LIMIT 20;";
$query .= "SELECT meaning, partSpeech FROM English_words WHERE (Id NOT BETWEEN '".$start."' AND '".$end."') AND partSpeech='동사' ORDER BY RAND() LIMIT 3;";
$query .= "SELECT meaning, partSpeech FROM English_words WHERE (Id NOT BETWEEN '".$start."' AND '".$end."') AND partSpeech='명사' ORDER BY RAND() LIMIT 3;";
$query .= "SELECT meaning, partSpeech FROM English_words WHERE (Id NOT BETWEEN '".$start."' AND '".$end."') AND partSpeech='형용사' ORDER BY RAND() LIMIT 3;";
$query .= "SELECT meaning, partSpeech FROM English_words WHERE (Id NOT BETWEEN '".$start."' AND '".$end."') AND partSpeech='부사' ORDER BY RAND() LIMIT 3";


$res = mysqli_multi_query($conn,$query);
$data = array();

if($res) {
	do {
		if ($result=mysqli_store_result($conn)) {
			while ($row=mysqli_fetch_row($result)) {
				if ($row[2]!=null) {
					array_push($data, array('Word'=>$row[0],'meaning'=>$row[1],'partSpeech'=>$row[2]));
				} else {
					array_push($data, array('meaning'=>$row[0],'partSpeech'=>$row[1]));
				}
			}
			mysqli_free_result($result);
		}
		if (mysqli_more_results($conn)) {
		}
	} while (mysqli_next_result($conn));

	header('Content-Type: application/json; charset=utf8');
	$json = json_encode(array("Test"=>$data), JSON_UNESCAPED_UNICODE);
	echo $json;
}

mysqli_close($conn);
?>
