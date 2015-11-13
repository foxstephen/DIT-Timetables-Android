package org.stephenfox.dittimetables.timetable;


import android.util.Log;

/**
 * A class that manages a Timetable.
 */
public class Timetable {

  private TimetableWeek timetableWeek;
  private String courseID;


  public Timetable(TimetableWeek timetableWeek) {
    this.timetableWeek = timetableWeek;
    Log.d("12345:", timetableWeek.toString());
  }



  public void setTimetableWeek(TimetableWeek timetableWeek) {
    this.timetableWeek = timetableWeek;
  }

  public void setCourseID(String courseID) {
    this.courseID = courseID;
  }

  public TimetableWeek getTimetableWeek() {
    return timetableWeek;
  }

  public TimetableDay getTimetableDay(Day day) { return timetableWeek.getDay(day); }

  public int getDayCount() { return timetableWeek.getNumberOfDays(); }

  public String getCourseID() {
    return courseID;
  }

  @Override
  public String toString() {
    return this.timetableWeek.toString();
  }
}
