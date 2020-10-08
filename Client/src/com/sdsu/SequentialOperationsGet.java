package com.sdsu;

/**
 * @author : Nishchita Ajit
 * @since : 10/7/20, Wed
 **/

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SequentialOperationsGet extends Remote {
  boolean openfiletoread(String fileName) throws IOException;
  BytesRead nextread() throws RemoteException;
  boolean closefile() throws IOException;
}
