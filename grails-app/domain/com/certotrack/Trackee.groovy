package com.certotrack

class Trackee {

    String name
    String emailAddress
    String specialCode

    static hasMany = [ tracks:Track ]

    static constraints = {
    }

    String toString() {
       name + " (" + emailAddress + ")"
    }
}
