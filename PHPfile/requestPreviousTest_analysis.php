<?php
$conn = mysqli_connect("localhost", "nobles1030", "hero!0628", "nobles1030");

$college = $_POST['college'];
$year = $_POST['year'];
$id = $_POST['id'];
$score = $_POST['score'];

$db_sql = "SELECT * FROM PaceMaker_PreviousTests_analysis WHERE college = '".$college."' AND year = '".$year."' AND id = '".$id."';";
$res = mysqli_query($conn, $db_sql);

if(mysqli_num_rows($res) > 0){
    $sql = "UPDATE PaceMaker_PreviousTests_analysis SET score = '".$score."' WHERE college = '".$college."' AND year = '".$year."' AND id = '".$id."';";
}
else{
    $sql = "INSERT INTO PaceMaker_PreviousTests_analysis(college, year, id, score) values('".$college."', '".$year."', '".$id."', '".$score."');";
}
mysqli_query($conn, $sql);

$select_all = "SELECT COUNT(id) AS cnt FROM PaceMaker_PreviousTests_analysis WHERE college = '".$college."' AND '".$year."';";
$all_result = mysqli_query($conn, $select_all);
$all = mysqli_fetch_array($all_result);

$select_rank="SELECT (SELECT COUNT(*) + 1 FROM PaceMaker_PreviousTests_analysis WHERE score > a.score AND college = '".$college."' AND year = '".$year."') AS rak
  FROM PaceMaker_PreviousTests_analysis AS a
  WHERE college = '".$college."' AND year = '".$year."' AND id = '".$id."'
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
