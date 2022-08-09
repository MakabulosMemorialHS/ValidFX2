/* **********************************************************************************************
 * vSplit.java 
 *
 * This utility, capable of being used standalone, is part of the ValidFX suite of
 * of programs for creating MMHS Valid Tickets.  If this program is to be
 * packaged with the ValidFX Jar File, then the following java declaration
 * command must be placed at the top.
 *
 *     package ph.mmhsvictoria.apps.validfx;
 *
 * Given a textfile, this program will print the contents of that text file in two
 * columns. It does not care about the width of the lines of the text file, it is
 * the user's problem to use this utility properly. While being part of the ValidFX Suite,
 * it does not depend on ValidFX and can be used standalone on any text file.
 *
 * To use this program, give the following command:
 * 
 *     $ java vSplit <input-text-file> <output-text-file> <lines-per-page>
 *
 * ********************************************************************************************/
package validfx;

import java.lang.*;
import java.util.*;
import java.io.*;

public class vSplit {
    public static void main(String args[]) {
    // When main() is called
    // args[0] <-- Path name of the text file to read.
    // args[1] <-- Path name of the output file to write.
    // args[2] <-- Format string. Indicates the format of the columns.
    //             Suggested for ValidFX: "%-38s         %-36s" 
    // args[3] <-- Number of lines per page. Suggested: 76.
        if (args.length != 3) {
            System.out.println("Usage: vSplit <input-file> <output-file> <format-string> <lines-per-page>\n\n");
        }
        else {
            verticalSplit(args[0], args[1], args[2], Integer.parseInt(args[3]));
        }
    }
    
    public static void verticalSplit( String fileToRead, 
                                      String fileToWrite,
                                      String lineFormat,
                                      int lpp) {

        int DebugMode = 0;       // 1 -- ON, 0 -- Off.
        BufferedReader csvInputStream;
        PrintWriter    jsonOutputStream;
        String FORMAT_STRING = lineFormat;    // Used to be: "%-38s         %-36s";

        int linesPerPage = lpp;    // 

        int pageWidth = 80;     // Page has width 80 characters.


        // The following lines are for debugging purposes.
        //
        if (DebugMode == 1) {
            System.out.println("File to read   : " + fileToRead);
            System.out.println("File to write  : " + fileToWrite);
            System.out.println("Lines per page : " + lpp);
        }


        // Open the given input files and prepare them for
        // Reading and writing.
        try {
            // The following is the idiom for creating a text line reader
            // These classes are in java.io
	    FileReader csvFile = new FileReader(fileToRead);
                csvInputStream = new BufferedReader(csvFile);

            // The following is the idiom for creating a text line writer.
            // These classes are in java.io
	    FileWriter jsonFile = new FileWriter(fileToWrite);
               jsonOutputStream = new PrintWriter(jsonFile);
        }
        catch (FileNotFoundException e) {
            System.err.println("Input file not found");
            return;
        }
        catch (IOException e) {
            System.err.println("Error in creating file.");
            return;
        }


        // We read in the first line of text before entering the
        // while loop.
        //

        String inputTextRow;    // Put the line that has been into this buffer
        try {
            inputTextRow = csvInputStream.readLine(); // Read only one line.
            if (inputTextRow == null) return;         // Nothing to read!!
        }
        catch(IOException e) {
            System.err.println("Unable to read input file.");
            return;
        }

        int pageCount = 0;
        int lineCount = 0;
        int currentColumn = 0;     // Zero indexed columns.

        // Right now we default to only two columns.
        String[] linesRead = new String[linesPerPage * 2];

        // Put blanks in linesRead;
        for (int i = 0; i < linesPerPage * 2; ++i) {
            linesRead[i] = "";
        }

        // Now proceed to read the lines.
        String output_line;

        while (inputTextRow != null) {
            linesRead[lineCount] = inputTextRow;
            lineCount += 1;
            
            // If linesRead is now full, then print it.
            if (lineCount == linesPerPage * 2) {

                for (int i = 0; i < linesPerPage; ++i) {
                   output_line = String.format(FORMAT_STRING, linesRead[i], linesRead[i + linesPerPage]);
                   jsonOutputStream.println(output_line);
                   if (DebugMode == 1) {
                       System.out.println("Line " + i + ": " + output_line);
                   }
                }

                // Put blanks in linesRead again;
                for (int i = 0; i < linesPerPage * 2; ++i) {
                    linesRead[i] = "";
                }

                lineCount = 0;                            // Clear lineCount
                pageCount += 1;                           // Read another page.
            }

            try {
                inputTextRow = csvInputStream.readLine(); // Read only one line.
                if (inputTextRow == null)  { // Nothing more to read!!

                    // Then print this one last page and then exit.
                    for (int i = 0; i < linesPerPage; ++i) {
                       output_line = String.format(FORMAT_STRING, linesRead[i], linesRead[i + linesPerPage]);
                       jsonOutputStream.println(output_line);
                       if (DebugMode == 1) {
                           System.out.println("Line " + i + ": " + output_line);
                       }
                    }
                    break;
                }
            }
            catch(IOException e) {
                System.err.println("Unable to read input file.");
                return;
            }
        }
        jsonOutputStream.flush();  // Don't forget this!!
    }
}


