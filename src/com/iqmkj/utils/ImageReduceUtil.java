package com.iqmkj.utils;

import config.FileUrlConfig;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

/**
 * Description:图片压缩
 * Author: hht
 * Date: 2018-03-16 10:16
 */
public class ImageReduceUtil {

    /**
     * 压缩图片, 仅压缩一次
     * 400K到5M以内的图片压缩至400K以内，5M-10M压缩到700k以内<BR/>
     * ****注意：处理结束后需删除本地文件****
     * @param img  图片文件
     * @param path 缓存文件夹
     * @return 压缩成功：返回保存的本地图片全路径，压缩失败：返回null
     */
    public static String reduceImage(MultipartFile img, String path) {
        StringBuilder url = new StringBuilder();
        url.append(path);
        url.append(NumberUtil.createNumberStr(6));
        url.append(".jpg");

        long size = img.getSize() / 1024;
        try {
            //400K-1M 0.4
            if (400 <= size && size <= 1024) {
                Thumbnails.of(img.getInputStream())
                        .scale(1)
                        .outputQuality(0.4)
                        .outputFormat("jpg")
                        .toFile(url.toString());
            }
            //1M-5M  0.2
            if (1024 < size && size <= 5 * 1024) {
                Thumbnails.of(img.getInputStream())
                        .scale(1)
                        .outputQuality(0.2)
                        .outputFormat("jpg")
                        .toFile(url.toString());
            }
            //5M-10M  0.1
            if (5 * 1024 < size && size <= 10 * 1024) {
                Thumbnails.of(img.getInputStream())
                        .scale(1)
                        .outputQuality(0.1)
                        .outputFormat("jpg")
                        .toFile(url.toString());
            }
        } catch (IOException ex) {
            LogUtil.printErrorLog(ex);
            FileWriteLocalUtil.deleteFileRealPath(url.toString());
            return null;
        }
        return url.toString();
    }

    /**
     * 根据指定大小和指定精度压缩图片,循环压缩至指定大小<BR/>
     * ****注意：处理结束后需删除本地文件****
     * @param img  图片文件
     * @param path 缓存到本地的文件夹
     * @param desFileSize  压缩到指定大小，单位kb
     * @param accuracy 压缩的比率，建议小于0.9
     * @return 压缩成功：返回保存的本地图片全路径，压缩失败：返回null
     */
    public static String reducePicForScale(MultipartFile img, String path,
                                           long desFileSize, double accuracy) {
        if (img == null || img.isEmpty() || path == null || path.length() <= 0 || !StringUtil.isNotNull(path)
                || desFileSize <= 0 || accuracy <= 0) {
            return null;
        }
        if (img.getSize() <= desFileSize * 1024) {
            return null;
        }

        String fileName = "";
        StringBuilder sb = new StringBuilder();
        try {
            fileName = FileWriteLocalUtil.SaveInputStreamToFileByPath(img.getInputStream(),
                    path, NumberUtil.createNumberStr(6) + ".jpg");

            //压缩，直到目标文件大小小于desFileSize
            while (true) {
                long fileSize = reducePicCycle(fileName, accuracy);

                if (fileSize <= desFileSize * 1024) {
                    break;
                }
            }
        } catch (Exception e) {
            LogUtil.printErrorLog(e);
            FileWriteLocalUtil.deleteFileRealPath(fileName);
            return null;
        }
        return fileName;
    }

    /**
     * 压缩图片
     *
     * @param desPath  目标文件
     * @param accuracy 压缩比率
     * @return 返回压缩后的大小
     */
    private static long reducePicCycle(String desPath, double accuracy) throws IOException {
        // 计算宽高
        BufferedImage bim = ImageIO.read(new File(desPath));
        int srcWdith = bim.getWidth();
        int srcHeigth = bim.getHeight();
        int desWidth = new BigDecimal(srcWdith)
                .multiply(new BigDecimal(accuracy)).intValue();
        int desHeight = new BigDecimal(srcHeigth)
                .multiply(new BigDecimal(accuracy)).intValue();

        Thumbnails.of(desPath).size(desWidth, desHeight)
                .outputQuality(accuracy).toFile(desPath);
        return new File(desPath).length();
    }

    /**
     * 压缩并上传单张图片到远程服务器<BR/>
     * 图片小于400k直接上传至服务器，图片大于400k压缩至400k以内并上传至服务器
     * @param img       图片文件
     * @param remoteDir 要保存的远程服务器文件夹
     * @return 压缩并上传成功：返回服务器图片路径，压缩或上传失败：返回null
     */
    public static String reduceImageUploadRemote(MultipartFile img, String remoteDir) {
        if (img == null || img.isEmpty() || remoteDir.length() <= 0 || !StringUtil.isNotNull(remoteDir)) {
            return null;
        }

        String remoteImageUrl;

        long size = img.getSize();
        //文件小于400K直接上传至服务器
        if (size <= 400 * 1024) {
            try {
                remoteImageUrl = FileWriteRemoteUtil.uploadFile(img.getOriginalFilename(),
                        img.getInputStream(), FileUrlConfig.file_remote_adImage_url);
            } catch (IOException ex) {
                LogUtil.printErrorLog(ex);
                return null;
            }
            return remoteImageUrl;
        }

        InputStream inputStream = null;

        //压缩后的本地文件
        String localFileUrl = reducePicForScale(img, "tempReduceImage", 400, 0.7);
        try {
            inputStream = new FileInputStream(localFileUrl);
        } catch (FileNotFoundException e) {
            LogUtil.printErrorLog(e);
        }

        remoteImageUrl = FileWriteRemoteUtil.uploadFile(img.getOriginalFilename(), inputStream, remoteDir);
        try {
            inputStream.close();
        } catch (IOException e) {
            LogUtil.printErrorLog(e);
        }

        //删除本地缓存文件
        FileWriteLocalUtil.deleteFileRealPath(localFileUrl);
        return remoteImageUrl;
    }
}
