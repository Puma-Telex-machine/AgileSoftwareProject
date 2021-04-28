package model;

import model.boxes.*;
import model.boxes.Class;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * Class that handles saving-/loading functionality.
 * @author Filip Hanberg
 */
public class Database {

    /**
     * Parses the given textfile and returns a Diagram.
     * (.uml files expected)
     * @param filename The name of the file to be loaded, currently not including general filepath and ".uml"
     * @return A Diagram object.
     */
    static public Diagram loadDiagram(String filename){
        try {
            int boxcount;
            ArrayList<Box> boxes = new ArrayList<>();
            File toRead = new File("diagrams/" + filename + ".uml");
            Scanner scanner = new Scanner(toRead);
            String[] nextSplit = scanner.nextLine().trim().split("=");
            if(nextSplit[0].toLowerCase().matches("boxcount"))
                boxcount = Integer.parseInt(nextSplit[1]); // not used for anything yet
            while (scanner.hasNextLine()){
                String next = scanner.nextLine().trim();
                if(next.startsWith("<BOX>"))
                    boxes.add(loadBox(scanner, BoxType.BOX));
            }
            Diagram result = new Diagram();
            for (Box box: boxes) {
                result.addBox(box);
            }
            return result;
        } catch (FileNotFoundException e){
            e.printStackTrace();
            return new Diagram();
        }
    }

    static private Box loadBox(Scanner scanner, BoxType type){
        String name = "";
        int xpos = 0;
        int ypos = 0;
        Visibility visibility = Visibility.PUBLIC;
        List<Method> methods = new ArrayList<>();
        List<Attribute> attributes = new ArrayList<>();
        Set<Modifier> modifiers = new HashSet<>();
        while (scanner.hasNextLine()){
            String next = scanner.nextLine().trim();
            if(next.startsWith("name=")){
                name = next.split("=")[1];
            }else if(next.startsWith("xposition=")){
                xpos = Integer.parseInt(next.split("=")[1]);
            }else if(next.startsWith("yposition=")){
                ypos = Integer.parseInt(next.split("=")[1]);
            }else if(next.startsWith("visibility=")){
                visibility = Visibility.valueOf(next.split("=")[1]);
            }else if(next.startsWith("<METHOD>")){
                methods.add(loadMethod(scanner));
            }else if(next.startsWith("<ATTRIBUTE>")){
                attributes.add(loadAttribute(scanner));
            }else if(next.startsWith("modifier")){
                modifiers.add(Modifier.valueOf(next.split("=")[1]));
            }else if(next.startsWith("<!")){
                switch(type){
                    case BOX:
                        Box box = new Box(name, new Point(xpos,ypos));
                        box.setVisibility(visibility);
                        box.setMethods(methods);
                        box.setAttributes(attributes);
                        box.setModifiers(modifiers);
                        return box;
                    case CLASS:
                    case ABSTRACTCLASS:
                        Class newClass = new Class(new Point(xpos,ypos), name);
                        newClass.setVisibility(visibility);
                        newClass.setMethods(methods);
                        newClass.setAttributes(attributes);
                        newClass.setModifiers(modifiers);
                        return newClass;
                    case INTERFACE:
                        Interface newInterface = new Interface(new Point(xpos,ypos),name);
                        newInterface.setVisibility(visibility);
                        newInterface.setMethods(methods);
                        newInterface.setAttributes(attributes);
                        newInterface.setModifiers(modifiers);
                        return newInterface;
                }
            }

        }
        return null;
    }

    static private Method loadMethod(Scanner scanner){
        String name = "";
        Visibility visibility = Visibility.PUBLIC;
        List<Attribute> parameters = new ArrayList<>();
        Set<Modifier> modifiers = new HashSet<>();
        while (scanner.hasNextLine()){
            String next = scanner.nextLine().trim();
            if(next.startsWith("name=")){
                name = next.split("=")[1];
            }else if(next.startsWith("visibility=")){
                visibility = Visibility.valueOf(next.split("=")[1]);
            }else if(next.startsWith("<PARAMETER>")){
                parameters.add(loadAttribute(scanner));
            }else if(next.startsWith("modifier")){
                modifiers.add(Modifier.valueOf(next.split("=")[1]));
            }else if(next.startsWith("<!")){
                return new Method(name, parameters, modifiers,visibility);
            }
        }
        return null;
    }

    static private Attribute loadAttribute(Scanner scanner){
        String name = "";
        Visibility visibility = Visibility.PRIVATE;
        Set<Modifier> modifiers = new HashSet<>();
        while (scanner.hasNextLine()){
            String next = scanner.nextLine().trim();
            if(next.startsWith("name=")){
                name = next.split("=")[1];
            }else if(next.startsWith("visibility=")){
                visibility = Visibility.valueOf(next.split("=")[1]);
            }else if(next.startsWith("modifier")){
                modifiers.add(Modifier.valueOf(next.split("=")[1]));
            }else if(next.startsWith("<!")){
                return new Attribute(name, modifiers, visibility);
            }
        }
        return null;
    }

    /**
     * Saves the given diagram as a .uml-file, with the given name.
     * @param target The diagram to be saved
     * @param filename The name of the saved file. Currently not including filepath or ".uml"
     */
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
        writer.write("  name=" + box.getName());
        writer.newLine();
        writer.write("  xposition=" + box.getPosition().x);
        writer.newLine();
        writer.write("  yposition=" + box.getPosition().y);
        writer.newLine();
        writer.write("  visibility=" + box.getVisibility());
        writer.newLine();
        for (Method method: box.getMethods()) {
            saveMethod(method, writer, "  ");
        }
        for(Attribute attribute: box.getAttributes()){
            writer.write("  <ATTRIBUTE>");
            writer.newLine();
            saveAttribute(attribute, writer, "  ");
            writer.write("  <!ATTRIBUTE>");
            writer.newLine();
        }
        saveModifiers(box.getModifiers(), writer,"  ");
        writer.write("<!"+box.getType()+">");
        writer.newLine();
    }

    static private void saveMethod(Method method, BufferedWriter writer, String tab) throws  IOException{
        writer.write(tab + "<METHOD>");
        writer.newLine();
        writer.write(tab + "  name=" + method.getName());
        writer.newLine();
        writer.write(tab + "  visibility=" + method.getVisibility());
        writer.newLine();
        for(Attribute attribute : method.getParameters()){
            writer.write(tab + "  <PARAMETER>");
            writer.newLine();
            saveAttribute(attribute, writer, tab + "  ");
            writer.write(tab + "  <!PARAMETER>");
            writer.newLine();
        }
        saveModifiers(method.getModifiers(), writer, tab + "  ");
        writer.write(tab + "<!METHOD>");
        writer.newLine();
    }

    static private void saveAttribute(Attribute attribute, BufferedWriter writer, String tab) throws IOException{
        writer.write(tab + "  name=" + attribute.getName());
        writer.newLine();
        writer.write(tab + "  visibility=" + attribute.getVisibility());
        writer.newLine();
        saveModifiers(attribute.getModifiers(), writer, tab + "  ");
    }

    static private void saveModifiers(Set<Modifier> modifiers, BufferedWriter writer, String tab) throws IOException{
        int modnum = 0;
        for (Modifier modifier: modifiers) {
            writer.write(tab + "  modifier" + modnum + "=" + modifier);
            writer.newLine();
            modnum++;
        }
    }
}
