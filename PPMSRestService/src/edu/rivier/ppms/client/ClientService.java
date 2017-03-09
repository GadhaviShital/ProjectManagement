package edu.rivier.ppms.client;

import java.sql.Connection;
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

@Consumes("application/json")
@Produces("application/json")
public class ClientService {
	
	@GET
	@Path("/client")
	public List<Client> getClients() throws Exception {
		List<Client> clients = new ArrayList<Client>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			Connection connection = edu.rivier.ppms.db.Connection.getConnection();
			statement = connection
					.prepareStatement("SELECT * FROM projectportfoliomanagement.client");
			resultSet = statement.executeQuery();

			while (resultSet.next())
				
			{
				int clientId = resultSet.getInt("client_id");
				String clientName=resultSet.getString("client_name");
				String address1=resultSet.getString("address1");
				String address2=resultSet.getString("address2");
				String city=resultSet.getString("city");
				String state=resultSet.getString("state");
				int zipcode=resultSet.getInt("zipcode");
				String country=resultSet.getString("country");
				String description =resultSet.getString("description");
				Date startDate=resultSet.getDate("start_date");
				Date dueDate=resultSet.getDate("due_date");
				String primaryPhoneNumber=resultSet.getString("primary_phone_number");
				String priority =resultSet.getString("priority");
				String status =resultSet.getString("status");
				String primaryEmailId =resultSet.getString("primary_email_id");
				
				Client client = new Client();
				client.setClientId(clientId);
				client.setClientName(clientName);
				client.setAddress1(address1);
				client.setAddress2(address2);
				client.setCity(city);
				client.setState(state);
				client.setZipcode(zipcode);
				client.setCountry(country);
				client.setDescription(description);
				client.setStartDate(startDate);
				client.setDueDate(dueDate);
				client.setPriority(priority);
				client.setStatus(status);		
				client.setPrimaryPhoneNumber(primaryPhoneNumber);
				client.setEmailId(primaryEmailId);
				
				clients.add(client);
				
				
			}
			return clients;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error while getting clients");
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
	@Path("/client/{clientId}")
	public Client getClient(@PathParam("clientId") int clientId) throws Exception {
		
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			Connection connection = edu.rivier.ppms.db.Connection.getConnection();
			statement = connection
					.prepareStatement("SELECT * FROM projectportfoliomanagement.client where client_id = "+clientId);
			resultSet = statement.executeQuery();

			while (resultSet.next())				
			{
				String clientName=resultSet.getString("client_name");
				String address1=resultSet.getString("address1");
				String address2=resultSet.getString("address2");
				String city=resultSet.getString("city");
				String state=resultSet.getString("state");
				int zipcode=resultSet.getInt("zipcode");
				String country=resultSet.getString("country");
				String description =resultSet.getString("description");
				Date startDate=resultSet.getDate("start_date");
				Date dueDate=resultSet.getDate("due_date");
				String primaryPhoneNumber=resultSet.getString("primary_phone_number");
				String priority =resultSet.getString("priority");
				String status =resultSet.getString("status");
				String primaryEmailId =resultSet.getString("primary_email_id");
				
				Client client = new Client();
				client.setClientId(clientId);
				client.setClientName(clientName);
				client.setAddress1(address1);
				client.setAddress2(address2);
				client.setCity(city);
				client.setState(state);
				client.setZipcode(zipcode);
				client.setCountry(country);
				client.setDescription(description);
				client.setStartDate(startDate);
				client.setDueDate(dueDate);
				client.setPriority(priority);
				client.setStatus(status);		
				client.setPrimaryPhoneNumber(primaryPhoneNumber);
				client.setEmailId(primaryEmailId);
				
				return client;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error while getting clients");
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
	@Path("/client")
	public Client add(Client client) throws Exception{

		PreparedStatement statement = null;
		try {
			java.sql.Connection connection = edu.rivier.ppms.db.Connection.getConnection();

			statement = connection.prepareStatement(
					"INSERT into projectportfoliomanagement.client( client_name, address1,address2,city,state,zipcode"
					+ "country,primary_phone_number,primary_email_id, description,  status, "
					+ "priority,start_date,due_date) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

			statement.setString(1, client.getClientName());
			statement.setString(2, client.getAddress1());
			statement.setString(3, client.getAddress2());
			statement.setString(4, client.getCity());
			statement.setString(5, client.getState());
			statement.setInt(6, client.getZipcode());
			statement.setString(7, client.getCountry());
			statement.setString(8, client.getPrimaryPhoneNumber());
			statement.setString(9, client.getEmailId());
			statement.setString(10, client.getDescription());
			statement.setString(11, client.getStatus());
			statement.setString(12, client.getPriority());
			statement.setDate(13, client.getStartDate());
			statement.setDate(14, client.getDueDate());

			statement.execute();
			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
	            if (generatedKeys.next()) {	            	
	            	client.setClientId(generatedKeys.getInt("client_id"));
	            }
	        }
			return client;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error while adding client, please try again");
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
	@Path("/client")
	public Client edit(Client client) throws Exception{

		PreparedStatement statement = null;
		try {
			java.sql.Connection connection = edu.rivier.ppms.db.Connection.getConnection();

			statement = connection.prepareStatement(
					"UPDATE into projectportfoliomanagement.client SET "
					+ "client_name = ?, "
					+ "address1 = ?, "
					+ "address2 = ?,"
					+ "city = ?,"
					+ "state = ?,"
					+ "zipcode = ?,"
					+ "country = ?,"
					+ "primary_phone_number = ?,"
					+ "primary_email_id = ?, "
					+ "description = ?, "
					+ "status = ?, "
					+ "priority = ?,"
					+ "start_date = ?,"
					+ "due_date = ?)");

			statement.setString(1, client.getClientName());
			statement.setString(2, client.getAddress1());
			statement.setString(3, client.getAddress2());
			statement.setString(4, client.getCity());
			statement.setString(5, client.getState());
			statement.setInt(6, client.getZipcode());
			statement.setString(7, client.getCountry());
			statement.setString(8, client.getPrimaryPhoneNumber());
			statement.setString(9, client.getEmailId());
			statement.setString(10, client.getDescription());
			statement.setString(11, client.getStatus());
			statement.setString(12, client.getPriority());
			statement.setDate(13, client.getStartDate());
			statement.setDate(14, client.getDueDate());

			statement.execute();
			
			return client;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error while updating client, please try again");
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
	@Path("/client/{clientId}")
	public void delete(@PathParam("clientId") int clientId) throws Exception{

		PreparedStatement statement = null;
		try {
			java.sql.Connection connection = edu.rivier.ppms.db.Connection.getConnection();

			statement = connection.prepareStatement(
					"DELETE FROM projectportfoliomanagement.client WHERE client_id="+clientId);
			statement.execute();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error while deleting client, please try again");
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
