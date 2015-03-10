package robotgame;

import robotgame.io.PopUp;

public class VerbosePopUp extends PopUp {

    private final String name;

    public VerbosePopUp(String name) {
        this.name = name;
    }

    @Override
    public void show(String message, String title) {
        System.out.println(String.format("%s.show(\"%s\", \"%s\");", name, message.replaceAll("\\n", "\" \\+ \"\\\\n\" \\+\n\t\t\""), title));
    }

}
