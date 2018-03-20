package com.iqmkj.utils;

import config.FileUrlConfig;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Description:图片压缩
 * Author: hht
 * Date: 2018-03-16 10:16
 */
public class ImageReduceUtil {

    /**
     * 压缩图片，400K到5M以内的图片压缩至400K以内，5M-10M压缩到700k以内
     *
     * @param img  图片文件
     * @param path 缓存文件夹
     * @return 压缩成功：返回图片路径，压缩失败：返回null
     */
    public static String reduceImage(MultipartFile img, String path) {
        StringBuffer url = new StringBuffer();
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
        } catch (Exception ex) {
            LogUtil.printErrorLog(ex);
            FileWriteLocalUtil.deleteFileRealPath(url.toString());
            return null;
        }
        return url.toString();
    }

    /**
     * 压缩并上传单张图片到远程服务器
     * 400K到5M以内的图片压缩至400K以内，5M-10M压缩到700k以内，10M以上不压缩返回null
     *
     * @param img       图片文件
     * @param request   请求
     * @param remoteDir 要保存的远程服务器文件夹
     * @return 压缩成功：返回图片路径，压缩失败：返回null
     */
    public static String reduceImageUploadRemote(MultipartFile img, HttpServletRequest request, String remoteDir) {
        if (img == null || img.isEmpty() || request == null || remoteDir.length() <= 0 || !StringUtil.isNotNull(remoteDir)) {
            return null;
        }

        StringBuffer url = new StringBuffer();
        String path = request.getServletContext().getRealPath("/upload") + "/tempReduceImage/";
        url.append(path);
        url.append(NumberUtil.createNumberStr(6));
        url.append(".jpg");

        String remoteImageUrl = "";
        long size = img.getSize() / 1024;
        InputStream inputStream = null;
        try {
            if (size < 400) {
                remoteImageUrl = FileWriteRemoteUtil.uploadFile(img.getOriginalFilename(),
                        img.getInputStream(), FileUrlConfig.file_remote_adImage_url);
                return remoteImageUrl;
            }

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

            if (size > 10 * 1024) {
                return null;
            }

            inputStream = new FileInputStream(url.toString());
        } catch (IOException ex) {
            LogUtil.printErrorLog(ex);
            FileWriteLocalUtil.deleteFileRealPath(url.toString());
            return null;
        }

        remoteImageUrl = FileWriteRemoteUtil.uploadFile(img.getOriginalFilename(), inputStream, remoteDir);
        try {
            inputStream.close();
        } catch (IOException e) {
            LogUtil.printErrorLog(e);
        } finally {
            //删除本地缓存文件
            FileWriteLocalUtil.deleteFileRealPath(url.toString());
        }

        return remoteImageUrl;
    }
}
