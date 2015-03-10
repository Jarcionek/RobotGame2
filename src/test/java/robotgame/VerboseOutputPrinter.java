package robotgame;

import robotgame.io.OutputPrinter;

public class VerboseOutputPrinter extends OutputPrinter {

    private final String name;

    public VerboseOutputPrinter(String name) {
        this.name = name;
    }

    @Override
    public void print(String s) {
        System.out.println(String.format("%s.print(\"%s\");", name, s.replaceAll("\\n", "\" \\+ \"\\\\n\" \\+\n\t\t\"")));
    }

    @Override
    public void println(String x) {
        System.out.println(String.format("%s.println(\"%s\");", name, x.replaceAll("\\n", "\" \\+ \"\\\\n\" \\+\n\t\t\"")));
    }

}
