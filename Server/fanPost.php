<?php
header('Content-Type: application/json');
    $response = array();
	include 'db_connect.php';
	include 'functions.php';
 
	//Get the input request parameters
	$inputJSON = file_get_contents('php://input');
	$input = json_decode($inputJSON, TRUE); //convert JSON into array
	if(isset($input['username'])&& isset($input['fanstatus'])){
			$username = $input['username'];
			$fan_status = $input['fanstatus'];
			if(userExists($username)){
					$query = "UPDATE fan f SET fan_status = ?, created_date = CURRENT_TIMESTAMP WHERE f.user_id = (SELECT user_id FROM member m WHERE m.username= ?)";
					if($stmt = $con->prepare($query)){
						$stmt->bind_param("is",$fan_status,$username);
						$stmt->execute();
						$response['fanstatus'] = 1; 
						$stmt->close();
						
						echo json_encode($response);
					}
				
			}
			else{
				$response['fanstatus'] = 0;
				echo json_encode($response);
			}
	}
?>

