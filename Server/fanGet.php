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
			$query = "SELECT fan_status FROM fan f WHERE f.user_id = (SELECT user_id FROM member m WHERE m.username= ?)";
			if($stmt = $con->prepare($query)){
				$stmt->bind_param("s",$username);
				$stmt->execute();
				$stmt->bind_result($fan_status);
				while($stmt->fetch()){
					 $response['fanstatus'] = $fan_status; 
				}
				echo json_encode($response);
			}
		}
	}
?>

