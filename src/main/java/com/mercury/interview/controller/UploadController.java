package com.mercury.interview.controller;

import com.mercury.interview.bean.Interview;
import com.mercury.interview.dao.InterviewDao;
import com.mercury.interview.service.ExcelService;
import com.mercury.interview.service.HRService;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.servlet.http.HttpServletResponse;

@RestController
public class UploadController {

    private static String UPLOADED_FOLDER = "../resource/";
    @Autowired
    private InterviewDao interviewDao;
    @Autowired
    private HRService hrService;

    @PostMapping("/resume/upload/{id}")
    public String singleFileUpload(@PathVariable int id, @RequestParam("file") MultipartFile file) {
        System.out.println("success");
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + id + "_" + file.getOriginalFilename());
            Files.write(path, bytes);

            Interview newInterview = interviewDao.findById(id).get();
            newInterview.setResume(id + "_" + file.getOriginalFilename());
            interviewDao.save(newInterview);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(UPLOADED_FOLDER + file.getOriginalFilename());
        return UPLOADED_FOLDER + id + "_" + file.getOriginalFilename();
    }
    @GetMapping("/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=interviews_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        ExcelService excelExporter = new ExcelService(interviewDao.findAll());

        excelExporter.export(response);
    }

    @PostMapping("/hr/upload")
    public void mapReapExcelDataToDB(@RequestParam("file") MultipartFile reapExcelDataFile) throws IOException, ParseException {
        hrService.uploadExcel(reapExcelDataFile);
//        System.out.println("success");
//        //List<Interview> tempStudentList = new ArrayList<Interview>();
//        XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
//        XSSFSheet worksheet = workbook.getSheetAt(0);
//        System.out.println('1');
//        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
//            System.out.println(i);
//            Interview tempInterview = new Interview();
//
//            XSSFRow row = worksheet.getRow(i);
//            System.out.println(i);
//
//            if (row.getCell(1)!= null)
////            {
////                String time = row.getCell(1).getStringCellValue();
////                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.mm.dd - HH:mm", Locale.US);
////                Date d1 = sdf.parse(time);
////                tempInterview.setTime(d1);
////            }
//                tempInterview.setTime(row.getCell(1).getStringCellValue());
//           // System.out.println(i);
//            if (row.getCell(2) != null)
//                tempInterview.setCandidate(row.getCell(2).getStringCellValue());
//            //System.out.println("!!!!!!!!!!!!!");
//            if (row.getCell(3)!= null)
//                tempInterview.setScheduler(row.getCell(3).getStringCellValue());
//
//            if (row.getCell(4)!= null)
//                tempInterview.setPhone(NumberToTextConverter.toText(row.getCell(4).getNumericCellValue()));
//           // System.out.println("!!!!!!!!!!!!!");
//            if (row.getCell(5) != null)
//                tempInterview.setEmail(row.getCell(5).getStringCellValue());
//
//            if(row.getCell(6)!=null)
//                tempInterview.setComments(String.valueOf(row.getCell(6).getStringCellValue()));
//
//            if (row.getCell(7) != null)
//                tempInterview.setStatus(row.getCell(7).getStringCellValue());
//           // System.out.println("!!!!!!!!!!!!!");
//            interviewDao.save(tempInterview);
//        }

    }

}
