package edu.iastate.cs228.hw4;

public interface PureStack<T> {
	void push(T item);

	T pop();

	T peek();

	boolean isEmpty();

	int size();
}
