package food.instructions;

import food.Instruction;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class FileInstruction extends Instruction {
    private File instructionFile;

    public FileInstruction(File instructionFile)
    {
        this.instructionFile = instructionFile;
        setInstructionContent();
    }

    public File getInstructionFile() {
        return instructionFile;
    }

    public void setInstructionFile(File instructionFile) {
        this.instructionFile = instructionFile;
    }

    public void setInstructionContent() {
        StringBuilder input = new StringBuilder();
        try(Scanner scan = new Scanner(new FileInputStream(instructionFile)))
        {
            while(scan.hasNextLine())
                input.append(scan.nextLine() + "\n");
            super.setInstructionContent(input.toString());
        }
        catch (IOException ioe)
        {Alert fileNotFoundAlert = new Alert(Alert.AlertType.ERROR);
        fileNotFoundAlert.setContentText("Nie znaleziono pliku!");
        fileNotFoundAlert.show();}
    }
}
