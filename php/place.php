<?php 
	$con=mysqli_connect("mysql6.000webhost.com", "a6596261_sagar","pr0gramm3r", "a6596261_footp") or die(mysql_error());
 
	$title = $_POST["pl_title"];
	$description = $_POST["pl_description"];
	$location = $_POST["pl_location"];
	$category = $_POST["pl_category"];
	if(isset($title) && !empty($title) AND isset($description) && !empty($description) AND isset($location) && !empty($location) AND isset($category) && !empty($category))
	{
		$statement = mysqli_prepare($con, "INSERT INTO fp_place_details (pl_title, pl_description, pl_location, pl_category) VALUES (?,?,?,?)") or die(mysql_error());
		mysqli_stmt_bind_param($statement, "ssss", $title, $description, $location, $category);
		mysqli_stmt_execute($statement);
		mysqli_stmt_close($statement);
	}
	mysqli_close($con);
?>
