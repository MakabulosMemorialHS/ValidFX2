/* *******************************************************************
 * ValidFX.java
 *
 * An implementation of the Valids program in JavaFX.
 * This implementation was accomplished with the aim of
 * practicing in the use of Java and Gradle.
 *
 * Note how this application now has a valid package name.
 * I never did anything of that sort before. Now I am doing so.
 * HOORAY FOR CHANGE!
 *
 * *******************************************************************/
package validfx;

import java.lang.*;
import java.util.*;

import java.io.*;
import java.net.URL;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;



public class ValidFX extends Application {

    public static void systemProperties() {
        System.out.println("OS        : " + System.getProperty("os.name"));
        System.out.println("OS Arch   : " + System.getProperty("os.arch"));
        System.out.println("Classpath : " + System.getProperty("java.class.path") + "\n");
    }

    public static void main(String[] args) {
        systemProperties();
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FileChooser fileChooser = new FileChooser();
 
        // We need to find the absolute location of the resource file. 
        // This is because the FXMLLoader.setLocation() function requires an
        // absolute path including a protocol.
        // In order to successfully do this while still keeping our application portable.
        // we need to do the following:
        // 1. Determine the absolute path where the JVM was invoked.
        // 2. From that location, we then move to the relative path of the resource file
        //    that we need.
        // Let's try this.
        systemProperties();
        String cwd = System.getProperty("user.dir");   // This locates where the JVM was invoked.
        System.out.println("Invoked from " + cwd);
        String path_to_resource = cwd + "/src/main/resources/validfx.fxml";

        VBox       root = new VBox(10);

        HBox      B1    = new HBox(10);
        Text       T    = new Text("File Name    ");
        TextField  tF   = new TextField();
        tF.setPrefColumnCount(20);
        Button browse   = new Button("Browse");
        browse.setOnAction((event) -> {
            // The File Object is declared in java.io.File.
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            String fileToRead = selectedFile.getAbsolutePath();
            tF.setText(fileToRead);
        });
        B1.getChildren().addAll(T, tF, browse);

        root.getChildren().addAll(B1);
        VBox.setMargin(B1, new Insets(10,10,5,10));


        HBox      B2    = new HBox(10);
        Text     targF  = new Text("Target Field ");
        TextField  tff  = new TextField();
        B2.getChildren().addAll(targF, tff);
        root.getChildren().addAll(B2);
        VBox.setMargin(B2, new Insets(5,10,5,10));


        HBox      B3    = new HBox(10);
        Text     targV  = new Text("Target Value");
        TextField  tfv  = new TextField();
        B3.getChildren().addAll(targV, tfv);
        root.getChildren().addAll(B3);
        VBox.setMargin(B3, new Insets(5,10,5,10));

        HBox      B4      = new HBox(10);
        B4.setAlignment(Pos.CENTER_RIGHT);
        Button    cancelB = new Button(" Close ");
        cancelB.setCancelButton(true);
        cancelB.setOnAction((event) -> Platform.exit());
       

        // Pressing the OK Button will do the job this
        // application was written for. Hence, this part
        // is also the most complex.

        Button    okB     = new Button("   OK   ");
        okB.setDefaultButton(true);

        // This is the event handler. Take note of the template.
        // This is how all event handlers are created.
        //        EventHandler<ActionEvent> OKEventHandler = new EventHandler<ActionEvent>() {
        //            @Override
        //            public void handle(ActionEvent aEvnt) {
        //		Process TSVtoJSONProcess;
        //                // String inputBuffer = new String();
        //
        //                int endIndexSlash     = tF.getText().lastIndexOf("/");
        //                String outputFilePath = tF.getText().substring(0, endIndexSlash + 1);
        //                String outputFileName = outputFilePath + "/output.json"; // The extra slash
        //                                                                         // won't hurt
        //                
        //		try {
        //		    TSVtoJSONProcess =
        //			Runtime.getRuntime().exec(
        //                             "java -cp build/classes/java/main "
        //                           + "ph/mmhsvictoria/apps/validfx/TSVtoJSON "
        //                           + tF.getText() + " "     // The file to read from
        //                           + outputFileName);
        //                } 
        //		catch (IOException e) {
        //		    System.err.println("Error on TSVtoJSON call.");
        //		    e.printStackTrace();
        //		}
        //            }
        //        };


        EventHandler<ActionEvent> OKEventHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent aEvnt) {
                String inputBuffer = new String();

                int endIndexSlash;
                String OSName         = System.getProperty("os.name");

                if (OSName.contains("Windows")) {
                    endIndexSlash     = tF.getText().lastIndexOf("\\");
                }
                else {
                    endIndexSlash     = tF.getText().lastIndexOf("/");
                }
                
                //  try {
                //      TSVtoJSONProcess =
                //          Runtime.getRuntime().exec(
                //               "java -cp build/classes/java/main "
                //             + "ph/mmhsvictoria/apps/validfx/TSVtoJSON "
                //             + tF.getText() + " "      The file to read from
                //             + outputFileName);
                //  } 
                //  catch (IOException e) {
                //      System.err.println("Error on TSVtoJSON call.");
                //      e.printStackTrace();
                //  }

                String inputFileName       = tF.getText();
                String outputFilePath      = tF.getText().substring(0, endIndexSlash + 1);
                String outputFileName      = outputFilePath + "/yonwah.txt";
                String validTicketFileName = outputFilePath + "/valid-tickets.txt";
                String fieldOfInterest = tff.getText();
                String valueOfInterest = tfv.getText();

                System.out.println("Input file:               " + inputFileName);
                System.out.println("Intermediate output file: " + outputFileName);
                System.out.println("Valid Ticket output file: " + validTicketFileName);
                System.out.println("field: " + fieldOfInterest + " value: " + valueOfInterest);

                // The following value for lines per page, paper size, and
                // margins were discovered by trial and error:
                //
                // Printer        : Brother Inkjet
                // Paper size     : legal
                // lines per page : 72
                // Left margin    : 20 mm
                // Right margin   :  5 mm
                // Top margin     :  8 mm
                // Bottom margin  : 20 mm
                //
                PrintTickets.convertTSV(inputFileName, 
                                        outputFileName, 
                                        fieldOfInterest, 
                                        valueOfInterest, 
                                        72);

                vSplit.verticalSplit(outputFileName, 
                                     validTicketFileName,
                                     "%-33s          %-33s", 
                                     79);

                RunEditor.editThisFile(validTicketFileName);
            }
        };


        okB.setOnAction(OKEventHandler);


        B4.getChildren().addAll(cancelB, okB);
        root.getChildren().addAll(B4);
        VBox.setMargin(B4, new Insets(5,10,10,10));

        primaryStage.setTitle("Create Valids JavaFX Version");
        primaryStage.setScene(new Scene(root, 500, 190));

        primaryStage.show();
    }
}

