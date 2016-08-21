/**
 *  OMGTemp
 *
 *  Copyright 2016 David Knaack
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
metadata {
	definition (name: "OMGTemp", namespace: "davidknaack", author: "David Knaack") {
		capability "Temperature Measurement"
	}


	simulator {
		status "temp 72": "temp:72"
		status "temp -15": "temp:-15"
		status "batt": "batt:85"
	}

	tiles {
		valueTile("temperature", "device.temperature", width: 2, height: 2) {
			state("temperature", label:'${currentValue}', unit:"F",
				backgroundColors:[
					[value: 31, color: "#153591"],
					[value: 44, color: "#1e9cbb"],
					[value: 59, color: "#90d2a7"],
					[value: 74, color: "#44b621"],
					[value: 84, color: "#f1d801"],
					[value: 95, color: "#d04e00"],
					[value: 96, color: "#bc2323"]
				]
			)
		}
		valueTile("battery", "device.battery", inactiveLabel: false, decoration: "flat", width: 2, height: 2) {
			state "battery", label:'${currentValue} battery', unit:"%"
		}
        main "temperature"
		details("temperature","battery")
	}
}

// parse events into attributes
def parse(String description) {
	log.debug "Parsing '${description}'"

	if (description?.startsWith('temp')) {
		def pair = description.split(":")
		createEvent(name: "temperature", value: pair[1].trim(), unit:"F")	
	}
	else if (description?.startsWith('batt')) {
		def pair = description.split(":")
		createEvent(name: "battery", value: pair[1].trim(), unit:"%")
	}
}