package model;

import model.boxes.Attribute;
import model.boxes.Box;
import model.boxes.Method;
import model.boxes.Modifier;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Set;

/**
 * Class that handles saving-/loading functionality.
 * @author Filip Hanberg
 */
public class Database {

    public void writeTest(String filename){
        try {
            FileWriter filetime = new FileWriter("diagrams/" + filename + ".uml");
            filetime.write("this is a test, lads");
            filetime.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    static public void saveDiagram(Diagram target, String filename) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("diagrams/" + filename + ".uml"));
            writer.write("boxcount=" + target.boxGrid.getBoxCounter());
            writer.newLine();
            ArrayList<Box> boxes =  new ArrayList<>(target.boxGrid.getAllBoxes()); //probably useful for relations later
            for (Box box: boxes) {
                saveBox(box, writer);
            }
            writer.close();
        } catch(IOException e){
            System.out.println("something went wrong when saving the diagram.");
            e.printStackTrace();
        }
    }

    static private void saveBox(Box box, BufferedWriter writer) throws IOException {
        writer.write("<" + box.getType().toString() + ">");
        writer.newLine();
        writer.write("name=" + box.getName());
        writer.newLine();
        writer.write("xposition=" + box.getPosition().x);
        writer.newLine();
        writer.write("yposition=" + box.getPosition().y);
        writer.newLine();
        writer.write("visibility=" + box.getVisibility());
        writer.newLine();
        for (Method method: box.getMethods()) {
            saveMethod(method, writer);
        }
        for(Attribute attribute: box.getAttributes()){
            writer.write("<ATTRIBUTE>");
            writer.newLine();
            saveAttribute(attribute, writer);
            writer.write("<!ATTRIBUTE>");
            writer.newLine();
        }
        saveModifiers(box.getModifiers(), writer);
        writer.write("<!"+box.getType()+">");
        writer.newLine();
    }

    static private void saveMethod(Method method, BufferedWriter writer) throws  IOException{
        writer.write("<METHOD>");
        writer.newLine();
        writer.write("name=" + method.getName());
        writer.newLine();
        writer.write("visibility=" + method.getVisibility());
        writer.newLine();
        for(Attribute attribute : method.getParameters()){
            writer.write("<PARAMETER>");
            writer.newLine();
            saveAttribute(attribute, writer);
            writer.write("<!PARAMETER>");
            writer.newLine();
        }
        saveModifiers(method.getModifiers(), writer);
        writer.write("<!METHOD>");
        writer.newLine();
    }

    static private void saveAttribute(Attribute attribute, BufferedWriter writer) throws IOException{
        writer.write("name=" + attribute.getName());
        writer.newLine();
        writer.write("visibility=" + attribute.getVisibility());
        writer.newLine();
        saveModifiers(attribute.getModifiers(), writer);
    }

    static private void saveModifiers(Set<Modifier> modifiers, BufferedWriter writer) throws IOException{
        int modnum = 0;
        for (Modifier modifier: modifiers) {
            writer.write("modifier" + modnum + "=" + modifier);
            writer.newLine();
            modnum++;
        }
    }
}
