<?php 
	$con=mysqli_connect("mysql6.000webhost.com", "a6596261_sagar","pr0gramm3r", "a6596261_footp") or die(mysql_error());
 
	$longitude = $_POST["pl_longitude"];
	$latitude = $_POST["pl_latitude"];
	if(isset($longitude) && !empty($longitude) AND isset($latitude) && !empty($latitude)){
		$statement = mysqli_prepare($con, "INSERT INTO fp_location_details (loc_longitude, loc_latitude) VALUES (?,?)") or die(mysql_error());
		mysqli_stmt_bind_param($statement, "dd", $longitude, $latitude);
		mysqli_stmt_execute($statement);
		mysqli_stmt_close($statement);
	}
	mysqli_close($con);
?>