<?php 
	$con=mysqli_connect("mysql6.000webhost.com", "a6596261_sagar","pr0gramm3r", "a6596261_footp") or die(mysql_error());
 
	$ev_title = $_POST["ev_title"];
	$ev_description = $_POST["ev_description"];
	$ev_repeat_weekly = $_POST["ev_repeat_weekly"];
	$ev_start_date = $_POST["ev_start_date"];
	$ev_end_date = $_POST["ev_end_date"];
	$ev_start_time_hour = $_POST["ev_start_time_hour"];
	$ev_start_time_minute = $_POST["ev_start_time_minute"];
	$ev_end_time_hour = $_POST["ev_end_time_hour"];
	$ev_end_time_minute = $_POST["ev_end_time_minute"];
	$ev_longitude = $_POST["ev_longitude"];
	$ev_address = $_POST["ev_address"];
	$ev_latitude = $_POST["ev_latitude"];

	if(isset($ev_title) && !empty($ev_title) AND 
		isset($ev_description) && !empty($ev_description) AND 
		isset($ev_repeat_weekly) && !empty($ev_repeat_weekly) AND 
		isset($ev_start_date) && !empty($ev_start_date) AND 
		isset($ev_end_date) && !empty($ev_end_date) AND 
		isset($ev_start_time_hour) && !empty($ev_start_time_hour) AND 
		isset($ev_start_time_minute) && !empty($ev_start_time_minute) AND 
		isset($ev_end_time_hour) && !empty($ev_end_time_hour) AND 
		isset($ev_end_time_minute) && !empty($ev_end_time_minute) AND
		isset($ev_longitude) && !empty($ev_longitude) AND
		isset($ev_address) && !empty($ev_address) AND
		isset($ev_latitude) && !empty($ev_latitude)){

		$statement = mysqli_prepare($con, "INSERT INTO pl_location_details (loc_longitude, loc_latitude) VALUES (?,?)") or die(mysql_error());
		mysqli_stmt_bind_param($statement, "ss", $ev_longitude, $ev_latitude);
		mysqli_stmt_execute($statement);
		$loc_id = $statement->insert_id;
		mysqli_stmt_close($statement);

		$statement = mysqli_prepare($con, "INSERT INTO fp_event_details (ev_loc_id, ev_title, ev_description,
			ev_repeat_weekly,
			ev_start_date, ev_end_date,
			ev_start_time_hour, ev_start_time_minute,
			ev_end_time_hour, ev_end_time_minute,
			ev_address
			) VALUES (?,?,?,?,?,?,?,?,?,?,?)") or die(mysql_error());
		mysqli_stmt_bind_param($statement, "issssssssss", $loc_id, $ev_title, $ev_description,
			$ev_repeat_weekly, 
			$ev_start_date, $ev_end_date,
			$ev_start_time_hour, $ev_start_time_minute,
			$ev_end_time_hour, $ev_end_time_minute, $ev_address
			);
		mysqli_stmt_execute($statement);
		$ev_id = $statement->insert_id;
		mysqli_stmt_close($statement);
	}
	mysqli_close($con);
?>