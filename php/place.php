<?php 
	$con=mysqli_connect("mysql6.000webhost.com", "a6596261_sagar","pr0gramm3r", "a6596261_footp") or die(mysql_error());
 
	$title = $_POST["pl_title"];
	$description = $_POST["pl_description"];
	$category = $_POST["pl_category"];
	$longitude = $_POST["pl_longitude"];
	$latitude = $_POST["pl_latitude"];
	$adminID = intval($_POST["pl_admin_id"]);
	
	if(isset($title) && !empty($title) AND isset($description) && !empty($description) AND 
		isset($category) && !empty($category) AND isset($longitude) && !empty($longitude) AND 
		isset($latitude) && !empty($latitude) AND isset($adminID) && !empty($adminID)){
		$statement = mysqli_prepare($con, "INSERT INTO pl_location_details (loc_longitude, loc_latitude) VALUES (?,?)") or die(mysql_error());
		mysqli_stmt_bind_param($statement, "ss", $longitude, $latitude);
		mysqli_stmt_execute($statement);
		$loc_id = $statement->insert_id;
		mysqli_stmt_close($statement);

		if(isset($loc_id) && !empty($loc_id))
		{
			$stmt = mysqli_prepare($con, "INSERT INTO fp_place_details (pl_loc_id, pl_title, pl_description, pl_category, pl_admin_id) VALUES (?,?,?,?,?)") or die(mysql_error());
			mysqli_stmt_bind_param($stmt, "isssi", $loc_id, $title, $description, $category, $adminID);
			mysqli_stmt_execute($stmt);
			mysqli_stmt_close($stmt);
		}
	}

	// $statement = mysqli_prepare($con,"SELECT pl_ID FROM fp_place_details WHERE pl_title= ? ") or die(mysql_error()); 
 //    mysqli_stmt_bind_param($statement, "s", $title);
	// mysqli_stmt_execute($statement);
	// mysqli_stmt_store_result($statement);
	// mysqli_stmt_bind_result($statement, $pl_ID);
	// mysqli_stmt_close($statement);
		
	// $image = $_POST["pl_image"];
	// if(isset($image) && !empty($image)){

	// 	$statement = mysqli_prepare($con, "INSERT INTO fp_pl_images (img_pl_id, img_blob) VALUES (?,?)") or die(mysql_error());
	// 	mysqli_stmt_bind_param($statement,"is",$pl_ID,$image);
	// 	mysqli_stmt_execute($statement);
	// 	mysqli_stmt_close($statement);

	// } 
	mysqli_close($con);
?>
