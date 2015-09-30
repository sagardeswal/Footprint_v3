<?php 
	$con=mysqli_connect("mysql6.000webhost.com", "a6596261_sagar","pr0gramm3r", "a6596261_footp") or die(mysql_error());
	$statement="";
	if(isset($_POST['ev_id']) && !empty($_POST['ev_id'])) {
		$ev_id = ($_POST['ev_id']);
		if($ev_id=="-1"){
			
			$statement = mysqli_prepare($con,"SELECT e.ev_id, e.ev_pl_id, e.ev_loc_id,
				e.ev_title, e.ev_description, 
				e.ev_repeat_weekly,  e.ev_start_date, e.ev_end_date, 
			 	e.ev_start_time_hour, e.ev_start_time_minute,
			 	e.ev_end_time_hour, e.ev_end_time_minute,
			 	l.loc_longitude, l.loc_latitude,
			 	e.ev_address
			 FROM fp_event_details e, pl_location_details l 
			 WHERE e.ev_loc_id = l.loc_id ") 
			or die(mysql_error()); 
		}
		else{
				$eventID = intval($ev_id);

				$statement = mysqli_prepare($con,"SELECT e.ev_id, e.ev_pl_id, e.ev_loc_id,
				e.ev_title, e.ev_description, 
				e.ev_repeat_weekly,  e.ev_start_date, e.ev_end_date, 
			 	e.ev_start_time_hour, e.ev_start_time_minute,
			 	e.ev_end_time_hour, e.ev_end_time_minute,
			 	l.loc_longitude, l.loc_latitude,
			 	e.ev_address
			 FROM fp_event_details e, pl_location_details l 
			 WHERE e.ev_loc_id = l.loc_id AND e.ev_id = ?") 
			or die(mysql_error()); 
			mysqli_stmt_bind_param($statement, "i", $ev_id);
		}

			mysqli_stmt_execute($statement);
			mysqli_stmt_store_result($statement);
			mysqli_stmt_bind_result($statement,$ev_id, $ev_pl_id, $ev_loc_id,
			$ev_title, $ev_description, 
			$ev_repeat_weekly, $ev_start_date, $ev_end_date,
			$ev_start_time_hour, $ev_start_time_minute,
			$ev_end_time_hour, $ev_end_time_minute,
			$loc_longitude, $loc_latitude, $ev_address);
	    	$eventdetails = array();
	   		$i=0;
			while(mysqli_stmt_fetch($statement)){
				$eventdetails[$i][ev_id] = $ev_id;
				$eventdetails[$i][ev_pl_id] = $ev_pl_id;
				$eventdetails[$i][ev_loc_id] = $ev_loc_id;
				$eventdetails[$i][ev_title] = $ev_title;
				$eventdetails[$i][ev_description] = $ev_description;
				$eventdetails[$i][ev_repeat_weekly] = $ev_repeat_weekly;
				$eventdetails[$i][ev_start_date] = $ev_start_date;
				$eventdetails[$i][ev_end_date] = $ev_end_date;
				$eventdetails[$i][ev_start_time_hour] = $ev_start_time_hour;
				$eventdetails[$i][ev_start_time_minute] = $ev_start_time_minute;
				$eventdetails[$i][ev_end_time_hour] = $ev_end_time_hour;
				$eventdetails[$i][ev_end_time_minute] = $ev_end_time_minute;
				$eventdetails[$i][loc_longitude] = $loc_longitude;
				$eventdetails[$i][loc_latitude] = $loc_latitude;
				$eventdetails[$i][ev_address] = $ev_address;
				$i=$i+1;
			}

		echo json_encode($eventdetails);

		mysqli_stmt_close($statement); 

		mysqli_close($con);   
}

?>