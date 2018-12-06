package guts;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class Queue_spec {

  protected final String itemA = "itemA";
  protected final String itemB = "itemB";
  protected final String itemC = "itemC";
  protected final String itemD = "itemD";

  Queue<String> queue = new Queue<>(3);

  @Nested
  public class A_new_queue {

    @Test
    public void is_empty() {
      assertThat(queue.length()).isEqualTo(0);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, Integer.MAX_VALUE})
    public void preserves_positive_bounding_capacity(int capacity) {

      assertThatCode(() -> new Queue<String>(capacity))
          .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    public void cannot_be_created_with_non_positive_bound_capacity(int capacity) {

      assertThatThrownBy(() -> new Queue<String>(capacity))
          .isInstanceOf(IllegalArgumentException.class);
    }
  }

  @Nested
  public class An_empty_queue {

    @Test
    public void dequeues_an_empty_value() {

      assertThat(queue.dequeue()).isEqualTo(Optional.empty());
    }

    @Test
    public void remains_empty_when_null_enqeued() {

      queue.enqueue(null);

      assertThat(queue.length()).isEqualTo(0);
    }

    @ParameterizedTest
    @ValueSource(strings = {itemA, ""})
    public void becomes_non_empty_when_non_null_value_enqeued(String value) {

      queue.enqueue(value);

      assertThat(queue.length()).isEqualTo(1);
    }
  }

  @Nested
  public class A_non_empty_queue {

    @BeforeEach
    void partiallyFillQueue() {
      queue.enqueue(itemA);
      queue.enqueue(itemB);
    }

    @Test
    public void dequeues_values_in_order_enqeued() {

      assertThat(queue.dequeue()).isEqualTo(Optional.of(itemA));
      assertThat(queue.dequeue()).isEqualTo(Optional.of(itemB));
    }

    @Test
    public void remains_unchanged_when_null_enqeued() {

      int lengthBefore = queue.length();

      queue.enqueue(null);

      assertThat(queue.length()).isEqualTo(lengthBefore);
    }

    @Nested
    public class that_is_not_full {

      @ParameterizedTest
      @ValueSource(strings = {itemC, ""})
      public void becomes_longer_when_non_null_value_enqeued(String value) {

        int lengthBefore = queue.length();

        queue.enqueue(value);

        assertThat(queue.length()).isEqualTo(lengthBefore + 1);
      }

      @Test
      public void becomes_full_when_enqeued_up_to_capacity() {

        queue.enqueue(itemC);
        queue.enqueue(itemD);

        assertThat(queue.length()).isEqualTo(3);
      }
    }

    @Nested
    public class that_is_full {

      @BeforeEach
      void fillQueue() {
        queue.enqueue(itemC);
      }

      @Test
      public void ignores_further_enqueued_values() {

        queue.enqueue(itemD);

        assertThat(queue.length()).isEqualTo(3);
        assertThat(queue.dequeue()).isEqualTo(Optional.of(itemA));
      }

      @Test
      public void becomes_non_full_when_dequeued() {

        queue.dequeue();

        assertThat(queue.length()).isLessThan(queue.capacity());
      }
    }
  }
}
