package Model;


public class PatternFormatException extends Exception {
    private String error;

    public PatternFormatException(String error) {
        this.error = "Error reading board from .rle-file" + error;
        System.err.println(error);
    }

    @Override
    public String toString() {
        return error;
    }
}
