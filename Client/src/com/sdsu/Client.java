package com.sdsu;

/**
 * @author : Nishchita Ajit
 * @since : 10/7/20, Wed
 **/

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {

  public Client() {}

  public static void executable(String ipAddress, String filename) {
    try {
      String serverIPAddress = ipAddress;
      Registry registry = LocateRegistry.getRegistry(serverIPAddress);
      DirectoryOperations dirStub = (DirectoryOperations) registry.lookup("Directory");
      SequentialOperationsPut seqStubPut =
              (SequentialOperationsPut) registry.lookup("SequentialPut");
      SequentialOperationsGet seqStubGet =
              (SequentialOperationsGet) registry.lookup("SequentialGet");
      RandomAccessOperation randomStub = (RandomAccessOperation) registry.lookup("RandomAccess");

      File inputfile = new File(filename);
      File inputcommand = new File (inputfile.getAbsolutePath());
      Scanner scan = new Scanner(inputcommand);

      while (scan.hasNextLine()) {
        String line = scan.nextLine();
        String[] lineArgs = line.split(" ");
        String command = lineArgs[0];
        if (command != null) {
          switch (command) {
            case "getdir":
              if (lineArgs.length > 1) System.out.println("Invalid input");
              else {
                String dirOutput = String.format("%s succeeded with %s", command, dirStub.getdir());
                System.out.println(dirOutput);
              }
              break;

            case "filecount":
              if (lineArgs.length > 2) System.out.println("Invalid input");
              else {
                if (lineArgs.length == 2) {
                  long fileFilterResult = dirStub.filecount(lineArgs[1]);
                  if (fileFilterResult != 0) {
                    String fileCountOutput =
                            String.format("%s succeeded with count of %s", command, fileFilterResult);
                    System.out.println(fileCountOutput);
                  }
                } else {
                  String countOutput =
                          String.format("%s succeeded with %s", command, dirStub.filecount());
                  System.out.println(countOutput);
                }
              }
              break;

            case "cd":
              if (lineArgs.length > 2) System.out.println("Invalid input");
              else {
                boolean cdResult = dirStub.cd(lineArgs[1]);
                if (cdResult) {
                  String cdOutput = String.format("%s succeeded", command);
                  System.out.println(cdOutput);
                } else {
                  System.out.println("cd failed");
                }
              }
              break;

            case "ls":
              String fileName = null;
              if (lineArgs.length == 2 && lineArgs[1].length() > 5) {
                String[] lsSize = lineArgs;
                char symbol = lsSize[1].charAt(4);
                String[] size = lsSize[1].split(String.valueOf(symbol));
                if (size[0].equals("size")) {
                  boolean lslResult = dirStub.openlist(fileName);
                  if (lslResult) {
                    int listLength = dirStub.lengthOfList();
                    for (int j = 0; j < listLength; j++) {
                      DirEntry dirEntry = dirStub.nextlist(size[1], symbol);
                      if (dirEntry.getName() != null) {
                        String lsFilterOutput = String.format("%s ", dirEntry.getName());
                        System.out.println(lsFilterOutput);
                      }
                    }
                  } else {
                    System.out.println("ls failed");
                  }
                  dirStub.closelist();
                  break;
                }
              }
              if (lineArgs.length > 1) {
                if (lineArgs[1].equals("-l")) {
                  if (lineArgs.length == 3) {
                    fileName = lineArgs[2];
                  }
                } else {
                  fileName = lineArgs[1];
                }
              }
              boolean lslResult = dirStub.openlist(fileName);
              if (lslResult) {
                int listLength = dirStub.lengthOfList();
                for (int i = 0; i < listLength; i++) {
                  DirEntry directoryEntry = dirStub.nextlist();
                  if (lineArgs.length > 1 && lineArgs[1].equals("-l")) {
                    String lslOutput =
                            String.format(
                                    "%s\t  %s\t  %s",
                                    directoryEntry.getSize(),
                                    directoryEntry.getLastModifiedDate(),
                                    directoryEntry.getName());
                    System.out.println(lslOutput);

                  } else {
                    String lsOutput = String.format("%s", directoryEntry.getName());
                    System.out.println(lsOutput);
                  }
                }
              } else {
                System.out.println("ls failed");
              }
              dirStub.closelist();
              break;

            case "put":
              if (lineArgs.length > 3) System.out.println("Invalid input");
              else {
                String localFileName = lineArgs[1];
                String remoteFileName = lineArgs[1];
                if (lineArgs.length > 2) {
                  if (lineArgs[2] != null) {
                    remoteFileName = lineArgs[2];
                  }
                }
                boolean putResult = seqStubPut.openfiletowrite(remoteFileName);
                if (putResult) {
                  File file = new File(localFileName);
                  // Read 512 bytes from local file and copy it to an empty byte array
                  byte[] byteArray = new byte[512];
                  int arrayLength = 0;
                  FileInputStream fileInputStream = new FileInputStream(file);
                  while (fileInputStream.read(byteArray) != -1) {
                    String str = new String(byteArray);
                    str = str.trim();
                    arrayLength += str.length();
                    seqStubPut.nextwrite(str.getBytes(), str.length());
                    byteArray = new byte[512];
                  }
                  String putOutput =
                          String.format("%s succeeded transferring %s", command, arrayLength);
                  System.out.println(putOutput);
                  seqStubPut.closefile();
                } else {
                  System.out.println("put failed");
                }
              }
              break;

            case "get":
              if (lineArgs.length > 3) System.out.println("Invalid input");
              else {
                String newLocalFileName = lineArgs[1];
                if (lineArgs.length > 2) {
                  if (lineArgs[2] != null) {
                    newLocalFileName = lineArgs[2];
                  }
                }
                boolean getResult = seqStubGet.openfiletoread(lineArgs[1]);
                File newFile = new File(newLocalFileName);
                if (getResult) {
                  BytesRead bytesRead = seqStubGet.nextread();
                  FileOutputStream fileOutputStream = new FileOutputStream(newFile);
                  int numRead = 0;
                  while (bytesRead.getCount() != -1) {
                    String str = new String(bytesRead.getBytesArray());
                    String str1 = str.trim();
                    numRead += str1.length();
                    fileOutputStream.write(str1.getBytes());
                    bytesRead = seqStubGet.nextread();
                  }
                  String getOutput = String.format("%s succeeded transferring %s", command, numRead);
                  System.out.println(getOutput);
                  seqStubGet.closefile();
                } else {
                  System.out.println("get failed");
                }
              }
              break;

            case "randomread":
              if (lineArgs.length > 4) System.out.println("Invalid input");
              else {
                boolean randResult = randomStub.openfiletoread(lineArgs[1]);
                int startIndex = Integer.parseInt(lineArgs[2]);
                int numOfBytes = Integer.parseInt(lineArgs[3]);
                if (randResult) {
                  BytesRead byteRead = randomStub.randomread(startIndex, numOfBytes);
                  String str = new String (byteRead.getBytesArray());
                  str = str.trim();
                  if (byteRead.getCount() != -1) {
                    String randOutput =
                            String.format("%s succeeded transferring %s", command, byteRead.getCount());
                    System.out.println(randOutput);
                    System.out.println(str);
                    randomStub.closefile();
                  } else {
                    System.out.println(command + " failed");
                  }
                } else {
                  System.out.println("randomread failed");
                }
              }
              break;

            case "exit":
              System.exit(0);
              break;
          }
        }
      }
    } catch (Exception e) {
      System.out.println("Client input invalid");
    }
  }
}