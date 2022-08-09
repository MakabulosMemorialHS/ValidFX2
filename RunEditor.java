/* *******************************************************************************
 * RunEditor.java
 *
 * Runs either the editor notepad.exe if one windows or the editor
 * gedit if on LInux.
 *
 * *******************************************************************************/
package validfx;

import java.lang.*;
import java.util.*;
import java.io.IOException;

public class RunEditor {
     public static void main(String[] args) {
         // The following should be true:
         // args[0] <--- The name of the file which will be edited.
         if (args.length != 1) {
             System.out.println("Usage: RunEditor <text-file-to-edit>");
         }
         else {
             editThisFile(args[0]);
         }
     }
     
     public static void editThisFile(String textfile) {
         String command;
         try {
              if (System.getProperty("os.name").equals("Linux")) {
                   command = "/usr/bin/gedit " + textfile;  // The space is important
              }
              else {
                   command = "notepad " + textfile;   // Space is important! 
              }
              Runtime run = Runtime.getRuntime();
              Process proc = run.exec(command);
         }
         catch (IOException e) {
              e.printStackTrace();
         }
     }
}

