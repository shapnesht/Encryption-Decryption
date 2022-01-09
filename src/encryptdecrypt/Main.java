package encryptdecrypt;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    static String currentAlgo = "shift";
    public static void main(String[] args) throws IOException {
        String strAns = "";
        boolean containsIn = contains(args, "-in");
        boolean containsOut = contains(args, "-out");
        String str = "";

        if (containsIn) {
            int fileLocation = indexOfMenu(args,"-in");
            File file = new File(args[fileLocation + 1]);
            Scanner scanner = new Scanner(file);
            str = scanner.nextLine();
            scanner.close();
        }
        if (contains(args, "-alg")) {
            currentAlgo = args[indexOfMenu(args, "-alg") + 1];
        }

        if (contains(args,"-mode") && contains(args,"-key") && contains(args,"-data")) {
            int modeIndex = indexOfMenu(args, "-mode");
            int keyIndex = indexOfMenu(args, "-key");
            int dataIndex = indexOfMenu(args, "-data");
            if (Objects.equals(args[modeIndex + 1], "enc")) {
                strAns = encryptString(args[dataIndex+1], Integer.parseInt(args[keyIndex+1]));
            } else {
                strAns = decryptString(args[dataIndex+1], Integer.parseInt(args[keyIndex+1]));
            }
        } else if (contains(args,"-key") && contains(args,"-data")) {
            int keyIndex = indexOfMenu(args, "-key");
            int dataIndex = indexOfMenu(args, "-data");
            strAns = encryptString(args[dataIndex+1], Integer.parseInt(args[keyIndex+1]));
        } else if (contains(args,"-mode") && contains(args,"-data")) {
            int modeIndex = indexOfMenu(args, "-mode");
            int dataIndex = indexOfMenu(args, "-data");
            if (Objects.equals(args[modeIndex + 1], "enc")) {
                strAns = encryptString(args[dataIndex+1], 0);
            } else {
                strAns = decryptString(args[dataIndex+1], 0);
            }
        } else {
            if (contains(args,"-mode") && contains(args,"-key")) {
                int modeIndex = indexOfMenu(args, "-mode");
                int keyIndex = indexOfMenu(args, "-key");
                if (Objects.equals(args[modeIndex + 1], "enc")) {
                    strAns = encryptString(str, Integer.parseInt(args[keyIndex+1]));
                } else {
                    strAns = decryptString(str, Integer.parseInt(args[keyIndex+1]));
                }
            } else if (contains(args,"-key")) {
                int keyIndex = indexOfMenu(args, "-key");
                strAns = encryptString(str, Integer.parseInt(args[keyIndex+1]));
            } else if (contains(args,"-mode")) {
                int modeIndex = indexOfMenu(args, "-mode");
                if (Objects.equals(args[modeIndex + 1], "enc")) {
                    strAns = encryptString(str, 0);
                } else {
                    strAns = decryptString(str, 0);
                }
            }
        }

        if (containsOut) {
            int fileLocation = indexOfMenu(args,"-out");
            File file = new File(args[fileLocation+1]);
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(strAns);
            fileWriter.close();
        } else {
            System.out.println(strAns);
        }
    }

    private static int indexOfMenu(String[] args, String s) {
        for (int i = 0; i < args.length; i++) {
            if (s.equals(args[i])) {
                return i;
            }
        }
        return -1;
    }

    private static boolean contains(String[] arr, String str) {
        for (String s : arr) {
            if (s.equals(str)) {
                return true;
            }
        }
        return false;
    }

    private static String encryptString(String str, int n) {
        StringBuilder strAns = new StringBuilder();
        if (currentAlgo.equals("shift")) {
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) >= 'a' && str.charAt(i) <= 'z') {
                    strAns.append((char) ((str.charAt(i) - 'a' + n) % 26 + 'a'));
                } else if (str.charAt(i) >= 'A' && str.charAt(i) <= 'Z') {
                    strAns.append((char) ((str.charAt(i) - 'A' + n) % 26 + 'A'));
                } else {
                    strAns.append(str.charAt(i));
                }
            }
            return strAns.toString();
        }
        for (int i = 0; i < str.length(); i++) {
            strAns.append((char) ((str.charAt(i) - 'a' + n) + 'a'));
        }
        return strAns.toString();
    }

    private static String decryptString(String str, int n) {
        StringBuilder strAns = new StringBuilder();
        if (currentAlgo.equals("shift")) {
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) >= 'a' && str.charAt(i) <= 'z') {
                    strAns.append((char) ((str.charAt(i) - 'z' - n) % 26 + 'z'));
                } else if (str.charAt(i) >= 'A' && str.charAt(i) <= 'Z') {
                    strAns.append((char) ((str.charAt(i) - 'Z' - n) % 26 + 'Z'));
                } else {
                    strAns.append(str.charAt(i));
                }
            }
            return strAns.toString();
        }
        for (int i = 0; i < str.length(); i++) {
            strAns.append((char) ((str.charAt(i) - 'a' - n) + 'a'));
        }
        return strAns.toString();
    }
}