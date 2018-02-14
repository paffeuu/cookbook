package main.utilities;
/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2017 iText Group NV
    Authors: iText Software.

    For more information, please contact iText Software at this address:
    sales@itextpdf.com
 */
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfDocumentInfo;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.PdfViewerPreferences;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.WriterProperties;
import com.itextpdf.layout.font.FontProvider;
import food.Dish;
import food.SimpleIngredient;
import javafx.scene.control.Alert;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;


public class PDFCreator {

    private final static String TEMPLATES_FOLDER;
    private final static String PREPARED_TEMPLATE_FOLDER;
    private final static String DESTINATION_FOLDER;
    private final static String RECIPE_TEMPLATE;
    private final static String SHOPPING_TEMPLATE;

    private String htmlSource;
    private String pdfDest;
    private String resourceFolder;
    private String fileName;
    private boolean properlyCreated;

    private static int shoppingListCounter;

    static
    {
        TEMPLATES_FOLDER = "templates/";
        PREPARED_TEMPLATE_FOLDER = "prepared/";
        DESTINATION_FOLDER = "pdf/";
        RECIPE_TEMPLATE = "recipe_template";
        SHOPPING_TEMPLATE = "shopping_template";
    }

    private PDFCreator(String pdfDest, String resourceFolder, String fileName)
    {
        this.pdfDest = pdfDest;
        this.resourceFolder = resourceFolder;
        this.fileName = fileName;
        properlyCreated = false;
    }

    public static PDFCreator create(String fileName)
    {
        String resourceFolder = TEMPLATES_FOLDER;
        String pdfDest = DESTINATION_FOLDER + fileName + ".pdf";
        File file = new File(pdfDest);

        System.out.println("Parsing: " + fileName);
        file.getParentFile().mkdirs();
        PDFCreator pdfCreator = new PDFCreator(pdfDest, resourceFolder, fileName);

        return pdfCreator;
    }

    public String preparePdf(Dish dish)
    {
        String htmlSource = TEMPLATES_FOLDER + RECIPE_TEMPLATE + ".html";
        this.htmlSource = htmlSource;
        prepareRecipeTemplate(dish);
        createPdf();
        return pdfDest;
    }

    private void prepareRecipeTemplate(Dish dish)
    {
        String template;
        char[] chars = new char[5000];
        int i = 0;
        try (FileReader fr = new FileReader(new File(htmlSource)))
        {
            for (i = 0; i < 5000; i++)
            {
                chars[i] = (char) fr.read();
                if (chars[i] == '\uFFFF')           // znak EOF
                    break;
            }

        }
        catch (IOException ioe)
        {ioe.printStackTrace();}
        finally {
            template = new String(chars);
            template = template.substring(0, i);
        }

        this.htmlSource = TEMPLATES_FOLDER + PREPARED_TEMPLATE_FOLDER + fileName + ".html";
        this.resourceFolder += PREPARED_TEMPLATE_FOLDER;
        File templateFile = new File(htmlSource);

        final String newLine = "</br>";
        final String tab = "&#09;";
        final String bullet = "&bull; ";
        final String newPoint = "<li>";
        final String newPointEnd = "</li>";

        String output = template;
        output = output.replace("@title", dish.getName());

        String ingredients = "";
        for (SimpleIngredient ingredient : dish.getIngredients())
            ingredients += newPoint + ingredient.toString() + newPointEnd;
        //    ingredients += (tab + bullet + ingredient.toString() + newLine);
        output = output.replace("@ingredients", ingredients);

        String instruction = dish.getInstruction().getInstructionContent();
        output = output.replace("@instruction", instruction);

        try (FileWriter fw = new FileWriter(templateFile))
        {
        //    output = convertPolishCharsToHTML(output);
            fw.write(output);
        }
        catch (IOException ioe)
        {ioe.printStackTrace();}
    }

    public String preparePdf(ArrayList<SimpleIngredient> ingredientList, ArrayList<Dish> dishesList)
    {
        if (shoppingListCounter++ > 0)
            this.fileName += ("_" + shoppingListCounter);
        String htmlSource = TEMPLATES_FOLDER + SHOPPING_TEMPLATE + ".html";
        this.htmlSource = htmlSource;
        prepareShoppingTemplate(ingredientList, dishesList);
        createPdf();
        return pdfDest;
    }

    private void prepareShoppingTemplate(ArrayList<SimpleIngredient> ingredients, ArrayList<Dish> dishes)
    {
        String template;
        char[] chars = new char[5000];
        int i = 0;
        try (FileReader fr = new FileReader(new File(htmlSource)))
        {
            for (i = 0; i < 5000; i++)
            {
                chars[i] = (char) fr.read();
                if (chars[i] == '\uFFFF')           // znak EOF
                    break;
            }

        }
        catch (IOException ioe)
        {ioe.printStackTrace();}
        finally {
            template = new String(chars);
            template = template.substring(0, i);
        }

        this.htmlSource = TEMPLATES_FOLDER + PREPARED_TEMPLATE_FOLDER + fileName + ".html";
        this.resourceFolder += PREPARED_TEMPLATE_FOLDER;
        File templateFile = new File(htmlSource);

        final String newLine = "</br>";
        final String tab = "&#09;";
        final String bullet = "&bull; ";
        final String newPoint = "<li>";
        final String newPointEnd = "</li>";

        String output = template;
        output = output.replace("@date", LocalDate.now().toString());

        String dishesContent = "";
        for (Dish dish : dishes)
            dishesContent += (newPoint + dish.toString() + newPointEnd);
        output = output.replace("@dishes", dishesContent);

        String ingredientsContent = "";
        for (SimpleIngredient simpleIngredient: ingredients)
            ingredientsContent += (newPoint + simpleIngredient.toString() + newPointEnd);
        output = output.replace("@ingredients", ingredientsContent);

        try (FileWriter fw = new FileWriter(templateFile))
        {
            output = convertPolishCharsToHTML(output);
            fw.write(output);
        }
        catch (IOException ioe)
        {ioe.printStackTrace();}
    }

    private void createPdf() {
        try {
            FileOutputStream outputStream = new FileOutputStream(pdfDest);

            WriterProperties writerProperties = new WriterProperties();
            //Add metadata
            writerProperties.addXmpMetadata();

            PdfWriter pdfWriter = new PdfWriter(outputStream, writerProperties);

            PdfDocument pdfDoc = new PdfDocument(pdfWriter);
            pdfDoc.getCatalog().setLang(new PdfString("pl-PL"));
            pdfDoc.getCatalog().setViewerPreferences(new PdfViewerPreferences().setDisplayDocTitle(true));

            //Set meta tags
            PdfDocumentInfo pdfMetaData = pdfDoc.getDocumentInfo();
            pdfMetaData.addCreationDate();
            pdfMetaData.getProducer();
            pdfMetaData.setCreator("iText Software");
            pdfMetaData.setKeywords("przepis");
            pdfMetaData.setSubject("Przepis z aplikacji Książka Kucharska");
            //Title is derived from html

            // pdf conversion
            ConverterProperties props = new ConverterProperties();
            FontProvider fp = new FontProvider();
       //     fp.addStandardPdfFonts();
      //      fp.addSystemFonts();
            fp.addDirectory(resourceFolder);//The noto-nashk font file (.ttf extension) is placed in the resources

            props.setFontProvider(fp);
            props.setBaseUri(resourceFolder);

            HtmlConverter.convertToPdf(new FileInputStream(htmlSource), pdfDoc, props);
            pdfDoc.close();
            properlyCreated = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (properlyCreated)
                wasProperlyCreatedAlert();
            else
                notCreatedAlert();
        }
    }

    private String convertPolishCharsToHTML(String s)
    {
        final String polishChars = "ą";//{, "ć", "ę", "ł", "ń", "ó", "ś", "ź", "ż"};
        String x = "&#261;"; // http://www.thesauruslex.com/typo/eng/enghtml.htm
        s = s.replace(polishChars, x);
        return s;
    }

    public boolean isProperlyCreated() {
        return properlyCreated;
    }

    public void wasProperlyCreatedAlert()
    {
        Alert properlyCreated = new Alert(Alert.AlertType.INFORMATION);
        properlyCreated.setTitle("Sukces!");
        properlyCreated.setHeaderText("");
        properlyCreated.setContentText("Plik PDF z przepisem został pomyślnie utworzony!\n" + pdfDest + "\nDrukowanie...");
        properlyCreated.show();
    }

    public void notCreatedAlert()
    {
        Alert notCreated = new Alert(Alert.AlertType.ERROR);
        notCreated.setTitle("Błąd!");
        notCreated.setHeaderText("");
        notCreated.setContentText("Wystąpił nieoczekiwany błąd! Plik PDF z przepisem nie został utworzony!");
        notCreated.show();
    }
}
