<?php
require_once 'Response.php';
		
			require_once('Login.php');
			if(isset($_POST['username'])||isset($_POST['password']))	
			{	
			$user = new Login;
			$user->username = $_POST['username'];
			$user->password = $_POST['password'];
			$user->role = $_POST['role'];
			//$user-> select();
			DB::db_close();
			if($user->username == null || $user->password == null)
				echo Response::deliver_response_password(500,"RegisterFailed",$user->username,$user->password,$user->role);	
			else
				echo Response::deliver_response_password(200,"Sucess",$user->username,$user->password,$user->role)	;
		
			}
			else
			{
				echo Response::deliver_response_password(200,"Invalid username or password",$user->username,$user->password,$user->role);	
				
			}
?>