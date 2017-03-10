<?php
$link = mysqli_connect("localhost", "Michelangelo", "Leonardo", "TritonTrade");

$result = mysqli_query($link, "SELECT COUNT(*) FROM posts");
if ($result) {
  $row = mysqli_fetch_array($result, MYSQLI_NUM);
  echo $row[0];
}
else{
	echo "Connection failed.";
}

mysqli_close($link);
?>