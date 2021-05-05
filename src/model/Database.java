package model;

import model.boxes.*;
import model.boxes.Class;
import model.boxes.Enum;
import model.grid.Diagram;
import model.point.Scale;
import model.point.ScaledPoint;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * Class that handles saving-/loading functionality.
 * @author Filip Hanberg
 */
public class Database{

    /**
     * Parses the given textfile and returns a Diagram.
     * (.uml files expected)
     * @param filename The name of the file to be loaded, currently not including general filepath and ".uml"
     * @return A Diagram object.
     */
    static public Diagram loadDiagram(String filename){
        try {
            ArrayList<Box> boxes = new ArrayList<>();
            File toRead = new File("diagrams/" + filename + ".uml");
            Scanner scanner = new Scanner(toRead);
            while (scanner.hasNextLine()){
                String next = scanner.nextLine().trim();
                if(next.startsWith("<BOX>"))
                    boxes.add(loadBox(scanner, BoxType.BOX));
                else if(next.startsWith("<CLASS>"))
                    boxes.add(loadBox(scanner, BoxType.CLASS));
                else if(next.startsWith("<ABSTRACTCLASS>"))
                    boxes.add(loadBox(scanner, BoxType.ABSTRACTCLASS));
                else if(next.startsWith("<INTERFACE>"))
                    boxes.add(loadBox(scanner, BoxType.INTERFACE));
                else if(next.startsWith("<ENUM>"))
                    boxes.add(loadBox(scanner, BoxType.ENUM));
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
            String[] nextSplit = next.split("=");
            switch (nextSplit[0]){
                case "name":
                    name = nextSplit[1];
                    break;
                case "xposition":
                    xpos = Integer.parseInt(nextSplit[1]);
                    break;
                case "yposition":
                    ypos = Integer.parseInt(nextSplit[1]);
                    break;
                case "visibility":
                    visibility = Visibility.valueOf(nextSplit[1]);
                    break;
                case "<METHOD>":
                    methods.add(loadMethod(scanner));
                    break;
                case "<ATTRIBUTE>":
                    attributes.add(loadAttribute(scanner));
                    break;
                default:
                    if(next.startsWith("modifier")){
                        modifiers.add(Modifier.valueOf(nextSplit[1]));
                    }else if(next.startsWith("<!")){
                        switch(type){
                            case BOX:
                                Box box = new Box(name, new ScaledPoint(Scale.Backend,new Point(xpos,ypos)));
                                box.setVisibility(visibility);
                                box.setMethods(methods);
                                box.setAttributes(attributes);
                                box.setModifiers(modifiers);
                                return box;
                            case CLASS:
                            case ABSTRACTCLASS:
                                Class newClass = new Class(new ScaledPoint(Scale.Backend,new Point(xpos,ypos)), name);
                                newClass.setVisibility(visibility);
                                newClass.setMethods(methods);
                                newClass.setAttributes(attributes);
                                newClass.setModifiers(modifiers);
                                return newClass;
                            case INTERFACE:
                                Interface newInterface = new Interface(new ScaledPoint(Scale.Backend,new Point(xpos,ypos)),name);
                                newInterface.setVisibility(visibility);
                                newInterface.setMethods(methods);
                                newInterface.setAttributes(attributes);
                                newInterface.setModifiers(modifiers);
                                return newInterface;
                            case ENUM:
                                Enum newEnum = new Enum(new ScaledPoint(Scale.Backend, new Point(xpos,ypos)), name);
                                newEnum.setVisibility(visibility);
                                newEnum.setMethods(methods);
                                newEnum.setAttributes(attributes);
                                newEnum.setModifiers(modifiers);
                                return newEnum;
                        }
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
            String[] next = scanner.nextLine().trim().split("=");
            switch (next[0]){
                case "name":
                    name = next[1];
                    break;
                case "visibility":
                    visibility = Visibility.valueOf(next[1]);
                    break;
                case "<PARAMETER>":
                    parameters.add(loadAttribute(scanner));
                    break;
                case "<!PARAMETER>":
                    return new Method(name, parameters, modifiers, visibility);
                default:
                    if(next[0].startsWith("modifier"))
                        modifiers.add(Modifier.valueOf(next[1]));
            }

        }
        return null;
    }

    static private Attribute loadAttribute(Scanner scanner){
        String name = "";
        Visibility visibility = Visibility.PRIVATE;
        Set<Modifier> modifiers = new HashSet<>();
        while (scanner.hasNextLine()){
            String[] next = scanner.nextLine().trim().split("=");
            switch (next[0]){
                case "name":
                    name = next[1];
                    break;
                case "visibility":
                    visibility = Visibility.valueOf(next[1]);
                    break;
                default:
                    if(next[0].startsWith("modifier")) {
                        modifiers.add(Modifier.valueOf(next[0]));
                    }else if(next[0].startsWith("<!"))
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
            ArrayList<Box> boxes = target.boxGrid.getAllBoxes(); //probably useful for relations later
            for (Box box: boxes) {
                saveBox(box, writer);
            }
            writer.close();
        } catch(IOException e){
            System.out.println("something went wrong when saving the diagram.");
            e.printStackTrace();
        }
    }

    /**
     * Writes a given line to a given file, then moves to a new line
     * @param line the line to be written
     * @param writer the writer
     * @throws IOException
     */
    static private void writeLine(String line, BufferedWriter writer) throws IOException {
        writer.write(line);
        writer.newLine();
    }

    static private void saveBox(Box box, BufferedWriter writer) throws IOException {
        writeLine("<" + box.getType().toString() + ">", writer);
        writeLine("  name=" + box.getName(), writer);
        writeLine("  xposition=" + box.getPosition().getX(Scale.Backend), writer);
        writeLine("  yposition=" + box.getPosition().getY(Scale.Backend), writer);
        writeLine("  visibility=" + box.getVisibility(), writer);
        for (Method method: box.getMethods()) {
            saveMethod(method, writer, "  ");
        }
        for(Attribute attribute: box.getAttributes()){
            writeLine("  <ATTRIBUTE>", writer);
            saveAttribute(attribute, writer, "  ");
            writeLine("  <!ATTRIBUTE>", writer);
        }
        saveModifiers(box.getModifiers(), writer,"  ");
        writeLine("<!"+box.getType()+">", writer);
    }

    static private void saveMethod(Method method, BufferedWriter writer, String tab) throws  IOException{
        writeLine(tab + "<METHOD>", writer);
        writeLine(tab + "  name=" + method.getName(), writer);
        writeLine(tab + "  visibility=" + method.getVisibility(), writer);
        for(Attribute attribute : method.getParameters()){
            writeLine(tab + "  <PARAMETER>", writer);
            saveAttribute(attribute, writer, tab + "  ");
            writeLine(tab + "  <!PARAMETER>", writer);
        }
        saveModifiers(method.getModifiers(), writer, tab + "  ");
        writeLine(tab + "<!METHOD>", writer);
    }

    static private void saveAttribute(Attribute attribute, BufferedWriter writer, String tab) throws IOException{
        writeLine(tab + "  name=" + attribute.getName(), writer);
        writeLine(tab + "  visibility=" + attribute.getVisibility(), writer);
        saveModifiers(attribute.getModifiers(), writer, tab + "  ");
    }

    static private void saveModifiers(Set<Modifier> modifiers, BufferedWriter writer, String tab) throws IOException{
        int modnum = 0;
        for (Modifier modifier: modifiers) {
            writeLine(tab + "  modifier" + modnum + "=" + modifier, writer);
            modnum++;
        }
    }

    public static String[] getAllFileNames() {
        File folder = new File("diagrams/");
        File[] matchingFiles = folder.listFiles((dir, name) ->  name.endsWith(".uml"));
        if(matchingFiles.length != 0) {
            String[] result = new String[matchingFiles.length];
            for (int i = 0; i < matchingFiles.length; i++) {
                result[i] = matchingFiles[i].getName();
                result[i] = result[i].substring(0, result[i].length()-4);
            }
            return result;
        }
        return new String[0];
    }

    public static String newFile() {
        File newFile = new File("diagrams/new.uml");
        boolean firstTry = true;
        int magicNumber = 0;
        try {
            while (!newFile.createNewFile()) { //if the filename already exists
                newFile = new File("diagrams/new" + magicNumber + ".uml");
                magicNumber++;
                firstTry = false;
            }
            if(firstTry){
                return "new";
            }
            return "new"+magicNumber;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
