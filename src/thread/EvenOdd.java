package thread;

public class EvenOdd {

	public static void main(String[] args) {

		Runnable r1 = () -> {
			try {
				for (int i = 2; i <= 10; i = i + 2) {
					if (i % 2 == 0)
						System.out.println("Even number: " + i);
					Thread.sleep(100);
				}

			} catch (Exception e) {
			}
		};

		Runnable r2 = () -> {
			try {
				for (int i = 1; i <= 10; i = i + 2) {
					if (i % 2 != 0)
						System.out.println("Odd number: " + i);
					Thread.sleep(100);
				}
			} catch (Exception e) {
			}
		};

		Thread t1 = new Thread(r1);
		Thread t2 = new Thread(r2);

		t1.start();
		t2.start();
		System.out.println("finished in main");
	}

}
