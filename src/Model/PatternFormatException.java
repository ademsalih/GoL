package Model;

/**
 * Created by patrikkvarmehansen on 03/04/17.
 */
public class PatternFormatException extends Exception {
    private String error;

    PatternFormatException(String error) {
        this.error = "Error reading board from .rle-file" + error;
        System.err.println(error);
    }

    @Override
    public String toString() {
        return error;
    }
}
