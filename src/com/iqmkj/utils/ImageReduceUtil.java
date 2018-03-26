package com.iqmkj.utils;

import config.FileUrlConfig;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
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
     * 根据指定大小和指定精度压缩图片
     *
     * @param img  图片文件
     * @param path 缓存到本地的文件夹
     * @param desFileSize  压缩到指定大小，单位kb
     * @param accuracy 压缩的比率，建议小于0.9
     * @return 压缩成功：返回本地图片路径，压缩失败：返回null
     */
    public static String reducePicForScale(MultipartFile img, String path,
                                           long desFileSize, double accuracy) {
        if (img == null || img.isEmpty() || path == null || path.length() <= 0 || !StringUtil.isNotNull(path)
                || desFileSize <= 0 || accuracy <= 0) {
            return null;
        }

        StringBuffer url = new StringBuffer();
        url.append(path);
        url.append(NumberUtil.createNumberStr(6));
        url.append(".jpg");

        String fileName = url.toString();
        try {
            Thumbnails.of(img.getInputStream())
                    .scale(1f)
                    .outputQuality(accuracy)
                    .outputFormat("jpg")
                    .toFile(fileName);
            //压缩，直到目标文件大小小于desFileSize
            while (true) {
                long fileSize = reducePicCycle(fileName, accuracy);
                if (fileSize <= desFileSize * 1024) {
                    break;
                }
            }
        } catch (IOException e) {
            LogUtil.printErrorLog(e);
            FileWriteLocalUtil.deleteFileRealPath(url.toString());
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
        int desWidth = new BigDecimal(srcWdith).multiply(
                new BigDecimal(accuracy)).intValue();
        int desHeight = new BigDecimal(srcHeigth).multiply(
                new BigDecimal(accuracy)).intValue();

        Thumbnails.of(desPath).size(desWidth, desHeight)
                .outputQuality(accuracy).toFile(desPath);
        return new File(desPath).length();
    }

    /**
     * 压缩并上传单张图片到远程服务器
     * 压缩至400k以内
     * @param img       图片文件
     * @param request   请求
     * @param remoteDir 要保存的远程服务器文件夹
     * @return 压缩并上传成功：返回服务器图片路径，压缩或上传失败：返回null
     */
    public static String reduceImageUploadRemote(MultipartFile img, HttpServletRequest request, String remoteDir) {
        if (img == null || img.isEmpty() || request == null || remoteDir.length() <= 0 || !StringUtil.isNotNull(remoteDir)) {
            return null;
        }

        String path = request.getServletContext().getRealPath("/upload") + "/tempReduceImage/";
        //压缩后的本地文件
        String localFileUrl = reducePicForScale(img, path, 400, 0.7);

        String remoteImageUrl = "";
        long size = img.getSize();
        InputStream inputStream = null;
        try {
            if (size <= 400 * 1024) {
                remoteImageUrl = FileWriteRemoteUtil.uploadFile(img.getOriginalFilename(),
                        img.getInputStream(), FileUrlConfig.file_remote_adImage_url);
                return remoteImageUrl;
            }

            inputStream = new FileInputStream(localFileUrl);
        } catch (IOException ex) {
            LogUtil.printErrorLog(ex);
            FileWriteLocalUtil.deleteFileRealPath(localFileUrl);
            return null;
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
