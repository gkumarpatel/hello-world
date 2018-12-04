package cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/***
 * Sample input out put
 * Input : 6 2 S 2 1 S 1 1 S 2 3 S 4 1 G 1 G 2
 * Output : -1 3
 *
 * Input : 53 6 G 9 S 1 14 G 1 S 10 1 S 10 2 S 9 2 G 14 G 15 G 4 G 5 G 13 G 5 S 3 13 G 10 G 9 S 5 11 G 6 G 1 G 3 S 3 7 S 7 4 S 13 7 G 2 G 4 S 4 9 G 3 S 12 1 G 9 G 12 G 13 G 10 G 2 G 12 G 15 S 13 10 S 9 6 S 11 4 S 15 14 S 15 5 G 6 S 11 14 G 15 S 6 13 S 10 6 G 2 S 9 2 S 1 4 S 11 7 G 14 G 1 S 13 13 S 9 9 S 11 4
 * Output : -1 14 -1 -1 -1 -1 -1 -1 2 2 -1 14 13 -1 -1 7 -1 1 7 -1 -1 1 -1 -1 5 -1 -1 4
 */

public class LRUDoublyLinkedListMap {
	private int capacity;
	private Map<Integer, Integer> cache;
	private LinkedList<Integer> linkedList;

	public LRUDoublyLinkedListMap(int capacity) {
		this.capacity = capacity;
		this.cache = new HashMap<>(capacity);
		this.linkedList = new LinkedList<>();
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int numberOfOperation = Integer.parseInt(sc.next());
		int capacity = Integer.parseInt(sc.next());
		LRUDoublyLinkedListMap lru = new LRUDoublyLinkedListMap(capacity);

		List<Integer> solution = new ArrayList();

		for (int i = 0; i < numberOfOperation; i++) {
			char operation = sc.next().charAt(0);
			if (operation == 'G') {
				int key = sc.nextInt();
				solution.add(lru.get(key));
			} else if (operation == 'S') {
				int key = sc.nextInt();
				int value = sc.nextInt();
				lru.set(key, value);
			} else {
				System.out.println("Wrong Input");
				return;
			}
		}

		for (Integer i : solution) {
			System.out.print(i + " ");
		}
	}

	public int get(int key) {
		if (cache.containsKey(key)) {
			updateLinkedList(key);
			return cache.get(key);
		}
		return -1;
	}

	private void updateLinkedList(int key) {
		linkedList.remove(Integer.valueOf(key));
		linkedList.addFirst(key);
	}

	public void set(int key, int value) {
		if (cache.containsKey(key)) {
			cache.put(key, value);
			updateLinkedList(key);
		} else {
			if (cache.size() >= capacity) {
				removeLRU();
			}
			insertIntoCache(key, value);
		}
	}

	private void insertIntoCache(int key, int value) {
		cache.put(key, value);
		linkedList.addFirst(key);
	}

	private void removeLRU() {
		int key = findLRU();
		cache.remove(key);
		linkedList.removeLast();
	}

	private int findLRU() {
		return linkedList.getLast();
	}
}
