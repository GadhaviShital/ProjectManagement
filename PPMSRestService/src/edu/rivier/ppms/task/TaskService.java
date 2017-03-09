package edu.rivier.ppms.task;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import edu.rivier.ppms.db.Connection;
import edu.rivier.ppms.project.Project;
import edu.rivier.ppms.project.ProjectService;

@Consumes("application/json")
@Produces("application/json")
public class TaskService {
	
	private ProjectService projectService = new ProjectService();
	
	@GET
	@Path("/{projectId}/task")
	public List<Task> getTaskByProject(@PathParam("projectId") int projectId) throws Exception{
		List<Task> tasks = new ArrayList<Task>();
		try
		{
			java.sql.Connection connection = Connection.getConnection();	
		
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM projectportfoliomanagement.task where project_id ="+projectId);
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next())
			{
				int taskId = resultSet.getInt("task_Id");
				String taskName =resultSet.getString("task_name") ;
				String description =resultSet.getString("description");
				Date startDate = resultSet.getDate("start_date");
				Date dueDate =resultSet.getDate("due_date");
				String status=resultSet.getString("status");
				String priority =resultSet.getString("priority");
				
				Task task = new Task();
				task.setTaskId(taskId);
				task.setTaskName(taskName);
				task.setDescription(description);
				task.setStartDate(startDate);
				task.setDueDate(dueDate);
				task.setStatus(status);
				task.setPriority(priority);
				
				Project project = projectService.getProject(projectId);
				task.setProject(project);
				
				tasks.add(task);
			}
			return tasks;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("Error while getting Task, please try again");
		}
		
	}
	
	@POST
	@Path ("/task")	
	public Task add(Task task) throws Exception
	{
		
		try
		{
			java.sql.Connection connection = Connection.getConnection();	
		
			PreparedStatement statement = connection.prepareStatement("INSERT into projectportfoliomanagement.task("
					+ "task_name, "
					+ "description,"
					+ "start_date, "
					+ "due_date, "
					+ "status, "
					+ "priority"
					+ "project_id) values (?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			
			statement.setString(1, task.getTaskName());
			statement.setString(2,task.getDescription());
			statement.setDate(3, task.getStartDate());
			statement.setDate(4, task.getDueDate());
			statement.setString(5, task.getStatus());
			statement.setString(6, task.getPriority());
			statement.setInt(7, task.getProject().getProjectId());
			
			statement.executeUpdate();
			
			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
	            if (generatedKeys.next()) {	            	
	            	task.setTaskId(generatedKeys.getInt("task_id"));
	            }
	        }
			
			return task;
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("Error while adding task, please try again");
		}
		
	}
	
	@PUT
	@Path ("/task")	
	public Task edit(Task task) throws Exception
	{
		
		try
		{
			java.sql.Connection connection = Connection.getConnection();	
		
			PreparedStatement statement = connection.prepareStatement("UPDATE projectportfoliomanagement.task SET"
					+ "task_name=?, "
					+ "description=?,"
					+ "start_date=?, "
					+ "due_date=?, "
					+ "status=?, "
					+ "priority=?, "
					+ "project_id=? WHERE task_id=?");
			
			statement.setString(1, task.getTaskName());
			statement.setString(2,task.getDescription());
			statement.setDate(3, task.getStartDate());
			statement.setDate(4, task.getDueDate());
			statement.setString(5, task.getStatus());
			statement.setString(6, task.getPriority());
			statement.setInt(7, task.getProject().getProjectId());
			statement.setInt(8, task.getTaskId());
			
			statement.executeUpdate();
			
			return task;
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("Error while updating task, please try again");
		}
		
	}
}

