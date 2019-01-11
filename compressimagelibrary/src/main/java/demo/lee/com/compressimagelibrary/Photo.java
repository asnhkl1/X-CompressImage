package demo.lee.com.compressimagelibrary;


import java.io.Serializable;

public class Photo implements Serializable {
    private String originalPath;
    private boolean compressed;
    private String compressPath;

    public Photo(String originalPath) {
        this.originalPath = originalPath;
    }

    public String getOriginalPath() {
        return this.originalPath;
    }

    public void setOriginalPath(String originalPath) {
        this.originalPath = originalPath;
    }

    public String getCompressPath() {
        return this.compressPath;
    }

    public void setCompressPath(String compressPath) {
        this.compressPath = compressPath;
    }

    public boolean isCompressed() {
        return this.compressed;
    }

    public void setCompressed(boolean compressed) {
        this.compressed = compressed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof Photo) {
            Photo photo = (Photo)o;
            return this.originalPath.equals(photo.originalPath);
        } else {
            return super.equals(o);
        }
    }
}