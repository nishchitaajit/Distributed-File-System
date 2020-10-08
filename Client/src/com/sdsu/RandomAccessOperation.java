package com.sdsu;

/**
 * @author : Nishchita Ajit
 * @since : 10/7/20, Wed
 **/

import java.io.IOException;
import java.rmi.Remote;

public interface RandomAccessOperation extends Remote {
  boolean openfiletoread(String fileName) throws IOException;
  BytesRead randomread(int startIndex, int numOfBytes) throws IOException;
  boolean closefile() throws IOException;
}
