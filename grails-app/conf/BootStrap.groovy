import com.certotrack.*
import java.text.SimpleDateFormat

class BootStrap {

   def springSecurityService

   def init = { servletContext ->
      Trackee missAdventure = Trackee.findByName("Miss Adventure") ?:
         new Trackee(name: 'Miss Adventure', emailAddress: 'kd4p@yahoo.com', specialCode: 'rootless').save(failOnError: true)

      Trackee testTrackee = Trackee.findByName("Miss Test") ?:
         new Trackee(name: 'Miss Test', emailAddress: 'dkmmbarnes@gmail.com', specialCode: 'roots').save(failOnError: true)

      def track1 = Track.get(1) ?:
         new Track(createdByEmail: 'dkmmbarnes@gmail.com', name: 'Isleta to Ft Pierce', trackee: testTrackee, sentDate: new Date()).save(failOnError: true)

      newTrackPosition(1, 'Hauled out at Riverside Marina', 27.479516666666665, -80.33211666666666, '2011-07-06 08:30:00', track1)
      newTrackPosition(15, 'Cast off Isleta Marina, Fajardo, Puerto Rico ', 18.338433333333334, -65.61995, '2011-06-29 05:55:00', track1)
      newTrackPosition(14, 'Underway m+e', 18.36938333333333, -65.61401666666667, '2011-06-29 06:24:00', track1)
      newTrackPosition(13, 'Underway m+g', 18.435566666666666, -65.6803, '2011-06-29 07:24:00', track1)
      newTrackPosition(8, 'Underway m+g', 19.8539, -67.79608333333333, '2011-06-30 05:43:00', track1)
      newTrackPosition(9, 'Underway m+g', 21.43635, -69.9266, '2011-07-01 05:53:00', track1)
      newTrackPosition(10, 'Underway g+e', 22.333266666666667, -72.06226666666667, '2011-07-02 05:55:00', track1)
      newTrackPosition(11, 'Underway m+e', 23.9767, -74.114, '2011-07-03 05:55:00', track1)
      newTrackPosition(6, 'Underway m+e', 25.199166666666667, -75.91306666666667, '2011-07-04 05:55:00', track1)
      newTrackPosition(12, 'Underway m+e', 25.199166666666667, -75.91306666666667, '2011-07-04 05:55:00', track1)
      newTrackPosition(7, 'Underway m+g+e', 26.21275, -78.08055, '2011-07-05 05:54:00', track1)
      newTrackPosition(5, 'Underway m+g', 26.638233333333332, -78.98936666666667, '2011-07-05 16:33:00', track1)
      newTrackPosition(4, 'Underway m+e', 27.44943333333333, -80.22773333333333, '2011-07-06 05:44:00', track1)
      newTrackPosition(3, 'Underway m+e', 27.465, -80.30971666666667, '2011-07-06 07:16:00', track1)
      newTrackPosition(2, 'Underway m+e', 27.465, -80.30971666666667, '2011-07-06 07:16:00', track1)

   }


   private newTrackPosition(positionId, comment, latitude, longitude, positionDateValue, track) {
      def positionDate = new SimpleDateFormat("yyyy-MM-dd H:m:s").parse(positionDateValue)

      Position.get(positionId) ?: new Position(comment: comment, latitude: latitude,
              longitude: longitude, positionDate: positionDate, track: track).save(failOnError: true)
   }

   def destroy = {
   }
}
