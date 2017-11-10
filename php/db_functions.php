<?php
ini_set('display_errors', '1');
error_reporting(E_ALL);
        // Connect to the Database
        $dsn = 'mysql:host=http://cssgate.insttech.washington.edu;dbname=msbry92';
        $username = 'msbry92';
        $password = 'cadett0';

        try {
                #make a new DB object to interact with
                $db = new PDO($dsn, $username, $password);
                //$criteria = $_GET['my_criteria'];
                $email = $_GET[email];
                $code = $_GET[code];

                //mail($email, $subject, $message, $header);
                #build a SQL statement to query the DB
                //$select_sql = "SELECT username, pwd FROM User WHERE username='$criteria'";
                //$insert_statement = "INSERT INTO User VALUES ('$usr', '$pwd');";
                //$db->exec($insert_statement);

                // $select_sql = "SELECT email FROM Users WHERE email='$email';";
                $select_sql = "SELECT code FROM loginData WHERE email='$email';";

                #make a query object
                $user_query = $db->query($select_sql);
                #run the query on the DB
                $query = $user_query->fetchAll(PDO::FETCH_ASSOC);
                #check to see if the db returned any values
                $db_code = $query[0]['campanion'];

                if ($db_code == $code) {
                        $result = array("code"=>100, "message"=>"Username already exists  $code");
                        $update_statement = "UPDATE loginData SET active = 1 WHERE email = '$email';";

                        $db->exec($update_statement);

                        #start an array to hold the results
                        // $result = array("code"=>100, "size"=>count($users));
                        // $user_array = array();
                        // #iterate through the results
                        // for ($i = 0; $i < count($users); $i++) {
                        //         $user_name = $users[$i]['username'];
                        //         $pwd_frag = substr($users[$i]['pwd'], 0, 2);
                        //         $user_array[$i] = array($user_name=>$pwd_frag);
                        // }
                        // $result["users"] = $user_array;
                } else {
                        $result = array("code"=>200, "message"=>"There was an error connecting to the database: $code");
                }
                echo json_encode($result);
                $db = null;
        } catch (PDOException $e) {
                $error_message = $e->getMessage();
                $result = array("code"=>300, "message"=>"There was an error connecting to the database: $error_message");
                echo json_encode($result);
                exit();
        }
?>