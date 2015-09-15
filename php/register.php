<?php 
	$con=mysqli_connect("mysql6.000webhost.com", "a6596261_sagar","pr0gramm3r", "a6596261_footp") or die(mysql_error());
 
	$fullname = $_POST["usr_fullname"];
	$mobile = $_POST["usr_mobile"];
	$email = $_POST["usr_email"];
	$username = $_POST["usr_username"];
	$passwordhashcode = (md5($_POST["usr_passwordhashcode"]));
	$msg="";
	if(isset($username) && !empty($username) AND isset($email) && !empty($email) AND isset($fullname) && !empty($fullname) AND isset($mobile) && !empty($mobile))
	{
 		if(!eregi("^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,3})$", $email)){
    		// Return Error - Invalid Email
		}else{
    		// Return Success 
    		$statement = mysqli_prepare($con,"SELECT usr_username FROM fp_user_details WHERE usr_username= ? OR usr_email= ?") or die(mysql_error()); 
		    mysqli_stmt_bind_param($statement, "ss", $username, $email);
			mysqli_stmt_execute($statement);
			mysqli_stmt_store_result($statement);
			mysqli_stmt_bind_result($statement, $check);
			if(mysqli_stmt_fetch($statement)){
				echo json_encode($check);
				mysqli_stmt_close($statement);
			}else{
				mysqli_stmt_close($statement);
				$hash = md5( rand(0,1000) ); // Generate random 32 character hash and assign it to a local variable.
				$statement = mysqli_prepare($con, "INSERT INTO fp_user_details (usr_fullname, usr_mobile, usr_email, usr_username, usr_passwordhashcode, hash) VALUES (?,?,?,?,?,?)") or die(mysql_error());
				mysqli_stmt_bind_param($statement, "ssssss", $fullname, $mobile, $email, $username, $passwordhashcode, $hash);
				mysqli_stmt_execute($statement);
				echo json_encode($check);
				mysqli_stmt_close($statement);

				// $to      = $email; // Send email to our user
				// $subject = 'Signup | Verification'; // Give the email a subject 
				// $message = '
				 
				// Thanks for signing up!
				// Your account has been created, you can login with the following credentials after you have activated your account by pressing the url below.
				 
				// ------------------------
				// Username: '.$name.'
				// ------------------------
				 
				// Please click this link to activate your account:
				// http://http://www.footprint.comuv.com/verify.php?email='.$email.'&hash='.$hash.'
				 
				// '; // Our message above including the link
				                     
				// $headers = 'From:noreply@footprint.comuv.com/' . "\r\n"; // Set from headers
				// mail($to, $subject, $message, $headers); // Send our email

				// echo "success";
			}
		}
	}
 
	mysqli_close($con);
?>