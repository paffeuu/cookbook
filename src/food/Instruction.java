package food;

public abstract class Instruction {
    private String instructionContent;

    protected Instruction()
    {}

    protected Instruction(String instructionContent)
    {
        this.instructionContent = instructionContent;
    }

    public String getInstructionContent() {
        return instructionContent;
    }

    public void setInstructionContent(String instructionContent) {
        this.instructionContent = instructionContent;
    }
}