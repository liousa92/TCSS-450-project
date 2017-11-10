<?php
ini_set('display_errors', '1');
error_reporting(E_ALL);
// Connect to the Database
$dsn = 'mysql:host=cssgate.insttech.washington.edu;dbname=msbry92';
$username = 'msbry92';
$password = 'cadett0';
try {
        #make a new DB object to interact with
        $db = new PDO($dsn, $email, $password);

        // receiving the get params
        $uEmail = $_GET['email'];
        $password = $_GET['password'];

        $code = rand();
        $subject = "Email verification";
        $message = "Enter Code" . $rand;
        $header = "From group4@gmail.com" . "\r\n";

        mail($email, $suject, $message, $header)

        #build a SQL statement to query the DB
        $select_sql = "SELECT email, active FROM loginData WHERE email='$email' AND password='$password';";
        #make a query object
        $user_query = $db->query($select_sql);
        #run the query on the DB
        $users = $user_query->fetchAll(PDO::FETCH_ASSOC);
        #check to see if the db returned any values

        if ($users) {
            $verification = $user[0]['active'];
            if($verification == 1) {
                $result = array("code"=>100, "Successfully logged in");
            } else {
                $result = array("code"=>200, "Account not verified");
            }
        

        $user_array = array();
        

        } else {
            #start an array to hold the results
        $result = array("code"=>300, "message"=> "Email or Password does not match");
        }
        echo json_encode($result);
        $db = null;

 } catch (PDOException $e) {
        $error_message = $e->getMessage();
        $result = array("code"=>300, "message"=>"There was an error connecting to
        the database: $error_message");
        echo json_encode($result);
        exit();
}
?>