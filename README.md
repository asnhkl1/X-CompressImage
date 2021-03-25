1.可以自定义大小和像素来进行压缩

2.要集成进自己项目,可以

        dependencies {
        
            implementation 'com.github.asnhkl1:ImageCompress:1.0.0'
        }
        
也可以直接把compressimagelibrarycopy作为module引入项目
在需要压缩的地方进行配置


(1)可以单张压缩,也可以list多张压缩

   
           ArrayList<Photo> photos = new ArrayList<>();

           Photo photo = new Photo(file.getPath());

           photo.setOriginalPath(path);

           photos.add(photo);
   
   
  
compressConfig链式调用即可配置,如果需要覆盖原图,可以把setCacheDir(path)传进去



           CompressConfig compressConfig = CompressConfig.builder()
   
                    .setUnCompressMinPixel(1000) // 最小像素不压缩，默认值：1000
                    
                    .setUnCompressNormalPixel(2000) // 标准像素不压缩，默认值：2000
                    
                    .setMaxPixel(1200) // 长或宽不超过的最大像素 (单位px)，默认值：1200
                    
                    .setMaxSize(200 * 1024) // 压缩到的最大大小 (单位B)，默认值：200 * 1024 = 200KB
                    
                    .enablePixelCompress(true) // 是否启用像素压缩，默认值：true
                    
                    .enableQualityCompress(true) // 是否启用质量压缩，默认值：true
                    
                    .enableReserveRaw(false) // 是否保留源文件，默认值：true
                    
                    .setCacheDir(path) // 压缩后缓存图片路径，默认值：Constants.COMPRESS_CACHE
                    
                    .setShowCompressDialog(true) // 是否显示压缩进度条，默认值：false
                    
                    .create();
                    
 (2)
 
        CompressImageManager.build(this,compressConfig, photos, new CompressImage.CompressListener() {
        
                @Override
                
                public void onCompressSuccess(ArrayList<Photo> var1) {
                
                    Log.i("imageCompress","success");
                    
                   //do what you want to do 
                   
                }
                

                @Override
                
                public void onCompressFailed(ArrayList<Photo> var1, String var2) {
                
                    Log.e("imageCompress","false",null);
                    
                }
                
            }).compress();
            
                    
                    
                    
