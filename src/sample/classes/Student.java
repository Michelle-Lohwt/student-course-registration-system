package sample.classes;
import java.io.FileNotFoundException;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.List;


public class Student {
    public static void main(String[] args) throws FileNotFoundException{
        List list1= new List();
        
        //Insert course list at here
        list1.add("Java");
        list1.add("Android");
        list1.add("Kotlin");
        list1.add("Android");
        
        String path = "Desktop/CourseList.pdf"; 
        PdfWriter pdfWriter = new PdfWriter(path);

        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        pdfDocument.addNewPage();

        Document document = new Document(pdfDocument);
        document.add(list1);
        document.close();

    }
}
