package Generics;

public class StringContainer implements Container<String>{
    private String item;
    public StringContainer(String item) {
        this.item = item;
    }

    @Override
    public void add(String item) {
        this.item = item;
    }

    @Override
    public String get() {
        return item;
    }
}
