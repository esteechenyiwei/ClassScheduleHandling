
package sideproject;

import net.fortuna.ical4j.model.component.CalendarComponent;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

class Course {
    private String courseName;
    // in the form of HHMM. eg. 1630
    private String starttime;
    private String endtime;
    private String location;
    private String description;
    private String day;
    
    
    public Course(CalendarComponent event) {
        Property dtstart = event.getProperty(Property.DTSTART);
        Property dtend = event.getProperty(Property.DTEND);
        starttime = dtstart.getValue().substring(9, 13);
        endtime = dtend.getValue().substring(9, 13);
        courseName = event.getProperty(Property.SUMMARY).getValue();
        location = event.getProperty(Property.LOCATION).getValue();
        description = event.getProperty(Property.DESCRIPTION).getValue();
 
        String rrule = event.getProperty(Property.RRULE).getValue();
        int dayPosition = rrule.indexOf("BYDAY=") + 6;
        day = rrule.substring(dayPosition, dayPosition + 2);
        
    }
    
    public JSONObject converToJSON() {
        JSONObject jo = new JSONObject();
        jo.put("name", courseName);
        jo.put("starttime", starttime);
        jo.put("endtime", endtime);
        jo.put("location", location);
        jo.put("description", description);
        jo.put("dayofweek", day);
        return jo;
        
    }
    
    public void printAllProperties() {
        System.out.println("courseName: " + courseName);
        System.out.println("start: " + starttime);
        System.out.println("ends: " + endtime);
        
        System.out.println("location: " + location);
        System.out.println("description: " + description);
        System.out.println("weekday: " + day);
    }
    
    public String getDescription() {
        return description;
    }
    
}
