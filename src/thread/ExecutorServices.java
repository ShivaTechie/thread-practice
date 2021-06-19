package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServices {

	public static void main(String[] args) throws InterruptedException {

		ExecutorService service = Executors.newFixedThreadPool(2);

		Runnable r1 = () -> {
			for (int i = 0; i < 10; i++) {
				System.out.println(Thread.currentThread() + " " + i);
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		Runnable r2 = () -> {
			for (int i = 0; i < 10; i++) {
				System.out.println(Thread.currentThread() + " " + i);
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

		service.submit(r1);
		service.submit(r2);
		service.shutdown();
		System.out.println(Thread.currentThread());
		// service.awaitTermination(3, TimeUnit.SECONDS);
	}
}
