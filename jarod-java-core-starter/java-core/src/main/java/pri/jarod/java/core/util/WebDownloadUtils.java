package pri.jarod.java.core.util;

import cn.hutool.core.io.IoUtil;
import com.google.common.io.Files;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import pri.jarod.java.core.exception.DapThrowException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * web下载工具(基于servlet的容器，支持系统文件/压缩包内文件的浏览器下载）
 * // Content-Disposition中指定的类型是文件的扩展名，并且弹出的下载对话框中的文件类型图片是按照文件的扩展名显示的，点保存后，文件以filename的值命名，保存类型以Content中设置的为准。注意：在设置Content-Disposition头字段之前，一定要设置Content-Type头字段。
 * // 该方式下载的文件名 中文乱码问题
 * // response.setHeader("Content-Disposition", "attachment;filename=" + outFileName);
 * // 方法1： 设置下载的文件的名称-该方式已解决中文乱码问题，swagger,postman看到的是%...等，浏览器直接输url,OK
 * response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(outFileName, fileEncode));
 * // 方法2： 设置下载的文件的名称-该方式已解决中文乱码问题，swagger,postman看到的是%...等，浏览器直接输url,OK（
 * // 把文件名按UTF-8取出并按ISO8859-1编码，保证弹出窗口中的文件名中文不乱码，中文不要太多，最多支持17个中文，因为header有150个字节限制。）
 * response.setHeader("Content-Disposition", "attachment;filename=" + new String(outFileName.getBytes(fileEncode), "ISO8859-1"));
 * // 方法3：设置下载的文件的名称-该方式已解决中文乱码问题，postman可以，，swagger看到的是%...等，浏览器直接输url,OK
 * response.setHeader("Content-Disposition", "attachment;filename=" + outFileName + ";filename*=utf-8''" + URLEncoder.encode(outFileName, fileEncode));
 *
 * @author Jarod.Kong
 * @date 2020/9/7 10:54
 */
@Slf4j
public class WebDownloadUtils {

    /**
     * 下载流文件（支持压缩包jar/zip etc）
     *
     * @param response 浏览器 输出流
     * @param inputStream 下载文件输入流
     * @param fileName 文件名称
     */
    public static void commonDownload(HttpServletResponse response, InputStream inputStream, String fileName) {
        try {
            BmAssetUtils.notNull(inputStream, "获取文件流失败！！");
            String fileEncode = StandardCharsets.UTF_8.name();
           final String outFileName = fileName == null ? IdWorker.getIdStr() : fileName;
            // 告诉浏览器输出内容为流
            response.setHeader("Content-Type", "application/octet-stream;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + outFileName + ";filename*=utf-8''"
                    + URLEncoder.encode(outFileName, fileEncode));
            IoUtil.copy(inputStream, response.getOutputStream());
        } catch (IOException ex) {
            log.error("下载文件异常", ex);
            throw new DapThrowException("下载文件异常," + ex.getMessage());
        }
    }

    /**
     * 文件输出到浏览器（支持系统文件下载，但是对于jar/zip等压缩包中的文件无效）
     *（文件）
     * @param response
     * @param file
     */
    public static void commonDownload(HttpServletResponse response, File file) {
        try {
            BmAssetUtils.notNull(file, "获取文件失败！！");
            String fileEncode = StandardCharsets.UTF_8.name();
            String outFileName = file.getName();
            // 告诉浏览器输出内容为流
            response.setHeader("Content-Type", "application/octet-stream;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + outFileName + ";filename*=utf-8''"
                    + URLEncoder.encode(outFileName, fileEncode));
            //noinspection UnstableApiUsage
            Files.copy(file, response.getOutputStream());
        } catch (IOException ex) {
            log.error("下载文件异常", ex);
            throw new DapThrowException("下载文件异常," + ex.getMessage());
        }
    }

    /**
     * 针对下载excel文件的方式（流）
     *
     * @param response
     * @param workbook
     * @param fileName
     */
    public static void commonDownload(HttpServletResponse response, Workbook workbook, String fileName) {
        try {
            BmAssetUtils.notNull(workbook, "文档对象不为空！！");
            String fileEncode = StandardCharsets.UTF_8.name();
            // 告诉浏览器输出内容为流
            response.setHeader("Content-Type", "application/octet-stream;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ";filename*=utf-8''"
                    + URLEncoder.encode(fileName, fileEncode));
            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
        } catch (IOException ex) {
            log.error("下载文件异常", ex);
            throw new DapThrowException("下载文件异常," + ex.getMessage());
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                log.error("下载文件异常", e);
            }
        }
    }
}
