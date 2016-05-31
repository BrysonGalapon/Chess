package text_file_io;

import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Represents a text file reader/parser/writer
 * @author Bryson
 */
public class FileIO {
    
    private final FileReader fileReader;
    
    /**
     * Create a new FileIO object that reads from/writes to the text file called filename
     * @param filename name of file to read from/write to
     *          - requires that filename is in the form "'filename'.txt"
     * @throws FileNotFoundException if we are unable to recognize the filename
     */
    public FileIO(String filename) throws FileNotFoundException{
        try {
            fileReader = new FileReader(filename);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("I am unable to recognize this filename");
        }
    }
    
    /**
     * Create an empty text file with name filename in 'games' folder
     * @param filename name to call the new file
     */
    public void createFile(String filename) {
        //TODO: Unimplemented
    }
    
}
