<?php
header('Content-Type: application/json');
    $response = array();
	include 'db_connect.php';
	include 'functions.php';
 
	//Get the input request parameters
	$inputJSON = file_get_contents('php://input');
	$input = json_decode($inputJSON, TRUE); //convert JSON into array
	if(isset($input['username'])&& isset($input['windowstatus'])){
			$username = $input['username'];
			$window_status = $input['windowstatus'];
			if(userExists($username)){
					$query = "UPDATE maindoor f SET window_status = ?, created_date = CURRENT_TIMESTAMP WHERE f.user_id = (SELECT user_id FROM member m WHERE m.username= ?)";
					if($stmt = $con->prepare($query)){
						$stmt->bind_param("is",$window_status,$username);
						$stmt->execute();
						$response['windowstatus'] = 1; 
						$stmt->close();
						
						echo json_encode($response);
					}
				
			}
			else{
				$response['windowstatus'] = 0;
				echo json_encode($response);
			}
	}
?>

