package app.appmeteo.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BookMark {

    public String Source;

    public BookMark() {

        this.Source = "./src/main/resources/app/appmeteo/data/Bookmarks.txt";

    }

    public List < String > ReadFile() throws IOException {
        BufferedReader My_Reader = null;
        String Line_Read;
        List < String > Global_Read = new ArrayList < String > ();
        try {
            My_Reader = new BufferedReader(new FileReader(this.Source));
            Line_Read = My_Reader.readLine();
            while (Line_Read != null) {

                Line_Read = Line_Read.toLowerCase();

                Global_Read.add(Character.toUpperCase(Line_Read.charAt(0)) + Line_Read.substring(1));

                Line_Read = My_Reader.readLine();
            }
        } catch (IOException Error) {
            Error.printStackTrace();
        }
        try {
            My_Reader.close();
        } catch (IOException Error) {
            Error.printStackTrace();
        }
        return Global_Read;
    }

    public void Write(List < String > Lines_To_Add) throws IOException {

        Writer writer = null;
        List < String > Lines_To_Write = ReadFile();
        Reset_File();

        for (String line: Lines_To_Add) {
            Lines_To_Write.add(line);

        }

        try {
            writer = new PrintWriter(new BufferedWriter(new FileWriter(this.Source)));

            for (int i = 0; i < Lines_To_Write.size() - 1; i++) {
                if (Lines_To_Write.get(i).length() > 1) {
                    writer.write(Lines_To_Write.get(i).toUpperCase() + "\n");
                }
            }
            writer.write(Lines_To_Write.get(Lines_To_Write.size() - 1));
        } catch (IOException exc) {
            exc.printStackTrace();
        }
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Reset_File() throws IOException {
        Writer writer = null;
        writer = new PrintWriter(new BufferedWriter(new FileWriter(this.Source)));

        try {
            writer = new PrintWriter(new BufferedWriter(new FileWriter(this.Source)));
            writer.write(" ");

        } catch (IOException exc) {
            exc.printStackTrace();
        }
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void Delete_Sequence(String sequence) throws IOException {
        List < String > List_Of_Str_In_File = ReadFile();
        List < String > Temp = ReadFile();

        for (String inFile: Temp) {
            if (sequence.equalsIgnoreCase(inFile)) {
                List_Of_Str_In_File.remove(sequence);

            }

        }
        Reset_File();
        Write(List_Of_Str_In_File);
    }

    public void AddBookMark(String City) throws IOException {
        boolean Is_city_is_not_in_list = true;
        List < String > Check_If_City_Is_Inside = ReadFile();
        for (String All_ready_inside: Check_If_City_Is_Inside) {
            if (City.equalsIgnoreCase(All_ready_inside)) {
                Is_city_is_not_in_list = false;

            }

        }
        if (Is_city_is_not_in_list) {
            ArrayList Adder = new ArrayList();

            Adder.add(City);
            Write(Adder);
        }
    }

}