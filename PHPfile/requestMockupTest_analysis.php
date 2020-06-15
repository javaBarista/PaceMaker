<?php
$conn = mysqli_connect("localhost", "nobles1030", "hero!0628", "nobles1030");

$subject=$_POST['year'];
$round=$_POST['college'];
$id = $_POST['id'];
$score = $_POST['score'];

$db_sql = "SELECT * FROM PaceMaker_MocupTests_analysis WHERE subject = '".$subject."' AND round = '".$round."' AND id = '".$id."';";
$res = mysqli_query($conn, $db_sql);

if(mysqli_num_rows($res) > 0){
    $sql = "UPDATE PaceMaker_MocupTests_analysis SET score = '".$score."' WHERE subject = '".$subject."' AND round = '".$round."' AND id = '".$id."';";
}
else{
    $sql = "INSERT INTO PaceMaker_MocupTests_analysis(subject, round, id, score) values('".$subject."', '".$round."', '".$id."', '".$score."');";
}
mysqli_query($conn, $sql);

$select_all = "SELECT COUNT(id) AS cnt FROM PaceMaker_MocupTests_analysis WHERE subject = '".$subject."' AND round = '".$round."';";
$all_result = mysqli_query($conn, $select_all);
$all = mysqli_fetch_array($all_result);

$select_rank="SELECT (SELECT COUNT(*) + 1 FROM PaceMaker_MocupTests_analysis WHERE score > a.score AND round = '".$round."' AND subject = '".$subject."') AS rak
  FROM PaceMaker_MocupTests_analysis AS a
  WHERE round = '".$round."' AND subject = '".$subject."' AND id = '".$id."'
  ORDER BY a.score;";
$rank_result = mysqli_query($conn, $select_rank);
$rank = mysqli_fetch_array($rank_result);

$rank_college1="SELECT university_name, count(*) as col_cnt FROM PaceMaker_MocupTests_analysis, PaceMaker_Target_University WHERE id = userID AND subject = '".$subject."' AND round = '".$round."' AND university_name in (SELECT university_name FROM PaceMaker_Target_University WHERE num = 1 AND userId = '".$id."');";
$college1_result = mysqli_query($conn, $rank_college1);
$col1_all = mysqli_fetch_array($college1_result);

$sel_rank_college1="SELECT (SELECT COUNT(*) + 1 FROM (SELECT * FROM PaceMaker_MocupTests_analysis, PaceMaker_Target_University WHERE id = userID AND subject = '".$subject."' AND round = '".$round."' AND university_name in (SELECT university_name FROM PaceMaker_Target_University WHERE num = 1 AND userId = '".$id."')) as b WHERE b.score > a.score AND subject = '".$subject."' AND round = '".$round."') as rak
  FROM (SELECT * FROM PaceMaker_MocupTests_analysis, PaceMaker_Target_University WHERE id = userID AND subject = '".$subject."' AND round = '".$round."' AND university_name in (SELECT university_name FROM PaceMaker_Target_University WHERE num = 1 AND userId = '".$id."')) AS a
  WHERE round = '".$round."' AND subject = '".$subject."' AND id = '".$id."'
  ORDER BY a.score;";
$sel_rank_college1_result = mysqli_query($conn, $sel_rank_college1);
$col1_rank = mysqli_fetch_array($sel_rank_college1_result);

$rank_college2="SELECT university_name, count(*) as col_cnt FROM PaceMaker_MocupTests_analysis, PaceMaker_Target_University WHERE id = userID AND subject = '".$subject."' AND round = '".$round."' AND university_name in (SELECT university_name FROM PaceMaker_Target_University WHERE num = 2 AND userId = '".$id."');";
$college2_result = mysqli_query($conn, $rank_college2);
$col2_all = mysqli_fetch_array($college2_result);

$sel_rank_college2="SELECT (SELECT COUNT(*) + 1 FROM (SELECT * FROM PaceMaker_MocupTests_analysis, PaceMaker_Target_University WHERE id = userID AND subject = '".$subject."' AND round = '".$round."' AND university_name in (SELECT university_name FROM PaceMaker_Target_University WHERE num = 2 AND userId = '".$id."')) as b WHERE b.score > a.score AND subject = '".$subject."' AND round = '".$round."') as rak
  FROM (SELECT * FROM PaceMaker_MocupTests_analysis, PaceMaker_Target_University WHERE id = userID AND subject = '".$subject."' AND round = '".$round."' AND university_name in (SELECT university_name FROM PaceMaker_Target_University WHERE num = 2 AND userId = '".$id."')) AS a
  WHERE round = '".$round."' AND subject = '".$subject."' AND id = '".$id."'
  ORDER BY a.score;";
$sel_rank_college2_result = mysqli_query($conn, $sel_rank_college2);
$col2_rank = mysqli_fetch_array($sel_rank_college2_result);

$rank_college3="SELECT university_name, count(*) as col_cnt FROM PaceMaker_MocupTests_analysis, PaceMaker_Target_University WHERE id = userID AND subject = '".$subject."' AND round = '".$round."' AND university_name in (SELECT university_name FROM PaceMaker_Target_University WHERE num = 3 AND userId = '".$id."');";
$college3_result = mysqli_query($conn, $rank_college3);
$col3_all = mysqli_fetch_array($college3_result);

$sel_rank_college3="SELECT (SELECT COUNT(*) + 1 FROM (SELECT * FROM PaceMaker_MocupTests_analysis, PaceMaker_Target_University WHERE id = userID AND subject = '".$subject."' AND round = '".$round."' AND university_name in (SELECT university_name FROM PaceMaker_Target_University WHERE num = 3 AND userId = '".$id."')) as b WHERE b.score > a.score AND subject = '".$subject."' AND round = '".$round."') as rak
  FROM (SELECT * FROM PaceMaker_MocupTests_analysis, PaceMaker_Target_University WHERE id = userID AND subject = '".$subject."' AND round = '".$round."' AND university_name in (SELECT university_name FROM PaceMaker_Target_University WHERE num = 3 AND userId = '".$id."')) AS a
  WHERE round = '".$round."' AND subject = '".$subject."' AND id = '".$id."'
  ORDER BY a.score;";
$sel_rank_college3_result = mysqli_query($conn, $sel_rank_college3);
$col3_rank = mysqli_fetch_array($sel_rank_college3_result);

$array = array(
  'all' => $all["cnt"],
  'rank' => $rank["rak"],
  'col1_name' => $col1_all["university_name"],
  'col1_all' => $col1_all["col_cnt"],
  'col1_rank' => $col1_rank["rak"],
  'col2_name' => $col2_all["university_name"],
  'col2_all' => $col2_all["col_cnt"],
  'col2_rank' => $col2_rank["rak"],
  'col3_name' => $col3_all["university_name"],
  'col3_all' => $col3_all["col_cnt"],
  'col3_rank' => $col3_rank["rak"],
);
echo json_encode($array, JSON_UNESCAPED_UNICODE);

mysqli_close($conn);
?>
