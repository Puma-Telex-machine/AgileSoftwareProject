package model;

import model.boxes.*;
import model.facades.AttributeFacade;
import model.facades.MethodFacade;
import model.diagram.Diagram;
import global.point.Scale;
import global.point.ScaledPoint;
import model.relations.ArrowType;
import model.relations.Relation;

import java.io.*;
import java.util.*;
import java.util.List;

/**
 * Class that handles saving-/loading functionality.
 * @author Filip Hanberg
 */
public class Database {

    private static final String nameMatch = "name";
    private static final String visibilityMatch = "visibility";
    private static final String modifierMatch = "modifier";
    private static final String typeMatch = "type";
    private static final String lineSplit = "=";

    
    /**
     * Parses the given textfile and returns a Diagram.
     * (.uml files expected)
     * @param folder The folder where the file can be found, including "/"
     * @param filename The name of the file to be loaded, currently not including ".uml"
     * @return A Diagram object.
     */
    static public Diagram loadDiagram(String folder, String filename, String fileEnder){
        try {
            ArrayList<Box> boxes = new ArrayList<>();
            File toRead = new File(folder + filename + fileEnder + ".uml");
            Scanner scanner = new Scanner(toRead);
            Diagram result = new Diagram();
            result.setName(filename);
            result.lockSaving();
            while (scanner.hasNextLine()){
                String next = scanner.nextLine().trim();
                if(next.startsWith("<BOX>"))
                    boxes.add(loadBox(scanner, BoxType.BOX, result));
                else if(next.startsWith("<CLASS>"))
                    boxes.add(loadBox(scanner, BoxType.CLASS, result));
                else if(next.startsWith("<ABSTRACT_CLASS>"))
                    boxes.add(loadBox(scanner, BoxType.ABSTRACT_CLASS, result));
                else if(next.startsWith("<INTERFACE>"))
                    boxes.add(loadBox(scanner, BoxType.INTERFACE, result));
                else if(next.startsWith("<ENUM>"))
                    boxes.add(loadBox(scanner, BoxType.ENUM, result));
                else if(next.startsWith("<RELATION>"))
                    loadRelation(scanner, boxes, result);
            }
            for (Box box: boxes) { //todo kanske onödig
                result.updateBox(box);
            }
            System.out.println("Successfully loaded " + filename + ".uml");
            result.unlockSaving();
            return result;
        } catch (FileNotFoundException e){
            e.printStackTrace();
            return new Diagram();
        }
    }

    static private Box loadBox(Scanner scanner, BoxType type, Diagram target) {
        String name = type.name();
        int xpos = 0;
        int ypos = 0;
        Visibility visibility = Visibility.PUBLIC;
        List<Method> methods = new ArrayList<>();
        List<Attribute> attributes = new ArrayList<>();
        Set<Modifier> modifiers = new HashSet<>();

        while (scanner.hasNextLine()) {
            String next = scanner.nextLine().trim();
            String[] nextSplit = next.split(lineSplit);
            switch (nextSplit[0]) {
                case nameMatch:
                    name = nextSplit[1];
                    break;
                case "xposition":
                    xpos = Integer.parseInt(nextSplit[1]);
                    break;
                case "yposition":
                    ypos = Integer.parseInt(nextSplit[1]);
                    break;
                case visibilityMatch:
                    visibility = Visibility.valueOf(nextSplit[1]);
                    break;
                case "<METHOD>":
                    methods.add(loadMethod(scanner));
                    break;
                case "<ATTRIBUTE>":
                    attributes.add(loadAttribute(scanner));
                    break;
                default:
                    if (next.startsWith(modifierMatch)) {
                        modifiers.add(Modifier.valueOf(nextSplit[1]));
                    } else if (next.startsWith("<!")) {
                        Box box = new Box(target, new ScaledPoint(Scale.Backend, xpos, ypos), type);
                        box.setVisibility(visibility);
                        box.setName(name);
                        for (Method method : methods) {
                            MethodFacade newMet = box.addMethod();
                            newMet.setName(method.getName());
                            newMet.setVisibility(method.getVisibility());
                            for (String param : method.getArguments()) {
                                newMet.addArgument(param);
                            }
                            for (Modifier modifier : method.getModifiers()) {
                                newMet.addModifier(modifier);
                            }
                        }
                        for (Attribute attribute : attributes) {
                            AttributeFacade newAttr = box.addAttribute();
                            newAttr.setName(attribute.getName());
                            newAttr.setVisibility(attribute.getVisibility());
                            for (Modifier modifier : attribute.getModifiers()) {
                                newAttr.addModifier(modifier);
                            }
                        }
                        for (Modifier modifier : modifiers) {
                            box.addModifier(modifier);
                        }
                        target.addBox(box);
                        return box;
                    }
            }


        }
        return null;
    }

    static private Method loadMethod(Scanner scanner){
        String name = "method";
        String type = "void";
        Visibility visibility = Visibility.PUBLIC;
        List<Attribute> parameters = new ArrayList<>();
        Set<Modifier> modifiers = new HashSet<>();
        while (scanner.hasNextLine()){
            String[] next = scanner.nextLine().trim().split(lineSplit);
            switch (next[0]){
                case visibilityMatch:
                    visibility = Visibility.valueOf(next[1]);
                    break;
                case typeMatch:
                    type = next[1];
                    break;
                case nameMatch:
                    name = next[1];
                    break;
                case "<PARAMETER>":
                    parameters.add(loadAttribute(scanner));
                    break;
                case "<!METHOD>":
                    Method method = new Method();
                    method.stopUpdates();
                    method.setVisibility(visibility);
                    method.setType(type);
                    method.setName(name);
                    for (Attribute attribute: parameters) {
                        method.addArgument(attribute.getName());
                    }
                    for (Modifier modifier: modifiers) {
                        method.addModifier(modifier);
                    }
                    method.startUpdates();
                    return method;
                default:
                    if(next[0].startsWith(modifierMatch))
                        modifiers.add(Modifier.valueOf(next[1]));
            }

        }
        return null;
    }

    static private Attribute loadAttribute(Scanner scanner){
        String name = "";
        String type = "int";
        Visibility visibility = Visibility.PRIVATE;
        Set<Modifier> modifiers = new HashSet<>();
        while (scanner.hasNextLine()){
            String[] next = scanner.nextLine().trim().split(lineSplit);
            switch (next[0]){
                case visibilityMatch:
                    visibility = Visibility.valueOf(next[1]);
                    break;
                case typeMatch:
                    type = next[1];
                    break;
                case nameMatch:
                    name = next[1];
                    break;
                default:
                    if(next[0].startsWith(modifierMatch)) {
                        modifiers.add(Modifier.valueOf(next[0]));
                    }else if(next[0].startsWith("<!")) {
                        Attribute attribute = new Attribute();
                        attribute.stopUpdates();
                        attribute.setVisibility(visibility);
                        attribute.setType(type);
                        attribute.setName(name);
                        for (Modifier modifier: modifiers) {
                            attribute.addModifier(modifier);
                        }
                        attribute.startUpdates();
                        return attribute;
                    }
            }
        }
        return null;
    }

    static private void loadRelation(Scanner scanner, ArrayList<Box> boxes, Diagram target){
        ArrowType arrowType = ArrowType.ASSOCIATION;
        int indexFrom = 0;
        int indexTo = 0;
        int xOffsetFrom = 0;
        int yOffsetFrom = 0;
        int xOffsetTo = 0;
        int yOffsetTo = 0;
        while (scanner.hasNextLine()){
            String[] next = scanner.nextLine().trim().split(lineSplit);
            switch (next[0]){
                case "from":
                    indexFrom = Integer.parseInt(next[1]);
                    break;
                case "xOffsetFrom":
                    xOffsetFrom = Integer.parseInt(next[1]);
                    break;
                case "yOffsetFrom":
                    yOffsetFrom = Integer.parseInt(next[1]);
                    break;
                case "to":
                    indexTo = Integer.parseInt(next[1]);
                    break;
                case "xOffsetTo":
                    xOffsetTo = Integer.parseInt(next[1]);
                    break;
                case "yOffsetTo":
                    yOffsetTo = Integer.parseInt(next[1]);
                    break;
                case typeMatch:
                    arrowType = ArrowType.valueOf(next[1]);
                    break;
                case "<!RELATION>":
                    ScaledPoint from = new ScaledPoint(Scale.Backend, xOffsetFrom, yOffsetFrom);
                    ScaledPoint to = new ScaledPoint(Scale.Backend, xOffsetTo, yOffsetTo);
                    target.createRelation(boxes.get(indexFrom), from, boxes.get(indexTo), to, arrowType);
            }
        }
    }

    /**
     * Saves the given diagram as a .uml-file, with the given name.
     * @param target The diagram to be saved
     * @param folder the folder to save the diagram in, written as "example/"
     */
    static public void saveDiagram(Diagram target, String folder, String extraName) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(folder + target.getName() + extraName + ".uml"));
            List<Box> boxes = target.getAllBoxes();
            List<Relation> relations = target.getAllRelations();
            for (Box box: boxes) {
                saveBox(box, writer);
            }
            for (Relation relation : relations) {
                saveRelation(relation, boxes.indexOf(relation.getFrom()), boxes.indexOf(relation.getTo()), writer);
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
        writeLine("  " + nameMatch + lineSplit + box.getName(), writer);
        writeLine("  xposition" + lineSplit + box.getPosition().getX(Scale.Backend), writer);
        writeLine("  yposition" + lineSplit + box.getPosition().getY(Scale.Backend), writer);
        writeLine("  " + visibilityMatch + lineSplit + box.getVisibility(), writer);
        for (MethodFacade method: box.getMethods()) {
            saveMethod(method, writer, "  ");
        }
        for(AttributeFacade attribute: box.getAttributes()){
            writeLine("  <ATTRIBUTE>", writer);
            saveAttribute(attribute, writer, "  ");
            writeLine("  <!ATTRIBUTE>", writer);
        }
        saveModifiers(box.getModifiers(), writer,"  ");
        writeLine("<!"+box.getType()+">", writer);
    }

    static private void saveMethod(MethodFacade method, BufferedWriter writer, String tab) throws  IOException{
        writeLine(tab + "<METHOD>", writer);
        writeLine(tab + "  " + visibilityMatch + lineSplit + method.getVisibility(), writer);
        writeLine(tab + "  " + typeMatch + lineSplit + method.getType(), writer);
        writeLine(tab + "  " + nameMatch + lineSplit + method.getName(), writer);
        for(String param : method.getArguments()){
            writeLine(tab + "  <PARAMETER>", writer);
            writeLine(tab + "    " + nameMatch + lineSplit + param, writer);
            writeLine(tab + "  <!PARAMETER>", writer);
        }
        saveModifiers(method.getModifiers(), writer, tab + "  ");
        writeLine(tab + "<!METHOD>", writer);
    }

    static private void saveAttribute(AttributeFacade attribute, BufferedWriter writer, String tab) throws IOException{
        writeLine(tab + "  " + visibilityMatch + lineSplit + attribute.getVisibility(), writer);
        writeLine(tab + "  " + typeMatch + lineSplit + attribute.getType(), writer);
        writeLine(tab + "  " + nameMatch + lineSplit + attribute.getName(), writer);
        saveModifiers(attribute.getModifiers(), writer, tab + "  ");
    }

    static private void saveModifiers(Set<Modifier> modifiers, BufferedWriter writer, String tab) throws IOException{
        int modnum = 0;
        for (Modifier modifier: modifiers) {
            writeLine(tab + "  "+ modifierMatch + modnum + lineSplit + modifier, writer);
            modnum++;
        }
    }

    static private void saveRelation(Relation relation, int fromPos, int toPos, BufferedWriter writer) throws IOException {
        writeLine("<RELATION>", writer);
        writeLine("  from" + lineSplit + fromPos, writer);
        writeLine("  xOffsetFrom" + lineSplit + relation.getOffsetFrom().getX(Scale.Backend), writer);
        writeLine("  yOffsetFrom" + lineSplit + relation.getOffsetFrom().getY(Scale.Backend), writer);
        writeLine("  to" + lineSplit + toPos, writer);
        writeLine("  xOffsetTo" + lineSplit + relation.getOffsetTo().getX(Scale.Backend), writer);
        writeLine("  yOffsetTo" + lineSplit + relation.getOffsetTo().getY(Scale.Backend), writer);
        writeLine("  "+ typeMatch + lineSplit + relation.getArrowType(), writer);
        writeLine("<!RELATION>", writer);
    }

    public static String[] getAllFileNames(String foldername) {
        File folder = new File(foldername);
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

    /**
     * Checks whether or not the given string is a directory
     * @param name the name of the directory, e.g. "temp/"
     * @return whether or not the string is a valid directory
     */
    public static Boolean directoryCheck(String name){
        return new File(name).isDirectory();
    }

    public static void deleteFile(String folder, String filename){
        File target = new File(folder + filename + ".uml");
        if(target.exists()) {
            target.delete();
            System.out.println("deleted " + filename + ".uml");
        }
    }
}
