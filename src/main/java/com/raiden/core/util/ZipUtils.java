package com.raiden.core.util;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @创建人:Raiden
 * @Descriotion:
 * @Date:Created in 0:03 2020/7/31
 * @Modified By:
 */
public final class ZipUtils {


    /**
     * 将所有文件写入压缩包
     * @param files
     * @param zipName
     * @throws IOException
     */
    public static void writeZip(List<File> files,String path, String zipName) throws IOException {
        if (StringUtils.isAnyBlank(path, zipName)){
            return;
        }
        File paperFile = new File(path);
        if (!paperFile.exists()){
            paperFile.mkdirs();
        }
        OutputStream os = new BufferedOutputStream(new FileOutputStream(path + zipName));
        try (ZipOutputStream zos = new ZipOutputStream(os)){
            byte[] buf = new byte[1024];
            int len;
            for (File file : files) {
                if (file == null || !file.isFile()) {
                    continue;
                }
                ZipEntry ze = new ZipEntry(file.getName());
                try {
                    zos.putNextEntry(ze);
                    BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
                    while ((len = bis.read(buf)) > 0) {
                        zos.write(buf, 0, len);
                    }
                    bis.close();
                    zos.closeEntry();
                }catch (IOException e){
                    continue;
                }finally {
                    file.delete();
                }
            }
        }
    }
}
