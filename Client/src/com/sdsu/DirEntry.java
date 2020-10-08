package com.sdsu;

/**
 * @author : Nishchita Ajit
 * @since : 10/7/20, Wed
 **/

import java.io.Serializable;

public class DirEntry implements Serializable {
  String name;
  long size;
  boolean isDirectory;
  String lastModifiedDate;

  public String getName() {
    return name;
  }

  public void setName(String name) { this.name = name; }

  public long getSize() {
    return size;
  }

  public void setSize(long size) {
    this.size = size;
  }

  public boolean isDirectory() {
    return isDirectory;
  }

  public void setDirectory(boolean directory) {
    isDirectory = directory;
  }

  public String getLastModifiedDate() {
    return lastModifiedDate;
  }

  public void setLastModifiedDate(String lastModifiedDate) {
    this.lastModifiedDate = lastModifiedDate;
  }

  @Override
  public String toString() {
    return "DirEntry{" +
        "name='" + name + '\'' +
        ", size=" + size +
        ", isDirectory=" + isDirectory +
        ", lastModifiedDate='" + lastModifiedDate + '\'' +
        '}';
  }
}