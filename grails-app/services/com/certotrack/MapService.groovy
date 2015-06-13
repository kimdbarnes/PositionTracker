package com.certotrack

class MapService {

   def emailService

   def getMapUrl(trackInstance) {
      def positionInstanceList = Position.findAllByTrack(trackInstance, [sort: "positionDate"])
      if (positionInstanceList.size() == 0) {
         return
      }

      def staticMapUrl = "http://maps.googleapis.com/maps/api/staticmap?sensor=false&size=1024x1024&maptype=satellite"

      def indexOfLastPoint = positionInstanceList.size() - 1

      if (!indexOfLastPoint) {
         staticMapUrl += buildUrl(positionInstanceList, '&markers=color:green|')
         staticMapUrl += buildUrl(positionInstanceList, '&center=')
         staticMapUrl += "&zoom=14"
      }
      else {
         staticMapUrl += buildUrl(positionInstanceList, '&path=color:blue|', 0, indexOfLastPoint)
         staticMapUrl += buildUrl(positionInstanceList, '&markers=color:blue|', 0, indexOfLastPoint - 1)
         staticMapUrl += buildUrl(positionInstanceList, '&markers=color:green|', -1, -1)
      }

      staticMapUrl
   }

   private buildUrl(positionInstanceList, baseUrl, startIndex = 0, endIndex = 0) {
      baseUrl + positionInstanceList.sort { it.date }[startIndex..endIndex].collect {
         "${it.latitudeDesc},${it.longitudeDesc}"

      }.join('|')
   }


}
