package com.certotrack

import javax.mail.*

/**

 Insert a new position:
 #Departed Lake Sylvia, Ft Lauderdale, FL
 @ 4,20,2011,1532,26,06.242,080,06.716

 Get list of all data:
 @@
 Note: please keep it in correct text format so that any part of it could be turned around and re-submitted eg # line followed by @ line, etc

 Delete a point
 ##  month, day, year, time
 ## 4,20,2011,1532

 Note: 4 numeric text values, comma delimited.
 Ideally you delete all points that match assuming there were multiple points with same date and time.

 Delete an entire track and all points
 Subject: Track Name
 ##DELETE TRACK##

 * */

class EmailReaderService {


   def interestedParties = ["dkmmbarnes@gmail.com","kd4p@yahoo.com"]

   def emailService
   def mapService

   enum LineType {
      COMMENT, LIST_POSITIONS, ADD_POSITIONS, DELETE_POSITIONS, LIST_TRACKS, DELETE_TRACK, UNKNOWN
   }

   static transactional = true

   Folder folder
   Store store
   Track track
   def subject
   def sentDate

   /**
    *  Check for new email, if any, process the request
    */
   def processNewEmail() {

      try {

         getMessages()?.each { message ->

            def sender = getSender(message)
            sentDate = message.sentDate
            subject = message.subject

//            debugMessage(message, sender)
            if (shouldProcessMessage(message, sender)) {
               def logInfo = processMessage(message, sender)
               sendConfirmationEmail(logInfo, sender)
               sendUpdatedMapEmail()
            }

         }

         try {
            folder.close(true);
            store.close();
         } catch (Exception e1) {}

      } catch (Exception e) {
         e.printStackTrace()
      }
   } // processNewEmail()

   private shouldProcessMessage(message, sender) {
      def trackee = Trackee.findByEmailAddress(sender)

      if (!trackee) { // if not verified sender
         println "Ignoring email from ${sender} with subject (${subject})!\n"
         return false
      }

      def tracks = Track.findAllByNameAndSentDate(subject, message.sentDate).findAll() { Track track ->
         track.trackee == trackee
      }

      if (tracks?.size()) {
         println "Got duplicate email from ${sender} with subject (${subject}) and date of ${message.sentDate}!\n"
         return false
      }

      true
   }

   private debugMessage(message, sender) {
      subject = message.subject
      def sentDate = message.sentDate
      def recDate = message.receivedDate
      println "Sender = $sender; Subject = $subject; Sent Date = $sentDate; Rec'd Date: $recDate; flags = ${message.flags}; recent? ${message.flags.contains(Flags.Flag.RECENT)}; seen? ${message.flags.contains(Flags.Flag.SEEN)}"
   }

   private processMessage(message, sender) {
      println "Processing message from $sender with subject ($subject)."

      def trackee = Trackee.findByEmailAddress(sender)

      track = null
      def logInfo = ""
      def body = ""
      def comment = "None"

      InputStream stream = message.getInputStream();
      while (stream.available() != 0) {
         body += (char) stream.read();
      }

      body.eachLine() { line ->

         switch (getLineType(line)) {
            case LineType.COMMENT:
               comment = line.split("#")[1]
               break;

            case LineType.LIST_POSITIONS:
               logInfo = listPositions(line, subject, logInfo, trackee)
               break;

            case LineType.ADD_POSITIONS:
               logInfo = addPositions(line, subject, comment, sender, logInfo, trackee)
               break;

            case LineType.DELETE_POSITIONS:
               logInfo = deletePositions(line, subject, logInfo, trackee)
               break;

            case LineType.DELETE_TRACK:
               logInfo = deleteTrack(subject, logInfo, trackee)
               break;

            case LineType.LIST_TRACKS:
               logInfo = listTracks(logInfo, trackee)
               break;

            case LineType.UNKNOWN:
            default:
               logInfo += "Skipping: ${line}\n"

         } // switch type of line

      } // each line in body

      return logInfo

   }

   private getSender(message) {
      message.getFrom()[0].address
   }

   private getMessages() {
      try {
         String host = "pop.gmail.com"
         //String user = "certotracktest@gmail.com"
         String user = "certotrack@gmail.com"
         String password = "Iluvsoftware!"

         // Get system properties 
         Properties properties = System.getProperties();

         // Get the default Session object.
         Session session = Session.getDefaultInstance(properties);
         //session.setDebug(true)

         // Get a Store object that implements the specified protocol.
         store = session.getStore("pop3s");

         //Connect to the current host using the specified username and password.
         store.connect(host, 995, user, password);

         //Create a Folder object corresponding to the given name.
         folder = store.getFolder("Inbox");

         // Debugging:
         Folder[] folders = store.getDefaultFolder().list("*");
         folders.each { it ->
            if ((it.getType() & javax.mail.Folder.HOLDS_MESSAGES) != 0) {
               println(it.fullName + ": " + it.messageCount);
            }
         }

         // Open the Folder.
         folder.open(Folder.READ_ONLY);
         println "message count = ${folder.messageCount}; unread msgs = ${folder.unreadMessageCount}; new msgs = ${folder.unreadMessageCount}"

//         FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
//         def unreadMessages = folder.search(ft);
//         unreadMessages.each {
//            println "unread message = ${it.subject}; ${it.sentDate}"
//            it.setFlag(Flags.Flag.SEEN, true)
//            it.saveChanges()
//         }

         folder.getMessages();

      } catch (Exception e) {
         println "Exception getting messages: " + e
      }
   }

   private getLineType(line) {
      def lineType = LineType.UNKNOWN

      if (line && line.size() > 1 && line[0] == '#' && line && line[1] != '#') {
         lineType = LineType.COMMENT
      } else if (line && line.size() > 1 && line[0] == '@' && line && line[1] != '@') {
         lineType = LineType.ADD_POSITIONS
      } else if (line && line.size() > 1 && line[0] == '@' && line && line[1] == '@') {
         lineType = LineType.LIST_POSITIONS
      } else if (line && line.trim()?.toUpperCase() == '##DELETE TRACK##') {
         lineType = LineType.DELETE_TRACK
      } else if (line && line.size() > 1 && line[0] == '#' && line && line[1] == '#') {
         lineType = LineType.DELETE_POSITIONS
      } else if (subject?.trim() == '@@') {
         lineType = LineType.LIST_TRACKS
      }
      lineType
   }

   private getOrCreateNewTrack(trackName, emailAddress, trackee) {
      track = Track.findByName(trackName)
      if (!track) {
         track = new Track(name: trackName, createdByEmail: emailAddress, trackee: trackee, sentDate: sentDate)
         track.save(flush: true, failOnError: true)
      }
      track
   }

   /*
   Email Format:
   #Departed Lake Sylvia, Ft Lauderdale, FL
   @ 4,20,2011,1532,26,06.242,080,06.716
   */

   private createNewPosition(comment, positionData) {
      def date = new Date().parse('MM-dd-yyyy HHmm',
              "${positionData[0].trim()}-${positionData[1].trim()}-${positionData[2].trim()} ${positionData[3].trim()}")
      def latitude = new Double(positionData[4].trim()) + new Double(positionData[5].trim()) / 60
      def longitude = 0 - (new Double(positionData[6].trim()) + new Double(positionData[7].trim()) / 60)
      def position = new Position(track: track, comment: comment,
              positionDate: date, latitude: latitude, longitude: longitude)
      position
   }

   private sendConfirmationEmail(logInfo, senderEmail) {
      def subjectLine
      def msgBody

      if (track) {
         subjectLine = "Position Tracker Success"
         msgBody = "Success!\nDetails:\nTrack: ${track.name}\n${logInfo}"
      } else {
         subjectLine = "Position Tracker Error"
         msgBody = "Errors!\nDetails:\n${logInfo}"
      }

      emailService.sendEmail(senderEmail, subjectLine, msgBody)
   }

   private sendUpdatedMapEmail() {
      def staticMapUrl = mapService.getMapUrl(track)
      def subjectLine = "Updated Track ($subject)!"
      def msgBody = "<div id='map'><img src='${staticMapUrl}' /></div>"

      interestedParties.each { recipient ->
         emailService.sendHtmlEmail(recipient, subjectLine, msgBody)
      }
   }

   private listPositions(it, subject, logInfo, trackee) {
      logInfo += "Position report for ${subject} generated on ${new Date()}\n"
      track = Track.findByName(subject)

      if (track) {
         Position.findAllByTrack(track, [sort: "positionDate"]).each() {
            logInfo += it.toString() + "\n"
         }
      } else {
         logInfo += "Track ${subject} not found!"
      }
      logInfo
   }

   private addPositions(it, subject, comment, sender, logInfo, trackee) {
      def line = it.split("@")[1].split(",")
      if (line.size() == 8) {
         if (!track) {
            track = getOrCreateNewTrack(subject, sender, trackee)
            track.save(flush: true, failOnError: true)
            track = Track.get(track.id)
         }
         def position = createNewPosition(comment, line)
         position.save(flush: true, failOnError: true)
         logInfo += "Position [${position}]\n"
      } else {
         logInfo += "Wrong number of parameters: ${line}\n"
      }
      logInfo
   }

   private listTracks(logInfo, trackee) {
      track = new Track(name: 'All Tracks')
      logInfo += "All Tracks for ${trackee.emailAddress}:\n"
      Track.findAllByTrackee(trackee, [order: 'name']).each {
         logInfo += it.name + '\n'
      }
      logInfo
   }

   private deleteTrack(subject, logInfo, trackee) {
      track = Track.findByNameAndTrackee(subject, trackee)
      if (track) {
         logInfo += "Deleted track ${track.name}\n"
         logInfo += "Positions deleted:\n"
         logInfo += track.positions
         track.delete(failOnError: true)
      }
      else {
         logInfo += "Track ${track.name} not found!"
      }
      logInfo
   }

   private deletePositions(it, subject, logInfo, trackee) {
      // This is a delete position(s) email
      track = Track.findByName(subject)
      if (track) {
         def line = it.split("##")[1].split(",")
         if (line.size() < 4) {
            logInfo += "Not enough parameters to delete position(s): ${line}\n"
         } else {
            def month = line[0].trim().size() == 1 ? "0" + line[0].trim() : line[0].trim()
            def day = line[1].trim().size() == 1 ? "0" + line[1].trim() : line[1].trim()
            def year = line[2].trim()
            def time = line[3].trim()
            def hour = time[0] + time[1]
            def min = time[2] + time[3]

            def pointsToDelete = Position.findAllByTrack(track).findAll() {
               def itMonth = new java.text.SimpleDateFormat("MM").format(it.positionDate)
               def itDay = new java.text.SimpleDateFormat("dd").format(it.positionDate)
               def itYear = new java.text.SimpleDateFormat("yyyy").format(it.positionDate)
               def itHour = new java.text.SimpleDateFormat("HH").format(it.positionDate)
               def itMin = new java.text.SimpleDateFormat("mm").format(it.positionDate)
               itMonth == month && itDay == day && itYear == year && itHour == hour && itMin == min
            }
            if (!pointsToDelete) {
               logInfo += "No matching points to delete found for track ${track.name}. \n"
            } else {
               logInfo += "Deleted point(s) for track ${track.name}: \n"
               logInfo += pointsToDelete
               if (pointsToDelete?.size() == track.positions?.size()) {
                  track.delete(failOnError: true)
                  logInfo += "\nDeleted empty track ${track.name}\n"
               }
               else {
                  pointsToDelete.each() { point ->
                     track.removeFromPositions(point);
                     point.delete(failOnError: true)
                  }
                  track.save(failOnError: true);
               }
            }
         }
      } else {
         logInfo += "Track ${subject} not found!"
      }
      logInfo
   }


}
