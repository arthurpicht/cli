package de.arthurpicht.cli.common;

public class ArgumentIterator {

    private final Arguments arguments;
    private int index;

    public ArgumentIterator(String[] args) {
        this.arguments = new Arguments(args);
        this.index = -1;
    }

    public ArgumentIterator(String[] args, int initialIndex) {
        if (initialIndex < -1 || initialIndex >= args.length)
            throw new IllegalArgumentException("Initial Index out of bounds: " + initialIndex);
        this.arguments = new Arguments(args);
        this.index = initialIndex;
    }

    public ArgumentIterator(Arguments arguments) {
        this.arguments = arguments;
        this.index = -1;
    }

    public void reset() {
        this.index = -1;
    }

    public boolean hasNext() {
        return this.arguments.size() > this.index + 1;
    }

    public String getNext() {
        this.index++;
        return this.arguments.get(this.index);
    }

    public String getCurrent() {
        if (this.index < 0) throw new IllegalStateException("First element of ArgumentIterator not obtained.");
        return this.arguments.get(this.index);
    }

    public boolean hasPrevious() {
        return this.index > 0;
    }

    @SuppressWarnings("UnusedReturnValue")
    public String getPrevious() {
        this.index--;
        return this.arguments.get(this.index);
    }

    public int getIndex() {
        return this.index;
    }

    public Arguments getArguments() {
        return this.arguments;
    }

}
