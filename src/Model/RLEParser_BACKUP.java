package Model;

/**
 * Created by patrikkvarmehansen on 03/04/17.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

    /**
     * A class that parses a .rle file and returns it as a 2D byte array through the getStaticBoard method.
     * It can also return the X and Y value if needed later on.
     */

    public class RLEParser_BACKUP {

        private int x = 5;
        private int y = 1000;
        private String boardInBitString = "";
        char cellType;
        int runCount;


        /*private void findRules(BufferedReader br) throws IOException {
            Boolean xNotFound = true;
            while (xNotFound) {
                String line = br.readLine();
                if (line.startsWith("x")) {
                    String test = " ? (\\d+)/, / ? (\\d+)";
                    Matcher matcher = Pattern.compile(test).matcher(line);
                    matcher.find();
                    System.out.println(matcher.group(1));
                }
            }
        }*/
        private void findRules(String line) {
            String test = "rule = b?(\\d+), s?(\\d+)";
            Matcher matcher = Pattern.compile(test).matcher(line);
            matcher.find();
            System.out.println(matcher.group(0));
        }

        private void findXY(String line) throws IOException {
            String test = "x ?= ?(\\d+),? y ?= ?(\\d+)";
            Matcher matcher = Pattern.compile(test).matcher(line);
            matcher.find();
            this.x = Integer.parseInt(matcher.group(1));
            this.y = Integer.parseInt(matcher.group(2));
        }

        private String findXYandRulesLine(BufferedReader br) throws IOException {
            Boolean xNotFound = true;
            while (xNotFound) {
                String line = br.readLine();
                if (line.startsWith("x")) {
                    return line;
                }
            }
            return null;
        }

        //Updates the BitString with the number of 1s or 0s that is needed according to the latest reading from the RLE-string
        private String updateBitString(int runCount, char cellType, String boardInBitString) {
            String partOfBitString = "";
            if (cellType == 'b') {
                partOfBitString += addToBitString(runCount, '0');
            } else if (cellType == 'o') {
                partOfBitString += addToBitString(runCount, '1');
            } else if (cellType == '$') {
                for (int i = 0; i < runCount; i++) {
                    if (i == 0) {
                        if (howFarAlongTheRow(boardInBitString) != 0) {
                            partOfBitString += addToBitString((x - howFarAlongTheRow(boardInBitString)), '0');
                        }
                    } else {
                        partOfBitString += addToBitString(x, '0');
                    }
                }
            }
            return partOfBitString;
        }

        //Runs through the rlePattern and updates the local variable boardInBitString using the updateBitString method.
        private void setBitStringFromRlePattern(String rlePattern) {
            for (int i = 0; i < rlePattern.length(); i++) {

                if (!checkForRleEnding(rlePattern, i)) {

                    if (getRunCount(rlePattern, i) != 0) {
                        runCount = getRunCount(rlePattern, i);
                        i += iUpdate(runCount);
                    } else if (getCellType(rlePattern, i) != 'a') {
                        cellType = getCellType(rlePattern, i);
                        if (prevCharIsNotInt(rlePattern, i)) {
                            runCount = 1;
                        }
                        this.boardInBitString += updateBitString(runCount, cellType, boardInBitString);
                    }

                }

            }
        }

        /**
         * Returns the X value of the StaticBoard from the RLE-file loaded into the given RLEParser-object
         */

        public int getX() {
            return x;
        }

        /**
         * Returns the Y value of the StaticBoard from the RLE-file loaded into the given RLEParser-object
         */
        public int getY() {
            return y;
        }

        /**
         * Main function of RLEParser. Returns a 2D byte array after loading in and parsing through a .rle file.
         * @return a byte array that is a representation of the rle pattern from the .rle file.
         * @throws IOException
         */
        public byte[][] importFile() throws IOException {
            BufferedReader br = ReadFile.readFileFromDisk();
            if (br != null) {
                String xYRulesLine = findXYandRulesLine(br);
                findXY(xYRulesLine);
                //findRules(xYRulesLine);
                String rlePattern = getRlePattern(br);
                setBitStringFromRlePattern(rlePattern);
                addingLastCharacters();
                br.close();
                return stringToByteArray(boardInBitString);
            }
            return null;
        }

        private String addingLastCharacters() {
            return boardInBitString += addToBitString(x - howFarAlongTheRow(boardInBitString), '0');
        }

        private String getRlePattern(BufferedReader br) throws IOException {
            String line;
            String RlePattern = null;
            Boolean intFound = false;
            while ((line = br.readLine()) != null) {
                if (!startNotFound(line)) {
                    intFound = true;
                }
                if (intFound) {
                    RlePattern += line;
                }
            }
            return RlePattern;
        }

        private boolean startNotFound(String line) {
            if (line.substring(0, 1).matches("[0-9ob$]")) {
                return false;
            } else {
                return true;
            }
        }

        private int getRunCount(String line, int i) {
            String runCount = "";
            if (line.substring(i, i + 1).matches("\\d")) {
                runCount += (char) line.charAt(i);
                if (line.substring(i + 1, i + 2).matches("\\d")) {
                    runCount += (char) line.charAt(i + 1);
                    if (line.substring(i + 2, i + 3).matches("\\d")) {
                        runCount += (char) line.charAt(i + 2);
                    }
                }
            } else {
                return 0;
            }
            int runCountInt = Integer.parseInt(runCount);
            return runCountInt;
        }

        private char getCellType(String line, int i) {
            char cellType = 'a';
            if (line.substring(i, i + 1).matches("[bo$]")) {
                cellType = line.charAt(i);
            }
            return cellType;
        }

        private Boolean checkForRleEnding(String line, int i) {
            if (line.substring(i, i + 1) == "!") {
                return true;
            } else {
                return false;
            }
        }

        private int iUpdate(int runCount) {
            int numberOfDigits = String.valueOf(runCount).length();
            if (numberOfDigits == 2) {
                return 1;
            } else if (numberOfDigits == 3) {
                return 2;
            } else {
                return 0;
            }
        }

        private String addToBitString(int runCount, char cellType) {
            String updateBitString = "";
            for (int i = 0; i < runCount; i++) {
                updateBitString += cellType;
            }
            return updateBitString;
        }

        private int howFarAlongTheRow(String boardInBitString) {
            int howFarAlong = boardInBitString.length() % x;
            return howFarAlong;
        }

        private boolean prevCharIsNotInt(String rlePattern, int i) {
            char cha = rlePattern.charAt(i - 1);
            if (Character.isLetter(cha)) {
                return true;
            } else {
                return false;
            }
        }

        private byte[][] stringToByteArray(String bitString) {
            byte[][] byteArray = new byte[y][x];
            int count = 0;
            for (int i = 0; i < y; i++) {
                for (int j = 0; j < x; j++) {
                    char charBit = bitString.charAt(count);
                    byteArray[i][j] = (byte) Character.getNumericValue(charBit);
                    count++;
                }
            }
            return byteArray;
        }

        /**
         * Test function that prints the array that is loaded through the RLEParser-object.
         *
         * @param array - the array that the parser extracts from the .rle file.
         */
        public void printArray(byte[][] array) {
            for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < array[i].length; j++) {
                    System.out.print(array[i][j]);
                }
                System.out.println("");
            }
        }
    }
