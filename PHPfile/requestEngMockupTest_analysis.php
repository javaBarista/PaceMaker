<?php
$conn = mysqli_connect("localhost", "nobles1030", "hero!0628", "nobles1030");

$round=$_POST['round'];
$id = $_POST['id'];
$score = $_POST['score'];

$db_sql = "SELECT * FROM PaceMaker_EngMockupTest_analysis WHERE round = '".$round."' AND id = '".$id."';";
$res = mysqli_query($conn, $db_sql);

if(mysqli_num_rows($res) > 0){
    $sql = "UPDATE PaceMaker_EngMockupTest_analysis SET score = '".$score."' WHERE round = '".$round."' AND id = '".$id."';";
}
else{
    $sql = "INSERT INTO PaceMaker_EngMockupTest_analysis(round, id, score) values('".$round."', '".$id."', '".$score."');";
}
mysqli_query($conn, $sql);

$select_all = "SELECT COUNT(id) AS cnt FROM PaceMaker_EngMockupTest_analysis WHERE round = '".$round."';";
$all_result = mysqli_query($conn, $select_all);
$all = mysqli_fetch_array($all_result);

$select_rank="SELECT (SELECT COUNT(*) + 1 FROM PaceMaker_EngMockupTest_analysis WHERE score > a.score) AS rak
  FROM PaceMaker_EngMockupTest_analysis AS a
  WHERE round = '".$round."' AND id = '".$id."'
  ORDER BY a.score;";
$rank_result = mysqli_query($conn, $select_rank);
$rank = mysqli_fetch_array($rank_result);

$array = array(
  'all' => $all["cnt"],
  'rank' => $rank["rak"]
);
echo json_encode($array, JSON_UNESCAPED_UNICODE);

mysqli_close($conn);
?>
