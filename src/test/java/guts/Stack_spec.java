package guts;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

public class Stack_spec {

  @Nested
  public class A_new_stack {

    @Test
    public void is_empty(){

      var stack = new Stack<String>();

      assertThat(stack.depth()).isEqualTo(0);
    }
  }

  @Nested
  public class An_empty_stack {

    private Stack<String> stack;

    @BeforeEach
    public void is_created(){
      stack = new Stack<>();
    }

    @Test
    public void throws_when_queried_for_its_top_item(){
      assertThatThrownBy(() -> stack.top())
          .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void throws_when_popped(){
      assertThatThrownBy(() -> stack.pop())
          .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void acquires_depth_by_retaining_a_pushed_item_as_its_top(){
      var item = "item";

      stack.push(item);
      assertThat(stack.depth()).isEqualTo(1);
      assertThat(stack.top()).isEqualTo(item);
    }
  }

  @Nested
  public class A_non_empty_stack {

    private Stack<String> stack;

    String itemA = "itemA";
    String itemB = "itemB";

    @BeforeEach
    public void is_created(){
      stack = new Stack<>();
      stack.push(itemA);
    }

    @Test
    public void becomes_deeper_by_retaining_a_pushed_item_as_its_top(){

      int depthBefore = stack.depth();

      stack.push(itemB);

      assertThat(stack.depth()).isEqualTo(depthBefore + 1);
      assertThat(stack.top()).isEqualTo(itemB);
    }

    @Test
    public void on_popping_reveals_tops_in_reverse_oder_of_pushing(){

      stack.push(itemB);

      stack.pop();

      assertThat(stack.top()).isEqualTo(itemA);
    }
  }
}
