package com.certotrack

class TrackeeController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [trackeeInstanceList: Trackee.list(params), trackeeInstanceTotal: Trackee.count()]
    }

    def create = {
        def trackeeInstance = new Trackee()
        trackeeInstance.properties = params
        return [trackeeInstance: trackeeInstance]
    }

    def save = {
        def trackeeInstance = new Trackee(params)
        if (trackeeInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'trackee.label', default: 'Trackee'), trackeeInstance.id])}"
            redirect(action: "show", id: trackeeInstance.id)
        }
        else {
            render(view: "create", model: [trackeeInstance: trackeeInstance])
        }
    }

    def show = {
        def trackeeInstance = Trackee.get(params.id)
        if (!trackeeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'trackee.label', default: 'Trackee'), params.id])}"
            redirect(action: "list")
        }
        else {
            [trackeeInstance: trackeeInstance]
        }
    }

    def edit = {
        def trackeeInstance = Trackee.get(params.id)
        if (!trackeeInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'trackee.label', default: 'Trackee'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [trackeeInstance: trackeeInstance]
        }
    }

    def update = {
        def trackeeInstance = Trackee.get(params.id)
        if (trackeeInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (trackeeInstance.version > version) {
                    
                    trackeeInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'trackee.label', default: 'Trackee')] as Object[], "Another user has updated this Trackee while you were editing")
                    render(view: "edit", model: [trackeeInstance: trackeeInstance])
                    return
                }
            }
            trackeeInstance.properties = params
            if (!trackeeInstance.hasErrors() && trackeeInstance.save(flush: true)) {
                flash.message = "${message(code: 'default.updated.message', args: [message(code: 'trackee.label', default: 'Trackee'), trackeeInstance.id])}"
                redirect(action: "show", id: trackeeInstance.id)
            }
            else {
                render(view: "edit", model: [trackeeInstance: trackeeInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'trackee.label', default: 'Trackee'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def trackeeInstance = Trackee.get(params.id)
        if (trackeeInstance) {
            try {
                trackeeInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'trackee.label', default: 'Trackee'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'trackee.label', default: 'Trackee'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'trackee.label', default: 'Trackee'), params.id])}"
            redirect(action: "list")
        }
    }
}
