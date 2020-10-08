package com.sdsu;

/**
 * @author : Nishchita Ajit
 * @since : 10/7/20, Wed
 **/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessOperationImpl implements RandomAccessOperation {

  RandomAccessFile randFile;

  /**
   * Opens a file
   * @param fileName file to be opened in read mode
   * @return reads true if file exists
   * @throws FileNotFoundException
   */
  @Override
  public boolean openfiletoread(String fileName) throws FileNotFoundException {
    File file = new File(fileName);
    if (file.exists()) {
      this.randFile = new RandomAccessFile(file.getAbsoluteFile(),"r");
      return true;
    }
    else return false;
  }

  /**
   * BytesRead is populated with number of bytes read in count and contents of file in byte array
   * @param startIndex sets file pointer to this value in the opened file
   * @param numOfBytes bytes to be read in the opened file
   * @return number of bytes read and the contents read
   */
  @Override
  public BytesRead randomread(int startIndex, int numOfBytes) {
    BytesRead byteRead = new BytesRead();
    try {
      if (this.randFile == null) {
        byteRead.setBytesArray(null);
        byteRead.setCount(0);
        return byteRead;
      } else {
        byte[] bytesArray = new byte[512];
        randFile.seek(startIndex);
        byteRead.setCount(this.randFile.read(bytesArray, 0, numOfBytes));
        byteRead.setBytesArray(bytesArray);
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    return byteRead;
  }

  /**
   * Closes a file.
   * @return true affter closing the opened file
   * @throws IOException
   */
  @Override
  public boolean closefile() throws IOException {
    this.randFile.close();
    return true;
  }
}
