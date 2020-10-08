Steps to run the code
1.	The client and server programs are to be run on two separate machines as the sequential operations require the
    creation of the same name files if the second input parameter is not given by the user.

2.	Steps to run
Step 1:
    Copy DistributedFileSystemServer to the server machine.
    Run/Start the server on the server machine. Run the below command from DistributedFileSystem/src
    cd DistributedFileSystem/src
    javac com/sdsu/*.java
    java com.sdsu.Server &

Step 2:
    Copy DistributedFileSystemClient to the client machine.
    Option 1: Run command “./execute.sh” from /DistributedFileSystemClient.
              Please update the below variables in the execute.sh before running.
                i. ServerIPAddress: Add IP address of the server machine

               ii. Input: Update <Absolute path> with your system path for input file (inputcommands.txt).
                   The inputcommands.txt is located in /DistributedFileSystemClient and is my input script
                   which contains all the commands to be tested.

              iii. Output: Update <absolute path> with your system path for output file (output.txt).
                   The output.txt is located in /DistributedFileSystemClient and will contain the results
                    of the commands after executing ./execute.sh.

	OR

    Option 2: Run below commands from DistributedFileSystemClient/src to execute all commands. The inputcommands.txt and
              output.txt are located in /DistributedFileSystemClient
	        ----------------
	        cd DistributedFileSystemClient/src
	        javac *.java
	        java com.sdsu.Main <Server IpAddress> <absolute path of inputcommands.txt> | tee <absolute path of output.txt>
            ----------------
------------------------------------------------------------------------------------------------------------------------

DirectoryOperations:
Directory operations interface that extends Remote and contains the below directory operation methods declaration that
can be invoked by the client are present in this interface.

DirectoryOpsImpl:
This file contains the server-side implementation of file operations – getdir, filecount, filecount <filter>,
cd [fileName], ls [fileName], ls -l [fileName], ls <filter>.

Direntry
This file contains the DirEntry class which contains the file name, a flag indicating whether it is a directory,
its size in bytes and its last modified time.

SeqeuntialOperationsPut:
Sequential operation put interface that extends Remote. Contains methods of sequential put operation declaration that
can be invoked by the client in this interface.

SequentialOperationsPutImpl:
This file contains the server-side implementation of sequential put operation, put localFile [remoteFile].

SequentialOperationsGet:
Sequential operation get interface that extends Remote. Contains methods of sequential get operation declaration that
can be invoked by the client in this interface.

SequentialOperationsGetImpl:
This file contains the implementation to all methods mentioned in the remote interface.This file contains the
server-side implementation of sequential get operation: get remoteFile [localFile].

BytesRead:
This file contains a byte array that holds the file contents read from a remoteFile and the count of bytes read.
This is the int,block to be returned for sequential get and randomread.

RandomAccessOperation:
Random read operation interface that extends Remote. Contains methods of random read operation declaration that can be
invoked by the client in this interface.

RandomAccessOperationImpl:
This file contains the implementation to all methods mentioned in the remote interface. This file contains the
server-side implementationrandom read operation: randomread remoteFile startByte numOfBytes.

Server:
A remote object is created by instantiating the implementation class. The remote object is exported using
exportObject(). It also binds the remote object created to the registry using the bind() method of the class named
Registry. To this method, we pass a string representing the bind name is and the object exported as parameters.

Client:
The client class invokes the remote object by making use of the getRegistry() and fetches the object using lookup(). To
this method, we have passed a string value representing the bind name as a parameter. This will return you the remote
object. lookup() returns an object of type remote. Then we invoke the required method using the obtained remote object.
This file takes as input a file which holds all the commands mentioned above and executes it. It takes the user input
from the file and passes the required parameters to the server.
Main
