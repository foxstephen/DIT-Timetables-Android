package org.stephenfox.dittimetables.timetable;

import android.content.Context;
import android.util.Log;

import org.stephenfox.dittimetables.database.TimetableDatabase;
import org.stephenfox.dittimetables.network.CustomAsyncTask;
import org.stephenfox.dittimetables.network.JsonParser;
import org.stephenfox.dittimetables.network.WeekDownloader;

import java.util.ArrayList;

/**
 * Use this class to determine the source/ location
 * of where a courses timetable will be fetched i.e network or database.
 */
public class TimetableSourceRetriever {

  Context context;
  String courseCode;
  String courseID;

  public TimetableSourceRetriever(Context context) {
    this.context = context;
  }


  /**
   * This method fetches a timetable from the appropriate source
   * either network or database.
   *
   * @param courseCode The course code of the timetable to fetch for e.g. DT228/3 etc.
   * @param courseID The id of the course on the server if needs be that it must be fetched there.
   * @param callback This will be called when the timetable has been found either from the server or
   *                 the database. A null value will be passed in the event a timetable cannot
   *                 be created, i.e there's insufficient information for it most likely when coming
   *                 from the server.
   **/
  public void
  fetchTimetable(String courseCode, String courseID, TimetableRetrieverCallback callback) {

    this.courseCode = courseCode;
    this.courseID = courseID;

    TimetableDatabase database = new TimetableDatabase(context);
    if (database.timetableExists(courseCode)) {
      // Generate a timetable from database.
      Log.d("SF", "Course already exists in the database");
    } else {
      Log.d("SF", "We need to go download the course");
      String url = TimetableSourceRetriever.constructURLToDownloadTimetable(courseID);
      fetchTimetableFromServer(url, callback);
    }
  }


  /**
   * A helper method to fetch timetable data from the server. When found or, possibly not found the
   * callback will be message.
   *
   * @param url The url to download the timetable from the server.
   * @param callback The callback to message when the timetable has been downloaded.
   * */
  private void fetchTimetableFromServer(String url, final TimetableRetrieverCallback callback) {
    WeekDownloader weekDownloader = new WeekDownloader();
    weekDownloader.downloadWeekForCourse(url, new CustomAsyncTask.AsyncCallback() {
      @Override
      public void finished(Object data) {
        Timetable timetable = createTimetableFromNetworkData((String)data);
        callback.timetableRetrieved(timetable);
      }
    });
  }


  /**
   * Creates a Timetable object from json data from the server.
   *
   * @param data The json data.
   * @return A new timetable instance if the json data was sufficient to create the timetable
   *         otherwise, null value will be passed.
   **/
  private Timetable createTimetableFromNetworkData(String data) {
    try {
      TimetableGenerator generator = new TimetableGenerator(parseJSON(data));
      return generator.generateTimetable(courseCode);
    } catch (InvalidTimetableDataException e) {
      return null;
    }
  }


  private ArrayList<TimetableSession> parseJSON(String JSONData) {
    JsonParser parser = new JsonParser();
    return parser.parseSessionsForTimetable(JSONData);
  }


  public static String constructURLToDownloadTimetable(int id) {
    return "http://timothybarnard.org/timetables/classes.php?courseID=" + id + "&semester=1";
  }

  public static String constructURLToDownloadTimetable(String id) {
    return "http://timothybarnard.org/timetables/classes.php?courseID=" + id + "&semester=1";
  }


  public interface TimetableRetrieverCallback {
    void timetableRetrieved(Timetable timetable);
  }
}