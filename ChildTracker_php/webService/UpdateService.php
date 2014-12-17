<?php
		
		require_once 'Response.php';
		
			require_once('Login.php');
			require_once('Dependent.php');
			require_once('Service_Mst.php');
			if(isset($_POST['username'])||isset($_POST['location']) ||isset($_POST['speed']) ||isset($_POST['status']))	
			{	
				$user = new Login;
				$dependent = new Dependent;
				$service = new Service_Mst;
				$user->username = $_POST['username'];			
				$user-> select();
				$dependent->child_id = $user->id;
				$dependent->select();
				if($dependent->child_id == null || $dependent->parent_id == null)
				{
				echo Response::deliver_response(501,"No Dependent data fund",NULL);	
				}
				else
				{
				$service->dependent_id = $dependent->id;
					$service->select();
					if ($service->id == null)
					{
					$service->dependent_id = $dependent->id;
					$service->child_location = $_POST['location'] ;
					$service->battery_status = $_POST['status'] ;
					$service->speed = $_POST['speed'] ;
					
						if($service->save())
						echo Response::deliver_response(200,"Sucess",NULL);	
						else
						echo Response::deliver_response(200,"db error",NULL);	
					}
					else
					{
					$service->child_location = $_POST['location'] ;
					$service->battery_status = $_POST['status'] ;
					$service->speed = $_POST['speed'] ;
						if($service->update())
						echo Response::deliver_response(200,"Sucess",NULL);	
						else
						echo Response::deliver_response(200,"db error",NULL);	
					}		
				}		
			}
			else
			{
				echo Response::deliver_response(500,"Invalid Service data",NULL);	
			}
	
		
