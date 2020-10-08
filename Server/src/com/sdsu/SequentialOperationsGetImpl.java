package com.sdsu;

/**
 * @author : Nishchita Ajit
 * @since : 10/7/20, Wed
 **/

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SequentialOperationsGetImpl implements SequentialOperationsGet {

  FileInputStream inputStream;

  /**
   * Opens a file
   * @param fileName file to be opened to read
   * @return true if file exists
   * @throws FileNotFoundException
   */
  @Override
  public boolean openfiletoread(String fileName) throws FileNotFoundException {
    File file = new File(fileName);
    if (file.exists()) {
      this.inputStream = new FileInputStream(fileName);
      return true;
    }
    else return false;
  }

  /**
   * BytesRead is populated with number of bytes read in count and contents of file in byte array
   * @return number of bytes read from the opened file and the contents of file
   */
  @Override
  public BytesRead nextread() {
    BytesRead bytesRead = new BytesRead();
    try {
      if (this.inputStream == null) {
        bytesRead.setBytesArray(null);
        bytesRead.setCount(0);
        return bytesRead;
      }
      else {
        byte[] byteArray = new byte[512];
        bytesRead.setCount(this.inputStream.read(byteArray));
        bytesRead.setBytesArray(byteArray);
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return bytesRead;
  }

  /**
   * Closes a file
   * @return true after closing the opened file
   * @throws IOException
   */
  @Override
  public boolean closefile() throws IOException {
    this.inputStream.close();
    return true;
  }
}
