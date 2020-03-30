import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PessimisticDemo {
	
	private int isolationLevel = Connection.TRANSACTION_REPEATABLE_READ; // Try and change this to TRANSACTION_SERIALIZABLE
	
	public void updateCustomerOrderCount(int customerId) throws Exception {
		
		String countOrdersSql = "SELECT COUNT(*) FROM Orders WHERE CustomerId = ?";
		String updateCustomerSql = "UPDATE Customers SET OrderCount = ? WHERE CustomerId = ?";
		
		Connection conn = Database.getConnection(isolationLevel);
		
		try {			
			conn.setAutoCommit(false); // setting autocommit to false ensures explicit transactions
					
			PreparedStatement countOrders = conn.prepareStatement(countOrdersSql);
			countOrders.setInt(1, customerId);
			ResultSet rs = countOrders.executeQuery();
			
			int orderCount = 0;
			
			if(rs.next()) {
				orderCount = rs.getInt(1);
			}			
			
			System.out.println(Thread.currentThread().getName() + " Counted "+ orderCount +" orders");
			
			Thread.sleep(50);
			
			PreparedStatement updateCustomer = conn.prepareStatement(updateCustomerSql);
			updateCustomer.setInt(1, orderCount);
			updateCustomer.setInt(2, customerId);
			updateCustomer.execute();
			
			System.out.println(Thread.currentThread().getName() + " Updated customer");
			
			Thread.sleep(50);
			
			conn.commit();			

			System.out.println(Thread.currentThread().getName() +" Committed");
												
		} catch (SQLException e) {

			conn.rollback();
			throw e;
			
		} finally {
			
			conn.setAutoCommit(true);
		}
	}	
	
	public void createOrder(int customerId, int total) throws Exception {
		
		String createOrderSql = "INSERT INTO Orders VALUES (?, GETDATE(), ?, 'A')";
		
		Connection conn = Database.getConnection(isolationLevel);
		
		try {		
			
			PreparedStatement createOrder = conn.prepareStatement(createOrderSql);
			createOrder.setInt(1, customerId);
			createOrder.setInt(2, total);
			
			createOrder.execute();
			
			System.out.println(Thread.currentThread().getName() + " Created order");
			
			Thread.sleep(100);
			
			conn.commit();		

			System.out.println(Thread.currentThread().getName() +" Committed");
			
		} catch (SQLException e) {

			conn.rollback();
			throw e;
			
		} finally {
			
			conn.setAutoCommit(true);
		}
	}
}
