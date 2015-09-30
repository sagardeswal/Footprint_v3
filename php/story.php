<?php 
	$con=mysqli_connect("mysql6.000webhost.com", "a6596261_sagar","pr0gramm3r", "a6596261_footp") or die(mysql_error());
 
	$st_usr_id = intval($_POST["st_usr_id"]);
	$st_pl_id = intval($_POST["st_pl_id"]);
	$st_loc_id = intval($_POST["st_loc_id"]);
	$st_ev_id = intval($_POST["st_ev_id"]);
	$st_text = $_POST["st_text"];
	
	if(isset($st_usr_id) && !empty($st_usr_id) AND isset($st_pl_id) && !empty($st_pl_id) AND 
		isset($st_loc_id) && !empty($st_loc_id) AND isset($st_ev_id) && !empty($st_ev_id) AND
		isset($st_text) && !empty($st_text)){
		$statement = mysqli_prepare($con, "INSERT INTO fp_story (st_usr_id, st_pl_id, st_loc_id, st_ev_id, st_text, st_created_by) VALUES (?,?,?,?,?,?)") or die(mysql_error());
		mysqli_stmt_bind_param($statement, "iiiisi", $st_usr_id, $st_pl_id, 
			$st_loc_id, $st_ev_id, $st_text, $st_usr_id);
		mysqli_stmt_execute($statement);
		$st_id = $statement->insert_id;
		mysqli_stmt_close($statement);
	}
	mysqli_close($con);
?>