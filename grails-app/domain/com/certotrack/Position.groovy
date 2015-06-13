package com.certotrack

class Position {

    String comment
    Date positionDate = new Date()
    Double latitude
    Double longitude

    static belongsTo = [ track:Track ]

    static constraints = {
    }

    String toString() {
       def pDate = new java.text.SimpleDateFormat("MM, dd, yyyy").format(positionDate)
       def pTime = new java.text.SimpleDateFormat("HHmm").format(positionDate)
       def lon = longitude.abs()
       Integer latDegrees = Math.floor(latitude)
       def latMinutes = ((latitude - latDegrees) * 60).round(4)
       Integer lonDegrees = (Math.floor(longitude.abs()))
       def lonMinutes = ((longitude.abs() - lonDegrees) * 60).round(4)
       "# " + comment + "\n" + "@ " + pDate + ", " + pTime + ", " + latDegrees + ", " + latMinutes + ", " + lonDegrees + ", " + lonMinutes
    }

    def getDate() {
       def pDate = new java.text.SimpleDateFormat("MM-dd-yyyy").format(positionDate)
       def pTime = new java.text.SimpleDateFormat("HH:mm").format(positionDate)
       pDate + " " + pTime
    }

    def getLatitudeDesc () {
       java.text.DecimalFormat df = new java.text.DecimalFormat("###.###");
       df.format(latitude);
    }

    def getLongitudeDesc () {
       java.text.DecimalFormat df = new java.text.DecimalFormat("###.###");
       df.format(longitude);
    }

    def getCommentDesc() {
       comment?.replaceAll("'", "\\\\'")?.encodeAsHTML()
    }

    def getPopup() {
       def pDate = new java.text.SimpleDateFormat("MM-dd-yyyy").format(positionDate)
       def pTime = new java.text.SimpleDateFormat("HH:mm").format(positionDate)
       "Date: ${pDate}<br>Time: ${pTime}<br>${getCommentDesc()}"
    }
}
