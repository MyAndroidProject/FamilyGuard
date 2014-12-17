<?php
		
		require_once ('Response.php');
		
			require_once('Login.php');
			require_once('Dependent.php');
			if(isset($_POST['childName'])||isset($_POST['parentName']))	
			{	
				$user = new Login;
				$dependent = new Dependent;
				$user->username = $_POST['childName'];
				$user-> select();
				$dependent->child_id = $user->id;
				
				$dependent->select();
				if($dependent->child_id == null || $dependent->parent_id == null)
				{
					$user-> select();
					$dependent->child_id = $user->id;
					$dependent->parent_id = $user->id;			
					if($dependent->child_id == null || $dependent->parent_id == null)
						echo Response::deliver_response_dependent(501,"Invalid child Name",$_POST['childName'],$_POST['parentName']);	
					else
					{
					
						if($dependent->save())
						echo Response::deliver_response_dependent(200,"Sucess",$_POST['childName'],$_POST['parentName']);
						else
						echo Response::deliver_response(502,"db error",Null);	
					}
				}
				else
				{
				echo Response::deliver_response_dependent(503,"Dependent Already added",$_POST['childName'],$_POST['parentName']);	
				}
			}
			else
			{
				echo Response::deliver_response(500,"Invalid username or password",NULL);	
			}
	
	DB::db_close();		
