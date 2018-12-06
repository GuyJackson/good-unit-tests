package guts;

import java.util.ArrayList;
import java.util.List;

public class Stack<T> {

  private List<T> items = new ArrayList();

  public int depth() {
    return items.size();
  }

  public T top() {
    if (depth() == 0) {
      throw new IllegalArgumentException();
    }

    return items.get(0);
  }

  public void pop() {
    if (depth() == 0) {
      throw new IllegalArgumentException();
    }
    items.remove(0);
  }

  public void push(T newTop) {
    items.add(0, newTop);
  }
}
