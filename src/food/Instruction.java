package food;

public class Instruction {
    private String instructionContent;

    public Instruction(String instructionContent)
    {
        this.instructionContent = instructionContent;
    }

    @Override
    public String toString() {
        return "Sposób przygotowania: " + instructionContent;
    }

    public String getInstructionContent() {
        return instructionContent;
    }

    public void setInstructionContent(String instructionContent) {
        this.instructionContent = instructionContent;
    }
}