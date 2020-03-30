
public class ConcurrencyDemoProgram {

	public static void main(String[] args) {

		//OptimisticDemo demo = new OptimisticDemo(); 
		PessimisticDemo demo = new PessimisticDemo();

		Thread t1 = new Thread("T1") {
			
			public void run() {
				try {
					
					demo.updateCustomerOrderCount(1);
				
				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		};
		
		Thread t2 = new Thread("T2") {
			
			public void run() {
				try {
					
					demo.createOrder(1, 100);		
					
				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		};
		
		t1.start();
		t2.start();
	}
}
