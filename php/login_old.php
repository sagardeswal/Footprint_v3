<?php
         
    $con=mysqli_connect("mysql6.000webhost.com", "a6596261_sagar","pr0gramm3r", "a6596261_footp") or die(mysql_error());
	
	if(isset($_POST['usr_username']) && !empty($_POST['usr_username']) AND isset($_POST['usr_password']) && !empty($_POST['usr_password'])){
    // Both fields are being posted and there not empty
	$username = mysql_escape_string($_POST['usr_username']); // Set variable for the username
    $passwordhashcode = mysql_escape_string(md5($_POST['usr_password'])); // Set variable for the password and convert it to an MD5 hash.

    $statement = mysqli_prepare($con,"SELECT usr_username, usr_fullname, usr_email, usr_mobile, usr_passwordhashcode, active FROM fp_user_details WHERE usr_username= ? AND usr_passwordhashcode= ? AND active='1'") or die(mysql_error()); 
    mysqli_stmt_bind_param($statement, "ss", $username, $passwordhashcode);
	mysqli_stmt_execute($statement);
	mysqli_stmt_store_result($statement);
	mysqli_stmt_bind_result($statement, $usr_username, $usr_fullname, $usr_email, $usr_mobile, $usr_passwordhashcode, $active);
    $userdetails = array();
	while(mysqli_stmt_fetch($statement)){
		$userdetails[usr_username] = $usr_username;
		$userdetails[usr_fullname] = $usr_fullname;
		$userdetails[usr_email] = $usr_email;
		$userdetails[usr_mobile] = $usr_mobile;
		$userdetails[usr_passwordhashcode] = $usr_passwordhashcode;
		$userdetails[active] = $active;
	}

	echo json_encode($userdetails);

	mysqli_stmt_close($statement); 

	mysqli_close($con);
}           
             
?>