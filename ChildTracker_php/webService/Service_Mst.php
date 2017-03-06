<?php
require_once 'db.php';
		class Service_Mst
		{
			var $id ;
			var $dependent_id ;
			var $child_location;
			var $battery_status;
			var $speed;
			function __construct( )
			{
				$db = new DB;
				if(!$db->select_db())
				die("cannot select db");
			}
			
			
			function select()
			{
				
				$sql="SELECT * FROM `service_mst` where dependent_id = $this->dependent_id";
				$result = mysql_query($sql) or die(mysql_error());
				if(mysql_num_rows($result)>0)
				{
					$row=mysql_fetch_assoc($result) or die(mysql_error());
					$this->id = $row['id'];
					$this->dependent_id = $row['dependent_id'];
					$this->child_location = $row['child_location'] ;
					$this->battery_status = $row['battery_status'] ;
					$this->speed = $row['speed'] ;
				}
				else
				{
					$this->id = null;
					$this->dependent_id = null;
					$this->child_location = null ;
					$this->battery_status = null ;
					$this->speed = null ;
				}
			}
			function maxid()
			{
				$sql = $sql = 'SELECT MAX( id )  "id" FROM  `service_mst`';
				$result = mysql_query($sql) or die(mysql_error());
				$row=mysql_fetch_assoc($result) or die(mysql_error());
				return $row['id'];
			}
			function update()
			{
				$sql= "UPDATE `service_mst` SET `child_location`='$this->child_location',`battery_status`=$this->battery_status,`speed`= $this->speed
				where dependent_id = $this->dependent_id ";
				$result = mysql_query($sql) or die(mysql_error());
				return $result;
			}
			
			function save()
			{
				$this->id = $this->maxid();
				$this->id++;
				$sql = "INSERT INTO `service_mst`(`id`, `dependent_id`, `child_location`,`battery_status`,`speed`) VALUES ($this->id,$this->dependent_id,'$this->child_location'
				,$this->battery_status,$this->speed)";
				$result = mysql_query($sql) or die(mysql_error());
				return $result;		
			}
			function delete()
			{
				$sql = "DELETE FROM `service_mst` where id = $this->id";
				$result = mysql_query($sql) or die(mysql_error());	
			}
			
			
		}
 
 
?>