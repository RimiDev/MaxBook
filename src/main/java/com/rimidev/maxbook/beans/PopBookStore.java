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
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author 1513733
 */
public class PopBookStore {
    public static void main(String[] args){
        List<String[]>lines = new ArrayList<>();
        Scanner inputFile = null;       
        String filename = "Books.csv";
        
        try{ 
            File file = new File(filename);
            // checks if file is valid
            if(!file.exists()||file.isDirectory())
                throw new IOException("Invalid file");
   
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename),
            StandardCharsets.UTF_8));
                                 
            inputFile = new Scanner(reader);
            System.out.println("INSERT INTO BOOK VALUES ");
            inputFile.nextLine();
            while(inputFile.hasNext()){
                String input = inputFile.nextLine();
                String[] line = input.trim().split(",");
                String data = "";
                int pubid = 1;
                int 
                // NEED  columns 0,1,4,5,6,7,8,9,10,11,13
                for(int i=0;i<line.length;i++){
                    switch (i) {
                        case 0:
                            data += "(\""+line[i]+"\",";
                            break;
                        case 1:
                            data += "\""+line[i]+"\",";
                            break;
                        case 4:
                            break;
                        case 5:
                            break;
                        case 6:
                            break;
                        case 7:
                            break;
                        case 8:
                            break;
                        case 9:
                            break;
                        case 10:
                            break;
                        case 11:
                            break;
                        case 13:
                            break;
                        default:
                            break;
                    }
                }
                if(line.length !=0)
                    lines.add(line);
            }
            
        } catch(IOException e){            
            System.out.println("Connection error: "+filename);
        }finally{
            if(inputFile != null)
                inputFile.close();
        }
        
    }
}
