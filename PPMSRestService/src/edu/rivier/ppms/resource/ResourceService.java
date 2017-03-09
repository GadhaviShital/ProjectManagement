package edu.rivier.ppms.resource;

import java.sql.Connection;
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
import javax.ws.rs.Produces;

import edu.rivier.ppms.project.Project;
import edu.rivier.ppms.project.ProjectService;

@Consumes("application/json")
@Produces("application/json")

public class ResourceService {
	
	private ProjectService projectService = new ProjectService();
	
	@GET
	@Path("/resource")
	public List<Resource> getResources() throws Exception{
		List<Resource> resources = new ArrayList<Resource>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			Connection connection = edu.rivier.ppms.db.Connection.getConnection();
			statement = connection
					.prepareStatement("SELECT * FROM projectportfoliomanagement.resources");
			resultSet = statement.executeQuery();

			while (resultSet.next())
				
			{
				int employeeId = resultSet.getInt("employee_id");
				String firstName = resultSet.getString("first_name");
				String lastName = resultSet.getString("last_name");
				String emailId = resultSet.getString("email_id");
				String reportTo = resultSet.getString("report_to");
				String skillSet = resultSet.getString("skillset");
				int experience = resultSet.getInt("experience");
				String designation = resultSet.getString("designation");
				String department = resultSet.getString("department");
				int projectId = resultSet.getInt("project_id");

				Resource resource = new Resource();
				resource.setEmployeeId(employeeId);
				resource.setFirstName(firstName);
				resource.setLastName(lastName);
				resource.setEmailId(emailId);
				resource.setReportTo(reportTo);
				resource.setSkillSet(skillSet);
				resource.setExperience(experience);
				resource.setDesignation(designation);
				resource.setDepartment(department);

				if(projectId != 0)
				{
					Project project = projectService.getProject(projectId);
					resource.setProject(project);
				}
				
				resources.add(resource);
			}
			return resources;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error while getting Resources, please try again");
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
	@Path("/resource/{resourceId}")
	public Resource getResource(int resourceId) throws Exception{
		
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			Connection connection = edu.rivier.ppms.db.Connection.getConnection();
			statement = connection
					.prepareStatement("SELECT * FROM projectportfoliomanagement.resources WHERE employee_id="+resourceId);
			resultSet = statement.executeQuery();

			while (resultSet.next())
				
			{
				String firstName = resultSet.getString("first_name");
				String lastName = resultSet.getString("last_name");
				String emailId = resultSet.getString("email_id");
				String reportTo = resultSet.getString("report_to");
				String skillSet = resultSet.getString("skillset");
				int experience = resultSet.getInt("experience");
				String designation = resultSet.getString("designation");
				String department = resultSet.getString("department");
				int projectId = resultSet.getInt("project_id");
				
				Resource resource = new Resource();
				resource.setEmployeeId(resourceId);
				resource.setFirstName(firstName);
				resource.setLastName(lastName);
				resource.setEmailId(emailId);
				resource.setReportTo(reportTo);
				resource.setSkillSet(skillSet);
				resource.setExperience(experience);
				resource.setDesignation(designation);
				resource.setDepartment(department);
				
				if(projectId != 0)
				{
					Project project = projectService.getProject(projectId);
					resource.setProject(project);
				}
				return resource;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error while getting Resources, please try again");
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
	@Path("/resource")
	public Resource add(Resource resource) throws Exception{

		try {
			java.sql.Connection connection = edu.rivier.ppms.db.Connection.getConnection();

			PreparedStatement statement = connection.prepareStatement(
					"INSERT into projectportfoliomanagement.resources( "
					+ "first_name, "
					+ "last_name, "
					+ "email_id, "
					+ "report_to,  "
					+ "skillset, "
					+ "experience, "
					+ "designation, "
					+ "department,"
					+ "project_id) values (?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

			statement.setString(1, resource.getFirstName());
			statement.setString(2, resource.getLastName());
			statement.setString(3, resource.getEmailId());
			statement.setString(4, resource.getReportTo());
			statement.setString(5, resource.getSkillSet());
			statement.setInt(6, resource.getExperience());
			statement.setString(7, resource.getDesignation());
			statement.setString(8, resource.getDepartment());
			
			if(resource.getProject() != null)
			{
				statement.setInt(9, resource.getProject().getProjectId());
			}
			else
			{
				statement.setInt(9, 0);
			}

			statement.executeUpdate();
			
			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
	            if (generatedKeys.next()) {	            	
	            	resource.setEmployeeId(generatedKeys.getInt("employee_id"));
	            }
	        }
			
			return resource;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error while adding Resource, please try again");
		}
		
	}
	
	@PUT
	@Path("/resource")
	public Resource edit(Resource resource) throws Exception{

		try {
			java.sql.Connection connection = edu.rivier.ppms.db.Connection.getConnection();

			PreparedStatement statement = connection.prepareStatement(
					"UPDATE into projectportfoliomanagement.resources SET "
					+ "first_name = ?, "
					+ "last_name = ?, "
					+ "email_id = ?, "
					+ "report_to = ?,  "
					+ "skillset = ?, "
					+ "experience = ?, "
					+ "designation = ?, "
					+ "department = ?, project_id=? WHERE employee_id=?");

			statement.setString(1, resource.getFirstName());
			statement.setString(2, resource.getLastName());
			statement.setString(3, resource.getEmailId());
			statement.setString(4, resource.getReportTo());
			statement.setString(5, resource.getSkillSet());
			statement.setInt(6, resource.getExperience());
			statement.setString(7, resource.getDesignation());
			statement.setString(8, resource.getDepartment());			
			statement.setInt(9, resource.getEmployeeId());
			if(resource.getProject() != null)
			{
				statement.setInt(10, resource.getProject().getProjectId());
			}
			else
			{
				statement.setInt(10, 0);
			}
			statement.executeUpdate();
			
			return resource;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error while updating Resource, please try again");
		}
		
	}
}
