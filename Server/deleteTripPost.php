<?php
    session_start();
    define('DB_SERVER', 'localhost');
    define('DB_USERNAME', 'root');
    define('DB_PASSWORD', '');
    define('DB_DATABASE', 'homework');
    $db = mysqli_connect(DB_SERVER,DB_USERNAME,DB_PASSWORD,DB_DATABASE);
    if ($db->connect_error) {
       die("Connection failed: " . $db->connect_error);
    }
    if(isset($_POST['emp_id'])){
        if($_POST['emp_id'] != ""){
            $emp_id = $_POST['emp_id'];			
			$sql_store = "DELETE FROM practitioner WHERE emp_id = '$emp_id'";
			$sql = mysqli_query($db, $sql_store) or die(mysqli_error($db));
		
        }
        else{
            echo("You need to enter data!");
        }
    }
    
?>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>DummyData</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" media="screen" href="main.css">
    <script src="main.js"></script>
</head>
<body>
    
    <form action="deleteTripPost.php" method="POST">
        <input type="text" name="emp_id" value="" placeholder="emp_id">
		
		
        <input type="submit" name="submit" value="Submit">

    </form>
</body>
</html>