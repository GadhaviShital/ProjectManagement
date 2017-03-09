package edu.rivier.ppms.budget;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
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

@Consumes("application/json")
@Produces("application/json")
public class BudgetService {

	private ClientService clientService = new ClientService();
	
	@GET
	@Path("/budget")
	public List<Budget> getBudget() throws Exception {
		List<Budget> budgets = new ArrayList<Budget>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			Connection connection = edu.rivier.ppms.db.Connection.getConnection();
			statement = connection
					.prepareStatement("SELECT * FROM projectportfoliomanagement.budget");
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int budgetId = resultSet.getInt("budget_Id");
				Date incomeDate = resultSet.getDate("income_date");
				String revenueType = resultSet.getString("revenue_type");
				String revenueAmount = resultSet.getString("revenue_amount");
				Date expenceDate = resultSet.getDate("expence_date");
				String description = resultSet.getString("description");
				int amount = resultSet.getInt("amount");
				int clientId = resultSet.getInt("client_id");
				
				Budget budget = new Budget();
				budget.setBudgetId(budgetId);
				budget.setIncomeDate(incomeDate);
				budget.setRevenueType(revenueType);
				budget.setRevenueAmount(revenueAmount);
				budget.setExpenceDate(expenceDate);
				budget.setDescription(description);
				budget.setAmount(amount);

				Client client = clientService.getClient(clientId);
				budget.setClient(client);
				budgets.add(budget);
			}

			return budgets;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error while getting budget");
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
	@Path("/budget/{budgetId}")
	public Budget getBudget(@PathParam("budgetId") int budgetId) throws Exception {
		
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			Connection connection = edu.rivier.ppms.db.Connection.getConnection();
			statement = connection
					.prepareStatement("SELECT * FROM projectportfoliomanagement.budget where budget_id="+budgetId);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Date incomeDate = resultSet.getDate("income_date");
				String revenueType = resultSet.getString("revenue_type");
				String revenueAmount = resultSet.getString("revenue_amount");
				Date expenceDate = resultSet.getDate("expence_date");
				String description = resultSet.getString("description");
				int amount = resultSet.getInt("amount");
				int clientId = resultSet.getInt("client_id");
				
				Budget budget = new Budget();
				budget.setBudgetId(budgetId);
				budget.setIncomeDate(incomeDate);
				budget.setRevenueType(revenueType);
				budget.setRevenueAmount(revenueAmount);
				budget.setExpenceDate(expenceDate);
				budget.setDescription(description);
				budget.setAmount(amount);

				Client client = clientService.getClient(clientId);
				budget.setClient(client);
				
				return budget;
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error while getting budget");
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
	@Path("/budget")
	public Budget add(Budget budget) throws Exception {

		try {
			java.sql.Connection connection = edu.rivier.ppms.db.Connection.getConnection();

			PreparedStatement statement = connection.prepareStatement(
					"INSERT into projectportfoliomanagement.budget(income_date,revenue_type,revenue_amount,expence_date,description,amount, client_id) "
					+ "values (?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

			statement.setDate(2, budget.getIncomeDate());
			statement.setString(3, budget.getRevenueType());
			statement.setString(4, budget.getRevenueAmount());
			statement.setDate(5, budget.getExpenceDate());
			statement.setString(6, budget.getDescription());
			statement.setInt(7, budget.getAmount());
			statement.setInt(8, budget.getClient().getClientId());
			statement.executeUpdate();

			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					budget.setBudgetId(generatedKeys.getInt("budget_id"));
				}
			}

			return budget;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error while adding budget, please try again");
		}

	}

	@PUT
	@Path("/budget")
	public Budget edit(Budget budget) throws Exception {

		try {
			java.sql.Connection connection = edu.rivier.ppms.db.Connection.getConnection();

			PreparedStatement statement = connection.prepareStatement(
					"UPDATE projectportfoliomanagement.budget set "
					+ "income_date = ?, "
					+ "revenue_type = ?,"
					+ "revenue_amount = ?,"
					+ "expence_date = ?,"
					+ "description = ?,"
					+ "amount  = ?,"
					+ "client_id=?"
					+ "where budget_id=?");

			statement.setDate(1, budget.getIncomeDate());
			statement.setString(2, budget.getRevenueType());
			statement.setString(3, budget.getRevenueAmount());
			statement.setDate(4, budget.getExpenceDate());
			statement.setString(5, budget.getDescription());
			statement.setInt(6, budget.getAmount());
			statement.setInt(7, budget.getClient().getClientId());
			statement.setInt(8, budget.getBudgetId());

			statement.executeUpdate();

			return budget;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error while updating budget, please try again");
		}

	}
	
	@DELETE
	@Path("/budget/{budgetId}")
	public void delete(@PathParam("budgetId") int budgetId) throws Exception{

		PreparedStatement statement = null;
		try {
			java.sql.Connection connection = edu.rivier.ppms.db.Connection.getConnection();

			statement = connection.prepareStatement(
					"DELETE FROM projectportfoliomanagement.budget WHERE budget_id="+budgetId);
			statement.execute();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error while deleting budget, please try again");
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
