package de.arthurpicht.cli.command;

public class Test {

    private static void test(Command command) {
        System.out.println("in test: " + command.toString());
        command = new OneCommand(null, "new");
        System.out.println("in test: " + command.toString());
    }

    public static void main(String[] args) {

        Command command = new OneCommand(null, "initial");
        System.out.println("in main pre: " + command.toString());
        test(command);
        System.out.println("in main post: " + command.toString());

    }


}
