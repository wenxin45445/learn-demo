package com.meng.file.controller;

import com.meng.file.entity.FileInfo;
import com.meng.file.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/downloadFile")
public class FileDownloadController {

    @Autowired
    private FileService fileService;


    @RequestMapping("/downloadFile")
    @ResponseBody
    public String testCache(@RequestParam FileInfo fileInfo, @RequestParam HttpServletResponse response){
        try {
            fileService.downloadFile(fileInfo, response);
        } catch (Exception e) {
            return "false";
        }
        return "success";
    }

}
