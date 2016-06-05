package com.helmes.task.filter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

public class FilterTest {

    PrintWriter writer;

    @Before
    public void before() throws IOException {
        writer = new PrintWriter(new BufferedWriter(new FileWriter("input.txt")));
    }

    @After
    public void after() throws IOException {
        writer.close();
        Filter filter = new Filter("input.txt", 1);
        String nameFile = filter.toFilter();
        BufferedReader reader = new BufferedReader(new FileReader(nameFile));
        String line;
        while((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }

    @Test
    public void testOne() throws IOException {
        writer.print("<rules>\n" +
                "<rule name=”a” type=”child” weight=”17”/>\n" +
                "<rule name=”a” type=”root” weight=”29”/>\n" +
                "<rule name=”b” type=”sub” weight=”56”/>\n" +
                "<rule name=”c” type=”child” weight=”99”/>\n" +
                "<rule name=”a” type=”sub” weight=”12”/>\n" +
                "<rule name=”c” type=”sub” weight=”99”/>\n" +
                "<rule name=”c” type=”root” weight=”99”/>\n" +
                "<rule name=”a” type=”child” weight=”34”/>\n" +
                "<rule name=”d” type=”root” weight=”45”/>\n" +
                "<rule name=”b” type=”sub” weight=”34”/>\n" +
                "</rules>");
    }

    @Test
    public void testTwo() {
        writer.print("<rules>\n" +
                "<rule name=”aaaa” type=”child” weight=”17”/>\n" +
                "<rule name=”aaaa” type=”child” weight=”45”/>\n" +
                "<rule name=”aaaa” type=”sub” weight=”56”/>\n" +
                "<rule name=”aaaa” type=”root” weight=”99”/>\n" +
                "<rule name=”aaaa” type=”root” weight=”12”/>\n" +
                "<rule name=”aaaa” type=”sub” weight=”99”/>\n" +
                "<rule name=”aaaa” type=”root” weight=”99”/>\n" +
                "<rule name=”a” type=”child” weight=”34”/>\n" +
                "<rule name=”d” type=”root” weight=”45”/>\n" +
                "<rule name=”b” type=”sub” weight=”34”/>\n" +
                "</rules>");
    }

    @Test
    public void testThree() {
        writer.print("<rules>\n" +
                "<rule name=”k” type=”child” weight=”1”/>\n" +
                "<rule name=”k” type=”child” weight=”1”/>\n" +
                "<rule name=”k” type=”child” weight=”4”/>\n" +
                "<rule name=”k” type=”child” weight=”4”/>\n" +
                "<rule name=”k” type=”sub” weight=”5”/>\n" +
                "<rule name=”k” type=”sub” weight=”5”/>\n" +
                "<rule name=”k” type=”root” weight=”9”/>\n" +
                "<rule name=”k” type=”root” weight=”1”/>\n" +
                "<rule name=”k” type=”sub” weight=”9”/>\n" +
                "<rule name=”k” type=”root” weight=”9”/>\n" +
                "<rule name=”k” type=”child” weight=”3”/>\n" +
                "<rule name=”k” type=”root” weight=”4”/>\n" +
                "<rule name=”k” type=”sub” weight=”4”/>\n" +
                "</rules>");
    }

    @Test
    public void testFour() throws IOException {
        writer.print("<rules>\n" +
                "<rule name=”a” type=”child” weight=”17”/>\n" +
                "</rules>");
    }

    @Test
    public void testFive() throws IOException {
        writer.print("<rules>\n" +
                "</rules>");
    }
}