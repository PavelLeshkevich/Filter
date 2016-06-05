package com.helmes.task.filter;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Filter {

    private final int partOfMemory = 50;

    private final String constFirst = "rule name=";
    private final String constSecond = "type=";
    private final String constThird = "weight=";

    private static Logger log = Logger.getLogger(Filter.class);

    private int count = 0;
    private String path;
    private long memorySize;
    private BufferedReader input;
    private List<Note> data = new ArrayList<Note>();
    private List<File> file = new ArrayList<File>();

    public Filter(String path) {
        this.path = path;
        memorySize = Math.max(1, Runtime.getRuntime().freeMemory() / partOfMemory);
    }

    public Filter(String path, int size) {
        this.path = path;
        memorySize = size;
    }

    public String toFilter() {

        writeInFile();

        while(file.size() > 1) {
            for (int it = 0; it < file.size() - 1; it++) {
                Merge merge = new Merge(file.get(it), file.get(it + 1));
                merge.run();
                file.get(it).delete();
                file.set(it, merge.getFile());
                file.get(it + 1).delete();
                file.remove(it + 1);
            }
        }

        return rewrite(file.get(0));
    }


    private String rewrite(File file) {
        String outputFile = "output.txt";
        try {
            FileInputStream inputFirstFIS = new FileInputStream(file);
            ObjectInputStream inputFirstOIS = new ObjectInputStream(inputFirstFIS);
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(outputFile)));
            writer.println("<rules>");
            Note note;
            while(true) {
                try {
                    note = (Note) inputFirstOIS.readObject();
                    StringBuilder builder = new StringBuilder("");
                    builder.append("<rule name=”");
                    builder.append(note.getName());
                    builder.append("” type=”");
                    builder.append(backChange(note.getType()));
                    builder.append("” weight=”");
                    builder.append(note.getWeight());
                    builder.append("”/>");
                    writer.println(builder.toString());
                } catch (EOFException e) {
                    writer.println("</rules>");
                    writer.close();
                    break;
                }
            }
        } catch (IOException e) {
            log.info(e.getMessage());
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage());
        }

        return outputFile;
    }

    private void writeInFile() {
        try {
            input = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            log.info(e.getMessage());
        }

        String line;
        try {
            while ((line = input.readLine()) != null) {
                if (line.equals("<rules>") || line.equals("</rules>")) {
                    continue;
                }
                count++;
                conversionLine(line);
            }

            count = 0;
            pushInNewFile();

        } catch (IOException e) {
            log.info(e.getMessage());
        }
    }

    private void conversionLine (String line) {
        String name, type, weight;

        int index = line.indexOf(constFirst) + constFirst.length() + 1;
        name = select(line, index);

        index = line.indexOf(constSecond) + constSecond.length() + 1;
        type = change(select(line, index));

        index = line.indexOf(constThird) + constThird.length() + 1;
        weight = select(line, index);

        data.add(new Note(name, type, weight));

        if (count >= memorySize) {
            count = 0;
            pushInNewFile();
        }
    }

    private void pushInNewFile() {
        Collections.sort(data);
        cleanDefect(data);
        try {
            File tmpFile = File.createTempFile("FileSort", "dat");
            FileOutputStream outputFOS = new FileOutputStream(tmpFile);
            ObjectOutputStream outputOOS = new ObjectOutputStream(outputFOS);
            for (Note note: data) {
                outputOOS.writeObject(note);
            }
            outputOOS.close();
            outputFOS.close();
            file.add(tmpFile);
        }
        catch (IOException e) {
            log.info(e.getMessage());
        }
    }

    private void cleanDefect(List<Note> note) {
        ArrayList<Note> newList = new ArrayList<Note>();
        if(data.size() > 0) {
            newList.add(data.get(0));
        }
        for(int it = 1; it < data.size(); it++) {
            Note tmpFirst = data.get(it - 1);
            Note tmpSecond = data.get(it);
            if(!(tmpFirst.getName().equals(tmpSecond.getName()))) {
                newList.add(tmpSecond);
            }
        }
        data = newList;
    }

    private String select(String line, int begin) {
        StringBuilder result = new StringBuilder("");
        while(begin < line.length()) {
            char tmp = line.charAt(begin);
            if(tmp == '\"' || tmp == '”') {
                break;
            }
            result.append(tmp);
            begin++;
        }
        return result.toString();
    }

    private String change(String type) {
        if(type.equals("child")) {
            return "1";
        }
        else if (type.equals("sub")) {
            return "2";
        }
        else {
            return "3";
        }
    }
    private String backChange(String type) {
        if(type.equals("1")) {
            return "child";
        }
        else if (type.equals("2")) {
            return "sub";
        }
        else {
            return "root";
        }
    }
}

