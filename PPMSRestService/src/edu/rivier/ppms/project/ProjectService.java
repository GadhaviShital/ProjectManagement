package edu.rivier.ppms.project;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import edu.rivier.ppms.client.Client;
import edu.rivier.ppms.client.ClientService;
import edu.rivier.ppms.db.Connection;

@Consumes("application/json")
@Produces("application/json")
public class ProjectService {
	private ClientService clientService = new ClientService();
	
	@GET
	@Path("/project")
	public List<Project> getProjects() throws Exception{
		List<Project> projects = new ArrayList<Project>();
		PreparedStatement statement = null;
		ResultSet resultSet =  null;
		try
		{
			java.sql.Connection connection = Connection.getConnection();	
		
			statement = connection.prepareStatement("SELECT * FROM projectportfoliomanagement.project");
			resultSet = statement.executeQuery();
			
			while(resultSet.next())
			{
				int projectId = resultSet.getInt("project_id");
				String projectName =resultSet.getString("project_name");
				String description =resultSet.getString("description");
				Date projectStartDate = resultSet.getDate("project_start_date");
				Date projectDueDate =resultSet.getDate("due_date");
				String status =resultSet.getString("status");
				double budget=resultSet.getDouble("budget");
				String billingType =resultSet.getString("billing_type");
				String billingRate=resultSet.getString("billing_rate");
				int clientId = resultSet.getInt("client_id");
					
				Project project = new Project();
				
				project.setProjectId(projectId);
				project.setProjectName(projectName);
				project.setDescription(description);
				project.setProjectStartDate(projectStartDate);
				project.setDueDate(projectDueDate);
				project.setStatus(status);
				project.setBudget(budget);
				project.setBillingType(billingType);
				project.setBillingRate(billingRate);
				
				Client client = clientService.getClient(clientId);
				project.setClient(client);
				
				projects.add(project);
			}
			return projects;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("Error while getting Project, please try again");
		}
		finally
		{
			if(statement!= null)
			{
				try
				{
					statement.close();
				}catch(Exception e){}				
			}
			
			if(resultSet != null)
			{
				try
				{
					resultSet.close();
				}catch(Exception e){}
				
			}
		}
	}
	
	@GET
	@Path("/project/{projectId}")
	public Project getProject(@PathParam("projectId") int projectId) throws Exception{
		
		PreparedStatement statement = null;
		ResultSet resultSet =  null;
		try
		{
			java.sql.Connection connection = Connection.getConnection();	
		
			statement = connection.prepareStatement("SELECT * FROM projectportfoliomanagement.project WHERE project_id="+projectId);
			resultSet = statement.executeQuery();
			
			while(resultSet.next())
			{
				
				String projectName =resultSet.getString("project_name");
				String description =resultSet.getString("description");
				Date projectStartDate = resultSet.getDate("project_start_date");
				Date projectDueDate =resultSet.getDate("due_date");
				String status =resultSet.getString("status");
				double budget=resultSet.getDouble("budget");
				String billingType =resultSet.getString("billing_type");
				String billingRate=resultSet.getString("billing_rate");
				int clientId = resultSet.getInt("client_id");
				
				Project project = new Project();
				
				project.setProjectId(projectId);
				project.setProjectName(projectName);
				project.setDescription(description);
				project.setProjectStartDate(projectStartDate);
				project.setDueDate(projectDueDate);
				project.setStatus(status);
				project.setBudget(budget);
				project.setBillingType(billingType);
				project.setBillingRate(billingRate);
				
				Client client = clientService.getClient(clientId);
				project.setClient(client);
				
				return project;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("Error while getting Project, please try again");
		}
		finally
		{
			if(statement!= null)
			{
				try
				{
					statement.close();
				}catch(Exception e){}				
			}
			
			if(resultSet != null)
			{
				try
				{
					resultSet.close();
				}catch(Exception e){}
				
			}
		}
		return null;
	}
	
	@POST
	@Path ("/project")
	public Project add(Project project) throws Exception
	{
		PreparedStatement statement = null;
		try
		{
			java.sql.Connection connection = Connection.getConnection();	
		
			statement = connection.prepareStatement("INSERT into projectportfoliomanagement.project"
					+ "(project_name, "
					+ "description, "
					+ "project_start_date, "
					+ "due_date, "
					+ "status, "
					+ "budget, "
					+ "billing_type, "
					+ "billing_rate, client_id) values (?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			
			statement.setString(1, project.getProjectName());
			statement.setString(2,project.getDescription());
			statement.setDate(3, project.getProjectStartDate());
			statement.setDate(4,project.getDueDate());
			statement.setString(5,project.getStatus());
			statement.setDouble(6,project.getBudget());
			statement.setString(7,project.getBillingType());
			statement.setString(8,project.getBillingRate());
			statement.setInt(9, project.getClient().getClientId());
			statement.executeUpdate();
			
			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
	            if (generatedKeys.next()) {	            	
	            	project.setProjectId(generatedKeys.getInt("project_id"));
	            }
	        }
			
			return project;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("Error while adding Project, please try again");
		}
		finally
		{
			if(statement!= null)
			{
				try
				{
					statement.close();
				}catch(Exception e){}				
			}			
		}		
	}
	
	@PUT
	@Path ("/project")
	public Project edit(Project project) throws Exception
	{
		PreparedStatement statement = null;
		try
		{
			java.sql.Connection connection = Connection.getConnection();	
		
			statement = connection.prepareStatement("UPDATE into projectportfoliomanagement.project"
					+ "SET project_name=?, "
					+ "description=?, "
					+ "project_start_date=?, "
					+ "due_date=?, "
					+ "status=?, "
					+ "budget=?, "
					+ "billing_type=?, "
					+ "billing_rate=?,"
					+ "client_id=?"
					+ " WHERE project_id=?");
			
			statement.setString(1, project.getProjectName());
			statement.setString(2,project.getDescription());
			statement.setDate(3, project.getProjectStartDate());
			statement.setDate(4,project.getDueDate());
			statement.setString(5,project.getStatus());
			statement.setDouble(6,project.getBudget());
			statement.setString(7,project.getBillingType());
			statement.setString(8,project.getBillingRate());
			statement.setInt(9,project.getProjectId());
			statement.setInt(9,project.getClient().getClientId());
			statement.executeUpdate();
			
			return project;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new Exception("Error while editing Project, please try again");
		}
		finally
		{
			if(statement!= null)
			{
				try
				{
					statement.close();
				}catch(Exception e){}				
			}			
		}		
	}
	
	@DELETE
	@Path("/project/{projectId}")
	public void delete(@PathParam("projectId") int projectId) throws Exception{

		PreparedStatement statement = null;
		try {
			java.sql.Connection connection = edu.rivier.ppms.db.Connection.getConnection();

			statement = connection.prepareStatement(
					"DELETE FROM projectportfoliomanagement.project WHERE project_id="+projectId);
			statement.execute();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error while deleting project, please try again");
		}
		finally
		{
			if(statement!= null)
			{
				try
				{
					statement.close();
				}catch(Exception e){}				
			}
		}
	}
}
