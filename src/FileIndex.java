import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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
  private static final int readBufferLength = 8192; // read buffer 8k

  private static void usage() {
    System.out.printf("Usage:%njava %s {generate X | SSSSS}%n", FileIndex.class.getName());
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
        SearchForString(args[0]);
        break;
      default:
        usage();
    }
  }

  private static void GenerateFile(double fileSizeBytes) throws IOException {
    final IndexChars[]   index;
    final int            charsInIndex = getCharsInIndex(fileSizeBytes);
    final double         lines = getLines(fileSizeBytes, charsInIndex);
    final BufferedWriter file = Files.newBufferedWriter(Paths.get(fileName), StandardOpenOption.CREATE_NEW);

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

    System.out.printf(
          "fileSizeBytes  : %.0f%n"
        + "Lines          : %.0f%n"
        + "Chars In Index : %d%n"
        + "Index Example  : %s%n",
        fileSizeBytes, lines, charsInIndex, getArrayAsString(index));

    StringBuilder outStringBuffer = new StringBuilder();
    String indexValue = null;
    for (double j = 0; j < lines; j++) {
      int i = index.length - 1;
      indexValue = getArrayAsString(index);

      outStringBuffer.setLength(0);
      outStringBuffer.append(indexValue)
          .append(" ")
          .append(getRandomString("0123456789" + IndexChars.enumAsString().toLowerCase(), randomStringLength))
          .append('\n');

      // System.out.println(outStringBuffer.toString()); // used for debug
      file.write(outStringBuffer.toString());

      index[i] = index[i].next();
      while (index[i] == null) {
        index[i] = IndexChars.A;
        i--;
        index[i] = index[i].next();
      }
    }
    file.close();
    System.out.println("Last Index Value: " + indexValue);
  }

  private static void SearchForString(String searchIndex) throws IOException {
    long startTime, endTime;
    String result;

    startTime = System.currentTimeMillis();
    result = SearchSeq(searchIndex);
    endTime = System.currentTimeMillis();
    System.out.printf("==== Sequential search ====%nSearch time (ms) : %d%nFound string : %s%n",
        endTime - startTime, result == null ? "Not Found" : result);

    startTime = System.currentTimeMillis();
    result = SearchBin(searchIndex);
    endTime = System.currentTimeMillis();
    System.out.printf("==== Binary search ====%nSearch time (ms) : %d%nFound string : %s%n",
        endTime - startTime, result == null ? "Not Found" : result);
  }

  private static String SearchSeq(String searchIndex) throws IOException {
    final BufferedReader file = Files.newBufferedReader(Paths.get(fileName));
    String result;

    do {
      result = file.readLine();
    } while (result != null && ! result.startsWith(searchIndex));
    // while ( (result = file.readLine()) != null && ! result.startsWith(searchIndex) );
    // file.lines().filter(s -> s.startsWith(searchIndex)).
    file.close();
    return result;
  }

  private static String SearchBin(String searchIndex) throws IOException {
    final RandomAccessFile file       = new RandomAccessFile(fileName, "r");
    final long             fileLength = file.length();
    final int              lineLength = searchIndex.length() + 1 + randomStringLength + 1;

    byte[] buffer = new byte[readBufferLength];
    long   currentPos = (fileLength / 2) - (readBufferLength / 2);
    long   currentBlockLength;
    int    startOfString;
    String resultBlock;
    String result;

    currentBlockLength = fileLength / 2;
    currentPos = currentPos - (currentPos % lineLength); // to simplify search
    do {
      file.seek(currentPos - (readBufferLength / 2));
      file.read(buffer);
      resultBlock = getBufferAsString(buffer);.toString().substring(
          buffer.toString().indexOf('\n') + 1,
          buffer.toString().lastIndexOf('\n') - 1);

      if (searchIndex.compareTo(resultBlock) < 0) {
        currentBlockLength = currentBlockLength / 2;
        currentPos = currentPos - currentBlockLength;
      } else if (searchIndex.compareTo(resultBlock.substring(resultBlock.lastIndexOf('\n') + 1)) > 0) {
        currentBlockLength = currentBlockLength / 2;
        currentPos = currentPos + currentBlockLength;
      } else {
        // we found block
        break;
      }
    } while (currentBlockLength > readBufferLength);

    result = resultBlock.buffer.toString().indexOf(searchIndex);

    result = String.format("%s %s", Long.toString(currentPos), Long.toString(fileLength));
    return result;
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

    for (Object c : index) {
      string.append(c);
    }
    return string.toString();
  }
}
