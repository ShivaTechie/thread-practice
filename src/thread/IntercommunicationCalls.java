package thread;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class IntercommunicationCalls {

	static volatile List<Integer> list = new LinkedList<>();
	int lockCount = 0;
	Lock lock = new ReentrantLock();

	public static void main(String[] args) throws InterruptedException {

		for (int i = 0; i < 5; i++) {
			list.add(i);
		}

		IntercommunicationCalls calls1 = new IntercommunicationCalls();

		Thread t1 = new Thread(() -> {
			try {

				// calls1.printNames("Shiva Verma", 200);
				calls1.addNumbersToTheList(list, "first");
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		});
		Thread t2 = new Thread(() -> {
			try {
				// calls1.printNames("Gauri Singh", 150);
				calls1.addNumbersToTheList(list, "second");
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		});

		t1.start();
		t2.start();
		Thread.sleep(4000);
		System.out.println(list);

	}

	public synchronized void printNames(String name, int time) throws InterruptedException {
		for (int i = 0; i < name.length(); i++) {

			System.out.println(Thread.currentThread() + " " + name.charAt(i));
			Thread.sleep(time);
		}
	}

	public void addNumbersToTheList(List<Integer> list, String type) throws InterruptedException {

		int n = list.size();
		synchronized (this) {
			for (int i = 0; i < 10; i++) {
				list.add(n + i);
				System.out.println(Thread.currentThread() + " with number is: " + (n + i));
				Thread.sleep(150);
			}
		}
		printLoops(type);
	}

	public void addNumbersToTheListFor(List<Integer> list, String type) throws InterruptedException {

		lock.lock();
		int n = list.size();
		int i = 0;
		
		for (Integer ele : list) {

			lock.lock();
			list.add(n + i);
			lock.unlock();
			System.out.println(Thread.currentThread() + " with number is: " + (n + i) + " ele: " + ele);
			Thread.sleep(150);
			// lockCount++;

		}
		lock.unlock();
		System.out.println("Total number of counts: " + lockCount);
		printLoops(type);
	}

	public void addNumbersToTheListIterator(List<Integer> list, String type) throws InterruptedException {

		lock.lock();
		System.out.println(Thread.currentThread() + " " + ((ReentrantLock) lock).isHeldByCurrentThread());

		int n = list.size();
		int i = 0;
		Iterator<Integer> itr = list.iterator();
		int m = n;
		while (itr.hasNext() && m != 0) {
			lock.lock();
			list.add(n + i);
			System.out.println(Thread.currentThread() + " " + (n + i));
			Thread.sleep(150);
			i++;
			m--;
			lockCount++;
			lock.unlock();
		}
		System.out.println(Thread.currentThread() + " " + ((ReentrantLock) lock).isHeldByCurrentThread());
		System.out.println("Total number of counts: " + lockCount);
		printLoops(type);
	}

	private void printLoops(String type) throws InterruptedException {

		Thread.sleep(200);
		for (int i = 0; i < 10; i++) {
			System.out.println(Thread.currentThread() + " " + type + " " + i);
		}
	}
}
