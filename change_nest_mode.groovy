/**
 *  Change Nest Mode
 *
 *  Author: brian@bevey.org
 *  Date: 9/25/13
 *
 *  Simply marks any thermostat "away" if able (primarily focused on the Nest
 *  thermostat).  This is intended to be used with an "Away" or "Home" mode.
 */

preferences {
  section("Change to this mode to...") {
    input "newMode", "mode", metadata:[values:["Away", "Home"]]
  }

  section("Change these thermostats modes...") {
    input "thermostats", "capability.thermostat", multiple: true
  }
}

def installed() {
  subscribe(location, changeMode)
  subscribe(app, changeMode)
}

def updated() {
  unsubscribe()
  subscribe(location, changeMode)
  subscribe(app, changeMode)
}

def changeMode(evt) {
  if(newMode == "Away") {
    thermostats?.away()
  }

  else {
    thermostats?.present()
  }
}