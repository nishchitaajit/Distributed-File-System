package com.sdsu;

/**
 * @author : Nishchita Ajit
 * @since : 10/7/20, Wed
 **/

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;


public class DirectoryOperationsImpl implements DirectoryOperations {

  ArrayList<File> dirFileArrayList = new ArrayList<>();
  String currentDirPath = new File("").getAbsolutePath();

  /**
   * This method gives the path of current directory.
   * @return path of the current directory
   */
  @Override
  public String getdir() {
      return this.currentDirPath;
  }

  /**
   * This method gives the number of files and directories in the current directory.
   * @return count of the number of files and directories
   */
  @Override
  public int filecount() {
    File[] fileList = new File(getdir()).listFiles();
    if(fileList != null)
      return fileList.length;
    else return 0;
  }

  /**
   * This method is an extension of filecount along with a size filter.
   * @param filterStr the filter condition string
   * @return counts the files and directories in the current directory based on the size filter.
   */
  @Override
  public long filecount(String filterStr) {
    String[] args = filterStr.split("(?<=<)|(?<=>)|(?<==)", 2);
    String filterType = args[0];
    String filterValue = args[1];
    File[] files = new File(getdir()).listFiles();
    if (files != null) {
      List<File> filesList = new ArrayList<>(Arrays.asList(files));
      Predicate<File> fileFilter;
      switch (filterType) {
        case "size<":
          fileFilter = file -> file.length() < Long.parseLong(filterValue);
          break;
        case "size>":
          fileFilter = file -> file.length() > Long.parseLong(filterValue);
          break;
        case "size=":
          fileFilter = file -> file.length() == Long.parseLong(filterValue);
          break;
        default:
          return 0;
      }
      return filesList.stream().filter(fileFilter).count();
    } else return 0;
  }

  /**
   * This method changes the directory to the desired directory mentioned if it exists.
   * @param fileName name of directory to be changed to
   * @return true if the file path exists
   */
  @Override
  public boolean cd(String fileName) {
    if (fileName.equals("..")) {
      File currentfile = new File(this.currentDirPath);
      String newdir = currentfile.getParent();
      this.currentDirPath = newdir;
      if (this.currentDirPath == getdir())
        return true;
    } else {
      String newFilePath = this.currentDirPath + "/" + fileName;
      File file = new File(newFilePath);
      if (file.isDirectory()) {
        this.currentDirPath = newFilePath;
        if (this.currentDirPath == getdir())
          return true;
      }
    }
    return false;
  }

  /**
   * Opens a directory to get its attributes.
   * @param fileName file to be opened
   * @return true if file exists
   */
  @Override
  public boolean openlist(String fileName) {
    String filePath;
    if (fileName != null) {
      filePath = this.currentDirPath + "/" + fileName;
    } else {
      filePath = this.currentDirPath;
    }
    File dirContent = new File(filePath);
    File[] listOfFiles = dirContent.listFiles();
    if (listOfFiles != null) {
      this.dirFileArrayList.add(new File(this.currentDirPath));
      this.dirFileArrayList.add(dirContent.getParentFile());
      this.dirFileArrayList.addAll(Arrays.asList(listOfFiles));
      return true;
    }
    return false;
  }

  /**
   * This method is called in a loop to get the file attributes of all the files and directories of the
   * directory opened in openlist(). DirEntry is populated with file attributes of one file at a time.
   * @return DirEntry of one file
   */
  @Override
  public DirEntry nextlist() {
    File file = dirFileArrayList.remove(0);
    File currentFile = new File(this.currentDirPath);
    DirEntry dirEntry = new DirEntry();
    if (file.getAbsolutePath().equals(currentFile.getAbsolutePath()))
      dirEntry.setName("./");
    else if (file.equals(currentFile.getParentFile()))
      dirEntry.setName("../");
    else if (file.isDirectory())
      dirEntry.setName(file.getName() + "/");
    else
      dirEntry.setName(file.getName());
    dirEntry.setDirectory(file.isDirectory());
    SimpleDateFormat sdf = new SimpleDateFormat("EEE dd HH:mm:ss z YYYY");
    dirEntry.setLastModifiedDate(sdf.format(file.lastModified()));
    dirEntry.setSize(file.length());
    return dirEntry;
  }

  /**
   * This method filters files in a directory based on the size filter.
   * @param size size of the file
   * @param symbol "<" ">" "=="
   * @return populates DirEntry for the files that match the filter criteria.
   */
  @Override
  public DirEntry nextlist(String size, char symbol) {
    long intSize = Long.parseLong(size);
    DirEntry directoryEntry = new DirEntry();
    File file = dirFileArrayList.remove(0);
    long fileSize = file.length();
      switch(symbol) {
        case '<':
          if (fileSize < intSize) {
            directoryEntry = createDirEntry(file);
          }
          break;
        case '>':
          if (fileSize > intSize) {
            directoryEntry = createDirEntry(file);
          }
          break;
        case '=':
          if (fileSize == intSize) {
            directoryEntry = createDirEntry(file);
          }
          break;
        default:
          directoryEntry.setName(null);
          break;
      }
    return directoryEntry;
  }

  /**
   * To avoid code repetition for populating DirEntry for the ls-filter above
   * @param file used to populate DirEntry of this file
   * @return populated DirEntry
   */
  private DirEntry createDirEntry(File file) {
    DirEntry dirEntry = new DirEntry();
    if (file.isDirectory())
      dirEntry.setName(file.getName() + "/");
    else
      dirEntry.setName(file.getName());
    dirEntry.setDirectory(file.isDirectory());
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    dirEntry.setLastModifiedDate(sdf.format(file.lastModified()));
    dirEntry.setSize(file.length());
    return dirEntry;
  }

  /**
   * This method gives the number of files in a directory.
   * @return count of files
   */
  @Override
  public int lengthOfList() {
    return this.dirFileArrayList.size();
  }

  /**
   * Closes a file
   * @return true after closing an opened file
   */
  @Override
  public boolean closelist() {
    dirFileArrayList.clear();
    return true;
  }
}