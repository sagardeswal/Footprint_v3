<?php 
	$con=mysqli_connect("mysql6.000webhost.com", "a6596261_sagar","pr0gramm3r", "a6596261_footp") or die(mysql_error());
	if(isset($_POST['userID']) && !empty($_POST['userID'])) {
    // Both fields are being posted and there not empty
	$userID = intval(mysql_escape_string($_POST['userID'])); // Set variable for the userID

    $statement = mysqli_prepare($con,"SELECT st_id, st_text, st_loc_id, st_pl_id FROM fp_story
    	WHERE st_usr_id = ?") or die(mysql_error()); 
	mysqli_stmt_bind_param($statement, "i", $userID);
	mysqli_stmt_execute($statement);
	mysqli_stmt_store_result($statement);
	mysqli_stmt_bind_result($statement,$st_id, $st_text, $st_loc_id, $st_pl_id);
    $story = array();
    $i=0;
	while(mysqli_stmt_fetch($statement)){
		$story[$i][st_id] = $st_id;
		$story[$i][st_text] = $st_text;
		$story[$i][st_loc_id] = $st_loc_id;
		$story[$i][st_pl_id] = $st_pl_id;
		$i=$i+1;
	}

	echo json_encode($story);

	mysqli_stmt_close($statement); 

	mysqli_close($con);
}           
             
?>