<?php
ini_set('display_errors', '1');
error_reporting(E_ALL);
// Connect to the Database
$dsn = 'mysql:host=cssgate.insttech.washington.edu;dbname=msbry92';
$username = 'msbry92';
$password = 'cadett0';

// receiving the get params
$uEmail = $_GET[my_email];
$uPassword = $_GET[my_password];

try {
        #make a new DB object to interact with
        $db = new PDO($dsn, $username, $password);

        
        
        $getNewID = "select count(*) from loginData";
        $getID_query = $db->query(getNewID);
        $getIDResult = $getID_query-.fetchAll(PDO::FETCH_ASSOC);
        $userID = ($getIDResult[0]['count(*)']) + 1;

        $insertUsers = "INSERT INTO  Users Values (email, password);";

        $insertloginData = "INSERT INTO loginData VALUES (email, password, userID, active) 
        values ('$uEmail', 'uPassword', $userID, 0)";

        $users_query = $db->query($insertUsers);
        $loginData_query = $db->query($insertloginData);

        // $code = rand(10000,100000);
        // $subject = "Email Verification";
        // $message = "Enter Code" . $code;
        // $header = "From barrysaliou18@gmail.com" . "\r\n";

        // mail($uEmail, $suject, $message, $header);



        if ($users_query && $loginData_query) {
            //$active = $users[0]['active'];
            //if($active == 1) {
                $result = array("code"=>200, "Successfully Registered");
            //} else {
                //$result = array("code"=>200, "Account not verified");
            //}
        } else {
             $insertloginData = "INSERT INTO loginData VALUES (email, password, userID, active) 
        VALUES ('$uEmail', 'uPassword', $userID, 0)";
            $db->exec($insert_statement);
            $result = array('successfully created user:' => $uEmail);
        }
        echo json_encode($result);
        $db = null;

 } catch (PDOException $e) {
$error_message = $e->getMessage();
$result = array("code"=>200, "message"=>"There was an error connecting to
the database: $error_message");
echo json_encode($result);
exit();
}
?>