package pl.mantiscrab;

import org.junit.Assert;
import org.junit.Test;

public class SolutionTest {
    @Test
    public void basicTests() {
        Assert.assertEquals("alpha beta gamma delta", Solution.removeDuplicateWords("alpha beta beta gamma gamma gamma delta alpha beta beta gamma gamma gamma delta"));
        Assert.assertEquals("my cat is fat", Solution.removeDuplicateWords("my cat is my cat fat"));
    }
}