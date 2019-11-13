<?php
header('Content-Type: application/json');
    $response = array();
	include 'db_connect.php';
	include 'functions.php';
 
	//Get the input request parameters
	$inputJSON = file_get_contents('php://input');
	$input = json_decode($inputJSON, TRUE); //convert JSON into array
	if(isset($input['username'])&& isset($input['garagestatus'])){
			$username = $input['username'];
			$garage_status = $input['garagestatus'];
			if(userExists($username)){
					$query = "UPDATE garagedoor g SET garage_status = ?, created_date = CURRENT_TIMESTAMP WHERE g.user_id = (SELECT user_id FROM member m WHERE m.username= ?)";
					if($stmt = $con->prepare($query)){
						$stmt->bind_param("is",$garage_status,$username);
						$stmt->execute();
						$response['garagestatus'] = 1; 
						$stmt->close();
						
						echo json_encode($response);
					}
				
			}
			else{
				$response['garagestatus'] = 0;
				echo json_encode($response);
			}
	}
?>

