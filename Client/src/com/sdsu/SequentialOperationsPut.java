package com.sdsu;

/**
 * @author : Nishchita Ajit
 * @since : 10/7/20, Wed
 **/

import java.io.IOException;
import java.rmi.Remote;

public interface SequentialOperationsPut extends Remote
{
  boolean openfiletowrite(String fileName) throws IOException;
  boolean nextwrite(byte[] block, int length) throws IOException;
  boolean closefile() throws IOException;
}