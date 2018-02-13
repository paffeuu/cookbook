package main.utilities;

import com.qoppa.pdfPrint.PDFPrint;

public class PDFPrinter {
    private final String pdfDest;

    public PDFPrinter(String pdfDest)
    {
        this.pdfDest = pdfDest;
    }

    public void print()
    {
        try
        {
            PDFPrint pdfPrint = new PDFPrint(pdfDest, null);
            pdfPrint.print(null);
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }
}
