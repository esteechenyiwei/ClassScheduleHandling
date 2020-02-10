package sideproject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import net.fortuna.ical4j.data.ParserException;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

class CourseBlock {
    
     
    
    public static void testValidJSONFile (File file) throws IOException, ParseException {
        try {
            JSONParser jp = new JSONParser();
            JSONArray ja = (JSONArray) jp.parse(new FileReader(file));
            System.out.println(ja.toJSONString());
        } catch (ParseException e) {
            System.out.println("not a valid json file");
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
        }
        
    } 
    
    public static void main(String[] args) throws IOException, ParseException {
        
        File f = new File("out.json");
        testValidJSONFile(f);
        
    }
    
    

}
