package org.skywang.linear;

import java.lang.reflect.Array;
import java.util.Arrays;

public class HelloStack<E> {
  private E[] elementArray = null;
  private int size = 0;
  private static final int DEFAULT_CAPACITY = 8; // can be configurable

  public HelloStack(Class<E> type, int size) {
    this.elementArray = (E[]) Array.newInstance(type, size);
  }

  public HelloStack(Class<E> type) {
    this(type, DEFAULT_CAPACITY);
  }

  public void push(E e) {
    if (this.size == elementArray.length) {
      ensureCapacity();
    }
    elementArray[size++] = e;
  }

  public E pop() {
    if (this.size == 0) {
      throw new IndexOutOfBoundsException("can not pop a empty stack array!");
    }
    E ret = elementArray[--size];
    elementArray[size] = null;
    return ret;
  }

  public E peek() {
    if (this.size == 0) {
      throw new IndexOutOfBoundsException("can not peak a empty stack array!");
    }
    return elementArray[size - 1];
  }

  public int size() {
    return this.size;
  }

  public boolean isEmpty() {
    return this.size == 0;
  }

  public String toString() {
    StringBuilder result = new StringBuilder();
    result.append("[");
    if (this.size == 0) {
      result.append("]");
    } else {
      for (int i = 0; i < this.size; i++) {
        result.append(elementArray[i]);
        if (i != (size - 1)) {
          result.append(", ");
        }
      }
      result.append("]");
    }
    return result.toString();
  }

  private void ensureCapacity() {
    if (elementArray == null) {
      return;
    }
    int length = this.elementArray.length * 2;
    elementArray = Arrays.copyOf(elementArray, length);
  }
}
