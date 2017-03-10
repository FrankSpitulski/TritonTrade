<?php
$validation = filter_input(INPUT_GET,"validation",FILTER_SANITIZE_STRING);
$link = mysqli_connect("localhost", "Michelangelo", "Leonardo", "TritonTrade");
$result = mysqli_query($link, "UPDATE users SET verified=1,emailVerificationLink='' WHERE emailVerificationLink = '" . $validation . "';");
if ($result) {
  echo "Validation success!";
}
else{
	echo "Validation failed.";
}

?>