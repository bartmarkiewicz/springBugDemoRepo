package com.example.springbugdemorepo.service;

import java.io.InputStream;
import org.springframework.stereotype.Service;

@Service
public class FileService {

  public FileService() {
  }

  public TestFileObj getTestFileObjById(String fileId) {
    return new TestFileObj();
  }

  public InputStream getFileContents(TestFileObj fileObj) {
    return null;
  }

  public static class TestFileObj {
    private String id;
    private String filename;
    private Long sizeBytes;

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public String getFilename() {
      return filename;
    }

    public void setFilename(String filename) {
      this.filename = filename;
    }

    public Long getSizeBytes() {
      return sizeBytes;
    }

    public void setSizeBytes(Long sizeBytes) {
      this.sizeBytes = sizeBytes;
    }
  }
}

