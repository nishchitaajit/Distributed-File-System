package com.sdsu;

/**
 * @author : Nishchita Ajit
 * @since : 10/7/20, Wed
 **/

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SequentialOperstionsPutImpl implements SequentialOperationsPut {
  FileOutputStream outputStream;

  /**
   * Opens a file to read
   *
   * @param fileName file to open to be written
   * @return true if file doesn't exist
   * @throws IOException
   */
  @Override
  public boolean openfiletowrite(String fileName) throws IOException {
    File file = new File(fileName);
    if (file.exists()) return false;
    else {
      this.outputStream = new FileOutputStream(fileName);
      return true;
    }
  }

  /**
   * Contents to be written into the opened file is taken from a byte array which is populated on the
   * client-side. Client opens the file to be read and populates the byte array.
   *
   * @param block  byte array from client
   * @param length size of the byte array
   * @return
   */
  @Override
  public boolean nextwrite(byte[] block, int length) {
    try {
      if (this.outputStream == null) return false;
      else {
        this.outputStream.write(block, 0, length);
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return true;
  }

  /**
   * Closes a file
   *
   * @return true after closing the opened file
   * @throws IOException
   */
  @Override
  public boolean closefile() throws IOException {
    if (this.outputStream.getChannel().isOpen()) {
      this.outputStream.close();
    }
    return true;
  }
}
