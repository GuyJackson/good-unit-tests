package guts;

import static guts.LeapYearValidator.isLeapYear;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


public class Leap_year_spec {

  @Nested
  public class A_year_is_a_leap_year {

    @ParameterizedTest
    @ValueSource(ints = {2016, 1984, 4})
    void if_it_is_divisible_by_4_but_not_by_100(int year) {

      assertThat(isLeapYear(year)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(ints = {2400, 2000, 400})
    void if_it_is_divisible_by_400(int year) {

      assertThat(isLeapYear(year)).isTrue();
    }
  }

  @Nested
  public class A_year_is_not_a_leap_year {

    @ParameterizedTest
    @ValueSource(ints = {2018, 2017, 42, 1})
    void if_it_is_not_divisible_by_4(int year) {

      assertThat(isLeapYear(year)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(ints = {2100, 1900, 100})
    void if_it_is_divisible_by_100_but_not_by_400(int year) {

      assertThat(isLeapYear(year)).isFalse();
    }
  }

  @Nested
  public class A_year_is_supported {

    @ParameterizedTest
    @ValueSource(ints = {1, Integer.MAX_VALUE})
    void if_it_is_positive(int year) {

      assertThatCode(() -> isLeapYear(year))
          .doesNotThrowAnyException();
    }
  }

  @Nested
  public class A_year_is_not_supported {

    @Test
    void if_it_is_0() {

      assertThatThrownBy(() -> isLeapYear(0))
          .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -4, -100, -400})
    void if_it_is_negative(int year) {

      assertThatThrownBy(() -> isLeapYear(year))
          .isInstanceOf(IllegalArgumentException.class);
    }
  }
}