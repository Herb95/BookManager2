package com.graywolfz.bms.web;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServlet;
import org.apache.commons.fileupload2.core.DiskFileItemFactory;
import org.apache.commons.io.FileCleaningTracker;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.commons.fileupload2.jakarta.servlet6.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/update")
public class UpdateController extends HttpServlet {

    /**
     * 我的上传方法
     *
     * @param req     请求接口
     * @param dirName 文件路劲
     * @return String
     */
    public String myUpdate(HttpServletRequest req, String dirName) {
        String res = null;  // 返回网络路径
        try {
            String staticDir = ResourceUtils.getURL("classpath:").getPath() + "static";  // 得到classes/static目录
            String localDir = staticDir + "/" + dirName;   //本地目录
            // 如果结果目录不存在，则创建目录
            File resDirFile = new File(localDir);
            if (!resDirFile.exists()) {
                boolean flag = resDirFile.mkdirs();
                if (!flag) throw new RuntimeException("创建结果目录失败");
            }
            var context = new JakartaServletRequestContext(req);
            //先判断上传的数据是否多段数据（只有是多段的数据，才是文件上传的）
            if (JakartaServletFileUpload.isMultipartContent(context)) {
                // 创建 FileItemFactory 工厂实现类
                // 创建工厂和上传对象
                JakartaServletFileUpload fileUpload = getJakartaServletFileUpload();
                // 解析上传的数据，得到每一个表单项 FileItem
//                List<FileItem> list = servletFileUpload.parseRequest(new ServletRequestContext(req));
                var list = fileUpload.parseRequest(req);
                // 循环判断，每一个表单项，是普通类型，还是上传的文件
                for (Object obj : list) {
                    FileItem fileItem = (FileItem) obj;
                    // 判断是不是表单文本数据，取反就是文件数据
                    if (!fileItem.isFormField()) { // 是上传的文件
                        // 上传的文件
                        System.out.println("表单项的 name 属性值：" + fileItem.getFieldName());
                        System.out.println("上传的文件名：" + fileItem.getName());
                        // 加个时间戳防止重名
                        String newFileName = System.currentTimeMillis() + fileItem.getName();
                        // 写文件
                        File file = new File(localDir + "/" + newFileName);
                        fileItem.write(file);
                        // 返回值
                        res = "http://localhost:8092/BookManager/" + dirName + "/" + newFileName;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    private static JakartaServletFileUpload getJakartaServletFileUpload() {
        DiskFileItemFactory.Builder builder = new DiskFileItemFactory.Builder();
        DiskFileItemFactory diskFileItemFactory = builder.get();

        JakartaServletFileUpload fileUpload = new JakartaServletFileUpload<>(diskFileItemFactory);
        return fileUpload;
    }

    public DiskFileItemFactory newDiskFileItemFactory(ServletContext context) {
        FileCleaningTracker fileCleaningTracker = JakartaFileCleaner.getFileCleaningTracker(context);
        return new DiskFileItemFactory.Builder()
                .setFileCleaningTracker(fileCleaningTracker)
                .get();
    }

    /**
     * 上传图片
     *
     * @param req
     * @return
     */
    @RequestMapping("/updateImg")
    @ResponseBody
    public Map<String, Object> updateImg(HttpServletRequest req) {
        String resPath = myUpdate(req, "pictures");

        Map<String, Object> res = new HashMap<>();
        res.put("code", 0);
        res.put("data", resPath);

        return res;
    }

}
