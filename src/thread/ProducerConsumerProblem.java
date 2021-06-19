package thread;

import java.util.LinkedList;
import java.util.Queue;

public class ProducerConsumerProblem {

	Queue<Integer> queue = new LinkedList<>();
	int maxQueue = 5;
	static ProducerConsumerProblem consumerProblem = new ProducerConsumerProblem();
	int jobNumber = 0;

	public static void main(String[] args) {

		Producer producer = new ProducerConsumerProblem().new Producer(consumerProblem);
		Consumer consumer = new ProducerConsumerProblem().new Consumer(consumerProblem);

		Thread t1 = new Thread(producer);
		Thread t2 = new Thread(consumer);
		t1.start();
		t2.start();
	}

	public void put() throws InterruptedException {
		while (true) {
			synchronized (this) {
				while (queue.size() == maxQueue) {
					consumerProblem.wait();
				}
				queue.add(jobNumber);
				System.out.println(Thread.currentThread() + " with job: " + jobNumber + " is added");
				Thread.sleep(500);
				jobNumber++;
				consumerProblem.notify();
			}
		}
	}

	public void get() throws InterruptedException {
		while (true) {
			synchronized (this) {
				while (queue.size() == 0) {
					consumerProblem.wait();
				}
				int job = queue.poll();
				System.out.println(Thread.currentThread() + " with job: " + job + " is fetched");
				Thread.sleep(500);
				consumerProblem.notify();
			}
		}
	}

	public boolean isQueueFull() {
		return queue.size() == 2;
	}

	public boolean isDataAvailable() {
		return queue.size() <= 2 && queue.size() > 0;
	}

	class Producer implements Runnable {

		ProducerConsumerProblem consumerProblem;

		public Producer(ProducerConsumerProblem consumerProblem) {
			this.consumerProblem = consumerProblem;
		}

		@Override
		public void run() {

			try {
				consumerProblem.put();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	class Consumer implements Runnable {

		ProducerConsumerProblem consumerProblem;

		public Consumer(ProducerConsumerProblem consumerProblem) {
			this.consumerProblem = consumerProblem;
		}

		@Override
		public void run() {
			try {
				consumerProblem.get();
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
		}

	}
}
