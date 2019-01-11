package demo.lee.com.compressimagelibrary;

import java.io.Serializable;

public class CompressConfig implements Serializable {
    private int unCompressMinPixel;
    private int unCompressNormalPixel;
    private int maxPixel;
    private int maxSize;
    private boolean enablePixelCompress;
    private boolean enableQualityCompress;
    private boolean enableReserveRaw;
    private String cacheDir;
    private boolean showCompressDialog;

    public static CompressConfig getDefaultConfig() {
        return new CompressConfig();
    }

    private CompressConfig() {
        this.unCompressMinPixel = 1000;
        this.unCompressNormalPixel = 2000;
        this.maxPixel = 1200;
        this.maxSize = 204800;
        this.enablePixelCompress = true;
        this.enableQualityCompress = true;
        this.enableReserveRaw = true;
    }

    public int getUnCompressMinPixel() {
        return this.unCompressMinPixel;
    }

    private void setUnCompressMinPixel(int unCompressMinPixel) {
        this.unCompressMinPixel = unCompressMinPixel;
    }

    public int getUnCompressNormalPixel() {
        return this.unCompressNormalPixel;
    }

    private void setUnCompressNormalPixel(int unCompressNormalPixel) {
        this.unCompressNormalPixel = unCompressNormalPixel;
    }

    public int getMaxPixel() {
        return this.maxPixel;
    }

    private void setMaxPixel(int maxPixel) {
        this.maxPixel = maxPixel;
    }

    public int getMaxSize() {
        return this.maxSize;
    }

    private void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public boolean isEnablePixelCompress() {
        return this.enablePixelCompress;
    }

    private void setEnablePixelCompress(boolean enablePixelCompress) {
        this.enablePixelCompress = enablePixelCompress;
    }

    public boolean isEnableQualityCompress() {
        return this.enableQualityCompress;
    }

    private void setEnableQualityCompress(boolean enableQualityCompress) {
        this.enableQualityCompress = enableQualityCompress;
    }

    public boolean isEnableReserveRaw() {
        return this.enableReserveRaw;
    }

    private void setEnableReserveRaw(boolean enableReserveRaw) {
        this.enableReserveRaw = enableReserveRaw;
    }

    public String getCacheDir() {
        return this.cacheDir;
    }

    public void setCacheDir(String cacheDir) {
        this.cacheDir = cacheDir;
    }

    public boolean isShowCompressDialog() {
        return this.showCompressDialog;
    }

    private void setShowCompressDialog(boolean showCompressDialog) {
        this.showCompressDialog = showCompressDialog;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private CompressConfig config;

        private Builder() {
            this.config = new CompressConfig();
        }

        public Builder setUnCompressMinPixel(int unCompressMinPixel) {
            this.config.setUnCompressMinPixel(unCompressMinPixel);
            return this;
        }

        public Builder setUnCompressNormalPixel(int unCompressNormalPixel) {
            this.config.setUnCompressNormalPixel(unCompressNormalPixel);
            return this;
        }

        public Builder setMaxSize(int maxSize) {
            this.config.setMaxSize(maxSize);
            return this;
        }

        public Builder setMaxPixel(int maxPixel) {
            this.config.setMaxPixel(maxPixel);
            return this;
        }

        public Builder enablePixelCompress(boolean enablePixelCompress) {
            this.config.setEnablePixelCompress(enablePixelCompress);
            return this;
        }

        public Builder enableQualityCompress(boolean enableQualityCompress) {
            this.config.setEnableQualityCompress(enableQualityCompress);
            return this;
        }

        public Builder enableReserveRaw(boolean enableReserveRaw) {
            this.config.setEnableReserveRaw(enableReserveRaw);
            return this;
        }

        public Builder setCacheDir(String cacheDir) {
            this.config.setCacheDir(cacheDir);
            return this;
        }

        public Builder setShowCompressDialog(boolean showCompressDialog) {
            this.config.setShowCompressDialog(showCompressDialog);
            return this;
        }

        public CompressConfig create() {
            return this.config;
        }
    }

}
