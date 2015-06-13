package com.certotrack


class CheckEmailJob {
   def emailReaderService

   def timeout = 60000l // execute job once in 60 seconds
//   def timeout = 5000l // execute job once in 5 seconds

   def execute() {
      emailReaderService.processNewEmail()
   }
}
