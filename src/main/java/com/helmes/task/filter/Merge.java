package com.helmes.task.filter;

import org.apache.log4j.Logger;

import java.io.*;

public class Merge extends Thread {

    private File fileFirst;
    private File fileSecond;
    private File tmpFile;

    private FileInputStream inputFirstFIS;
    private ObjectInputStream inputFirstOIS;

    private FileInputStream inputSecondFIS;
    private ObjectInputStream inputSecondOIS;

    private FileOutputStream outputFOS;
    private ObjectOutputStream outputOOS;

    private static Logger log = Logger.getLogger(Merge.class);

    public Merge(File fileFirst, File fileSecond) {

        this.fileFirst = fileFirst;
        this.fileSecond = fileSecond;

        try {

            inputFirstFIS = new FileInputStream(fileFirst);
            inputFirstOIS = new ObjectInputStream(inputFirstFIS);

            inputSecondFIS = new FileInputStream(fileSecond);
            inputSecondOIS = new ObjectInputStream(inputSecondFIS);

            tmpFile = File.createTempFile("FileSort", "dat");

            outputFOS = new FileOutputStream(tmpFile);
            outputOOS = new ObjectOutputStream(outputFOS);

        }
        catch (IOException e) {
            log.info(e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            Note tmpFirst, tmpSecond;

            tmpFirst = (Note) inputFirstOIS.readObject();
            tmpSecond = (Note) inputSecondOIS.readObject();

            while (true) {
                try {
                    if (tmpFirst.getName().equals(tmpSecond.getName())) {
                        if (tmpFirst.compareTo(tmpSecond) > 0) {
                            tmpFirst = null;
                            tmpFirst = (Note) inputFirstOIS.readObject();
                        } else {
                            tmpSecond = null;
                            tmpSecond = (Note) inputSecondOIS.readObject();
                        }
                    } else {
                        if (tmpFirst.compareTo(tmpSecond) > 0) {
                            outputOOS.writeObject(tmpSecond);
                            tmpSecond = null;
                            tmpSecond = (Note) inputSecondOIS.readObject();
                        } else {
                            outputOOS.writeObject(tmpFirst);
                            tmpFirst = null;
                            tmpFirst = (Note) inputFirstOIS.readObject();
                        }
                    }
                } catch (EOFException e) {
                    break;
                }
            }
            while (tmpFirst != null) {
                try {
                    outputOOS.writeObject(tmpFirst);
                    tmpFirst = (Note) inputFirstOIS.readObject();
                } catch (EOFException e) {
                    break;
                }
            }
            while (tmpSecond != null) {
                try {
                    outputOOS.writeObject(tmpSecond);
                    tmpSecond = (Note) inputSecondOIS.readObject();
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException e) {
            log.info(e.getMessage());
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage());
        }
    }

    public File getFile() {
        return tmpFile;
    }
}

