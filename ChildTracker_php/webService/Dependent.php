<?php
require_once 'db.php';
		class Dependent
		{
			var $id ;
			var $parent_id ;
			var $child_id;
			var $db;
			function __construct( )
			{
				$db = new DB;
				if(!$db->select_db())
				die("cannot select db");
			}
			
			function select()
			{
				if($this->parent_id != null)
				$sql="SELECT * FROM `dependent` where parent_id	 = $this->parent_id";
				else
				$sql="SELECT * FROM `dependent` where child_id	 = $this->child_id";
				$result = mysql_query($sql) or die(mysql_error());
				if(mysql_num_rows($result)>0)
				{
					$row=mysql_fetch_assoc($result) or die(mysql_error());
					$this->id = $row['id'];
					$this->parent_id = $row['parent_id'];
					$this->child_id = $row['child_id'] ;
								}
				else
				{
					$this->id = null;
					$this->parent_id = null;
					$this->child_id = null ;
				}
			}
			function maxid()
			{
				$sql = $sql = 'SELECT MAX( id )  "id" FROM  `dependent`';
				$result = mysql_query($sql) or die(mysql_error());
				$row=mysql_fetch_assoc($result) or die(mysql_error());
				return $row['id'];
			}
			function update()
			{
				$sql= "UPDATE `dependent` SET `parent_id`='$this->parent_id',`child_id`=$this->child_id,where id = $this->id ";
				$result = mysql_query($sql) or die(mysql_error());
				return $result;
			}
			
			function save()
			{
				$this->id = $this->maxid();
				$this->id++;
				$sql = "INSERT INTO `dependent`(`id`, `parent_id`, `child_id`) VALUES ($this->id,$this->parent_id,$this->child_id)";
				$result = mysql_query($sql) or die(mysql_error());
				return $result;
						
			}
			function delete()
			{
				$sql = "DELETE FROM `dependent` where id = $this->id";
				$result = mysql_query($sql) or die(mysql_error());	
			}
			
			
		}
 
 
?>