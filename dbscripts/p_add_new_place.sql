Create or Replace procedure p_add_new_place
(
IN in_title varchar(100),
IN in_description varchar(100),
IN in_category varchar(30),
IN in_longitude double,
IN in_latitude double
)

IS

m_loc_id	int(2)

BEGIN

	INSERT INTO pl_location_details 
	(
		loc_longitude,
		loc_latitude
	)
	values
	(
		in_longitude,
		in_latitude
		);

	SELECT loc_id INTO m_loc_id FROM pl_location_details WHERE loc_latitude=in_latitude AND loc_longitude=in_longitude;

	INSERT INTO pl_place_details
	(
		pl_loc_id,
		pl_title,
		pl_description,
		pl_category
		)
	values
	(
		m_loc_id,
		in_title,
		in_description,
		in_category
		);

END p_add_new_place