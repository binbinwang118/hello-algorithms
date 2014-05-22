package org.skywang.linear;

/**
 * Doubly-linked list implementation link index from 1 to nodeCount, which is number of node in the
 * list (does not include the head & tail) For example, a doubly-linked list as below, index is from
 * 1 to 4. Head <--> node(1) <--> node(2) <--> node(3) <--> node(4) <--> Tail
 */
public class DoubleLinkedList<E> {

  /** link head */
  private DLNode<E> head;
  /** link tail */
  private DLNode<E> tail;
  /** link node count */
  private int nodeCount;

  /** link node */
  private class DLNode<E> {
    public DLNode<E> prevNode;
    public DLNode<E> nextNode;
    public E element;

    public DLNode(DLNode<E> prevNode, DLNode<E> nextNode, E element) {
      this.prevNode = prevNode;
      this.nextNode = nextNode;
      this.element = element;
    }

    public DLNode<E> getPrevNode() {
      return prevNode;
    }

    public void setPrevNode(DLNode<E> prevNode) {
      this.prevNode = prevNode;
    }

    public DLNode<E> getNextNode() {
      return nextNode;
    }

    public void setNextNode(DLNode<E> nextNode) {
      this.nextNode = nextNode;
    }

    public E getElement() {
      return element;
    }

    public void setElement(E element) {
      this.element = element;
    }

    public String toString() {
      if (element == null) {
        return null;
      } else {
        return element.toString();
      }
    }

  }

  /**
   * initialize an empty double link
   */
  public DoubleLinkedList() {
    head = new DLNode<E>(null, null, null);
    tail = new DLNode<E>(null, null, null);
    // set the reference
    head.setNextNode(tail);
    tail.setPrevNode(head);
    nodeCount = 0;
  }

  /**
   * get the node count of the list
   */
  public int size() {
    return nodeCount;
  }

  /**
   * return the link is empty or not
   */
  public boolean isEmpty() {
    return nodeCount == 0;
  }

  /**
   * get the value of node at index position
   */
  public E get(int index) {
    return getNode(index).getElement();
  }

  /**
   * get the value of first node
   */
  public E getFirst() {
    return getNode(1).getElement();
  }

  /**
   * get the value of last node
   */
  public E getLast() {
    return getNode(nodeCount).getElement();
  }

  /**
   * insert E before the index position
   */
  public void insertBeforeIndex(E e, int index) {
    if (index == 0) {
      DLNode<E> insertNode = new DLNode<E>(head, head.getNextNode(), e);
      head.getNextNode().setPrevNode(insertNode);
      head.setNextNode(insertNode);
      nodeCount++;
    } else {
      DLNode<E> indexNode = getNode(index);
      DLNode<E> insertNode = new DLNode<E>(indexNode.getPrevNode(), indexNode, e);
      indexNode.getPrevNode().setNextNode(insertNode);
      indexNode.setPrevNode(insertNode);
      nodeCount++;
    }
  }

  /**
   * insert E after the index position
   */
  public void insertAfterIndex(E e, int index) {
    if (index == nodeCount) {
      DLNode<E> node = new DLNode<E>(tail.getPrevNode(), tail, e);
      tail.getPrevNode().setNextNode(node);
      tail.setPrevNode(node);
      nodeCount++;
    } else {
      DLNode<E> indexNode = getNode(index);
      DLNode<E> insertNode = new DLNode<E>(indexNode, indexNode.getNextNode(), e);
      indexNode.getNextNode().setPrevNode(insertNode);
      indexNode.setNextNode(insertNode);
      nodeCount++;
    }
  }

  /**
   * insert E after the first node
   */
  public void insertFirst(E e) {
    insertBeforeIndex(e, 0);
  }

  /**
   * append E in the last node of the link
   */
  public void appendLast(E e) {
    insertAfterIndex(e, nodeCount);
  }

  /**
   * remove node at position index
   */
  public void removeNode(int index) {
    DLNode<E> removeNode = getNode(index);
    removeNode.getPrevNode().setNextNode(removeNode.getNextNode());
    removeNode.getNextNode().setPrevNode(removeNode.getPrevNode());
    nodeCount--;
    removeNode.setPrevNode(null);
    removeNode.setNextNode(null);
    removeNode = null;
  }

  /**
   * remove the first node
   */
  public void removeFirstNode() {
    if (this.isEmpty()) {
      throw new IndexOutOfBoundsException("Double Linked list is empty, cannot remove element");
    }
    removeNode(1);
  }

  public void removeLast() {
    if (this.isEmpty()) {
      throw new IndexOutOfBoundsException("Double Linked list is empty, cannot remove element");
    }
    removeNode(nodeCount);
  }

  public String toString() {
    StringBuilder result = new StringBuilder();
    result.append("[");
    DLNode<E> node = head.getNextNode();
    for (int i = 0; i < nodeCount; i++) {
      result.append(node);
      if (i != (nodeCount - 1)) {
        result.append(", ");
      }
      node = node.getNextNode();
    }
    result.append("]");
    return result.toString();
  }

  /** get the node of index position */
  private DLNode<E> getNode(int index) {
    DLNode<E> node = null;
    if (index < 0 || index > nodeCount) {
      throw new IndexOutOfBoundsException();
    } else if (index > (nodeCount / 2)) {
      DLNode<E> reverseNode = tail.getPrevNode();
      for (int i = nodeCount; i > index; i--) {
        reverseNode = reverseNode.getPrevNode();
      }
      node = reverseNode;
    } else {
      DLNode<E> forwardNode = head.getNextNode();
      for (int j = 0; j < (index - 1); j++) {
        forwardNode = head.nextNode;
      }
      node = forwardNode;
    }
    return node;
  }

  /** get the next node of reference node */
  private DLNode<E> getNext(DLNode<E> node) {
    if (node == tail) {
      throw new IndexOutOfBoundsException("reference node can not be equal to the tail");
    }
    return node.getNextNode();
  }

  /** get the previous node of the reference node */
  private DLNode<E> getPrev(DLNode<E> node) {
    if (node == head) {
      throw new IndexOutOfBoundsException("reference node can not be equal to the head");
    }
    return node.getPrevNode();
  }
}
