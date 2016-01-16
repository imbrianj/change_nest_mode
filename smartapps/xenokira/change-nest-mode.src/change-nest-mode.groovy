/**
 *  Change Nest Mode
 *
 *  Author: brian@bevey.org
 *  Date: 5/5/14
 *
 *  Simply marks any thermostat "away" if able (primarily focused on the Nest
 *  thermostat). This is intended to be used with any SmartThings mode that
 *  represents 'Away'.
 * 
 *  Changes:
 *  2016.01.15  xenokira
 *  Manually moved to personal repository for better integration with SmartThings.
 *  Allow multiple "away modes" to trigger thermostat away mode (Home, Vacation, etc...)
 *  Cleaned up strings.
 *  
 */

definition(
    name:        "Change Nest Mode",
    namespace:   "xenokira",
    author:      "brian@bevey.org & nathaniel.pitts@gmail.com",
    description: "Simply marks any thermostat 'away' if able (primarily focused on the Nest thermostat). This is intended to be used with any SmartThings mode that represents 'Away'.",
    category:    "Green Living",
    iconUrl:     "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url:   "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience%402x.png"
)

preferences {
  section() {
    input "awayMode", "mode", title: "If SmartThings changes to", required: true, multiple: true
    input "thermostats", "capability.thermostat", title: "then set these themostats to Away", required: true, multiple: true
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
  def curMode = location.currentMode
  log.debug("New SmartThings mode is ${curMode.name}")
  
  if(awayMode.contains(curMode.name)) {
    log.info("Changing thermostat mode to Away")
    thermostats?.away()
  } else {
    log.info("Changing thermostat mode to Home")
    thermostats?.present()
  }
}
