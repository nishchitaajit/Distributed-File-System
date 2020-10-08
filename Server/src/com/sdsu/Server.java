package com.sdsu;

/**
 * @author : Nishchita Ajit
 * @since : 10/7/20, Wed
 **/

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    public Server() {
    }

  public static void main(String[] args) {
    try {
      DirectoryOperationsImpl directoryOps = new DirectoryOperationsImpl();
      DirectoryOperations dirStub =
          (DirectoryOperations) UnicastRemoteObject.exportObject(directoryOps, 0);

      SequentialOperstionsPutImpl sequentialOpsPut = new SequentialOperstionsPutImpl();
      SequentialOperationsPut seqStubPut =
          (SequentialOperationsPut) UnicastRemoteObject.exportObject(sequentialOpsPut, 0);

      SequentialOperationsGetImpl sequentialOpsGet = new SequentialOperationsGetImpl();
      SequentialOperationsGet seqStubGet =
          (SequentialOperationsGet) UnicastRemoteObject.exportObject(sequentialOpsGet, 0);

      RandomAccessOperationImpl randomAccessOps = new RandomAccessOperationImpl();
      RandomAccessOperation randomStub =
          (RandomAccessOperation) UnicastRemoteObject.exportObject(randomAccessOps, 0);

      Registry registry = LocateRegistry.createRegistry(1099);
      // Binding the remote object (stub) in the registry
      registry.bind("Directory", dirStub);
      registry.bind("SequentialGet", seqStubGet);
      registry.bind("SequentialPut", seqStubPut);
      registry.bind("RandomAccess", randomStub);
      System.out.println("Server is ready ....");

    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}