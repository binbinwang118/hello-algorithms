package org.skywang.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * a LRU (lease recently used) cache implementation 
 * (1) there is capacity limitation of the lru cache 
 * (2) cache access time should be 0(1) 
 * (3) cache entry replacement algorithm is least recently used
 * 
 * utilize the LinkedHashMap to implement the lru cache 
 * (1) maintain a HashMap for fast entry lookup
 * (2) maintain a doubly linked list of entries either in AccessOrder or InsertionOrder 
 * (3) has a method removeEldestEntry() which can override to return true when cache size exceed the specified
 * capacity
 */
public class ConcurrentLruCache<K, V> extends LinkedHashMap<K, V> {
  private static final long serialVersionUID = 1L;
  private int capacity;
  private final static float loadFactor = 0.75f;
  private final static int initCapacity = (int) Math.ceil((1 / loadFactor) + 1);

  private final ReentrantReadWriteLock cacheLock = new ReentrantReadWriteLock();
  private final Lock readLock = cacheLock.readLock();
  private final Lock writeLock = cacheLock.writeLock();

  public ConcurrentLruCache(int capacity) {
    super(initCapacity, loadFactor, true);
    this.capacity = capacity;
  }
  
  public ConcurrentLruCache() {
    this(initCapacity);
  }

  @Override
  protected boolean removeEldestEntry(Map.Entry<K, V> entry) {
    return size() > this.capacity;
  }

  @Override
  public V get(Object key) {
    try {
      readLock.lock();
      return super.get(key);
    } finally {
      readLock.unlock();
    }
  }

  @Override
  public V put(K key, V value) {
    try {
      writeLock.lock();
      return super.put(key, value);
    } finally {
      writeLock.unlock();
    }
  }

  @Override
  public boolean containsKey(Object key) {
    try {
      readLock.lock();
      return super.containsKey(key);
    } finally {
      readLock.unlock();
    }
  }

  @Override
  public int size() {
    try {
      readLock.lock();
      return super.size();
    } finally {
      readLock.unlock();
    }
  }

  @Override
  public void clear() {
    try {
      writeLock.lock();
      super.clear();
    } finally {
      writeLock.unlock();
    }
  }

  public Collection<Map.Entry<K, V>> getAll() {
    try {
      readLock.lock();
      return new ArrayList<Map.Entry<K, V>>(super.entrySet());
    } finally {
      readLock.unlock();
    }
  }


  public static void main(String[] args) {
    ConcurrentLruCache<String, Integer> cache = new ConcurrentLruCache<String, Integer>(5);
    cache.put("a", 1);
    cache.put("b", 2);
    cache.put("c", 3);
    cache.put("d", 4);
    cache.put("e", 5);
    System.out.println("key-a-check-1 - does cache contain key \"a\":" + cache.containsKey("a"));
    cache.put("f", 6);
    System.out.println("key-a-check-2 - does cache contain key \"a\":" + cache.containsKey("a"));

    System.out.println("key-b-check-1 - does cache contain key \"b\":" + cache.containsKey("b"));
    cache.get("b");
    cache.put("g", 7);
    System.out.println("key-b-check-2 - does cache contain key \"b\":" + cache.containsKey("b"));

    System.out.println("key-c-check-1 - does cache contain key \"b\":" + cache.containsKey("c"));
  }

}