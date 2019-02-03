/**
 * Tori Windrich
 * 2/18/2018
 * Project: Calculator
 */
package Calculator;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;

public class Main 
{
    //main function opens the proper files, performing file checks, creates two
    //objects parameters that will be assigned to numbers or complexNumbers later.
    //Goes through the file line by line, validates the line, if it's valid, it will
    //create the proper type of object, and perform the proper calculation and print
    //to the output file using a the proper function
    public static void main(String[] args) throws FileNotFoundException 
    {
        //opening input file and validating that it was open successfully
        File fileInput = new File("expressions.txt");
        if (!fileInput.exists() || !fileInput.canRead())
        {
            System.out.println("The input file was not opened successfully.");
            System.exit(1);
        }
        //open output file with printWriter and input file with Scanner
        File fileOutput = new File("results.txt");
        PrintWriter output = new PrintWriter(fileOutput);
        if(!fileOutput.canWrite())
        {
            System.out.println("The output file was not created successfully.");
            System.exit(1);
        }
        Scanner input = new Scanner(fileInput);
        //expression string to store from file and two objects to use for calculations
        String expression;
        Number currentNum1;
        Number currentNum2;
        //while the file still has expressions...
        while(input.hasNextLine())
        {
            //get the next expression from the file
            expression = input.nextLine();
            //as long as the string isn't empty (if it is, skip it)
            if(expression.compareTo("") != 0)
            {
                //if the expression is valid
                if(isValid(expression))
                {
                    //print the original expression to the output file, formatted
                    output.printf("%-20s\t\t",expression);
                    //if the number has an i (is complex), call the getComplex method to parse it
                    if ((expression.substring(0, expression.indexOf(" "))).contains("i"))
                    {
                        currentNum1 = getComplex(expression.substring(0, expression.indexOf(" ")));
                    }
                    //if the number is just a real number, declare it by parsing the expression as a double
                    else
                    {
                        currentNum1 = new Number(Double.parseDouble(expression.substring(0, expression.indexOf(" "))));
                    }
                    //if the number has an i (is complex), call the getComplex method to parse it
                    if ((expression.substring(expression.lastIndexOf(" "))).contains("i"))
                    {
                        currentNum2 = getComplex(expression.substring(expression.lastIndexOf(" ")+1));
                    }
                    //if the number is just a real number, declare it by parsing the expression as a double
                    else
                    {
                        currentNum2 = new Number(Double.parseDouble(expression.substring(expression.lastIndexOf(" ")+1)));
                    }
                    //switching the operator between the two spaces to determine the operation that must take place
                    switch(expression.substring(expression.indexOf(" ")+1, expression.lastIndexOf(" ")))
                    {
                        //addition
                        case "+":
                            addNumbers(currentNum1, currentNum2, output);
                            break;
                        //subtraction
                        case "-":
                            subNumbers(currentNum1, currentNum2, output);
                            break;
                        //multiplication
                        case "*":
                            multNumbers(currentNum1, currentNum2, output);
                            break;
                        //division
                        case "/":
                            divideNumbers(currentNum1, currentNum2, output);
                            break;
                        //less than
                        case "<":
                            lessThan(currentNum1, currentNum2, output);
                            break;
                        //greater than
                        case ">":
                            greaterThan(currentNum1, currentNum2, output);
                            break;
                        //equal to
                        case "=":
                            equalTo(currentNum1, currentNum2, output);
                            break;
                        //not equal to  
                        case "/=":
                            notEqualTo(currentNum1, currentNum2, output);
                            break;
                    }
                    //prints an endline at the end of the newly printed result
                    output.println();
                }
            }
        }
        //closes the input and output files
        input.close();
        output.close();
    }
    
    //addNumbers determines which type of objects the two numbers are, and performs the
    //proper addition, creating a new number, and using toString from the apppropriate object
    //will write the result to the output file
    public static void addNumbers(Object one, Object two, PrintWriter output)
    {
        //if both numbers are just REAL NUMBERS
        if (one instanceof Number && !(one instanceof ComplexNumber) && two instanceof Number && !(two instanceof ComplexNumber))
        {
            //create a new Number by adding the real components of both numbers together, and print to the output file
            Number newNum = new Number(((Number)one).getReal() + ((Number)two).getReal());
            output.printf("%-10s\n",newNum.toString());
        }
        //if the first number is a REAL NUMBER and the second is a COMPLEX NUMBER
        else if (one instanceof Number && !(one instanceof ComplexNumber) && two instanceof ComplexNumber)
        {
            //create a new ComplexNumber by adding the real components of both, and using the imaginary component from the second
            //and print to output file
            ComplexNumber newNum = new ComplexNumber(((Number)one).getReal() + ((ComplexNumber)two).getReal(), ((ComplexNumber)two).getImaginary());
            output.printf("%-10s\n",newNum.toString());
        }
        //if the first number is a COMPLEX NUMBER and the second is a REAL NUMBER
        else if (one instanceof ComplexNumber && two instanceof Number && !(two instanceof ComplexNumber))
        {
            //create a new ComplexNumber by adding the real components of both, and using the imaginary component from the first
            //and print to output file
            ComplexNumber newNum = new ComplexNumber(((ComplexNumber)one).getReal() + ((Number)two).getReal(), ((ComplexNumber)one).getImaginary());
            output.printf("%-10s\n",newNum.toString());
        }
        //if both of the numbers are COMPLEX NUMBERS
        else if (one instanceof ComplexNumber && two instanceof ComplexNumber)
        {
            //create a new ComplexNumber by adding the real components of both, and adding the
            //imaginary components of both, and print to output file
            ComplexNumber newNum = new ComplexNumber(((ComplexNumber)one).getReal() + ((ComplexNumber)two).getReal(), ((ComplexNumber)one).getImaginary() + ((ComplexNumber)two).getImaginary());
            output.printf("%-10s\n",newNum.toString());
        }
    }
    //subNumbers determines which type of objects the two numbers are, and performs the
    //proper subtraction, creating a new number, and using toString from the apppropriate object
    //will write the result to the output file
    public static void subNumbers(Object one, Object two, PrintWriter output)
    {
        //if both numbers are REAL NUMBERS
        if (one instanceof Number && !(one instanceof ComplexNumber) && two instanceof Number && !(two instanceof ComplexNumber))
        {
            //create a new Number by subtracting the real components from each other, print to output file
            Number newNum = new Number(((Number)one).getReal() - ((Number)two).getReal());
            output.printf("%-10s\n",newNum.toString());
        }
        //if the first number is a REAL NUMBER and the second number is a COMPLEX NUMBER
        else if (one instanceof Number && !(one instanceof ComplexNumber) && two instanceof ComplexNumber)
        {
            //create a new ComplexNumber by subtracting the real components and using the imaginary component from the second number
            //print to output file
            ComplexNumber newNum = new ComplexNumber(((Number)one).getReal() - ((ComplexNumber)two).getReal(), ((ComplexNumber)two).getImaginary());
            output.printf("%-10s\n",newNum.toString());
        }
        //if the first number is a COMPLEX NUMBER and the second number is a REAL NUMBER
        else if (one instanceof ComplexNumber && two instanceof Number && !(two instanceof ComplexNumber))
        {
            //create a new ComplexNumber by subtracting the real components and using the imaginary component from the first number
            //print to output file
            ComplexNumber newNum = new ComplexNumber(((ComplexNumber)one).getReal() - ((Number)two).getReal(), ((ComplexNumber)one).getImaginary());
            output.printf("%-10s\n",newNum.toString());
        }
        //if both numbers are COMPLEX NUMBERS
        else if (one instanceof ComplexNumber && two instanceof ComplexNumber)
        {
            //create a new ComplexNumber by subtracting the real components and subtracting the imaginary components
            //print to output file
            ComplexNumber newNum = new ComplexNumber(((ComplexNumber)one).getReal() - ((ComplexNumber)two).getReal(), ((ComplexNumber)one).getImaginary() - ((ComplexNumber)two).getImaginary());
            output.printf("%-10s\n",newNum.toString());
        }
    }
    //multNumbers determines which type of objects the two numbers are, and performs the
    //proper multiplication, creating a new number, and using toString from the apppropriate object
    //will write the result to the output file
    public static void multNumbers(Object one, Object two, PrintWriter output)
    {
        //if both numbers are REAL NUMBERS
        if (one instanceof Number && !(one instanceof ComplexNumber) && two instanceof Number && !(two instanceof ComplexNumber))
        {
            //create a new Number by multiplying the real components from both numbers, print to output file
            Number newNum = new Number(((Number)one).getReal() * ((Number)two).getReal());
            output.printf("%-10s\n",newNum.toString());
        }
        //if the first number is a REAL NUMBER and the second number is a COMPLEX NUMBER
        else if (one instanceof Number && !(one instanceof ComplexNumber) && two instanceof ComplexNumber)
        {
            //create a new ComplexNumber by multiplying the components of the second number by the real first number
            //print to output file
            ComplexNumber newNum = new ComplexNumber(((Number)one).getReal() * ((ComplexNumber)two).getReal(), ((Number)one).getReal() * ((ComplexNumber)two).getImaginary());
            output.printf("%-10s\n",newNum.toString());
        }
        //if the first number is a COMPLEX NUMBER and the second number is a REAL NUMBER
        else if (one instanceof ComplexNumber && two instanceof Number && !(two instanceof ComplexNumber))
        {
            //create a new ComplexNumber by multiplying the components of the first number by the real second number
            //print to output file
            ComplexNumber newNum = new ComplexNumber(((Number)two).getReal() * ((ComplexNumber)one).getReal(), ((Number)two).getReal() * ((ComplexNumber)one).getImaginary());
            output.printf("%-10s\n",newNum.toString());
        }
        //if both of the numbers are COMPLEX NUMBERS
        else if (one instanceof ComplexNumber && two instanceof ComplexNumber)
        {
            //create a new ComplexNumber by using the FOIL technique... Essentially if
            //the multiplication is represented by (a+bi)(c+di), the formula
            //being used for final values is: (ac-bd) + (ad+bc)i
            //print to output file
            double realNum = ((ComplexNumber)one).getReal() * ((ComplexNumber)two).getReal();
            realNum -= ((ComplexNumber)one).getImaginary() * ((ComplexNumber)two).getImaginary();
            double imagNum = ((ComplexNumber)one).getReal() * ((ComplexNumber)two).getImaginary();
            imagNum += ((ComplexNumber)one).getImaginary() * ((ComplexNumber)two).getReal();
            ComplexNumber newNum = new ComplexNumber(realNum,imagNum);
            output.printf("%-10s\n", newNum.toString());
        }
    }
    //divideNumbers determines which type of objects the two numbers are, and performs the
    //proper division, creating a new number, and using toString from the apppropriate object
    //will write the result to the output file
    public static void divideNumbers(Object one, Object two, PrintWriter output)
    {
        //if both of the numbers are REAL NUMBERS
        if (one instanceof Number && !(one instanceof ComplexNumber) && two instanceof Number && !(two instanceof ComplexNumber))
        {
            //create a new Number by dividing the real components of both, prints to output file
            Number newNum = new Number(((Number)one).getReal() / ((Number)two).getReal());
            output.printf("%-10s\n",newNum.toString());
        }
        //if the first number is a REAL NUMBER and the second number is a COMPLEX NUMBER
        else if (one instanceof Number && !(one instanceof ComplexNumber) && two instanceof ComplexNumber)
        {
            //if the complex number has both components as nonzero values
            if (((ComplexNumber)two).getReal() != 0)
            {
                //create a new ComplexNumber by multiplying by the conjugate, essentially using the formula
                //where if dividing (a)/(c+di) = [(ac)/(c^2+d^2)] - [(ad)/(c^2+d^2)]i
                //prints to output file
                double realNum = ((Number)one).getReal() * ((ComplexNumber)two).getReal();
                realNum /= Math.pow(((ComplexNumber)two).getReal(),2) + Math.pow(((ComplexNumber)two).getImaginary(),2);
                double imagNum = -1 * (((Number)one).getReal() * ((ComplexNumber)two).getImaginary());
                imagNum /= Math.pow(((ComplexNumber)two).getReal(),2) + Math.pow(((ComplexNumber)two).getImaginary(),2);
                ComplexNumber newNum = new ComplexNumber(realNum,imagNum);
                output.printf("%-10s\n",newNum.toString());
            }
            //if the complex number has no real component (it's just imaginary)
            else
            {
                //real number will be zero, and imaginary number will be -1 times the division of the real component
                //of one, and the imaginary number of two
                //prints to output file
                double imagNum = -1 * (((Number)one).getReal() / ((ComplexNumber)two).getImaginary());
                ComplexNumber newNum = new ComplexNumber(0,imagNum);
                output.printf("%-10s\n",newNum.toString());
            }
        }
        //if the first number is a COMPLEX NUMBER and the second number is a REAL NUMBER
        else if (one instanceof ComplexNumber && two instanceof Number && !(two instanceof ComplexNumber))
        {
            //creates a new ComplexNumber by dividing the real and imaginary component of the first number by the real component of the second number
            //prints to output file
            ComplexNumber newNum = new ComplexNumber(((ComplexNumber)one).getReal()/((Number)two).getReal(), ((ComplexNumber)one).getImaginary()/((Number)two).getReal());
            output.printf("%-10s\n",newNum.toString());
        }
        //if both numbers are COMPLEX NUMBERS
        else if (one instanceof ComplexNumber && two instanceof ComplexNumber)
        {
            //create a new ComplexNumber by multiplying by the conjugate, essentially using the formula
            //where if dividing (a+bi)/(c+di) = [(ac+bd)/(c^2+d^2)] - [(bc-ad)/(c^2+d^2)]i
            //prints to output file
            double realNum = ((ComplexNumber)one).getReal() * ((ComplexNumber)two).getReal();
            realNum += ((ComplexNumber)one).getImaginary() * ((ComplexNumber)two).getImaginary();
            realNum /= Math.pow(((ComplexNumber)two).getReal(),2) + Math.pow(((ComplexNumber)two).getImaginary(),2);
            double imagNum = ((ComplexNumber)one).getImaginary() * ((ComplexNumber)two).getReal();
            imagNum -= ((ComplexNumber)one).getReal() * ((ComplexNumber)two).getImaginary();
            imagNum /= Math.pow(((ComplexNumber)two).getReal(),2) + Math.pow(((ComplexNumber)two).getImaginary(),2);
            ComplexNumber newNum = new ComplexNumber(realNum,imagNum);
            output.printf("%-10s\n",newNum);
        }
    }
    //lessThan determines which type of objects the two numbers are, and performs the
    //proper comparison, writing the result to the output file (true or false)
    public static void lessThan(Object one, Object two, PrintWriter output)
    {
        //if both numbers are REAL NUMBERS
        if (one instanceof Number && !(one instanceof ComplexNumber) && two instanceof Number && !(two instanceof ComplexNumber))
        {
            //if one is smaller than two, print out true, otherwise, false
            if (((Number)one).getReal() < (((Number)two).getReal()))
                output.printf("%-10s\n", "true");
            else
                output.printf("%-10s\n", "false");
        }
        //if the first number is a REAL NUMBER and the second number is a COMPLEX NUMBER
        else if (one instanceof Number && !(one instanceof ComplexNumber) && two instanceof ComplexNumber)
        {
            //get the magnitude of the second number (which is essentially (a^2+b^2)^(1/2) )
            //if the first number is smaller than the second's magnitude, print out true, otherwise false
            double mag2 = Math.pow(Math.pow(((ComplexNumber)two).getReal(),2) + Math.pow(((ComplexNumber)two).getImaginary(),2), .5);
            if (((Number)one).getReal() < mag2)
                output.printf("%-10s\n", "true");
            else
                output.printf("%-10s\n", "false");
        }
        //if the first number is a COMPLEX NUMBER and the second number is a REAL NUMBER
        else if (one instanceof ComplexNumber && two instanceof Number && !(two instanceof ComplexNumber))
        {
            //get the magnitude of the first number (which is essentially (a^2+b^2)^(1/2) )
            //if the first's magnitude is smaller than the second number, print out true, otherwise false
            double mag1 = Math.pow(Math.pow(((ComplexNumber)one).getReal(),2) + Math.pow(((ComplexNumber)one).getImaginary(),2), .5);
            if (mag1 < ((Number)two).getReal())
                output.printf("%-10s\n", "true");
            else
                output.printf("%-10s\n", "false");
        }
        //if both numbers are COMPLEX NUMBERS
        else if (one instanceof ComplexNumber && two instanceof ComplexNumber)
        {
            //get the magnitude of the both numbers (which is essentially (a^2+b^2)^(1/2) )
            //if the first's magnitude is smaller than the second's, print out true, otherwise false
            double mag1 = Math.pow(Math.pow(((ComplexNumber)one).getReal(),2) + Math.pow(((ComplexNumber)one).getImaginary(),2), .5);
            double mag2 = Math.pow(Math.pow(((ComplexNumber)two).getReal(),2) + Math.pow(((ComplexNumber)two).getImaginary(),2), .5);
            if (mag1 < mag2)
                output.printf("%-10s\n", "true");
            else
                output.printf("%-10s\n", "false");
        }
    }
    //greaterThan determines which type of objects the two numbers are, and performs the
    //proper comparison, writing the result to the output file (true or false)
    public static void greaterThan(Object one, Object two, PrintWriter output)
    {
        //if both numbers are REAL NUMBERS
        if (one instanceof Number && !(one instanceof ComplexNumber) && two instanceof Number && !(two instanceof ComplexNumber))
        {
            //if one is bigger than two, print out true, otherwise false
            if (((Number)one).getReal() > (((Number)two).getReal()))
                output.printf("%-10s\n", "true");
            else
                output.printf("%-10s\n", "false");
        }
        //if the first number is a REAL NUMBER and the second number is a COMPLEX NUMBER
        else if (one instanceof Number && !(one instanceof ComplexNumber) && two instanceof ComplexNumber)
        {
             //get the magnitude of the second number (which is essentially (a^2+b^2)^(1/2) )
            //if the first number is bigger than the second's magnitude, print out true, otherwise false
            double mag2 = Math.pow(Math.pow(((ComplexNumber)two).getReal(),2) + Math.pow(((ComplexNumber)two).getImaginary(),2), .5);
            if (((Number)one).getReal() > mag2)
                output.printf("%-10s\n", "true");
            else
                output.printf("%-10s\n", "false");
        }
        //if the first number is a COMPLEX NUMBER and the second number is a REAL NUMBER
        else if (one instanceof ComplexNumber && two instanceof Number && !(two instanceof ComplexNumber))
        {
            //get the magnitude of the first number (which is essentially (a^2+b^2)^(1/2) )
            //if the first's magnitude is bigger than the second number, print out true, otherwise false
            double mag1 = Math.pow(Math.pow(((ComplexNumber)one).getReal(),2) + Math.pow(((ComplexNumber)one).getImaginary(),2), .5);
            if (mag1 > ((Number)two).getReal())
                output.printf("%-10s\n", "true");
            else
                output.printf("%-10s\n", "false");
        }
        //if both numbers are COMPLEX NUMBERS
        else if (one instanceof ComplexNumber && two instanceof ComplexNumber)
        {
            //get the magnitude of the both numbers (which is essentially (a^2+b^2)^(1/2) )
            //if the first's magnitude is bigger than the second's, print out true, otherwise false
            double mag1 = Math.pow(Math.pow(((ComplexNumber)one).getReal(),2) + Math.pow(((ComplexNumber)one).getImaginary(),2), .5);
            double mag2 = Math.pow(Math.pow(((ComplexNumber)two).getReal(),2) + Math.pow(((ComplexNumber)two).getImaginary(),2), .5);
            if (mag1 > mag2)
                output.printf("%-10s\n", "true");
            else
                output.printf("%-10s\n", "false");
        }
    }
    //equalTo determines which type of objects the two numbers are, and performs the
    //proper comparison, writing the result to the output file (true or false)
    public static void equalTo(Object one, Object two, PrintWriter output)
    {
        //if both numbers are REAL NUMBERS
        if (one instanceof Number && !(one instanceof ComplexNumber) && two instanceof Number && !(two instanceof ComplexNumber))
        {
            //if one's real number equals two's, print out true, otherwise false
            if (((Number)one).getReal() == (((Number)two).getReal()))
                output.printf("%-10s\n", "true");
            else
                output.printf("%-10s\n", "false");
        }
        //if the first number is a REAL NUMBER and the second number is a COMPLEX NUMBER
        else if (one instanceof Number && !(one instanceof ComplexNumber) && two instanceof ComplexNumber)
        {
            //if the two real components equal and the second number's imaginary component is zero, print true, otherwise false
            if (((Number)one).getReal() != ((ComplexNumber)two).getReal() || ((ComplexNumber)two).getImaginary() != 0)
                output.printf("%-10s\n", "false");
            else
                output.printf("%-10s\n", "true");
        }
        //if the first number is a COMPLEX NUMBER and the second number is a REAL NUMBER
        else if (one instanceof ComplexNumber && two instanceof Number && !(two instanceof ComplexNumber))
        {
            //if the two real components equal and the first number's imaginary component is zero, print true, otherwise false
            if (((Number)two).getReal() != ((ComplexNumber)one).getReal() || ((ComplexNumber)one).getImaginary() != 0)
                output.printf("%-10s\n", "false");
            else
                output.printf("%-10s\n", "true");
        }
        //if both numbers are COMPLEX NUMBERS
        else if (one instanceof ComplexNumber && two instanceof ComplexNumber)
        {
            //if the two real components equal and the two imaginary components equal, print true, otherwise false
            if (((ComplexNumber)one).getReal() != ((ComplexNumber)two).getReal() || ((ComplexNumber)one).getImaginary() != ((ComplexNumber)two).getImaginary())
                output.printf("%-10s\n","false");
            else
                output.printf("%-10s\n","true");
        }
    }
    //notEqualTo determines which type of objects the two numbers are, and performs the
    //proper comparison, writing the result to the output file (true or false)
    public static void notEqualTo(Object one, Object two, PrintWriter output)
    {
        //if both numbers are REAL NUMBERS
        if (one instanceof Number && !(one instanceof ComplexNumber) && two instanceof Number && !(two instanceof ComplexNumber))
        {
            //if the two real components equal, print false, otherwise true
            if (((Number)one).getReal() == (((Number)two).getReal()))
                output.printf("%-10s\n", "false");
            else
                output.printf("%-10s\n", "true");
        }
        //if the first number is a REAL NUMBER and the second number is a COMPLEX NUMBER
        else if (one instanceof Number && !(one instanceof ComplexNumber) && two instanceof ComplexNumber)
        {
            //if the two real components are equal and the imaginary component of the second number is zero, print false, otherwise true
            if (((Number)one).getReal() == ((ComplexNumber)two).getReal() && ((ComplexNumber)two).getImaginary() == 0)
                output.printf("%-10s\n", "false");
            else
                output.printf("%-10s\n", "true");
        }
        //if the first number is a COMPLEX NUMBER and the second number is a REAL NUMBER
        else if (one instanceof ComplexNumber && two instanceof Number && !(two instanceof ComplexNumber))
        {
            //if the two real components are equal and the imaginary component of the first number is zero, print false, otherwise true
            if (((Number)two).getReal() == ((ComplexNumber)one).getReal() && ((ComplexNumber)one).getImaginary() == 0)
                output.printf("%-10s\n", "false");
            else
                output.printf("%-10s\n", "true");
        }
        //if both numbers are COMPLEX NUMBERS
        else if (one instanceof ComplexNumber && two instanceof ComplexNumber)
        {
            //if the two real components are equal and the imaginary components are equal, print false, otherwise true
            if (((ComplexNumber)one).getReal() == ((ComplexNumber)two).getReal() && ((ComplexNumber)one).getImaginary() == ((ComplexNumber)two).getImaginary())
                output.printf("%-10s\n","false");
            else
                output.printf("%-10s\n","true");
        }
    }
    
    //converts the passed in string to a complex number by parsing to the plus
    //or minus sign. If it's actually just an imaginary number (i.e. there is no
    //plus sign and any minus sign is not at position 0), accounts for that as well.
    //returns the complex number
    public static ComplexNumber getComplex(String num)
    {
        double realNum, imNum;
        //if the number contains a plus sign (this means it's definitely complex)
        if(num.contains("+"))
        {
            //divide up around the plus and excluding the i and parse to double
            realNum = Double.parseDouble(num.substring(0,num.indexOf("+")));
            imNum = Double.parseDouble(num.substring(num.indexOf("+")+1,num.indexOf("i")));
        }
        //if the number contains a minus that's not the first character (this means it's definitely complex)
        else if (num.lastIndexOf("-") > 0)
        {
            //divide up around the last minus and excluding the i and parse to double
            realNum = Double.parseDouble(num.substring(0,num.indexOf("-")));
            imNum = Double.parseDouble(num.substring(num.indexOf("-"),num.indexOf("i")));
        }
        //otherwise, it's just an imaginary number
        else
        {
            //so make realNum zero and parse the number up to i for imaginary number
            realNum = 0;
            imNum = Double.parseDouble(num.substring(0,num.indexOf("i")));
        }
        //use the found values to create a new complex number and return it
        ComplexNumber compNum = new ComplexNumber(realNum,imNum);
        return compNum;
    }
    
    //checks if the passed in string is a valid expression by first checking that the string
    //isn't empty, then counting up the number of spaces and i's (there must be exactly 2 spaces and
    //no more than 2 i's). If those checks are failed, false is returned. If those checks are passed,
    //breaks down the line and checks if the operator in between the spaces are valid. If not, returns false.
    //If passed, it then breaks down each individual number and determines if they're valid numbers
    public static boolean isValid(String line)
    {
        if (line.compareTo("") == 0)
            return false;
        int numSpaces = 0;
        int numI = 0;
        //checking the number of spaces and number of i's
        for(int i = 0; i < line.length(); i++)
        {
            if (line.charAt(i) == ' ')
            {
                numSpaces++;
            }
            else if (line.charAt(i) == 'i')
            {
                numI++;
            }
        }
        if (numSpaces != 2)
            return false;
        if (numI > 2)
            return false;
        //isolating the operator and switching it
        String operator = line.substring(line.indexOf(" ") + 1, line.lastIndexOf(" "));
        //switching the length (if it's 2, it can only be /=, if it's 1, switches again for specifics,
        //if it's not 1 or 2, return false
        switch (operator.length()) {
            case 2:
                if (operator.compareTo("/=") != 0)
                    return false;
                break;
            case 1:
                //switching the single character operators
                switch (operator)
                {
                    case "+":break;
                    case "-":break;
                    case "*":break;
                    case "/":break;
                    case ">":break;
                    case "<":break;
                    case "=":break;
                    default: return false;
                }   break;
            default:
                return false;
        }
        //breaking up the line into two numbers
        String num;
        String firstNum = line.substring(0,line.indexOf(" "));
        String secondNum = line.substring(line.lastIndexOf(" ") + 1);
        String currentNumber = firstNum;
        //loops twice to perform the same checks on both sides of the operator
        for (int j = 0; j < 2; j++)
        {
            //if it is a complex number with a plus sign
            if (currentNumber.lastIndexOf("+") > 0)
            {
                //checking the real number
                num = currentNumber.substring(0,currentNumber.lastIndexOf("+"));
                int decCount = 0;
                int minusCount = 0;
                //check no more than one decimal and no more than one minus and no invalid characters
                for (int i = 0; i < num.length(); i++)
                {
                    if ((num.charAt(i) < '0' || num.charAt(i) > '9') && num.charAt(i) != '.' && num.charAt(i) != '-')
                        return false;
                    if (num.charAt(i) == '.')
                        decCount++;
                    else if (num.charAt(i) == '-')
                        minusCount++;
                }
                if (decCount > 1 || minusCount > 1)
                    return false;
                //checking the imaginary number
                num = currentNumber.substring(currentNumber.lastIndexOf("+")+1);
                //checks that i is the last character
                if (num.charAt(num.length()-1) != 'i')
                    return false;
                //take off i for the rest of the checks
                /*if (num.compareTo("i") == 0)
                    return false;*/
                num = num.substring(0,num.length()-2);
                decCount = 0;
                minusCount = 0;
                //checking for incorrect characters and too many minuses/decimals
                for (int i = 0; i < num.length(); i++)
                {
                    if ((num.charAt(i) < '0' || num.charAt(i) > '9') && num.charAt(i) != '.' && num.charAt(i) != '-')
                        return false;
                    if (num.charAt(i) == '.')
                        decCount++;
                    else if (num.charAt(i) == '-')
                        minusCount++;
                }
                if (decCount > 1 || minusCount > 1)
                    return false;
            }//complex number with plus
            //if it's complex with a minus
            else if (currentNumber.lastIndexOf("-") > 0)
            {
                //isolating first half
                num = currentNumber.substring(0,currentNumber.lastIndexOf("-"));
                int decCount = 0;
                int minusCount = 0;
                //checking for invalid characters and too many minuses or decimals
                for (int i = 0; i < num.length(); i++)
                {
                    if ((num.charAt(i) < '0' || num.charAt(i) > '9') && num.charAt(i) != '.' && num.charAt(i) != '-')
                        return false;
                    if (num.charAt(i) == '.')
                        decCount++;
                    else if (num.charAt(i) == '-')
                        minusCount++;
                }
                if (decCount > 1 || minusCount > 0)
                    return false;
                num = currentNumber.substring(currentNumber.lastIndexOf("-")+1);
                //checking second half, make sure i is the last character
                if (num.charAt(num.length()-1) != 'i')
                    return false;
                //taking off i for rest of checks
                /*if (num.compareTo("i") == 0)
                    return false;*/
                num = num.substring(0,num.length()-2);
                decCount = 0;
                minusCount = 0;
                //checking for invalid characters and looking for too many decimals or minuses
                for (int i = 0; i < num.length(); i++)
                {
                    if ((num.charAt(i) < '0' || num.charAt(i) > '9') && num.charAt(i) != '.' && num.charAt(i) != '-')
                        return false;
                    if (num.charAt(i) == '.')
                        decCount++;
                    else if (num.charAt(i) == '-')
                        minusCount++;
                }
                if (decCount > 1 || minusCount > 1)
                    return false;
            }//complex number with minus
            //if the number is just an imaginary number
            else if (currentNumber.contains("i"))
            {
                //checks that the last spot is the i
                if (currentNumber.charAt(currentNumber.length()-1) != 'i')
                    return false;
                //takes off i for the rest of checks
                /*if (num.compareTo("i") == 0)
                    return false;*/
                num = currentNumber.substring(0,currentNumber.indexOf("i"));
                int decCount = 0;
                int minusCount = 0;
                //looking for invalid characters or too many decimals or minuses
                for (int i = 0; i < num.length(); i++)
                {
                    if ((num.charAt(i) < '0' || num.charAt(i) > '9') && num.charAt(i) != '.' && num.charAt(i) != '-')
                        return false;
                    if (num.charAt(i) == '.')
                        decCount++;
                    else if (num.charAt(i) == '-')
                        minusCount++;
                }
                if (decCount > 1 || minusCount > 0)
                    return false;
            }//imaginary number
            //if the number is just a real number
            else
            {
                num = currentNumber;
                int decCount = 0;
                int minusCount = 0;
                //checking for invalid characters, too many decimals or too many minuses
                for (int i = 0; i < num.length(); i++)
                {
                    if ((num.charAt(i) < '0' || num.charAt(i) > '9') && num.charAt(i) != '.' && num.charAt(i) != '-')
                        return false;
                    if (num.charAt(i) == '.')
                        decCount++;
                    else if (num.charAt(i) == '-')
                        minusCount++;
                }
                if (decCount > 1 || minusCount > 0)
                    return false;
            }//real number
            //getting ready for second run through
            currentNumber = secondNum;
        }
        return true;
    }
}