package org.stephenfox.dittimetables.timetable;


/**
 * A class that manages a Timetable.
 */
public class Timetable {

  private TimetableWeek timetableWeek;


  public Timetable(TimetableWeek timetableWeek) {
    this.timetableWeek = timetableWeek;
  }

  public void setTimetableWeek(TimetableWeek timetableWeek) {
    this.timetableWeek = timetableWeek;
  }

  public TimetableWeek getTimetableWeek() {
    return timetableWeek;
  }


  public TimetableDay getTimetableDay(Day day) { return timetableWeek.getDay(day); }


  public int getDayCount() { return timetableWeek.getNumberOfDays(); }


  @Override
  public String toString() {
    return this.timetableWeek.toString();
  }
}
