
<?php
	class Response
			{
				public static function deliver_response($status,$status_message,$data)
				{	header("Content-Type:Application/json");	
					header("HTTP/1.1 $status $status_message");
					$response['status'] = $status;
					$response['status_message'] = $status_message;
					$response['data'] = $data;
					$json_response = json_encode($response);
					return $json_response;
				}
				public static function deliver_response_password($status,$status_message,$username,$passwod,$role)
				{
					header("Content-Type:Application/json");	
					header("HTTP/1.1 $status $status_message");
					$response['status'] = $status;
					$response['status_message'] = $status_message;
					$response['username'] = $username;
					$response['password'] = $passwod;
					$response['role'] = $role;
					$json_response = json_encode($response);
					return $json_response;
				}
			}
?>