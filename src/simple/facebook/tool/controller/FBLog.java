/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simple.facebook.tool.controller;

import com.restfb.FacebookClient;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author nguye
 */
public class FBLog {
    private static String TOKEN = "";
    private static int MAX_NUM = 10;
    private static String targetUserID = "";
    private static FacebookClient facebookClient = null;
    public static boolean readLog(){
        File inputFile = new File("log.txt");
        if(!inputFile.exists()) return false;
        try {
            FileReader fileReader = new FileReader(new File("log.txt"));
            BufferedReader br = new BufferedReader(fileReader);
            TOKEN = br.readLine();
            try{
                MAX_NUM = Integer.parseInt(br.readLine());
            } catch(Exception e){
                MAX_NUM = 10;
            }
            try{
                facebookClient = FBHelper.getFacebookClient(TOKEN);
                targetUserID = br.readLine();
            } catch(Exception ee){
            }
            br.close();
            fileReader.close();
            return true;
        } catch (IOException ex) {
            return false;
        }
    }
    public static boolean writeLog(){
        if(TOKEN.equals("")) return false;
        try {
            FileWriter fileWriter = new FileWriter(new File("log.txt"));
            BufferedWriter bw = new BufferedWriter(fileWriter);
            bw.write(TOKEN + "\n");
            bw.write(MAX_NUM + "\n");
            bw.write(targetUserID);
            bw.close();
            fileWriter.close();
            return true;
        } catch (IOException ex) {
            return false;
        }
    }
    public FBLog() {
    }

    public static String getTOKEN() {
        return TOKEN;
    }

    public static void setTOKEN(String TOKEN) {
        FBLog.TOKEN = TOKEN;
    }

    public static int getMAX_NUM() {
        return MAX_NUM;
    }

    public static void setMAX_NUM(int MAX_NUM) {
        FBLog.MAX_NUM = MAX_NUM;
    }

    public static FacebookClient getFacebookClient() {
        return facebookClient;
    }

    public static void setFacebookClient(FacebookClient facebookClient) {
        FBLog.facebookClient = facebookClient;
    }

    public static String getTargetUserID() {
        return targetUserID;
    }

    public static void setTargetUserID(String targetUserID) {
        FBLog.targetUserID = targetUserID;
    }
    
  
}
