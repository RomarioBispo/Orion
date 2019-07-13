package br.com.ufs.orionframework.usersguide;

public class TesteLambdas {
    public static void main(String args[]) {
        lambdas Mylambda = s -> s.length();
       int n = Mylambda.func("romario");
       System.out.println(n);
    }
    @FunctionalInterface
    public interface lambdas {
        int func(String s);
    }
}
