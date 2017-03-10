<?php
/* https://stackoverflow.com/questions/4356289/php-random-string-generator */
function generateRandomString($length = 10) {
    $characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
    $charactersLength = strlen($characters);
    $randomString = '';
    for ($i = 0; $i < $length; $i++) {
        $randomString .= $characters[rand(0, $charactersLength - 1)];
    }
    return $randomString;
}

$link = mysqli_connect("localhost", "Michelangelo", "Leonardo", "TritonTrade");
$validation = filter_input(INPUT_GET,"validation",FILTER_SANITIZE_STRING);
$password = generateRandomString();
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