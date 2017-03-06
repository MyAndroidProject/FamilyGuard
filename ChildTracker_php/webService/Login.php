<?php
require_once 'db.php';
		class Login
		{
			var $id ;
			var $username ;
			var $password;
			var $role;
			var $db;
			function __construct( )
			{
				$db = new DB;
				if(!$db->select_db())
				die("cannot select db");
			}
			
			function retrive($username)
			{
				
				$sql="SELECT * FROM `login` where username = '$username'";
				$result = mysql_query($sql) or die(mysql_error());
				if(mysql_num_rows($result)>0)
				{
					$row=mysql_fetch_assoc($result) or die(mysql_error());
					$this->id = $row['id'];
					$this->username = $row['username'];
					$this->password = $row['password'] ;
					$this->role = $row['role'] ;
				}
				else
				{
					$this->id = null;
					$this->username = null;
					$this->password = null ;
					$this->role = null ;
				}
			}
			function select()
			{
				
				$sql="SELECT * FROM `login` where username = '$this->username'";
				$result = mysql_query($sql) or die(mysql_error());
				if(mysql_num_rows($result)>0)
				{
					$row=mysql_fetch_assoc($result) or die(mysql_error());
					$this->id = $row['id'];
					$this->username = $row['username'];
					$this->password = $row['password'] ;
					$this->role = $row['Role'] ;
				}
				else
				{
					$this->id = null;
					$this->username = null;
					$this->password = null ;
					$this->role = null ;
				}
			}
			function maxid()
			{
				$sql = $sql = 'SELECT MAX( id )  "id" FROM  `login`';
				$result = mysql_query($sql) or die(mysql_error());
				$row=mysql_fetch_assoc($result) or die(mysql_error());
				return $row['id'];
			}
			function update()
			{
				$sql= "UPDATE `login` SET ,`username`='$this->username',`password`='$this->password',`role`= $this->role where id = $this->id ";
				$result = mysql_query($sql) or die(mysql_error());
			}
			function update_username($username)
			{
				$sql= "update login set username ='$username'" ;
				$result = mysql_query($sql) or die(mysql_error());
			}
			function update_password($pass)
			{
				//$pass = md5($pass);
				$sql= "update `login` set password ='$pass' where id = $this->id ";
				$result = mysql_query($sql) or die(mysql_error());
			}
			function save()
			{
				$this->id = $this->maxid();
				$this->id++;
				$sql = "INSERT INTO `login`(`id`, `username`, `password`,`role`) VALUES ($this->id,'$this->username','$this->password',$this->role)";
				$result = mysql_query($sql) or die(mysql_error());
						
			}
			function delete()
			{
				$sql = "DELETE FROM `login` where id = $this->id";
				$result = mysql_query($sql) or die(mysql_error());	
			}
			
			
		}
 
 
?>