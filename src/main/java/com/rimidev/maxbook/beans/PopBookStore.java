/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rimidev.maxbook.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author 1513733
 */
public class PopBookStore {
    public static void main(String[] args){
        Scanner inputFile = null;       
        String filename = "Books.csv";
        
        //try{ 
//            File file = new File(filename);
//            // checks if file is valid
//            if(!file.exists()||file.isDirectory())
//                throw new IOException("Invalid file");
   
//            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename),
//            StandardCharsets.UTF_8));
//                                 
//            inputFile = new Scanner(reader);
////            System.out.println("INSERT INTO Author_Book (author_id, book_isbn) VALUES ");
//            System.out.println("INSERT INTO Publisher (name) VALUES ");
//            inputFile.nextLine();
//            //int auth_id = 1;
//            while(inputFile.hasNext()){
//                String input = inputFile.nextLine();
//                System.out.println(input);
//                String[] line = input.trim().split(",");
//                //System.out.println("("+"\""+line[3]+"\"),");
//                //auth_id += 1;
//            }
//            System.out.print(";");
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {

                    File file = new File(filename);
                    // checks if file is valid
                    if (!file.exists() || file.isDirectory()) {
                        throw new IOException("Invalid file");
                    }
                    String line="";
                    int count = 0;
                    //lines 66 96 not good
                    System.out.println("INSERT INTO PUBLISHER (name) VALUES");
                    while ((line = br.readLine()) != null) {
                        //System.out.println(line);
                        String[] record = line.trim().split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)",-1);
                  
                        System.out.println("(\""+ record[3].trim() +"\"),");
                        count++;
                    }
            
        } catch(IOException e){            
            System.out.println("Connection error: "+filename);
        }finally{
            if(inputFile != null)
                inputFile.close();
        }
        
    }
}
