package com.helmes.task.main;

import com.helmes.task.filter.Filter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String args[]) throws IOException {

        if(args.length == 0) {
            System.out.print("You haven't specified the file!");
            return;
        }
        else {
            String nameXML = args[0];
            Filter filter = new Filter(nameXML);
            String nameFile = filter.toFilter();
            BufferedReader reader = new BufferedReader(new FileReader(nameFile));
            String line;
            while((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}
