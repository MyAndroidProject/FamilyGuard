<?php
	/*header("Content-Type:Application/json");	
					//header("HTTP/1.1 $status $status_message");
					$response['status'] = 200;
					$response['status_message'] = "Success";
					$response['username'] = 'nikhil';
					$response['passwod'] = 'tutu';
					$response['role'] = 0;
					$json_response = json_encode($response);
					echo $json_response;*/
			if(isset($_POST['username'])||isset($_POST['password']))	
			{			
			//echo $_POST['username']+$_POST['password'];
			header("Content-Type:Application/json");	
					//header("HTTP/1.1 $status $status_message");
					$response['status'] = 200;
					$response['status_message'] = "Success";
					$response['username'] = $_POST['password'];
					$response['passwod'] = $_POST['username'];
					$response['role'] = 1;
					$json_response = json_encode($response);
					echo $json_response;
			}
?>