package sample.classes;
import java.io.File;
//import java.io.File;
import java.io.FileNotFoundException;
//import java.util.Scanner;
import java.util.Scanner;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.List;


public class Student {

    public static String ReceiveFilePath() throws FileNotFoundException{
      
               File obj1 = new File("filepath.txt");
              Scanner fileReader = new Scanner(obj1);
                 String filepath= fileReader.next();
                 fileReader.close();
                 return filepath;
            
    }
    public static void main(String[] args) throws FileNotFoundException{
        List list1= new List();
        

        //input the text file for the course list
        // try {
        //     File fileObj = new File("student1.txt");
        //     Scanner fileReader = new Scanner(fileObj);
        //     while (fileReader.hasNextLine()) {
        //       registeredCourse.getItems().list1.add(fileReader.nextLine());
        //     }
        //     fileReader.close();
        //   } catch (FileNotFoundException e) {
        //     System.out.println("An error occurred.");
        //     e.printStackTrace();
        //   }

        //Insert course list at here
        list1.add("Java");
        list1.add("Android");
        list1.add("Kotlin");
        list1.add("Android");
        
        String path = ReceiveFilePath(); 
        PdfWriter pdfWriter = new PdfWriter(path);

        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        pdfDocument.addNewPage();

        Document document = new Document(pdfDocument);
        document.add(list1);
        document.close();

    }
}
