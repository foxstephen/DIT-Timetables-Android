package org.stephenfox.dittimetables.timetable;


import java.util.ArrayList;

public class TimetableWeek {


  private ArrayList<TimetableDay> days;

  /**
   * The number of days the timetable is spread over.
   */
  private int numberOfDays;


  /**
   * Instantiates a new instance with an array of timetable days
   *
   * @param days The days for the week.
   */
  public TimetableWeek(ArrayList<TimetableDay> days) {
    this.days = days;
    this.numberOfDays = days.size();
  }


  public TimetableWeek() {}


  public void addDay(TimetableDay day) {
    if (this.days == null) {
      this.days = new ArrayList<>();
    }
    this.days.add(day);
  }



  public int getNumberOfDays() { return numberOfDays; }



  /**
   * Returns a Day in the week.
   *
   * @param day The day to return.
   * @return The day.
   */
  public TimetableDay getDay(Day day) {
    return this.days.get(day.toInt());
  }



  @Override
  public String toString() {
    String returnString = getNumberOfDays() + "\n";
    for (TimetableDay day : days) {
      returnString += day.toString();
    }
    return returnString;
  }
}
