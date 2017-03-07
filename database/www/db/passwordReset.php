<?php
/**
 * Note that the salt here is randomly generated.
 * Never use a static salt or one that is not randomly generated.
 *
 * For the VAST majority of use-cases, let password_hash generate the salt randomly for you
 */
 
$link = mysqli_connect("localhost", "Michelangelo", "Leonardo", "TritonTrade");
$validation = filter_input(INPUT_GET,"validation",FILTER_SANITIZE_STRING);
$password = "Doge!";
$result = mysqli_query($link, "SELECT salt FROM users WHERE emailVerificationLink='" . $validation . "'");
$salt = "";
if ($result) {
  $row = mysqli_fetch_array($result, MYSQLI_NUM);
  $salt = $row[0];
}
else{
	echo "Connection failed.";
}

$newPass = hash("sha256", $password . $salt, false);
$result = mysqli_query($link, "UPDATE users SET password='" . $newPass . "' WHERE emailVerificationLink='" . $validation . "'");
if ($result) {
  echo "Success. Your temporary password is \"" . $password ."\". Please change it from within TritionTrade.";
}
else{
	echo "Connection failed.";
}
mysqli_query($link, "UPDATE users SET emailVerificationLink='' WHERE emailVerificationLink='" . $validation . "'");
?>