import java.util.*;

public class checksum {
    public static void main(String args[]) {

        Scanner sc = new Scanner(System.in);

        // getting input string.
        System.out.println("Enter the input string: ");
        String input = sc.next();

        // calling the checksum generation function and storing the result.
        int checkSum = generateCheckSum(input);
        System.out.println("The generated checksum is: " + Integer.toHexString(checkSum));

        // getting the data to be sent to the receiver.
        System.out.println("Enter the data for sending to the receiver: ");
        input = sc.next();

        // getting the checksum to be sent to the receiver.
        System.out.println("Enter the checksum for sending to the receiver: ");

        // Parsing the checksum and calling the receiver's function.
        checkSum = Integer.parseInt((sc.next()), 16);
        receive(input, checkSum);
        sc.close();
    }


    static int generateCheckSum(String s) {
        // generating a hexadecimal value for checksum.
        String hexadecimalValue = new String();
        int x, i, checkSum = 0;

        // iterating through the input data string.
        for (i = 0; i < s.length() - 2; i = i + 2) {

            // converting the character into an integer for the operation.
            x = (int) (s.charAt(i));

            // generating a hexadecimal value for the converted integer.
            hexadecimalValue = Integer.toHexString(x);

            // converting the next character into an integer.
            x = (int) (s.charAt(i + 1));

            // generating a new hexadecimal value using current and previous characters.
            hexadecimalValue = hexadecimalValue + Integer.toHexString(x);

            // printing the character.
            System.out.println(s.charAt(i) + "" + s.charAt(i + 1) + " : " + hexadecimalValue);

            // finally parsing the hexadecimal value into an integer and appending it to the checksum.
            x = Integer.parseInt(hexadecimalValue, 16);
            checkSum += x;
        }

        // running operation if the string's length is even.
        if (s.length() % 2 == 0) {
            // converting the character into an integer.
            x = (int) (s.charAt(i));
            hexadecimalValue = Integer.toHexString(x);

            // converting the next character into an integer
            x = (int) (s.charAt(i + 1));

            // performing the same above operation.
            hexadecimalValue = hexadecimalValue + Integer.toHexString(x);

            // printing the character.
            System.out.println(s.charAt(i) + "" + s.charAt(i + 1) + " : " + hexadecimalValue);

            // parsing the current hexadecimal value into an integer
            x = Integer.parseInt(hexadecimalValue, 16);
        } else {
            // converting the character into an integer
            x = (int) (s.charAt(i));

            // generating a hexadecimal value using the current character and adding 00.
            hexadecimalValue = "00" + Integer.toHexString(x);

            // parsing the hexadecimal value into an integer
            x = Integer.parseInt(hexadecimalValue, 16);

            // printing the character.
            System.out.println(s.charAt(i) + " : " + hexadecimalValue);
        }
        // finally parsing the hexadecimal value into an integer and appending it to the checksum.
        checkSum += x;

        // converting the checksum data into a hexadecimal string.
        hexadecimalValue = Integer.toHexString(checkSum);

        // now checking if the length hexadecimal string is larger than 4 or not. If greater then perform the below operations.
        if (hexadecimalValue.length() > 4) {
            int carry = Integer.parseInt(("" + hexadecimalValue.charAt(0)), 16);

            hexadecimalValue = hexadecimalValue.substring(1, 5);

            checkSum = Integer.parseInt(hexadecimalValue, 16);

            checkSum += carry;
        }
        // generating complement for the checksum.
        checkSum = generateComplement(checkSum);

        // finally returning the generated checksum.
        return checkSum;
    }

    // a function that will call the generate checksum function and check whether the syndrome is error-free or not.
    static void receive(String s, int checkSum) {
        // getting checksum for the input string.
        int generatedChecksum = generateCheckSum(s);

        // generating complement for the generated checksum.
        generatedChecksum = generateComplement(generatedChecksum);

        // generating the syndrome which is a summation of the generated checksum and the input checksum for error detection.
        int syndrome = generatedChecksum + checkSum;

        // generating a syndrome for the calculated syndrome.
        syndrome = generateComplement(syndrome);

        // printing the syndrome.
        System.out.println("The syndrome is " + Integer.toHexString(syndrome));

        // if the syndrome is 0 then the correct value is received.
        if (syndrome == 0) {
            System.out.println("Data received without any errors");
        } else {
            System.out.println("Some error encountered in the received data");
        }
    }

    // a function that will generate a complement
    static int generateComplement(int checkSum) {

        // generating complement by adding FFFF to the input checksum and then parsing the entire data into an integer.
        checkSum = Integer.parseInt("FFFF", 16) - checkSum;
        return checkSum;
    }
}
