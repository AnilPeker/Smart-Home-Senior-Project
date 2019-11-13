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
			$query = "SELECT waterPump_status, soilHumidity FROM garden a WHERE a.user_id = (SELECT user_id FROM member m WHERE m.username= ?) ORDER BY a.sensor_id DESC LIMIT 1";
			if($stmt = $con->prepare($query)){
				$stmt->bind_param("s",$username);
				$stmt->execute();
				$stmt->bind_result($waterPump_status, $soilHumidity);
				while($stmt->fetch()){
					 $response['waterPump'] = $waterPump_status; 
					 $response['soilHumidity'] = $soilHumidity;  
				}
				echo json_encode($response);
			}
		}
	}
?>

