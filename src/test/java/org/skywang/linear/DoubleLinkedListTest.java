package org.skywang.linear;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DoubleLinkedListTest {
  private static DoubleLinkedList<String> doubleLink;

  @BeforeClass
  public static void globalInit() {
    System.out.print("init\t");
    doubleLink = new DoubleLinkedList<String>();
    doubleLink.insertFirst("aa");
    doubleLink.appendLast("bb");
    doubleLink.insertAfterIndex("cc", 1);
    System.out.println(doubleLink.toString());
  }

  @AfterClass
  public static void globalDestroy() {
    System.out.print("finish\t");
    System.out.println(doubleLink.toString());
  }

  @Before
  public void init() {}

  @After
  public void destroy() {}

  @Test
  public void test_a_Size() {
    int size = doubleLink.size();
    assertEquals(3, size);
  }

  @Test
  public void test_b_IsEmpty() {
    boolean empty = doubleLink.isEmpty();
    assertTrue(!empty);
  }

  @Test
  public void test_c_Get() {
    String result = doubleLink.get(2);
    assertEquals("cc", result);
  }

  @Test
  public void test_d_GetFirst() {
    String result = doubleLink.getFirst();
    assertEquals("aa", result);
  }

  @Test
  public void test_e_GetLast() {
    String result = doubleLink.getLast();
    assertEquals("bb", result);
  }

  @Test
  public void test_f_InsertAfterIndex() {
    doubleLink.insertAfterIndex("dd", 2);
    assertEquals("dd", doubleLink.get(3));
  }

  @Test
  public void test_g_InsertBeforeIndex() {
    doubleLink.insertBeforeIndex("ee", 2);
    assertEquals("ee", doubleLink.get(2));
  }

  @Test
  public void test_h_InsertFirst() {
    doubleLink.insertFirst("hello");
    assertEquals("hello", doubleLink.get(1));
  }

  @Test
  public void test_i_AppendLast() {
    doubleLink.appendLast("world");
    assertEquals("world", doubleLink.getLast());
  }

  @Test
  public void test_j_RemoveNode() {
    int size = doubleLink.size();
    doubleLink.removeNode(2);
    assertEquals((size - 1), doubleLink.size());
  }

  @Test
  public void test_k_RemoveFirst() {
    int size = doubleLink.size();
    doubleLink.removeFirstNode();
    assertEquals((size - 1), doubleLink.size());
  }

  @Test
  public void test_l_RemoveLast() {
    int size = doubleLink.size();
    doubleLink.removeLast();
    assertEquals((size - 1), doubleLink.size());
  }

}
