package org.skywang.cache;

import java.util.concurrent.ConcurrentHashMap;

/**
 * reinvent the wheel, implement the lru cache w/i doublylinkedlist
 * doubly-linked-list data structure as below,
 * null <-- Head <--> node(1) <--> node(2) <--> node(3) <--> node(4) <--> Tail --> null
 */
public class LruCacheWheel<K, V> {
  private CacheEntry<K, V> head;
  private CacheEntry<K, V> tail;
  private int capacity;
  private ConcurrentHashMap<K, CacheEntry<K, V>> cache =
      new ConcurrentHashMap<K, CacheEntry<K, V>>(capacity);

  public LruCacheWheel(int capacity) {
    this.capacity = capacity;
    head = new CacheEntry<K, V>(null, null);
    tail = new CacheEntry<K, V>(null, null);
    head.next = tail;
    tail.prev = head;
  }

  public V get(K key) {
    CacheEntry<K, V> entry = cache.get(key);
    if (entry == null) {
      return null;
    }

    if (cache.size() == 1) {
      return entry.value;
    }

    removeCacheEntry(entry);
    addCacheEntryAfterHead(entry);
    return entry.value;
  }

  public void put(K key, V value) {
    if (capacity == 0) {
      return;
    }

    CacheEntry<K, V> entry = cache.get(key);
    if (entry != null) {
      removeCacheEntry(entry);
      addCacheEntryAfterHead(entry);
      entry.value = value;
    } else {
      entry = new CacheEntry<K, V>(key, value);
      cache.put(key, entry);
      addCacheEntryAfterHead(entry);
      if (cache.size() > capacity) {
        cache.remove(tail.prev.key);
        removeCacheEntry(tail.prev);
      }
    }
  }

  public boolean containsKey(K key) {
    return cache.containsKey(key);
  }

  private void addCacheEntryAfterHead(CacheEntry<K, V> entry) {
    entry.next = head.next;
    entry.prev = head;
    head.next.prev = entry;
    head.next = entry;
  }

  private void removeCacheEntry(CacheEntry<K, V> entry) {
    if (entry == head || entry == tail) {
      return;
    }
    entry.next.prev = entry.prev;
    entry.prev.next = entry.next;
  }


  private class CacheEntry<K, V> {
    public CacheEntry<K, V> prev;
    public CacheEntry<K, V> next;
    public K key;
    public V value;

    public CacheEntry(K key, V value) {
      this.key = key;
      this.value = value;
    }
  }


  public static void main(String[] args) {
    LruCacheWheel<String, Integer> cache = new LruCacheWheel<String, Integer>(5);
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
