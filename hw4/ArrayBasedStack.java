package edu.iastate.cs228.hw4;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class ArrayBasedStack<T> implements PureStack<T> {
	private static final int DEFAULT_SIZE = 10;
	/**
	 * Index of next available cell in array.
	 */
	private int top;
	/**
	 * The data store.
	 */
	private T[] data;

	public ArrayBasedStack() {
		// Unchecked warning is unavoidable
		data = (T[]) new Object[DEFAULT_SIZE];
	}

	@Override
	public void push(T item) {
		checkCapacity();
		data[top++] = item;
	}

	@Override
	public T pop() {
		if (top == 0)
			throw new NoSuchElementException();
		T ret = data[--top];
		data[top] = null;
		return ret;
	}

	@Override
	public T peek() {
		if (top == 0)
			throw new NoSuchElementException();
		return data[top - 1];
	}

	@Override
	public int size() {
		return top;
	}

	@Override
	public boolean isEmpty() {
		return top == 0;
	}

	private void checkCapacity() {
		if (top == data.length) {
			data = Arrays.copyOf(data, data.length * 2);
		}
	}

}