package disk.rest;

/**
 * @author Julia
 */
public class FileInfo {

    private String description;
    private String fileKey;
    private boolean isDelete;

    public FileInfo() {
    }

    public FileInfo(String description, String fileKey, boolean isDelete) {
        this.description = description;
        this.fileKey = fileKey;
        this.isDelete = isDelete;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }
}
