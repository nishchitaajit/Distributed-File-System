package com.sdsu;

/**
 * @author : Nishchita Ajit
 * @since : 10/7/20, Wed
 **/

import java.io.Serializable;
import java.util.Arrays;

public class BytesRead implements Serializable {
  int count;
  byte[] bytesArray;

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public byte[] getBytesArray() {
    return bytesArray;
  }

  public void setBytesArray(byte[] bytesArray) {
    this.bytesArray = bytesArray;
  }

  @Override
  public String toString() {
    return "BytesRead{" +
        "count=" + count +
        ", bytesArray=" + Arrays.toString(bytesArray) +
        '}';
  }
}