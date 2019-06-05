package Manager;

import java.io.*;
import java.util.List;
/**
 * Represents the DataManager of MoblimaApp that provides the read and write methods using serializable to manipulate binary files.
 * @author: CZ2002 Team 3
 * @version: Final version
 * @since 2017-11-16
 */

public class DataManager 
{	
	// To specify the location to store database files
    private static final String cFilePath = System.getProperty("user.dir") + "\\database\\"; 
    
    /**
     * Initialises the DataManager object which creates a "database" folder in the root directory of the application.
     * The database file created has the name that is passed in, filename, in the format of .dat (E.g. movie.dat).
     * @param filename The inputed filename for the database file.
     */
    public DataManager(final String filename) 
    {
    	//Creates the directory
        File directory = new File(cFilePath);

        // Checks if directory already exist
        if (!directory.exists()) { 
            try {
            	// Creates a new directory if not available
                if (directory.mkdirs()) 
                {
                    System.out.println(cFilePath + " directory created");
                } else {
                    System.out.println(cFilePath + " directory not created");
                }
            } catch (SecurityException e) {
                e.printStackTrace();
                System.out.println("Program does not have enough privilege to create folder.");
            }

        }

        //Creates the file if there is no file exists
        File file = new File(cFilePath + filename);
        // Checks if file already exist
        if (!file.exists()) { 
            try {
            	// Creates file if not available
                if (file.createNewFile()) 
                {
                    System.out.println(filename + " database was created");
                } else {
                    System.out.println(filename + " database already exists");
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Initialises the DataManager object which creates a "database" folder in the root directory of the application.
     * Two database files will be created in the "database" folder with names given by the inputed filepath1, and filepath2.
     * @param filepath1 First database filename.
     * @param filepath2 Second database filename.
     */
    public DataManager(final String filepath1, final String filepath2)//Constructor for two files
    {
    	//Creates the directory
        File directory = new File(cFilePath);

        // Checks if directory already exist
        if (!directory.exists()) { 
            try {
            	// Creates directory if not available
                if (directory.mkdirs()) 
                {
                    System.out.println(cFilePath + " directory created");
                } else {
                    System.out.println(cFilePath + " directory not created");
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Program does not have enough privilege to create folder.");
            }
        }

        //Creates the file if none exists
        File file1 = new File(cFilePath + filepath1);
        // Checks if file already exist
        if (!file1.exists()) { 
            try {
                // Creates file if not available
                if (file1.createNewFile()) 
                {
                    System.out.println(filepath1 + " database was created");
                } else {
                    System.out.println(filepath1 + " database already exists");
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        File file2 = new File(cFilePath + filepath2);
        // Checks if file already exist
        if (!file2.exists()) { 
            try {
            	// Creates file if not available
                if (file2.createNewFile()) 
                {
                    System.out.println(filepath2 + " database was created");
                } else {
                    System.out.println(filepath2 + " database already exists");
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * This method allows the reading of the content in the file of the inputed filename.
     * @param filename The database file to open and load serializable objects from.
     * @return object Object should be downcasted into the appropriate dervied object and then stored into an ArrayList
     */
    protected Object read(String filename) 
    {
        File file = new File(cFilePath + filename); 
        // Checks if file exist
        if ((!file.exists())) {
            System.out.println("File not exist in: " + cFilePath + filename);
            // returns null if file does not exist
            return null; 
        }

        Object pDetails = null;
        FileInputStream fis;
        ObjectInputStream in;
        try {
            fis = new FileInputStream(cFilePath + filename);
            in = new ObjectInputStream(fis);
            pDetails = in.readObject();
            in.close();
            // returns list if successful read
            return pDetails; 
        } catch (Exception ex) {
        }
        // return null by default if error occurred
        return null; 
    }

    /**
     * This method writes a list of objects into the database file of the inputed filename.
     * @param list List of objects that will be written to the database file.
     * @param filename Name of the database file that will be written into.
     * @return true if there is a successful writing of the list to the database file, else false.
     */
    protected boolean write(List list, String filename) 
    {
        File directory = new File(cFilePath);
        File file = new File(cFilePath + filename);
        // Checks if directory already exist
        if (!directory.exists()) { 
        	// Creates a new directory if not available
            if (!directory.mkdir()) 
            	// Returns false if fails
                return false; 
        }
        // Checks if file already exist
        if (!file.exists()) { 
            try {
            	// Creates file if not available
                file.createNewFile(); 
            } catch (IOException e) {
            	// Returns false if fails
                return false; 
            }
        }
        
        // Checks if list is null
        if (list != null) { 

            FileOutputStream fos = null;
            ObjectOutputStream out = null;
            try {
                fos = new FileOutputStream(cFilePath + filename);
                out = new ObjectOutputStream(fos);
                // Write entire list into file
                out.writeObject(list); 
                out.close();
                // Returns true if write operation succeeds
                return true; 
            } catch (IOException ex) {
            	// Returns false if error occurred
                return false; 
            }
        }
        // Returns false by default
        return false; 
    }
}
