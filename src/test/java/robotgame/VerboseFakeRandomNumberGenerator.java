package robotgame;

import java.util.Stack;

public class VerboseFakeRandomNumberGenerator extends RandomNumberGenerator {

    private final String name;
    private final Stack<Integer> stack = new Stack<>();

    public VerboseFakeRandomNumberGenerator(String name) {
        this.name = name;
    }

    @Override
    public int nextInt(int bound) {
        Integer value = stack.pop();
        System.out.println(String.format("%s.nextInt(%s); // -> %s", name, bound, value));
        return value;
    }

    public VerboseFakeRandomNumberGenerator willReturn(int value) {
        stack.add(0, value);
        return this;
    }

}
