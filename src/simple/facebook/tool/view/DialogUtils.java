/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simple.facebook.tool.view;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 *
 * @author nguye
 */
public class DialogUtils {
    public static void showMessage(Component parentComponent, String title, String msg){
        JOptionPane.showMessageDialog(parentComponent, msg, title, JOptionPane.INFORMATION_MESSAGE);
    }
    public static void showWarning(Component parentComponent, String title, String msg){
        JOptionPane.showMessageDialog(parentComponent, msg, title, JOptionPane.WARNING_MESSAGE);
    }
}
