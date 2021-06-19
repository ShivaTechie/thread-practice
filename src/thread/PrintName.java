package thread;

public class PrintName {

	public static void main(String[] args) throws InterruptedException {

		String str = "Shiva Verma";

		Runnable runOddPlaces = () -> {
			print(str, "odd");
		};

		Runnable runEvenPlaces = () -> {
			print(str, "even");
		};
		Thread t1 = new Thread(runEvenPlaces);
		Thread t2 = new Thread(runOddPlaces);
		t2.start();
		t2.join();
		t1.start();
		// t2.join();
	}

	private static void print(String str, String type) {

		try {
			if (type.equalsIgnoreCase("even"))
				for (int i = 0; i < str.length(); i = i + 2) {
					System.out.println(str.charAt(i) + " even");
					Thread.sleep(500);
				}
			else if (type.equalsIgnoreCase("odd"))
				for (int i = 1; i < str.length(); i = i + 2) {
					System.out.println(str.charAt(i) + " odd");
					Thread.sleep(500);
				}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}
