package org.skywang.tree;

import java.util.NoSuchElementException;

import org.skywang.linear.HelloQueue;

public class BinarySearchTree<Key extends Comparable<Key>, Value> {
  // root of binary search tree
  private BstNode root;

  // binary search tree node structure
  private class BstNode {
    // node comparable key for sort
    private Key key;
    // node data
    private Value value;
    // left subtree
    private BstNode left;
    // right subtree
    private BstNode right;
    // number of nodes in sub-tree
    private int numNode;

    public BstNode(Key key, Value value, int numNode) {
      this.key = key;
      this.value = value;
      this.numNode = numNode;
    }

    public Key getKey() {
      return key;
    }

    public void setKey(Key key) {
      this.key = key;
    }

    public Value getValue() {
      return value;
    }

    public void setValue(Value value) {
      this.value = value;
    }

    public BstNode getLeft() {
      return left;
    }

    public void setLeft(BstNode left) {
      this.left = left;
    }

    public BstNode getRight() {
      return right;
    }

    public void setRight(BstNode right) {
      this.right = right;
    }

    public int getNumNode() {
      return numNode;
    }

    public void setNumNode(int numNode) {
      this.numNode = numNode;
    }

  }

  public BinarySearchTree() {
    this.root = null;
  }

  // pre-order traversal the tree
  public void preOrder() {
    preOrder(root);
  }

  private void preOrder(BstNode node) {
    if (node != null) {
      System.out.print(node.getKey() + ":" + node.getValue() + ", ");
      preOrder(node.left);
      preOrder(node.right);
    }
  }

  // in-order traversal the tree
  public void inOrder() {
    inOrder(root);
  }

  private void inOrder(BstNode node) {
    if (node != null) {
      inOrder(node.left);
      System.out.print(node.getKey() + ":" + node.getValue() + ", ");
      inOrder(node.right);
    }
  }

  // post-order traversal the tree
  public void postOrder() {
    postOrder(root);
  }

  private void postOrder(BstNode node) {
    if (node != null) {
      postOrder(node.left);
      postOrder(node.right);
      System.out.print(node.getKey() + ":" + node.getValue() + ", ");
    }
  }

  // get size (node #) of the tree
  public int size() {
    return size(root);
  }

  private int size(BstNode node) {
    if (node == null) {
      return 0;
    } else {
      return node.getNumNode();
    }
  }

  // is the tree empty
  public boolean isEmpty() {
    return this.root == null;
  }

  // does the tree contain key-value pair w/i the given key
  public boolean contain(Key key) {
    return get(key) != null;
  }

  // return the associated value of the given key. null if the given key is not existed
  public Value get(Key key) {
    return get(root, key);
  }

  private Value get(BstNode node, Key key) {
    if (node == null) {
      return null;
    }

    int cmp = node.key.compareTo(key);
    if (cmp > 0) {
      return get(node.left, key);
    } else if (cmp < 0) {
      return get(node.right, key);
    } else {
      return node.getValue();
    }
  }

  // put the key-value pair into the tree, if key is already exists, update the value
  public void put(Key key, Value value) {
    if (value == null) {
      delete(key);
      return;
    }
    root = put(root, key, value);
    assert check();
  }

  private BstNode put(BstNode node, Key key, Value value) {
    if (node == null) {
      return new BstNode(key, value, 1);
    }
    int cmp = node.key.compareTo(key);
    if (cmp > 0) {
      node.left = put(node.getLeft(), key, value);
    } else if (cmp < 0) {
      node.right = put(node.getRight(), key, value);
    } else {
      node.value = value;
    }
    node.setNumNode(1 + size(node.getLeft()) + size(node.getRight()));
    return node;
  }

  // delete the minimal node from the tree
  public void deleteMin() {
    if (isEmpty()) {
      throw new NoSuchElementException("can not perform deleteMin in a empty tree");
    }
    root = deleteMin(root);
    assert check();
  }

  private BstNode deleteMin(BstNode node) {
    if (node.getLeft() == null) {
      return node.getRight();
    }
    node.left = deleteMin(node.getLeft());
    node.setNumNode(1 + size(node.getLeft()) + size(node.getRight()));
    return node;
  }

  // delete the maximal node from the tree
  public void deleteMax() {
    if (isEmpty()) {
      throw new NoSuchElementException("can not perform deleteMax in a empty tree");
    }
    root = deleteMax(root);
    assert check();
  }

  private BstNode deleteMax(BstNode node) {
    if (node.getRight() == null) {
      return node.left;
    }
    node.right = deleteMax(node.getRight());
    node.setNumNode(1 + size(node.getRight()) + size(node.getLeft()));
    return node;
  }

  // delete a key-value pair from the subtree at given root node
  public void delete(Key key) {
    root = delete(root, key);
    assert check();
  }

  private BstNode delete(BstNode node, Key key) {
    if (node == null) {
      return null;
    }
    int cmp = node.getKey().compareTo(key);
    if (cmp > 0) {
      node.left = delete(node.getLeft(), key);
    } else if (cmp < 0) {
      node.right = delete(node.getRight(), key);
    } else {
      if (node.right == null) {
        return node.getLeft();
      }
      if (node.left == null) {
        return node.getRight();
      }
      BstNode tempNode = node;
      node = getMin(tempNode.getRight());
      node.right = deleteMin(tempNode.getRight());
      node.left = tempNode.getLeft();
      tempNode = null;
    }
    node.setNumNode(1 + size(node.getLeft()) + size(node.getRight()));
    return node;
  }

  // get the minimal node from the tree
  public BstNode min() {
    return getMin(root);
  }

  private BstNode getMin(BstNode node) {
    if (node == null) {
      return null;
    }
    while (node.left != null) {
      node = node.getLeft();
    }
    return node;
  }

  // get the maximal node from the tree
  public BstNode max() {
    return getMax(root);
  }

  private BstNode getMax(BstNode node) {
    if (node == null) {
      return null;
    }
    while (node.right != null) {
      node = node.getRight();
    }
    return node;
  }

  // find the floor node (largest node in BST tree less than or equal to key)
  public BstNode floor(Key key) {
    return floor(root, key);
  }

  private BstNode floor(BstNode node, Key key) {
    if (node == null) {
      return null;
    }
    int cmp = key.compareTo(node.getKey());
    if (cmp < 0) {
      return floor(node.getLeft(), key);
    } else if (cmp > 0) {
      BstNode temp = floor(node.getRight(), key);
      if (temp == null) {
        return node;
      } else {
        return temp;
      }
    } else {
      return node;
    }
  }

  // find the ceil node (smallest node in BST tree larger than or equal to key)
  public BstNode ceil(Key key) {
    return ceil(root, key);
  }

  private BstNode ceil(BstNode node, Key key) {
    if (node == null) {
      return null;
    }
    int cmp = key.compareTo(node.getKey());
    if (cmp > 0) {
      return ceil(node.right, key);
    } else if (cmp < 0) {
      BstNode temp = ceil(node.left, key);
      if (temp == null) {
        return node;
      } else {
        return temp;
      }
    } else {
      return node;
    }
  }

  // select the node that at rank index k
  public BstNode select(int k) {
    if (k < 0 || k > size()) {
      return null;
    }
    return select(root, k);
  }

  private BstNode select(BstNode node, int k) {
    if (node == null) {
      return null;
    }

    int leftNum = size(node.left);
    if (leftNum > k) {
      return select(node.left, k);
    } else if (leftNum < k) {
      return select(node.right, (k - leftNum - 1));
    } else {
      return node;
    }
  }

  // get the number of key in the tree that smaller the given key
  public int rank(Key key) {
    return rank(root, key);
  }

  private int rank(BstNode node, Key key) {
    if (node == null) {
      return 0;
    }
    int cmp = key.compareTo(node.key);
    if (cmp > 0) {
      return (1 + size(node.left) + rank(node.right, key));
    } else if (cmp < 0) {
      return rank(node.left, key);
    } else {
      return size(node.left);
    }
  }

  // store the keys in queue with two given keys lo & hi
  public HelloQueue<Key> keys() {
    return keys(min().key, max().key);
  }

  public HelloQueue<Key> keys(Key lo, Key hi) {
    HelloQueue<Key> queue = new HelloQueue<Key>(null);
    keys(root, queue, lo, hi);
    return queue;
  }

  private void keys(BstNode node, HelloQueue<Key> queue, Key lo, Key hi) {
    if (node == null) {
      return;
    }
    int cmplo = lo.compareTo(node.key);
    int cmphi = hi.compareTo(node.key);
    if (cmplo < 0) {
      keys(node.left, queue, lo, hi);
    }
    if (cmplo <= 0 && cmphi >= 0) {
      queue.add(node.key);
    }
    if (cmphi > 0) {
      keys(node.right, queue, lo, hi);
    }
  }

  // get the number of node between the two given key
  public int size(Key lo, Key hi) {
    if (lo.compareTo(hi) > 0) {
      return 0;
    }
    if (contain(hi)) {
      return rank(hi) - rank(lo) + 1;
    } else {
      return rank(hi) - rank(lo);
    }
  }

  // get the height of the binary search tree
  public int height() {
    return height(root);
  }

  private int height(BstNode node) {
    if (node == null) {
      return 0;
    }
    return Math.max(height(node.left), height(node.right));
  }

  // check the integrity of the binary search tree
  private boolean check() {
    if (!isBinarySearchTree()) {
      System.out.println("Not int a symmetic order");
    }
    if (!isSizeConsistent()) {
      System.out.println("subtree # not consistent");
    }
    if (!isRankConsistent()) {
      System.out.println("ranks not consistent");
    }

    return isBinarySearchTree() && isSizeConsistent() && isRankConsistent();
  }

  private boolean isBinarySearchTree() {
    return isBinarySearchTree(root, null, null);
  }

  private boolean isBinarySearchTree(BstNode node, Key min, Key max) {
    if (node == null) {
      return true;
    }
    if (min != null && min.compareTo(node.key) >= 0) {
      return false;
    }
    if (max != null && max.compareTo(node.key) <= 0) {
      return false;
    }
    return isBinarySearchTree(node.left, min, node.key)
        && isBinarySearchTree(node.right, node.key, max);
  }

  private boolean isSizeConsistent() {
    return isSizeConsistent(root);
  }

  private boolean isSizeConsistent(BstNode node) {
    if (node == null) {
      return true;
    }
    if (node.getNumNode() != (1 + size(node.left) + size(node.right))) {
      return false;
    }
    return isSizeConsistent(node.left) && isSizeConsistent(node.right);
  }

  private boolean isRankConsistent() {
    for (int i = 0; i < size(); i++) {
      if (i != rank(select(i).key)) {
        return false;
      }
    }
    HelloQueue<Key> keys = keys();
    while (!keys.isEmpty()) {
      Key key = keys.pop();
      if (key.compareTo(select(rank(key)).key) != 0) {
        return false;
      }
    }
    return true;
  }

  public static void main(String[] args) {
    String[] keys = {"S", "E", "A", "R", "C", "H", "E", "X", "A", "M", "P", "L", "E"};
    BinarySearchTree<String, Integer> tree = new BinarySearchTree<String, Integer>();
    for (int i = 0; i < keys.length; i++) {
      tree.put(keys[i], i);
    }

    System.out.println("pre-order traversal...");
    tree.preOrder();
    System.out.println("\n");

    System.out.println("in-order traversal...");
    tree.inOrder();
    System.out.println("\n");

    System.out.println("post-order traversal...");
    tree.postOrder();
    System.out.println("\n");

  }
}
