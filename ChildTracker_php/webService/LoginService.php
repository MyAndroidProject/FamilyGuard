<?php
		
		require_once 'Response.php';
		
			require_once('Login.php');
			if(isset($_POST['username'])||isset($_POST['password']))	
			{	
			$user = new Login;
			$user->username = $_POST['username'];
			$user-> select();
			DB::db_close();
			if($user->username == null || $user->password == null)
				echo Response::deliver_response(200,"LoginFailed",$user->username);	
			else
				echo Response::deliver_response_password(200,"Sucess",$user->username,$user->password,$user->role)	;
		
			}
			else
			{
				echo Response::deliver_response(200,"Invalid username or password",NULL);	
			}
	
		
?>