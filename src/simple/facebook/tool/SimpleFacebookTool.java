/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simple.facebook.tool;

import simple.facebook.tool.controller.FBLog;
import simple.facebook.tool.view.MainActivity;

/**
 *
 * @author nguye
 */
public class SimpleFacebookTool {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        FBLog.readLog();
        MainActivity mainActivity = new MainActivity();
        mainActivity.setVisible(true);
    }
    
}
