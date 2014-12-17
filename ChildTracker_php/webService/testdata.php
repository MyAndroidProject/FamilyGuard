<?php
require_once 'Response.php';
		
			require_once('Login.php');
			require_once('Dependent.php');
			require_once('Service_Mst.php');
			require_once('Service_Mst.php');
			//$user = new Login;
			//$user->username ='rohit';
			//$user->password = 'Sharma';
			//$user->role = 0;
			//echo $user->maxid();
			//$user-> save();
			//$user-> select();
			//$dependent = new Dependent;			
			//$dependent->child_id = 5;
			//$dependent->select();
			//echo 	$dependent->parent_id    ;
			
		//	$service = new Service_Mst;
			//$service->dependent_id =1;
			
			
		///	$service->dependent_id = 1;
				///	$service->child_location = "ernakulam";
				//	$service->battery_status = 31 ;
				//	$service->speed = 23.6 ;
				//	$service->update();
					
			
			//echo 	$service->child_location; 
			
			
		$user = new Login;
				$dependent = new Dependent;
				$service = new Service_Mst;
				$user->username = "tutu";			
				$user-> select();
				$dependent->child_id = $user->id;
				echo $dependent->child_id ;
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
					$service->child_location = "paravoor" ;
					$service->battery_status = 33 ;
					$service->speed = 44 ;
					echo "sadsad";
						if($service->save())
						echo Response::deliver_response(200,"Sucess",NULL);	
						else
						echo Response::deliver_response(200,"db error",NULL);	
					}
					else
					{
					$service->dependent_id = $dependent->id;
					$service->child_location = "paravoor" ;
					$service->battery_status = 23 ;
					$service->speed = 54 ;
						if($service->update())
						echo Response::deliver_response(200,"Sucess",NULL);	
						else
						echo Response::deliver_response(200,"db error",NULL);	
					}		
				}
		
		
			
?>