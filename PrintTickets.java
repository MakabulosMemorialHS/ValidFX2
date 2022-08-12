/* ************************************************************************************************
 * PrintTickets.java
 *
 * This utility, capable of being used standalone, is part of the ValidFX suite of
 * of programs for creating MMHS Valid Tickets.
 * If this program is to be packaged with the ValidFX Jar File, then the following package
 * command must be uncommented.
 *
 * package ph.mmhsvictoria.apps.validfx;
 *
 * To use this program, give the following command:
 * 
 *    $ java -jar PrintData <valids-csv-file> <output-file> <field-name> <target-field-value>
 *
 * ***********************************************************************************************/
package validfx;
import java.lang.*;
import java.util.*;
import java.io.*;

public class PrintTickets {
    public static void main(String args[]) {
    // When main() is called
    // args[0] <-- The name of the CSV file to read.
    // args[1] <-- The name of the outputfile to write the valid tickets.
    // args[2] <-- The field name to look for
    // args[3] <-- The value to be looked up.
    // args[4] <-- The height of the page in text lines.
        if (args.length != 6) {
            System.out.println("Usage: PrintData <csv-file> <output-file> <field-name> <field-value> <lines-per-page\n\n");
        }
        else {
            convertTSV(args[0], args[1], args[2], args[3], 
                      Integer.parseInt(args[4]));
        }
    }
    
    public static void convertTSV(String fileToRead, 
                                  String fileToWrite,
                                  String paramTargetField,
                                  String paramTargetValue,
                                  int paramLPP) {
   
        int DebugMode = 0;        // 1 --- ON, 0 --- OFF
        BufferedReader csvInputStream;
        PrintWriter    jsonOutputStream;
        String headerRow;
        String[] headerArray;
        String dataRow;
        int targetIndex;          // Index of the field to be targetted.
        int numHeaderElements;
        int numDataElements;
        int numElementsToRead;    // How many columns will be read in a data row.
        String targetField = paramTargetField.toUpperCase();
        String targetValue = paramTargetValue.toUpperCase();


        if (DebugMode == 1) {
            System.out.println("File to read  : " + fileToRead);
            System.out.println("File to write : " + fileToWrite);
            System.out.println("Target field  : " + targetField);
            System.out.println("Target value  : " + targetValue);
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

        // Now we read the header.
        try {
            do {
                headerRow = csvInputStream.readLine(); // Read the header
                if (headerRow == null) return;         // Nothing to read!!
            } while (headerRow.length() == 0);
        }
        catch(IOException e) {
            System.err.println("Unable to read header.");
            return;
        }

        headerArray = headerRow.replace('\"', ' ').split("\t");
        numHeaderElements = headerArray.length;

        // Find indices of the important fields
        // Find the index of the targetField.

        int idnum_std = -1;
        int status_std = -1;
        int section_std = -1;
        int sex_std = -1;
        int gname_std = -1;
        int fname_std = -1;
        int gu_g_name_std = -1;
        int gu_f_name_std = -1;
        int brgy_std = -1;
        int heading_std = -1;
        int date1_std = -1;
        int amt1_std = -1;
        int rem1_std = -1;
        int date2_std = -1;
        int amt2_std = -1;
        int rem2_std = -1;
        int date3_std = -1;
        int amt3_std = -1;
        int rem3_std = -1;
        int ms_mak_std = -1;
        int af_std = -1;
        int tpaper_std = -1;
        int tapsa_std = -1;
        int ins_std = -1;
        int lfee_std = -1;
        int code_std = -1;
        int rem4_std = -1;
        int comment5_std = -1;
        int total_std = -1;
        int due_std = -1;
        int miscf_std = -1;
        int misc_due_std = -1;
        String strUCPtr;        // String under the current pointer


        targetIndex = -1;     // This value indicates the target was not found.
        for (int i = 0; i < numHeaderElements; ++i) {
              strUCPtr = headerArray[i].toUpperCase();
              if ((headerArray[i].strip()).equals(targetField.strip())) {
                   targetIndex = i;
              }

              if (strUCPtr.strip().equals("ID NO.")) {
                   idnum_std = i;
              }
              else if (strUCPtr.strip().equals("STATUS")) {
                   status_std = i;
              }
              else if (strUCPtr.strip().equals("SECT")) {
                   section_std = i;
              }
              else if (strUCPtr.strip().equals("SEX")) {
                   sex_std = i;
              }
              else if (strUCPtr.strip().equals("GNAME")) {
                   gname_std = i;
              }
              else if (strUCPtr.strip().equals("FNAME")) {
                   fname_std = i;
              }
              else if (strUCPtr.strip().equals("GU.G.NAME")) {
                   gu_g_name_std = i;
              }
              else if (strUCPtr.strip().equals("GU.F.NAME")) {
                   gu_f_name_std = i;
              }
              else if (strUCPtr.strip().equals("BRGY")) {
                   brgy_std = i;
              }
              else if (strUCPtr.strip().equals("HEADING")) {
                   heading_std = i;
              }
              else if (strUCPtr.strip().equals("DATE-1")) {
                   date1_std = i;
              }
              else if (strUCPtr.strip().equals("AMT-1")) {
                   amt1_std = i;
              }
              else if (strUCPtr.strip().equals("REM-1")) {
                   rem1_std = i;
              }
              else if (strUCPtr.strip().equals("DATE-2")) {
                   date2_std = i;
              }
              else if (strUCPtr.strip().equals("AMT-2")) {
                   amt2_std = i;
              }
              else if (strUCPtr.strip().equals("REM-2")) {
                   rem2_std = i;
              }
              else if (strUCPtr.strip().equals("DATE-3")) {
                   date3_std = i;
              }
              else if (strUCPtr.strip().equals("AMT-3")) {
                   amt3_std = i;
              }
              else if (strUCPtr.strip().equals("REM-3")) {
                   rem3_std = i;
              }
              else if (strUCPtr.strip().equals("MS MAK")) {
                   ms_mak_std = i;
              }
              else if (strUCPtr.strip().equals("AF")) {
                   af_std = i;
              }
              else if (strUCPtr.strip().equals("T-PAPER")) {
                   tpaper_std = i;
              }
              else if (strUCPtr.strip().equals("TAPSA")) {
                   tapsa_std = i;
              }
              else if (strUCPtr.strip().equals("INS.")) {
                   ins_std = i;
              }
              else if (strUCPtr.strip().equals("L. FEE")) {
                   lfee_std = i;
              }
              else if (strUCPtr.strip().equals("CODE")) {
                   code_std = i;
              }
              else if (strUCPtr.strip().equals("REM-4")) {
                   rem4_std = i;
              }
              else if (strUCPtr.strip().equals("COMMENT-5")) {
                   comment5_std = i;
              }
              else if (strUCPtr.strip().equals("TOTAL")) {
                   total_std = i;
              }
              else if (strUCPtr.strip().equals("DUE")) {
                   due_std = i;
              }
              else if (strUCPtr.strip().equals("MISC-F")) {
                   miscf_std = i;
              }
              else if (strUCPtr.strip().equals("MISC DUE")) {
                   misc_due_std = i;
              }
        }

        // Now attempting to read the data rows.

	try {
            do {
	        dataRow = csvInputStream.readLine();
            } while (dataRow != null && dataRow.length() == 0); 
	}
	catch (IOException e) {
	    System.err.println("Error reading data row.");
	    e.printStackTrace();
	    return;
	}
       

        // We read the number of lines per page of output and
        // stack as many tickets as can be placed on the page.
        // The excess lines will then be padded with empty lines.
        // The padding will be made at the bottom of the
        // stack.
        // Note that the height of each Valid Ticket includes the empty lines
        // above and below the ticket. In our present implementation, the
        // following is true.
        //
        // Ticket margin above = 0 lines
        // Ticket body          = 15 lines
        // Ticket margin below = 3 lines
        //

        int LPP = paramLPP;    // Number of lines per page. Looks like this because of an edit.
        int ticketHeight = 16;
        int numTicketsPerPage = 4;
        int paddingBottom = 0;      // No padding
        int ticketCount = 0;
        String[] dataArray;
        String output_line;
        String outA, outB, outC, outD;

        while (dataRow != null) {

	    dataArray = dataRow.replace('\"', ' ').split("\t");
	    numDataElements = dataArray.length;

	    // Read data only to the number of Elements in this data row
	    // or, if elements in this data row is greater than the
	    // number of headers, only up to the number of header elements.

	    if (numDataElements <= numHeaderElements) {
	       numElementsToRead = numDataElements;
	    }
	    else
	       numElementsToRead = numHeaderElements;

            // We look for missing data and replace the missing data with an
            // empty string.

            
            if (targetIndex > -1 && targetIndex < numElementsToRead) {
                String tstran = dataArray[targetIndex].strip();
                if (tstran.equals(targetValue.strip())) {
                  
                    // Now print each line of the Valids Ticket
                    // Each line of the ticket, excluding margins, is 35 characters long.
                    output_line = String.format("%-16s %16s", 
                        (idnum_std < 0)   ? "" : dataArray[idnum_std].strip(), 
                        (section_std < 0) ? "" : dataArray[section_std].strip());
                    jsonOutputStream.println(output_line);

                    output_line = String.format("%s %s", 
                        (gname_std < 0) ? "" : dataArray[gname_std].strip(), 
                        (fname_std < 0) ? "" : dataArray[fname_std].strip());
                    jsonOutputStream.println(output_line);

                    output_line = String.format("%s %s", 
                        (gu_g_name_std < 0) ? "" : dataArray[gu_g_name_std].strip(), 
                        (gu_f_name_std < 0) ? "" : dataArray[gu_f_name_std].strip());
                    jsonOutputStream.println(output_line);

                    output_line = String.format("%-16s %16s", 
                        (brgy_std < 0)   ?   "" : dataArray[brgy_std].strip(), 
                        (status_std < 0) ? "" : dataArray[status_std].strip());
                    jsonOutputStream.println(output_line);

                    jsonOutputStream.print("\n"); // Add an extra line
                    jsonOutputStream.println("     >> Payments Received <<");
                    output_line = String.format("%-11s %12s %-8s", 
                        (date1_std < 0) ? "" : dataArray[date1_std].strip(), 
                        (rem1_std < 0)  ?  "" : dataArray[amt1_std].strip(), 
                        (rem1_std < 0)  ?  "" : dataArray[rem1_std].strip());
                    jsonOutputStream.println(output_line);

                    output_line = String.format("%-11s %12s %-8s", 
                        (date2_std < 0) ? "" : dataArray[date2_std].strip(), 
                        (amt2_std < 0) ?  "" : dataArray[amt2_std].strip(), 
                        (rem2_std < 0) ?  "" : dataArray[rem2_std].strip());
                    jsonOutputStream.println(output_line);

                    output_line = String.format("%-11s %12s %-8s", 
                        (date3_std < 0) ? "" : dataArray[date3_std].strip(), 
                        (amt3_std < 0)  ? "" : dataArray[amt3_std].strip(), 
                        (rem3_std < 0)  ? "" : dataArray[rem3_std].strip());
                    jsonOutputStream.println(output_line);
                    jsonOutputStream.println("      >>>>--->>o0o<<---<<<<");

                    output_line = String.format("%-7s %8s %-7s %8s", "MS", 
                        (ms_mak_std < 0) ? "" : dataArray[ms_mak_std], "USB", 
                        (af_std < 0)     ? "" : dataArray[af_std].strip());
                    jsonOutputStream.println(output_line);

                    output_line = String.format("%-7s %8s %-7s %8s", "Online", 
                        (tpaper_std < 0) ? "" : dataArray[tpaper_std], "BS", 
                        (tapsa_std < 0)  ? "" : dataArray[tapsa_std].strip());
                    jsonOutputStream.println(output_line);

                    output_line = String.format("%-7s %8s %-7s %8s", "INS", 
                        (ins_std < 0)  ? "" : dataArray[ins_std], "MISC", 
                        (lfee_std < 0) ? "" : dataArray[lfee_std].strip());
                    jsonOutputStream.println(output_line);

                    output_line = String.format("%-8s %8s", "RM", 
                        (rem4_std < 0) ? "" : dataArray[rem4_std]);
                    jsonOutputStream.println(output_line);

                    jsonOutputStream.println("");
                    output_line = String.format("%-33s", 
                        (comment5_std < 0) ? "" : dataArray[comment5_std].strip());
                    jsonOutputStream.println(output_line);

                    ticketCount = ticketCount + 1;

                    if (ticketCount < numTicketsPerPage) {
                        jsonOutputStream.print("\n\n\n\n\n");
                    }

                    if (ticketCount == numTicketsPerPage) {
                        ticketCount = 0;
                    }
                }
            }

	    try {
                do {
		    dataRow = csvInputStream.readLine();
                } while (dataRow != null && dataRow.length() == 0);
	    }
	    catch (IOException e) {
		e.printStackTrace();
		break;
	    }
        }
        jsonOutputStream.flush();  // Don't forget this!!
    }
}


