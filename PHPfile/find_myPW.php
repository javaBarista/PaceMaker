<?php
function GenerateString($length){
   $characters  = "0123456789";
   $characters .= "abcdefghijklmnopqrstuvwxyz";
   $characters .= "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
   $characters .= "_";

   $string_generated = "";

   $nmr_loops = $length;
   while ($nmr_loops--){
       $string_generated .= $characters[mt_rand(0, strlen($characters) - 1)];
   }

   return $string_generated;
}

$conn = mysqli_connect("localhost", "nobles1030", "hero!0628", "nobles1030");

$id = $_POST['id'];

$sql = "SELECT * FROM PaceMakerMembers WHERE id = '".$id."';";
$result = mysqli_query($conn,$sql);

if(mysqli_num_rows($result) > 0){
    $tmpPw = GenerateString(mt_rand(8, 15));

    $sql_user = "UPDATE PaceMakerMembers SET password = HEX(AES_ENCRYPT('".$tmpPw."', 'pwkey')) WHERE id = '".$id."';";
    mysqli_query($conn, $sql_user);
    echo json_encode($tmpPw, JSON_UNESCAPED_UNICODE);
}
else{
    echo json_encode(failure, JSON_UNESCAPED_UNICODE);
}

mysqli_close($conn);
?>
