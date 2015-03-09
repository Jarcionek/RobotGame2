package robotgame;

public class VerboseProgramTerminator extends ProgramTerminator {

    private final String name;

    public VerboseProgramTerminator(String name) {
        this.name = name;
    }

    @Override
    public void exit() {
        System.out.println(String.format("%s.exit();", name));
        throw new TestPassed();
    }

}
