package com.mercury.interview.service;

import com.mercury.interview.bean.Interview;
import com.mercury.interview.dao.InterviewDao;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;

@Service
public class HRService {

    @Autowired
    private InterviewDao interviewDao;

    private static String UPLOADED_FOLDER = "../resource/";

    public String singleFileUpload(int id, MultipartFile file) {
        System.out.println("success");
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + id + "_" + file.getOriginalFilename());
            //Files.write(path, bytes);

        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(UPLOADED_FOLDER + file.getOriginalFilename());
        return UPLOADED_FOLDER + id + "_" + file.getOriginalFilename();
    }

   @Async
    public void uploadExcel(MultipartFile reapExcelDataFile)throws IOException, ParseException {
        System.out.println("success");
        //List<Interview> tempStudentList = new ArrayList<Interview>();
        XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);
        System.out.println('1');
        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            //System.out.println(i);
            Interview tempInterview = new Interview();

            XSSFRow row = worksheet.getRow(i);
            //System.out.println(i);

            if (row.getCell(1)!= null)
//            {
//                String time = row.getCell(1).getStringCellValue();
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.mm.dd - HH:mm", Locale.US);
//                Date d1 = sdf.parse(time);
//                tempInterview.setTime(d1);
//            }
                tempInterview.setTime(row.getCell(1).getStringCellValue());
            // System.out.println(i);
            if (row.getCell(2) != null)
                tempInterview.setCandidate(row.getCell(2).getStringCellValue());
            //System.out.println("!!!!!!!!!!!!!");
            if (row.getCell(3)!= null)
                tempInterview.setScheduler(row.getCell(3).getStringCellValue());

            if (row.getCell(4)!= null)
                tempInterview.setPhone(NumberToTextConverter.toText(row.getCell(4).getNumericCellValue()));
            // System.out.println("!!!!!!!!!!!!!");
            if (row.getCell(5) != null)
                tempInterview.setEmail(row.getCell(5).getStringCellValue());

            if(row.getCell(6)!=null)
                tempInterview.setComments(String.valueOf(row.getCell(6).getStringCellValue()));

            if (row.getCell(7) != null)
                tempInterview.setStatus(row.getCell(7).getStringCellValue());
            // System.out.println("!!!!!!!!!!!!!");
            interviewDao.save(tempInterview);
        }
    }
}

