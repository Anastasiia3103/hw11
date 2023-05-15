package hw11;

import java.io.*;

public class FileManager {
    private static String currentDirectory = System.getProperty ("user.dir");

    public static void main (String[] args) {
        BufferedReader reader = new BufferedReader (new InputStreamReader (System.in));

        while (true) {
            System.out.print ("> ");
            try {
                String command = reader.readLine ().trim ();
                executeCommand (command);
            } catch (IOException e) {
                System.out.println ("An error occurred while reading the input: " + e.getMessage ());
            }
        }
    }

    private static void executeCommand (String command) {
        String[] tokens = command.split (" ");
        String action = tokens[0];

        switch (action) {
            case "cd":
                if (tokens.length > 1){
                    changeDirectory (tokens[1]);
                }
                else {
                    System.out.println ("Usage: cd <directory>");
                }
                break;
            case "cp":
                if (tokens.length > 2){
                    copyFile (tokens[1], tokens[2]);
                }
                else {
                    System.out.println ("Usage: cp <source> <destination>");
                }
                break;
            case "ls":
                listFiles ();
                break;
            case "pwd":
                printWorkingDirectory ();
                break;
            case "exit":
                System.exit (0);
                break;
            default:
                System.out.println ("Invalid command");
        }
    }

    private static void changeDirectory (String directory) {
        if (directory.equals ("..")){
            // Move to parent directory
            File currentDir = new File (currentDirectory);
            String parentDir = currentDir.getParent ();
            if (parentDir != null){
                currentDirectory = parentDir;
            }
            else {
                System.out.println ("Already in the root directory");
            }
        }
        else {
            File newDir = new File (currentDirectory, directory);
            if (newDir.isDirectory () && newDir.exists ()){
                currentDirectory = newDir.getAbsolutePath ();
            }
            else {
                System.out.println ("Directory not found: " + newDir.getAbsolutePath ());
            }
        }
    }

    private static void copyFile (String source, String destination) {
        File sourceFile = new File (currentDirectory, source);
        File destFile = new File (currentDirectory, destination);

        try (InputStream in = new FileInputStream (sourceFile);
             OutputStream out = new FileOutputStream (destFile)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read (buffer)) > 0) {
                out.write (buffer, 0, length);
            }
            System.out.println ("File copied successfully");
        } catch (IOException e) {
            System.out.println ("An error occurred while copying the file: " + e.getMessage ());
        }
    }

    private static void listFiles () {
        File directory = new File (currentDirectory);
        File[] files = directory.listFiles ();
        if (files != null){
            for (File file : files) {
                System.out.println (file.getName ());
            }
        }
    }

    private static void printWorkingDirectory () {
        System.out.println (currentDirectory);
    }
}

