<?php
header('Content-Type: application/json');
    $response = array();
	include 'db_connect.php';
	include 'functions.php';
 
	//Get the input request parameters
	$inputJSON = file_get_contents('php://input');
	$input = json_decode($inputJSON, TRUE); //convert JSON into array
	if(isset($input['username'])){
		$username = $input['username'];
		if(userExists($username)){
			$query = "SELECT alarm_status FROM alarm t WHERE t.user_id = (SELECT user_id FROM member m WHERE m.username= ?) ORDER BY t.sensor_id DESC LIMIT 1";
			if($stmt = $con->prepare($query)){
				$stmt->bind_param("s",$username);
				$stmt->execute();
				$stmt->bind_result($alarm_status);
				while($stmt->fetch()){
					 $response['alarm_status'] = $alarm_status; 
				}
				echo json_encode($response);
			}
		}
	}
?>

