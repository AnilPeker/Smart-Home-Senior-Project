<?php
header('Content-Type: application/json');
    $response = array();
	include 'db_connect.php';
	include 'functions.php';
 
	//Get the input request parameters
	$inputJSON = file_get_contents('php://input');
	$input = json_decode($inputJSON, TRUE); //convert JSON into array
	if(isset($input['username'])&& isset($input['gasvalve'])){
			$username = $input['username'];
			$gasvalve_status = $input['gasvalve'];
			if(userExists($username)){
					$query = "UPDATE valve f SET gasvalve_status = ?, created_date = CURRENT_TIMESTAMP WHERE f.user_id = (SELECT user_id FROM member m WHERE m.username= ?)";
					if($stmt = $con->prepare($query)){
						$stmt->bind_param("is",$gasvalve_status,$username);
						$stmt->execute();
						$response['gasvalve'] = 1; 
						$stmt->close();
						
						echo json_encode($response);
					}
				
			}
			else{
				$response['gasvalve'] = 0;
				echo json_encode($response);
			}
	}
?>

