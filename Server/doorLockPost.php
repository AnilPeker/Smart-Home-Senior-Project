<?php
header('Content-Type: application/json');
    $response = array();
	include 'db_connect.php';
	include 'functions.php';
 
	//Get the input request parameters
	$inputJSON = file_get_contents('php://input');
	$input = json_decode($inputJSON, TRUE); //convert JSON into array
	if(isset($input['username'])&& isset($input['doorlock']) && isset($input['doorstatus'])){
			$username = $input['username'];
			$lock_status = $input['doorlock'];
			$door_status = $input['doorstatus'];
			if(userExists($username) && $door_status == 0){
					$query = "UPDATE maindoor m SET lock_status = ?, created_date = CURRENT_TIMESTAMP WHERE m.user_id = (SELECT user_id FROM member m WHERE m.username= ?)";
					if($stmt = $con->prepare($query)){
						$stmt->bind_param("is",$lock_status,$username);
						$stmt->execute();
						$response['doorlock'] = 1; 
						$stmt->close();
						
						echo json_encode($response);
					}
				
			}
			else{
				$response['doorlock'] = 0;
				echo json_encode($response);
			}
	}
?>

