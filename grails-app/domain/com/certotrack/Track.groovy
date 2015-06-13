package com.certotrack

class Track {

   String name
   String createdByEmail
   Date createdDate = new Date()
   Date sentDate
   Trackee trackee

   static hasMany = [positions: Position]

   static constraints = {
   }

   String toString() {
      name + "; " + createdByEmail + "; " + createdDate
   }
}
