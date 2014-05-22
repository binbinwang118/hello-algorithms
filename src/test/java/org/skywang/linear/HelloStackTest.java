package org.skywang.linear;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HelloStackTest {
  private static HelloStack<String> stack;

  @BeforeClass
  public static void globalInit() {
    System.out.print("init\t");
    stack = new HelloStack<String>(String.class, 20);
    stack.push("aa");
    stack.push("bb");
    stack.push("cc");
    System.out.println(stack.toString());
  }

  @AfterClass
  public static void globalDestroy() {
    System.out.print("finish\t");
    System.out.println(stack.toString());
  }

  @Test
  public void test_a_Size() {
    assertEquals(3, stack.size());
  }

  @Test
  public void test_b_Peak() {
    assertEquals("cc", stack.peek());
  }

  @Test
  public void test_c_pop() {
    String top = stack.pop();
    assertEquals(2, stack.size());
    assertEquals("cc", top);
  }

  @Test
  public void test_d_push() {
    stack.push("hello");
    assertEquals("hello", stack.peek());
  }

  @Test
  public void test_e_isEmpty() {
    assertTrue(!stack.isEmpty());
  }
}
