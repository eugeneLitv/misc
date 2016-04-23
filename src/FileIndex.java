import java.io.File;
import java.util.Arrays;

/**
 * Package: PACKAGE_NAME
 * Project: FileIndex
 */

enum IndexChars {
  A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z;
  private static final IndexChars[] arrayOfIndexChars = values();

  public IndexChars next() {
    if (this.ordinal() + 1 >= arrayOfIndexChars.length) {
      return null;
    }
    return arrayOfIndexChars[this.ordinal() + 1];
  }
  public int length() {
    return arrayOfIndexChars.length;
  }
}

public class FileIndex {
  //private static final String[] IndexChars = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" }
  private static final String fileName = "big_ordered_file.txt";
  private static final String searchAtBegin = "";
  private static final String searchAtEnd = "";
  private static final double bytesInGb = 1024f * 1024f * 1024f;

  private static void usage() {
    System.out.printf("Usage:\njava %s {generate X | SSSSS}\n", FileIndex.class.getName());
    System.out.println("\tX - size of generated file in Gigabytes");
    System.out.println("\tSSSSS - search string in the generated file");
  }
  public static void main(String args[]) {
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

  private static void GenerateFile(double fileSizeBytes) {
    final File         file = new File(fileName);
    final IndexChars[] index;
    final int          charsInIndex = getCharsInIndex(fileSizeBytes);
    final double       lines = getLines(fileSizeBytes, charsInIndex);

    // simple check
    if ((Math.log(lines) / Math.log(26)) > charsInIndex) {
      System.out.println("Incorrect Number of lines - function getCharsInIndex is wrong.");
      System.exit(1);
    }
    index = new IndexChars[charsInIndex];
    // Fill array
    for (int i = 0; i < index.length; i++) {
      index[i] = IndexChars.A;
    }

    System.out.printf("fileSizeBytes  : %.0f\nLines          : %.0f\nChars In Index : %d\nIndex Example  : %s\n",
        fileSizeBytes, lines, charsInIndex, Arrays.toString(index));
    for (double j = 0; j < lines; j++) {
      int i = index.length - 1;

      index[i] = index[i].next();
      while (index[i] == null) {
        index[i] = IndexChars.A;
        i--;
        index[i] = index[i].next();
      }
    }
    System.out.println("Last Index Value: " + Arrays.toString(index));
  }

  private static void SearchForString() {
    System.out.println("Not Implemented Yet.");
  }

  private static int getCharsInIndex(double fileSize) {
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
      lines = fileSize / (26 + 1 + 1 + charsInIndex); // 26 letters, 1 space 1 eol, at least 1 index
    } while ( (Math.log(lines) / Math.log(26)) > charsInIndex );
    return charsInIndex;
  }

  private static double getLines(double fileSizeBytes, int charsInIndex) {
    int lineLength = charsInIndex + 1 + 26 + 1; // index + space + 26_bytes_string + eol
    return fileSizeBytes / lineLength;
  }
}
