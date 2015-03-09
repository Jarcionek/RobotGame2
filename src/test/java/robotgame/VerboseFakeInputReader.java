package robotgame;

import java.util.Stack;

public class VerboseFakeInputReader extends InputReader {

    private final String name;
    private final Stack<String> stack = new Stack<>();

    public VerboseFakeInputReader(String name) {
        this.name = name;
    }

    @Override
    public String next() {
        String value = stack.pop();
        System.out.println(String.format("%s.next(); // -> \"%s\"", name, value));
        return value;
    }

    @Override
    public String nextLine() {
        String value = stack.pop();
        System.out.println(String.format("%s.nextLine(); // -> \"%s\"", name, value));
        return value;
    }

    public VerboseFakeInputReader willReturn(String value) {
        stack.add(0, value);
        return this;
    }

}
