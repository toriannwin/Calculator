/**
 * Tori Windrich
 * 2/18/2018
 * Project: Calculator
 */
package Calculator;

public class Number 
{
    protected double realNum = 0;
    
    //creates a number by assigning the passed in double to realNum
    public Number(double num)
    {
        realNum = num;
    }
    //returns the real number
    public double getReal()
    {
        return realNum;
    }
    //changes the real number
    public void setReal(double num)
    {
        realNum = num;
    }
    //overrides toString and returns the formatted real number
    @Override
    public String toString()
    {
        return String.format("%.2f", realNum);
    }
    //overrides equals, if the object is a number, returns if the real number
    //of it is equal to the real number in the current object. Returns false
    //if it's not a Number
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Number)
        {
            return ((Number)(obj)).getReal() == realNum;
        }
        else
            return false;
    }
}
