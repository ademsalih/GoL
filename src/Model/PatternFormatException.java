package Model;

/**
 * Exception for handling wrong formatted RLE files
 */

public class PatternFormatException extends Exception {
    private String error;


    /**
     * Error for use when the RLE pattern is wrong
     * @param error
     */
    public PatternFormatException(String error) {
        this.error = "Error reading board from .rle-file" + error;
        System.err.println(error);
    }

    @Override
    public String toString() {
        return error;
    }
}
