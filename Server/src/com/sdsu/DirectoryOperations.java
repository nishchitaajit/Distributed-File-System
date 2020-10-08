package com.sdsu;

/**
 * @author : Nishchita Ajit
 * @since : 10/7/20, Wed
 **/

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DirectoryOperations extends Remote
{
    String getdir() throws RemoteException;
    boolean cd(String dirName) throws RemoteException;
    int filecount() throws RemoteException;
    long filecount(String filterStr) throws RemoteException;
    boolean openlist(String fileName) throws RemoteException;
    DirEntry nextlist() throws RemoteException;
    DirEntry nextlist(String size, char symbol) throws RemoteException;
    int lengthOfList() throws RemoteException;
    boolean closelist() throws RemoteException;
}
