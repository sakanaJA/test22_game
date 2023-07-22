package sample;

public class Debug {
    public static void main(String[] args) {
        System.out.println("a");
        System.out.println("java引数　→ " + args[0]);
        String vmarg = System.getProperty("vmarg");
        System.out.println("VM引数 → " + vmarg);
    }
}