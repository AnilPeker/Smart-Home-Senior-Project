<?php
header('Content-Type: application/json');
    $response = array();
	include 'db_connect.php';
	include 'functions.php';
 
	//Get the input request parameters
	$inputJSON = file_get_contents('php://input');
	$input = json_decode($inputJSON, TRUE); //convert JSON into array
	if(isset($input['username'])&& isset($input['alarm_status'])){
			$username = $input['username'];
			$alarm_status = $input['alarm_status'];
			if(userExists($username)){
					$query = "UPDATE alarm SET alarm_status = ?, created_date = CURRENT_TIMESTAMP WHERE user_id = (SELECT user_id FROM member WHERE username= ?)";
					if($stmt = $con->prepare($query)){
						$stmt->bind_param("is",$alarm_status,$username);
						$stmt->execute();
						$response['alarm_status'] = 1; 
						$stmt->close();
						
						echo json_encode($response);
					}
				
			}
			else{
				$response['alarm_status'] = 0;
				echo json_encode($response);
			}
	}
?>

