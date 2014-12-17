<?php
require_once 'Response.php';
		
			require_once('Login.php');
			if(isset($_POST['username'])||isset($_POST['password']))	
			{	
			$user = new Login;
			$user->username = $_POST['username'];
			
			$user-> select();
			
			if($user->username == null || $user->password == null)
			{
			$user->username = $_POST['username'];
			$user->password = $_POST['password'];
			$user->role = $_POST['role'];
			$user-> save();
			$user-> select();
			if($user->username == null || $user->password == null)
			echo Response::deliver_response_password(502,"DBError",$user->username,$user->password,$user->role)	;	
			else
			echo Response::deliver_response_password(200,"Sucess",$user->username,$user->password,$user->role)	;	
			}			
			else
			echo Response::deliver_response_password(501,"Username Already Exist",$user->username,$user->password,$user->role);		
		
			}
			else
			{
				echo Response::deliver_response_password(500,"Username or Password cannot be null",$user->username,$user->password,$user->role);	
				
			}
?>