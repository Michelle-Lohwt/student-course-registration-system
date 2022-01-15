package sample.classes;
//import java.io.File;
import java.io.FileNotFoundException;
import com.itextpdf.layout.element.List;


public class Student {


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
        
        // String path = ;
        // PdfWriter pdfWriter = new PdfWriter(path);

        // PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        // pdfDocument.addNewPage();

        // Document document = new Document(pdfDocument);
        // document.add(list1);
        // document.close();

    }
}
