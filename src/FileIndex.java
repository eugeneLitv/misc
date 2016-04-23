import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Package: PACKAGE_NAME
 * Project: FileIndex
 */

enum IndexChars {
  A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z;
  private static final IndexChars[] arrayOfIndexChars = values();

  public static int length() {
    return arrayOfIndexChars.length;
  }
  public static String enumAsString() {
    char [] str = new char[arrayOfIndexChars.length];
    for (int i = 0; i < str.length; i++) {
      str[i] = arrayOfIndexChars[i].toString().charAt(0);
    }
    return new String(str);
  }

  public IndexChars next() {
    if (this.ordinal() + 1 >= arrayOfIndexChars.length) {
      return null;
    }
    return arrayOfIndexChars[this.ordinal() + 1];
  }
}

public class FileIndex {
  private static final String fileName = "big_ordered_file.txt";
  private static final String searchAtBegin = "";
  private static final String searchAtEnd = "";
  private static final double bytesInGb = 1024f * 1024f * 1024f;
  private static final int randomStringLength = 26;

  private static void usage() {
    System.out.printf("Usage:\njava %s {generate X | SSSSS}\n", FileIndex.class.getName());
    System.out.println("\tX - size of generated file in Gigabytes");
    System.out.println("\tSSSSS - search string in the generated file");
  }
  public static void main(String args[]) throws IOException {
    final double fileSizeBytes;
    switch (args.length) {
      case 2:
        if ("generate".equals(args[0])) {
          try {
            fileSizeBytes = bytesInGb * Integer.parseInt(args[1]);
            GenerateFile(fileSizeBytes);
          } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
            usage();
          }
        } else {
          System.out.println("Wrong Parameters");
          usage();
        }
        break;
      case 1:
        SearchForString();
        break;
      default:
        usage();
    }
  }

  private static void GenerateFile(double fileSizeBytes) throws IOException {
    final Path         filePath = Paths.get(fileName);
    final IndexChars[] index;
    final int          charsInIndex = getCharsInIndex(fileSizeBytes);
    final double       lines = getLines(fileSizeBytes, charsInIndex);

    int bufSizeInLines;
    // simple check
    if ((Math.log(lines) / Math.log(IndexChars.length())) > charsInIndex) {
      System.out.println("Incorrect Number of lines - function getCharsInIndex is wrong.");
      System.exit(1);
    }
    index = new IndexChars[charsInIndex];
    // Fill array
    for (int i = 0; i < index.length; i++) {
      index[i] = IndexChars.A;
    }

    bufSizeInLines = 10 * 1048576 / (charsInIndex + 1 + IndexChars.length() + 1);

    System.out.printf(
          "fileSizeBytes  : %.0f\n"
        + "Lines          : %.0f\n"
        + "Chars In Index : %d\n"
        + "Index Example  : %s\n"
        + "Buffer Lines   : %d\n",
        fileSizeBytes, lines, charsInIndex, getArrayAsString(index), bufSizeInLines);

    List<String> outLines = new ArrayList<>();
    Files.write(filePath, outLines, StandardOpenOption.CREATE_NEW);
    StringBuilder outStringBuffer = new StringBuilder();

    for (double j = 0; j < lines; j++) {
      int i = index.length - 1;

      outStringBuffer.setLength(0);
      outStringBuffer.append(getArrayAsString(index))
          .append(" ")
          .append(getRandomString("0123456789" + IndexChars.enumAsString().toLowerCase(), randomStringLength));

      // System.out.println(outStringBuffer.toString()); // used for debug
      outLines.add(outStringBuffer.toString());
      if (outLines.size() >= bufSizeInLines) {
        Files.write(filePath, outLines, StandardOpenOption.APPEND);
        outLines.clear();
      }

      index[i] = index[i].next();
      while (index[i] == null) {
        index[i] = IndexChars.A;
        i--;
        index[i] = index[i].next();
      }
    }
    Files.write(filePath, outLines); //. write(new Byte(outStringBuffer.toString()));
    System.out.println("Last Index Value: " + getArrayAsString(index));
  }

  private static void SearchForString() {
    System.out.println("Not Implemented Yet.");
  }

  private static int getCharsInIndex(double fileSizeBytes) {
    /**
     * ABCDEFGHIJ abcdefghijklmnopqastuvwxyz\n
     * ABCDEFGHIJ - [A-Z] ln(lines)/ln(26) bytes
     * " abcdefghijklmnopqastuvwxyz\n" - 28 bytes
     *
     *  (ln(lines)/ln(26) + 28) * lines = fileSize in bytes
     *  Where ln(lines)/ln(26) - charsInIndex (number of positions in index)
     */
    double lines;
    int   charsInIndex = 0;
    do {
      charsInIndex++;
      lines = fileSizeBytes / (charsInIndex + 1 + randomStringLength + 1); // 1 space, 1 eol
    } while ( (Math.log(lines) / Math.log(IndexChars.length())) > charsInIndex );
    return charsInIndex;
  }

  private static double getLines(double fileSizeBytes, int charsInIndex) {
    int lineLength = charsInIndex + 1 + randomStringLength + 1; // index + space + string + eol
    return fileSizeBytes / lineLength;
  }

  private static String getRandomString(String allowedChars, int outStringLength) {
    Random rnd = new Random();
    char [] charArray = new char[outStringLength];

    for (int i = 0; i < charArray.length; i++) {
      charArray[i] = allowedChars.charAt(rnd.nextInt(allowedChars.length()));
    }
    return new String(charArray);
  }

  private static String getArrayAsString(Object[] index) {
    StringBuilder string = new StringBuilder();

    for (int i = 0; i < index.length; i++) {
      string.append(index[i]);
    }
    return string.toString();
  }
}
