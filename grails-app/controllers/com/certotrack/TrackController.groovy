package com.certotrack

class TrackController {

   def emailReaderService
   def mapService

   static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

   def index = {
      redirect(action: "list", params: params)
   }

   def adminMenu = {
   }

   def list = {
      params.max = Math.min(params.max ? params.int('max') : 10, 100)
      params.specialCode = 'rootless'

      if (!params.specialCode) {
         flash.message = "Secret Code required"
         redirect(uri: "/index.gsp")
         return
      }

      def trackee = Trackee.findBySpecialCode(params.specialCode)
      if (!trackee) {
         flash.message = "Invalid Secret Code"
         redirect(uri: "/index.gsp")
         return
      }

      def trackCount = Track.countByTrackee(trackee)
      def trackList = Track.findAllByTrackee(trackee, params)

      if (trackList.size() == 1) {
         redirect(action: "mapTrackById", params: [id: trackList[0].id, specialCode: params.specialCode])
      }

      [trackInstanceList: trackList, trackInstanceTotal: trackCount, specialCode: params.specialCode]
   }

   def listTracks = {
      params.max = Math.min(params.max ? params.int('max') : 10, 100)
      [trackInstanceList: Track.list(params), trackInstanceTotal: Track.count()]
   }

   def create = {
      def trackInstance = new Track()
      trackInstance.properties = params
      return [trackInstance: trackInstance]
   }

   def save = {
      def trackInstance = new Track(params)
      if (trackInstance.save(flush: true)) {
         flash.message = "${message(code: 'default.created.message', args: [message(code: 'track.label', default: 'Track'), trackInstance.id])}"
         redirect(action: "show", id: trackInstance.id)
      }
      else {
         render(view: "create", model: [trackInstance: trackInstance])
      }
   }

   def show = {
      def trackInstance = Track.get(params.id)
      if (!trackInstance) {
         flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'track.label', default: 'Track'), params.id])}"
         redirect(action: "list")
      }
      else {
         [trackInstance: trackInstance]
      }
   }

   def edit = {
      def trackInstance = Track.get(params.id)
      if (!trackInstance) {
         flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'track.label', default: 'Track'), params.id])}"
         redirect(action: "list")
      }
      else {
         return [trackInstance: trackInstance]
      }
   }

   def update = {
      def trackInstance = Track.get(params.id)
      if (trackInstance) {
         if (params.version) {
            def version = params.version.toLong()
            if (trackInstance.version > version) {

               trackInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'track.label', default: 'Track')] as Object[], "Another user has updated this Track while you were editing")
               render(view: "edit", model: [trackInstance: trackInstance])
               return
            }
         }
         trackInstance.properties = params
         if (!trackInstance.hasErrors() && trackInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.updated.message', args: [message(code: 'track.label', default: 'Track'), trackInstance.id])}"
            redirect(action: "show", id: trackInstance.id)
         }
         else {
            render(view: "edit", model: [trackInstance: trackInstance])
         }
      }
      else {
         flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'track.label', default: 'Track'), params.id])}"
         redirect(action: "list")
      }
   }

   def delete = {
      def trackInstance = Track.get(params.id)
      if (trackInstance) {
         try {
            trackInstance.delete(flush: true)
            flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'track.label', default: 'Track'), params.id])}"
            redirect(action: "list")
         }
         catch (org.springframework.dao.DataIntegrityViolationException e) {
            flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'track.label', default: 'Track'), params.id])}"
            redirect(action: "show", id: params.id)
         }
      }
      else {
         flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'track.label', default: 'Track'), params.id])}"
         redirect(action: "list")
      }
   }

   def mapTrackByName = {
      assert params.name
      def trackInstance = Track.findByName(params.name)
      assert trackInstance
      def positionInstanceList = Position.findAllByTrack(trackInstance, [sort: "positionDate"])
      render(view: "mapTrack", model:
              [trackInstance: trackInstance, positionInstanceList: positionInstanceList])
   }

   def mapTrackByIdAdmin_orig = {
      assert params.id
      def trackInstance = Track.get(params.id)
      assert trackInstance

      def positionInstanceList = Position.findAllByTrack(trackInstance, [sort: "positionDate"])
      render(view: "mapTrack", model:
              [trackInstance: trackInstance, positionInstanceList: positionInstanceList])
   }

   def mapTrackByIdAdmin = {
      assert params.id
      def trackInstance = Track.get(params.id)
      assert trackInstance

      def staticMapUrl = mapService.getMapUrl(trackInstance)

      if (!staticMapUrl) {
         redirect action: 'list'
      }

      redirect url: staticMapUrl
   }

   def mapTrackById = {
      assert params.id
      if (!params.specialCode) {
         flash.message = "Secret Code required"
         redirect(uri: "/index.gsp")
         return
      }

      def trackee = Trackee.findBySpecialCode(params.specialCode)
      if (!trackee) {
         flash.message = "Invalid Secret Code"
         redirect(uri: "/index.gsp")
         return
      }

      def trackInstance = Track.get(params.id)
      assert trackInstance
      assert trackInstance.trackee == trackee

      def positionInstanceList = Position.findAllByTrack(trackInstance, [sort: "positionDate"])
      render(view: "mapTrack", model:
              [trackInstance: trackInstance, positionInstanceList: positionInstanceList])
   }

   def test = {
   }

   def testEmail = {
      assert params.subject
      assert params.body
      def track = emailReaderService.getOrCreateNewTrack(params.subject, params.createdByEmail)
      assert track
      track.save(flush: true, failOnError: true)

      def line = params.body.split(",")
      def position = emailReaderService.createNewPosition(track, line)
      assert position
      position.save(flush: true, failOnError: true)

      render(view: "test", model: [])
   }

}
