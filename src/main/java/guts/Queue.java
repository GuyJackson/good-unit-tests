package guts;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Optional;

public class Queue<T> {

  private Deque<T> items = new LinkedList<>();
  private final int capacity;

  public Queue(int boundingCapacity) {

    if (boundingCapacity < 1) throw new IllegalArgumentException();

    capacity = boundingCapacity;
  }

  public int length() {
    return items.size();
  }

  public int capacity(){
    return capacity;
  }

  public boolean enqueue(T last){
    boolean enqueueing = last != null && length() < capacity();

    if(enqueueing){
      items.addLast(last);
    }
    return enqueueing;
  }

  public Optional<T> dequeue(){
    return Optional.ofNullable(items.pollFirst());
  }
}
