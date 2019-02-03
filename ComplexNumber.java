/**
 * Tori Windrich
 * 2/18/2018
 * Project: Calculator
 */
package Calculator;

public class ComplexNumber extends Number 
{
    private double imaginaryNum = 0;
    
    //creates a complex number by calling the super constructor for the real number
    //and assigning the imaginary number accordingly
    public ComplexNumber(double num1, double num2)
    {
        super(num1);
        imaginaryNum = num2;
    }
    //returns imaginary number
    public double getImaginary()
    {
        return imaginaryNum;
    }
    //changes the imaginary number
    public void setImaginary(double num)
    {
        imaginaryNum = num;
    }
    //overrides toString, prints out the proper format for negative imaginary numbers,
    //positive imaginary numbers, imaginary numbers without real numbers, or 
    //complex numbers without an imaginary component. Utilizes super.toString
    //to print out the real number. (Also formats the number to 2 decimal points)
    @Override
    public String toString()
    {
        if (imaginaryNum > 0)
            return super.toString() + "+" + String.format("%.2fi", imaginaryNum);
        else if (imaginaryNum < 0)
            return super.toString() + String.format("%.2fi", imaginaryNum);
        else if (realNum == 0)
            return String.format("%.2fi", imaginaryNum);
        else
            return super.toString();
    }
    //if the passed in object is a complex number, returns if the real number
    //and imaginary number are equal. Returns false if it's not a complex number
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof ComplexNumber)
        {
            return (((ComplexNumber)(obj)).getReal() == super.getReal())
                    && (((ComplexNumber)(obj)).getImaginary() == imaginaryNum);
        }
        else
            return false;
    }
}
