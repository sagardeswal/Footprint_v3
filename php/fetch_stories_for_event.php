<?php 
	$con=mysqli_connect("mysql6.000webhost.com", "a6596261_sagar","pr0gramm3r", "a6596261_footp") or die(mysql_error());
	if(isset($_POST['eventID']) && !empty($_POST['eventID'])) {
    // Both fields are being posted and there not empty
	$eventID = intval(mysql_escape_string($_POST['eventID'])); // Set variable for the eventID

    $statement = mysqli_prepare($con,"SELECT st_id, st_text, st_loc_id, st_pl_id, st_ev_id, st_usr_id FROM fp_story
    	WHERE st_ev_id = ?") or die(mysql_error()); 
	mysqli_stmt_bind_param($statement, "i", $eventID);
	mysqli_stmt_execute($statement);
	mysqli_stmt_store_result($statement);
	mysqli_stmt_bind_result($statement,$st_id, $st_text, $st_loc_id, $st_pl_id, $st_ev_id, $st_usr_id);
    $story = array();
    $i=0;
	while(mysqli_stmt_fetch($statement)){
		$story[$i][st_id] = $st_id;
		$story[$i][st_text] = $st_text;
		$story[$i][st_loc_id] = $st_loc_id;
		$story[$i][st_pl_id] = $st_pl_id;
		$story[$i][st_ev_id] = $st_ev_id;
		$story[$i][st_usr_id] = $st_usr_id;
		$i=$i+1;
	}

	echo json_encode($story);

	mysqli_stmt_close($statement); 

	mysqli_close($con);
}           
             
?>