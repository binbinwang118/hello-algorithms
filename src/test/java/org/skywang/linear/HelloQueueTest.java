package org.skywang.linear;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HelloQueueTest {
  private static HelloQueue<String> queue;
  
  @BeforeClass
  public static void globalInit() {
    System.out.print("init\t");
    queue = new HelloQueue<String>(String.class, 16);
    queue.add("aa");
    queue.add("bb");
    queue.add("cc");
    System.out.println(queue.toString());
  }
  
  @AfterClass
  public static void globalDestroy() {
    System.out.print("finish\t");
    System.out.println(queue.toString());
  }
  
  @Test
  public void test_a_Size() {
    assertEquals(3, queue.size());
  }
  
  @Test
  public void test_b_Add() {
    queue.add("hello");
    assertEquals(4, queue.size());
  }
  
  @Test
  public void test_c_front() {
    assertEquals("aa", queue.front());
    assertEquals(4, queue.size());
  }
  
  @Test
  public void test_d_pop() {
    assertEquals("aa", queue.pop());
    assertEquals(3, queue.size());
  }
  
  @Test
  public void test_e_IsEmpty() {
    assertTrue(!queue.isEmpty());
  }
}
