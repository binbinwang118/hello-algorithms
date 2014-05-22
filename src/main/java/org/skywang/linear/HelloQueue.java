package org.skywang.linear;

import java.lang.reflect.Array;
import java.util.Arrays;

public class HelloQueue<E> {
  private E[] elementArray = null;
  private int size = 0;
  private final static int DEFAULT_CAPACITY = 8;

  public HelloQueue(Class<E> type, int size) {
    this.elementArray = (E[]) Array.newInstance(type, size);
  }

  public HelloQueue(Class<E> type) {
    this(type, DEFAULT_CAPACITY);
  }

  public int size() {
    return this.size;
  }

  public void add(E e) {
    if (this.size == elementArray.length) {
      ensureCapacity();
    }
    elementArray[size++] = e;
  }

  public E front() {
    if (this.size == 0) {
      return null;
    }
    return elementArray[0];
  }

  public E pop() {
    if (this.size == 0) {
      return null;
    }
    E result = elementArray[0];
    for (int i = 0; i < size - 1; i++) {
      elementArray[i] = elementArray[i + 1];
    }
    elementArray[size - 1] = null;
    this.size--;
    return result;
  }

  public boolean isEmpty() {
    return this.size == 0;
  }

  public String toString() {
    StringBuilder result = new StringBuilder();
    result.append("[");
    if (this.size == 0) {
      result.append("]");
    }
    for (int i = 0; i < size; i++) {
      result.append(elementArray[i]);
      if (i != (size - 1)) {
        result.append(", ");
      }
    }
    result.append("]");
    return result.toString();
  }

  private void ensureCapacity() {
    if (elementArray == null) {
      return;
    }
    int length = elementArray.length * 2;
    elementArray = Arrays.copyOf(elementArray, length);
  }



}
