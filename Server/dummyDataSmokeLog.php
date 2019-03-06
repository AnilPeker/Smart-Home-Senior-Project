<?php
    session_start();
    define('DB_SERVER', 'localhost');
    define('DB_USERNAME', 'root');
    define('DB_PASSWORD', '');
    define('DB_DATABASE', 'database');
    $db = mysqli_connect(DB_SERVER,DB_USERNAME,DB_PASSWORD,DB_DATABASE);
    if ($db->connect_error) {
       die("Connection failed: " . $db->connect_error);
    }
    if(isset($_POST['smoke'])){
        if($_POST['smoke'] != ""){
            $smoke = $_POST['smoke'];
            $sql_store = "INSERT into smokelog (smoke) VALUES ('$smoke')";
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
    <title>Dummy Smoke Data</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" media="screen" href="main.css">
    <script src="main.js"></script>
</head>
<body>
    <h1> Smoke Data </h1>
    <form action="dummyDataSmokeLog.php" method="POST">
        <input type="text" name="smoke" value="" placeholder="Smoke">
        <input type="submit" name="submit" value="Submit">

    </form>
</body>
</html>