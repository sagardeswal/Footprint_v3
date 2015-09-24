<?php 
	$con=mysqli_connect("mysql6.000webhost.com", "a6596261_sagar","pr0gramm3r", "a6596261_footp") or die(mysql_error());
	if(isset($_POST['location']) && !empty($_POST['location'])) {
    // Both fields are being posted and there not empty
	$location = mysql_escape_string($_POST['location']); // Set variable for the location

    $statement = mysqli_prepare($con,"SELECT p.pl_ID, p.pl_title, p.pl_description, p.pl_loc_id, p.pl_category , l.loc_longitude, l.loc_latitude FROM fp_place_details p, pl_location_details l WHERE p.active='1' AND p.pl_loc_id = l.loc_id") or die(mysql_error()); 
	mysqli_stmt_execute($statement);
	mysqli_stmt_store_result($statement);
	mysqli_stmt_bind_result($statement,$pl_ID, $pl_title, $pl_description, $pl_loc_id, $pl_category, $pl_loc_long, $pl_loc_lat);
    $placedetails = array();
    $i=0;
	while(mysqli_stmt_fetch($statement)){
		$placedetails[$i][pl_ID] = $pl_ID;
		$placedetails[$i][pl_title] = $pl_title;
		$placedetails[$i][pl_description] = $pl_description;
		$placedetails[$i][pl_loc_id] = $pl_loc_id;
		$placedetails[$i][pl_category] = $pl_category;
		$placedetails[$i][pl_loc_long] = $pl_loc_long;
		$placedetails[$i][pl_loc_lat] = $pl_loc_lat;
		$i=$i+1;
	}

	echo json_encode($placedetails);

	mysqli_stmt_close($statement); 

	mysqli_close($con);
}           
             
?>