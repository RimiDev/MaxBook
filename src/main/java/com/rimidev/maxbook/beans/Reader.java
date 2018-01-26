package com.rimidev.maxbook.beans;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Philippe
 */
public class Reader {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String csvFile = "books.csv";
        String line = "";
        String csvSplitBy = ",";
        
        try(BufferedReader br = new BufferedReader(new FileReader(csvFile))){
            int count = 0;
            System.out.println("INSERT INTO BOOKS (isbn, title, author_id, "
                    + "pub_id, pub_date, pages, genre, list_price, sale_price, "
                    + "wholesale_price, image_name, description, format, entered_date, "
                    + "removal_status) VALUES");
            while((line = br.readLine()) != null){
                String[] record = line.split(csvSplitBy);
                System.out.println(count +" (" +record[0] +", " +record[1] +", " 
                        +record[2] +", " +record[3] +", " +record[4] +", " +record[5] +", "
                        +record[6] +", " +record[7] +", " +record[8] +", " +record[9] +", " 
                        +record[10] +", " +"filename, " +"description, " +".png, "
                        +"'January 1, 2018', " +"available)");
                count++;
            }
        }catch(IOException e){
            System.out.print(e.getMessage());
        }
    }
    
}
