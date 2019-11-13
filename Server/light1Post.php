<?php
header('Content-Type: application/json');
    $response = array();
	include 'db_connect.php';
	include 'functions.php';
 
	//Get the input request parameters
	$inputJSON = file_get_contents('php://input');
	$input = json_decode($inputJSON, TRUE); //convert JSON into array
	if(isset($input['username'])&& isset($input['light1'])){
			$username = $input['username'];
			$light1_status = $input['light1'];
			if(userExists($username)){
				$query = "UPDATE light1 SET light1_status = ?, created_date = CURRENT_TIMESTAMP WHERE user_id = (SELECT user_id FROM member m WHERE m.username= ?)";
				if($stmt = $con->prepare($query)){
					$stmt->bind_param("is",$light1_status,$username);
					$stmt->execute();
					$response['light1'] = 1; 
					$stmt->close();
					
					echo json_encode($response);
				}
			}
	}
?>

