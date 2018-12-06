package guts;

public class LeapYearValidator {
  public static boolean isLeapYear(int year) {

    if (year < 1) throw new IllegalArgumentException();

    return (year % 4 == 0 && year % 100 != 0 ||
            year % 400 == 0);
  }
}
