package sideproject;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.fortuna.ical4j.*;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.CalendarComponent;
import net.fortuna.ical4j.model.Component;
import org.slf4j.*;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class CourseUnitGenerator {
    public static void main(String[] args) throws FileNotFoundException, ParserException, IOException {
    //import the ics fle as iCal4j calendar
        
        //String filename = args[0];
        FileInputStream in = new FileInputStream("2020A3.ics");
        CalendarBuilder cb = new CalendarBuilder();
        net.fortuna.ical4j.model.Calendar calendar = cb.build(in);

        List<Course> courseList = getCourseList(calendar); 
        Map<String, Long> myMap = getFrequencyMap(courseList);
        getHighestFrequencyWords(myMap, 30);
//        writeJSONFile(courseList);

    }
    

    
    /* 
     * name: writeJSONFile
     * description: writes a JSONArray to an external output file
     * 
     * returns: void
     * param: li, an ArrayList of courses to be written to the external file
     */
    public static void writeJSONFile(List<Course> li) throws IOException {
        try {
            File out = new File("out.json");
            boolean b = out.createNewFile();
            PrintWriter pw = new PrintWriter(out);
            pw.write("[");
            for (int i = 0; i < li.size(); i++) {
                pw.write(li.get(i).converToJSON().toJSONString());
                if (i != li.size() - 1) {
                    pw.write(", ");
                }
                System.out.println("Successfully Copied JSON Object to File...");
                System.out.println("\nJSON Object: " + li.get(i));
            }
            pw.write("]");

            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        } 
        
    }
    
    /* 
     * name: getCourseList
     * description: returns a list of courses from an inputted Calendar type
     * 
     * returns: courseList, a list of courses
     * param: calendar, a Calendar-classed calendar file
     */
    public static List<Course> getCourseList(net.fortuna.ical4j.model.Calendar calendar) {
        List<Course> courseList = new ArrayList<Course>();
        for (Iterator<CalendarComponent> i = calendar.getComponents().iterator(); i.hasNext();) {
            
            CalendarComponent component = i.next();
            if (component.getName().equalsIgnoreCase("VEVENT")) {
                
                
                Course newCourse = new Course(component);
                

                courseList.add(newCourse);
            }
        }
        return courseList;
    }
    
    
    
    /*
     * calculates the words with highest frequencies in your course descriptions. 
     * Number of words specified by the user. Prints to the console(for now)
     * param 1: map, a Map recording the frequency ranking of the words in courses descriptions
     * param 2: number, showing top x words on the frequency ranking list 
     */
    public static void getHighestFrequencyWords(Map<String, Long> map, int number) {
        
        String[] connectors = {"under", "will", "would", "then", "that", "this", "when", "after", 
               "with", "them", "what", "when", "which", "where", "both", "such",
               "course", "students", "student", "from", "because", "thus", "one-", "have", 
               "had", "", "include", "well", "focus", "covers", "about", "your", 
               "through", "more", "class", "theirs", "learn", "their", "over", "also"};
        for (String s : connectors) {
            map.remove(s);
        }
       
        
        List<Map.Entry<String, Long>> wordToFreqList = new ArrayList<Map.Entry<String, Long>>(map.entrySet());
        wordToFreqList.sort(Entry.comparingByValue());
        Collections.reverse(wordToFreqList);
        
        PrintWriter pw = new PrintWriter(System.out);
        for (int i = 0; i < number; i++) {
            if (i == wordToFreqList.size()) {
                break;
            }
            pw.write(wordToFreqList.get(i).getKey() + " " +
                    wordToFreqList.get(i).getValue() + "\n"); 
        }
        
        pw.flush(); 
        pw.close(); 
     }
    
    /*
     * Returns a frequency map of the words that appear in all your courses' descriptions
     */
    public static Map<String, Long> getFrequencyMap(List<Course> li) {
        StringBuilder sb = new StringBuilder();
        for (Course c : li) {
            sb.append(" ");
            sb.append(c.getDescription());
        }
        String content = sb.toString().toLowerCase();
        
        String[] descriptionArray = content.split(" ");
        for (int i = 0; i < descriptionArray.length; i++) {
            if (descriptionArray[i].length() <= 3) {
                descriptionArray[i] = "";
            } 
        }

        Map<String, Long> frequencyMap = Stream.of(descriptionArray)
                .collect(Collectors.groupingBy(Function.identity(), 
                        Collectors.counting()));
        return frequencyMap;
    }
    
    //testing function if the out-written file is a valid json file

}
