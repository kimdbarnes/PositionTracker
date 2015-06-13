package com.certotrack

class EmailService {


   def sendHtmlEmail(recipient, subjectLine, emailBody) {
      sendMail {
         to recipient
         subject subjectLine
         html emailBody
      }
   }

   def sendEmail(recipient, subjectLine, emailBody) {
      sendMail {
         to recipient
         subject subjectLine
         body emailBody
      }
   }


}
