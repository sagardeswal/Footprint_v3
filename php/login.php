<?php
         
    $con=mysqli_connect("mysql6.000webhost.com", "a6596261_sagar","pr0gramm3r", "a6596261_footp") or die(mysql_error());
	
	if(isset($_POST['email']) && !empty($_POST['email']) AND isset($_POST['password']) && !empty($_POST['password'])){
    // Both fields are being posted and there not empty
	$email = mysql_escape_string($_POST['email']); // Set variable for the email
    $passwordhashcode = mysql_escape_string(md5($_POST['password'])); // Set variable for the password and convert it to an MD5 hash.

    $statement = mysqli_prepare($con,"SELECT usr_fullname, usr_email, usr_mobile, usr_passwordhashcode FROM fp_user_details WHERE usr_email= ? AND usr_passwordhashcode= ? AND active='1'") or die(mysql_error()); 
    mysqli_stmt_bind_param($statement, "ss", $email, $passwordhashcode);
	mysqli_stmt_execute($statement);
	mysqli_stmt_store_result($statement);
	mysqli_stmt_bind_result($statement, $usr_fullname, $usr_email, $usr_mobile, $usr_passwordhashcode);
    $userdetails = array();
	while(mysqli_stmt_fetch($statement)){
		$userdetails[usr_fullname] = $usr_fullname;
		$userdetails[usr_email] = $usr_email;
		$userdetails[usr_mobile] = $usr_mobile;
		$userdetails[usr_passwordhashcode] = $usr_passwordhashcode;
	}

	echo json_encode($userdetails);

	mysqli_stmt_close($statement); 

	mysqli_close($con);
}           
             
?>