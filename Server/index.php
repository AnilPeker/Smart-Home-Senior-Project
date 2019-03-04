<?php
    session_start();
    include_once("db.php");
    if(isset($_POST['temperature']) && isset($_POST['humidity'])){
        if($_POST['temperature'] != "" && $_POST['humidity'] != ""){
            $temp = $_POST['temperature'];
            $humi = $_POST['humidity']
            $sql_store = "INSERT into templog(temperature, humidity) VALUES ('$temp', '$humi')";
            $sql = mysqli_query($db, $sql_store) or die(mysl_error());
        }
        else{
            echo("You need to enter data!");
        }



    }
    else{
        echo("You need to enter data!");
    }
?>

<<!DOCTYPE html>
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
    <h1>Temp&Humi Data </h1>
    <form action="index.php" method="POST">
        <input type="text" name="temperature" value="" placeholder="Temperature">
        <input type="text" name="humidity" value="" placeholder="Humidity">
        <input type="submit" name="submit" value="Submit">

    </form>
</body>
</html>