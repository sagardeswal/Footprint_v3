<?php 
	$con=mysqli_connect("mysql6.000webhost.com", "a6596261_sagar","pr0gramm3r", "a6596261_footp") or die(mysql_error());
	if(isset($_POST['location']) && !empty($_POST['location'])) {
    // Both fields are being posted and there not empty
	$location = mysql_escape_string($_POST['location']); // Set variable for the location

    $statement = mysqli_prepare($con,"SELECT pl_title, pl_description, pl_location, pl_category FROM fp_place_details WHERE pl_location= ? AND active='1'") or die(mysql_error()); 
    mysqli_stmt_bind_param($statement, "s", $location);
	mysqli_stmt_execute($statement);
	mysqli_stmt_store_result($statement);
	mysqli_stmt_bind_result($statement, $pl_title, $pl_description, $pl_location, $pl_category);
    $placedetails = array();
    $i=0;
	while(mysqli_stmt_fetch($statement)){
		$placedetails[$i][pl_title] = $pl_title;
		$placedetails[$i][pl_description] = $pl_description;
		$placedetails[$i][pl_location] = $pl_location;
		$placedetails[$i][pl_category] = $pl_category;
		$i=$i+1;
	}

	echo json_encode($placedetails);

	mysqli_stmt_close($statement); 

	mysqli_close($con);
}           
             
?>