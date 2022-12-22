import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
/***
         Author: Yasmine Karim Hussein El Shafei
              SIC-XE project
 The output is printed in files not in the console
 Location counter file
 Symbol Table file
 Object Code file
 HTE Record file
 ***/

//Main Class
public class main {

    //Search register number
    public static String SearchReg(String reg, String[][] registers)
    {
        for(int i = 0;i < 9;i++)
        {
        if(reg.equals(registers[i][0]))
        {
            return registers[i][1];
        }

        }
        return null;
    }
    //Search Opcode
    public static String SearchOpcode(String inst,String[][] OPTAB)
    {

        for(int i = 0;i < 59; i++)
        {
                if(inst.equals(OPTAB[i][0]))
                {
           return OPTAB[i][2];
                }

        }
        return  null;
    }

    //Method to check format 1
    public static boolean checkFormat1(String inst)
    {
            if(inst == ("FIX") || inst.equals("FLOAT") || inst.equals("NORM") || inst.equals("HIO") || inst.equals("SIO") || inst.equals("TIO"))
            {
                return true;
            }
        return false;
    }
    //Check instruction  format 2
    public static boolean checkFormat2(String inst)
    {
       if(inst.equals("CLEAR") || inst.equals("ADDR") || inst.equals("COMPR") || inst.equals("DIVR") || inst.equals("MULR") || inst.equals("RMO")
               || inst.equals("SHIFTR") || inst.equals("SHIFTL") || inst.equals("SUBR") || inst.equals("SVC") || inst.equals("TIXR"))
       {
           return true;
       }
       return false;
    }

    //Check if it is format 3
    public static boolean checkFormat3(String inst)
    {
            if (inst.equals("LDA") || inst.equals("ADD") || inst.equals("ADDF") || inst.equals("AND") || inst.equals("COMP") || inst.equals("COMF") ||
                    inst.equals("DIV") || inst.equals("DIVF") || inst.equals("J") || inst.equals("JEQ") || inst.equals("JGT") || inst.equals("JLS") ||
                    inst.equals("JSUB") || inst.equals("LDB") || inst.equals("LDCH") || inst.equals("LDF") || inst.equals("LDS") || inst.equals("LDT") ||
                    inst.equals("LDX") || inst.equals("LPS") || inst.equals("MUL") || inst.equals("MULF") || inst.equals("OR") || inst.equals("RD") ||
                    inst.equals("RSUB") || inst.equals("SSK") || inst.equals("STA") || inst.equals("STB") || inst.equals("STCH") || inst.equals("STF")
                    || inst.equals("STI") || inst.equals("STL") || inst.equals("STS") || inst.equals("STSW") || inst.equals("STT") || inst.equals("JLT") ||
                    inst.equals("STX") || inst.equals("SUB") || inst.equals("SUBF") || inst.equals("TD") || inst.equals("TIX") || inst.equals("WD"))
            {
            return true;
            }
        return false;
    }
    //Covert to String to ASCII then Hexadecimal
    public static String AscII(String str)
    {
        String st ="";
        byte[] bytes = str.getBytes(StandardCharsets.US_ASCII);

            for (int i = 0 ; i < bytes.length ; i++)
            {

                st +=  String.format("%02X", bytes[i]);
                //System.out.println("Hexa"+st);
            }
    return st;
    }
    //Function to search for Reference Address
    public static int SearchRef(String ref2,int count,String[] label,int[] locter)
    {
        for (int i = 0;i < count;i++)
        {

            if(ref2.equals(label[i]))
            {

                //System.out.println(i + " " + label[i]);
                return locter[i];

            }
        }
        return 0;
    }
    //Search for base location counter
    public static int SearchBase(String[] inst,int count,String[] ref,String[] label,int[] locttr)
    {
        for(int i = 1 ;i <count;i++)
        {
            //get base reference
            if(inst[i].equals("BASE"))
            {
               // System.out.println(ref[i]);
                int Base_ref_Location = SearchRef(ref[i], count, label, locttr);
                String hexalocation  = Integer.toHexString(Base_ref_Location);
                return Base_ref_Location;
            }
          //if base not found
            else
            {
            if(inst[i].equals("LDB"))
            {
                int Base_ref_Location = SearchRef(ref[i], count, label, locttr);
                String hexalocation  = Integer.toHexString(Base_ref_Location);
                //System.out.println("Base ref loc" + Base_ref_Location);
                return Base_ref_Location;
            }
            }
        }
        return 0;
        }


    //TO convert hexadecimal to binary
    public static String HexaTOBin(String BinString)
    {
        int i = Integer.parseInt(BinString, 16);
        String binary = Integer.toBinaryString(i);
        return binary;
    }
    //Covert Binary to hexadecimal
    private static String StringBInToHexs(String Hexa)
    {
     if(Hexa.equals("0000"))
         return "0";
     if (Hexa.equals("0001"))
         return "1";
     if (Hexa.equals("0010"))
         return "2";
     if (Hexa.equals("0011"))
         return "3";
     if (Hexa.equals("0100"))
         return "4";
     if (Hexa.equals("0101"))
         return "5";
     if (Hexa.equals("0110"))
         return "6";
     if (Hexa.equals("0111"))
         return "7";
     if (Hexa.equals("1000"))
         return "8";
     if (Hexa.equals("1001"))
         return "9";
     if (Hexa.equals("1010"))
         return "A";
     if (Hexa.equals("1011"))
         return "B";
     if (Hexa.equals("1100"))
         return "C";
     if (Hexa.equals("1101"))
         return "D";
     if (Hexa.equals("1110"))
         return "E";
     if (Hexa.equals("1111"))
         return "F";
    return null;
    }

    //Check Pc relative
public static boolean checkPc(int current_location,int TA)
{
    int disp = TA - current_location;
    if(disp >= -2048 && disp <=2047)
        return true;
    else
    return false;
}
//Check if it is Base relative
public static boolean checkBase(int current_location,int TA)
{
    int disp = TA - current_location;
    if(disp >= 0 && disp <=4095){
       // System.out.println("Base answer: " + disp);
        return true;}
    else
        return false;
}

    //The main function
    public static void main(String[] args)
    {
        final String[][] OPTAB = new String[59][3];

        //The Object Code for each instruction
        //format opcode
        //format 1
        OPTAB[0] = new String[]{"FIX", "1", "C4"};
        OPTAB[1] = new String[]{"FLOAT", "1", "C0"};
        OPTAB[2] = new String[]{"HIO", "1", "F4"};
        OPTAB[3] = new String[]{"NORM", "1", "C8"};
        OPTAB[4] = new String[]{"SIO", "1", "F0"};
        OPTAB[5] = new String[]{"TIO", "1", "F8"};
        //format 2
        OPTAB[6] = new String[]{"ADDR", "2", "90"};
        OPTAB[7] = new String[]{"CLEAR", "2", "B4"};
        OPTAB[8] = new String[]{"COMPR", "2", "A0"};
        OPTAB[9] = new String[]{"DIVR", "2", "9C"};
        OPTAB[10] = new String[]{"MULR", "2", "98"};
        OPTAB[11] = new String[]{"RMO", "2", "AC"};
        OPTAB[12] = new String[]{"SHIFTL", "2", "A4"};
        OPTAB[13] = new String[]{"SHIFTR", "2", "A8"};
        OPTAB[14] = new String[]{"SUBR", "2", "94"};
        OPTAB[15] = new String[]{"SVC", "2", "B0"};
        OPTAB[16] = new String[]{"TIXR", "2", "B8"};
        //format 3
        OPTAB[17] = new String[]{"ADD", "3", "18"};
        OPTAB[18] = new String[]{"ADDF", "3", "58"};
        OPTAB[19] = new String[]{"AND", "3", "40"};
        OPTAB[20] = new String[]{"COMP", "3", "28"};
        OPTAB[21] = new String[]{"COMPF", "3", "88"};
        OPTAB[22] = new String[]{"DIV", "3", "24"};
        OPTAB[23] = new String[]{"DIVF", "3", "64"};
        OPTAB[24] = new String[]{"J", "3", "3C"};
        OPTAB[25] = new String[]{"JEQ", "3", "30"};
        OPTAB[26] = new String[]{"JGT", "3", "34"};
        OPTAB[27] = new String[]{"JLT", "3", "38"};
        OPTAB[28] = new String[]{"JSUB", "3", "48"};
        OPTAB[29] = new String[]{"LDA", "3", "00"};
        OPTAB[30] = new String[]{"LDB", "3", "68"};
        OPTAB[31] = new String[]{"LDCH", "3", "50"};
        OPTAB[32] = new String[]{"LDF", "3", "70"};
        OPTAB[33] = new String[]{"LDL", "3", "08"};
        OPTAB[34] = new String[]{"LDS", "3", "6C"};
        OPTAB[35] = new String[]{"LDT", "3", "74"};
        OPTAB[36] = new String[]{"LDX", "3", "04"};
        OPTAB[37] = new String[]{"LPS", "3", "D0"};
        OPTAB[38] = new String[]{"MUL", "3", "20"};
        OPTAB[39] = new String[]{"MULF", "3", "60"};
        OPTAB[40] = new String[]{"OR", "3", "44"};
        OPTAB[41] = new String[]{"RD", "3", "D8"};
        OPTAB[42] = new String[]{"RSUB", "3", "4C"};
        OPTAB[43] = new String[]{"SSK", "3", "EC"};
        OPTAB[44] = new String[]{"STA", "3", "0C"};
        OPTAB[45] = new String[]{"STB", "3", "78"};
        OPTAB[46] = new String[]{"STCH", "3", "54"};
        OPTAB[47] = new String[]{"STF", "3", "80"};
        OPTAB[48] = new String[]{"STI", "3", "D4"};
        OPTAB[49] = new String[]{"STL", "3", "14"};
        OPTAB[50] = new String[]{"STS", "3", "7C"};
        OPTAB[51] = new String[]{"STSW", "3", "E8"};
        OPTAB[52] = new String[]{"STT", "3", "84"};
        OPTAB[53] = new String[]{"STX", "3", "10"};
        OPTAB[54] = new String[]{"SUB", "3", "1C"};
        OPTAB[55] = new String[]{"SUBF", "3", "5C"};
        OPTAB[56] = new String[]{"TD", "3", "E0"};
        OPTAB[57] = new String[]{"TIX", "3", "2C"};
        OPTAB[58] = new String[]{"WD", "3", "DC"};

        //Registers Numbers
        String[][] registers = new String[9][9];

        registers[0] = new String[]{"A","0"};
        registers[1] = new String[]{"X","1"};
        registers[2] = new String[]{"L","2"};
        registers[3] = new String[]{"B","3"};
        registers[4] = new String[]{"S","4"};
        registers[5] = new String[]{"T","5"};
        registers[6] = new String[]{"F","6"};
        registers[7] = new String[]{"PC","8"};
        registers[8] = new String[]{"SW","9"};

        //Counter to count the lines in the file
        //It will be used in th whole program
        int count = 0;
        //Arrays to store three parts
        //1- Labels
        //2- Instructions
        //3- References
        String[] label = new String[100];
        String[] inst = new String[100];
        String[] ref = new String[100];
       /******************* File reading *************************/
        //In order to read the file
        try {
            File myfile = new File("inSICXE.txt");
            //Another file to test
          // File myfile = new File("Test1.txt");
            Scanner read = new Scanner(myfile);
            while (read.hasNextLine())
            {
                String file = read.nextLine();
                String[] parts = file.split(" ");

                //If the three parts exist
                if (parts.length == 3)
                {
                    label[count] = parts[0];
                    inst[count] = parts[1];
                    ref[count] = parts[2];
                }

                //If Only two parts
                //Used & to prevent null error(NULl Pointer exception)
                if (parts.length == 2)
                {
                    label[count] = "&";
                    inst[count] = parts[0];
                    ref[count] = parts[1];
                }

                //Check if it is only RSUB
                if (parts.length == 1)
                {
                    label[count] = "&";
                    inst[count] = parts[0];
                    ref[count] = "&";
                }

                //counter to count the lines of a file
                count++;
            }
            read.close();
        }
        catch (FileNotFoundException e)
        {
            System.out.println("An error occured.");
            e.printStackTrace();
        }
        //End of file reading


      /****************************** Pass 1 ****************************/
      int locttr[] = new int[100];


        //To iterate over the whole instruction
        for (int i = 1; i < count; i++)
        {
            //Check if it is the first program
            //It equals to the first reference of the program which was in this file (0)
            if ( inst[0].equals("START"))
            {
                //TO make it hexadecimal
                int reference =  Integer.parseInt(ref[0],16);
                locttr[0] = reference;
                locttr[1] = reference;
            }

            //Assembly directive
            //Check first inst format
            if(label[i].equals("FIRST"))
            {
                if (checkFormat1(inst[1]))
                {
                    locttr[2] = locttr[1] + 1 ;
                }
                //Check format 2 and add it to the previous location counter
                if (checkFormat2(inst[1]))
                {

                    locttr[2] += locttr[1] + 2;

                }

                //Check Format 4 and add it to the previous location counter
                if (inst[1].startsWith("+"))
                {
                    locttr[2] = locttr[1] + (4) ;

                }

                //Check format 3 and add it to the previous Location counter
                if (checkFormat3(inst[1]))
                {
                    locttr[2] = locttr[1] + 3;

                }
            }

            //Assembly Directive
            if(inst[i].equals("END"))
            {
                        locttr[i] = Integer.parseInt(ref[0]);

            }

            //To Check format 1 and add it to the previous location counter
            if (checkFormat1(inst[i-1]))
            {
                locttr[i] = locttr[i - 1] + 1 ;

            }
            //Check format 2 and add it to the previous location counter
            if (checkFormat2(inst[i-1]))
            {

                locttr[i] = locttr[i - 1] + 2;
            }

            //Check format 3 and add it to the previous Location counter
            if (checkFormat3(inst[i-1]))
            {
                    locttr[i] = locttr[i - 1] + 3;

            }
            //Check Format 4 and add it to the previous location counter
            if (inst[i-1].startsWith("+"))
            {
                locttr[i] = locttr[i - 1] + (4) ;

            }
            //Assembly directive
            if (inst[i-1].equals("BASE"))
            {
                locttr[i] = locttr[i- 1];

            }

            //Assembly directive
            if (inst[i-1].equals("RESW"))
            {
                locttr[i] = locttr[i - 1] + (3 * (Integer.parseInt(ref[i-1])));
            }

            //Assembly Directive
            if (inst[i-1].equals("RESB"))
            {
                locttr[i] = locttr[i - 1] + Integer.parseInt(ref[i-1]);

            }

            //Word is integer
            if (inst[i-1].equals("WORD"))
            {
                locttr[i] += locttr[i - 1] + 3;
            }

           //Byte Location counter
            if (inst[i-1].equals("BYTE")){
                //Example
                //X'01'
                //X'0001'
                //It will count them and add them to the location counter and divide by two in order to convert them byte
                if (ref[i-1].startsWith("X"))
                {
                    int len = ref[i-1].length();
                    locttr[i] += locttr[i - 1] + ((len - 3) / 2);

                }

                //We will count them and add them as they are
                //Characters are byte
                if (ref[i-1].startsWith("C"))
                {
                    //Example
                    //C'EOF'
                    //Subtract
                    locttr[i] = locttr[i - 1] + (ref[i-1].length() - 3);
                }
            }
        }

        //Get the length of the program
        //We subtract it in the int then convert to hexadecimal
        //TO access counter location the before
        int program_length = locttr[count-1] - locttr[0];
        String program_length_hexa = Integer.toHexString(program_length);

        //Function to convert int to hexadecimal
        String[] lochex = new String[count];
        for (int i = 0; i < count; i++)
        {
            //Function to convert an integer to hexadecimal string
            String hexa_Location = Integer.toHexString(locttr[i]);
            while (hexa_Location.length() < 4)
            {
                hexa_Location = "0" + hexa_Location;
            }

            lochex[i] = hexa_Location.toUpperCase();

        }

        //Location counter table in a file
        try{
            FileWriter writer = new FileWriter("Location Counter.txt");
            writer.write("    ----------------\n");
            writer.write("  | Location Counter |\n");
            writer.write("    ----------------\n");
            for (int i = 0; i < count; i++)
            {

                    writer.write("|    " + inst[i]+ " | " + lochex[i].toUpperCase()+  "    |" +"\n");

            }
            writer.write("=====================\n");
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


        //To Create a file and put the Symbol table
        try{
            FileWriter writer = new FileWriter("SYTMAB.txt");
            writer.write("    ----------------\n");
            writer.write("    | SYMBOL TABLE |\n");
            writer.write("    ----------------\n");
            for (int i = 0; i < count; i++)
            {
                ///Skip & parts
                if(label[i].equals("&"))
                {
                    continue;
                }
                else
                {
            writer.write("|    " + label[i]+ " | "+ lochex[i]+"    |" +"\n");
                }
            }
            writer.write("=====================\n");
            writer.write("Length of program = " + program_length_hexa);
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

 /*********************** End of pass 1 ************************/


/************************ Pass 2 ***************************/
String[][] OPCODE = new String[count][2];
//Flag bits and opcode
String OpCOde,n,I,x,b,p,e,flagBits;

//Loop around instructions
for (int i = 0; i < count; i++)
{
    //Format 1 object code
    if (checkFormat1(inst[i]))
    {
        OPCODE[i][0] = inst[i];
        OPCODE[i][1] = SearchOpcode(inst[i], OPTAB);
    }
    //Format 2 Object code
    if (checkFormat2(inst[i]))
    {
        OpCOde = SearchOpcode(inst[i], OPTAB);
        String[] RegParts = ref[i].split(",");
        //If it has one register
        if (RegParts.length == 1)
        {
            OPCODE[i][0] = inst[i];
            OPCODE[i][1] = OpCOde + SearchReg(RegParts[0], registers) + "0";
        }

        //If it has two registers
        if (RegParts.length == 2)
        {
            OPCODE[i][0] = inst[i];
            OPCODE[i][1] = OpCOde + SearchReg(RegParts[0], registers) + SearchReg(RegParts[1], registers);
            //if it contains only one reg
            if (OPCODE[i][1].length() < 4)
            {
                OPCODE[i][1] = OPCODE[i][1] + "0";
            }

            else {
                OPCODE[i][1] = OPCODE[i][1];

            }
        }
    }




    //Format 3 instruction set
    if (checkFormat3(inst[i])) {
        String Opcodebin = "";
        String part1, part2, part3, part4,Result;
        //search opcode
        OpCOde = SearchOpcode(inst[i], OPTAB);
        //Opcode to binary conversion
        Opcodebin = HexaTOBin(OpCOde);
        //In order to be 8 bits if the conversion is less than 8
        while (Opcodebin.length() < 8)
        {
            Opcodebin = "0" + Opcodebin;
        }
        //With discarded two last bits
        //6 bits
        String OpcodeRes = Opcodebin.substring(0, Opcodebin.length() - 2);
        //first part of opcode
        part1 = StringBInToHexs(OpcodeRes.substring(0, 4));
        //to be added on n and i
        String lastbits = OpcodeRes.substring(4, 6);

        //Reference starts with #
        if (ref[i].startsWith("#"))
        {
            int len = ref.length;
            String s = ref[i].substring(1);
            //if the reference is a number
                if(s.startsWith("0")||s.startsWith("1")||s.startsWith("2")||s.startsWith("3")||s.startsWith("4")||s.startsWith("5")||s.startsWith("6")
                ||  s.startsWith("7")||s.startsWith("8")||s.startsWith("9"))
                {

                    String hexa = ref[i].substring(1);
                    while (hexa.length() < 3)
                    {
                        hexa = "0" + hexa;
                    }
                    part4 = hexa.toUpperCase();
                    n = "0";
                    I = "1";
                    x = "0";
                    b = "0";
                    p = "0";
                    e = "0";

                    String cont = x + b + p + e;
                    part2 = StringBInToHexs(lastbits + n + I);
                    part3 = StringBInToHexs(cont);
                    Result = part1 + part2 + part3 + part4;
                    OPCODE[i][0] = inst[i];
                    OPCODE[i][1] = Result;
                }
                //if it reference
                else{
                String reference = ref[i].substring(1);
                    int TA = SearchRef(reference, count, label,locttr);
                    //Check if is PC relative
                    if (checkPc(locttr[i + 1], TA)) {
                        int result = TA - locttr[i+1];
                        part4 = "0" + Integer.toHexString(result).toUpperCase();
                        n = "0";
                        I = "1";
                        x = "0";
                        b = "0";
                        p = "1";
                        e = "0";
                        String cont = x + b + p + e;
                        part2 = StringBInToHexs(lastbits + n + I);
                        part3 = StringBInToHexs(cont);
                        Result = part1 + part2 + part3 + part4;
                        OPCODE[i][0] = inst[i];
                        OPCODE[i][1] = Result;
                    }
                    }
                }



        //Reference starts with @
         if (ref[i].startsWith("@"))
        {
            String reference = ref[i].substring(1);

            int TA = SearchRef(reference, count, label,locttr);
            //Check if is PC relative
            if (checkPc(locttr[i + 1], TA)) {
                //to get the disp
                int result = Integer.parseInt(String.valueOf(TA), 16) - Integer.parseInt(String.valueOf(locttr[i + 1]), 16);
               String hexa = Integer.toHexString(result);
               //in order to be three bits
               while (hexa.length() < 3) {
                    hexa = "0" + hexa;
                }
                //To convert any lower case
                part4 =  hexa.toUpperCase();
                n = "1";
                I = "0";
                x = "0";
                b = "0";
                p = "1";
                e = "0";
                String cont = x + b + p + e;
                part2 = StringBInToHexs(lastbits + n + I);
                part3 = StringBInToHexs(cont);
                Result = part1 + part2 + part3 + part4;
                OPCODE[i][0] = inst[i];
                OPCODE[i][1] = Result;

        }
        }

        //Get the base location for base relative
        int Baseloc = SearchBase(inst,count,ref,label,locttr);

         //If it is format three SIC XE normal form
       if(!ref[i].startsWith("@") && !ref[i].startsWith("#"))
       {

           if(ref[i].contains(",X"))
           {
               x = "1";
               ref[i] = ref[i].replace(",X", "");
           }
           else
               x ="0";
           //To search target address
           int TA = SearchRef(ref[i], count, label,locttr);
           //To check if it is pc relative
           if (checkPc(locttr[i + 1], TA))
           {
               //To get the location
               int result = TA - locttr[i+1];
               part4 =  Integer.toHexString(result);

               if(part4.length() > 3)
               {
                part4 = part4.substring(part4.length()-3,part4.length()).toUpperCase();
               }
               while (part4.length() < 3)
               {
                   part4 = "0" + part4.toUpperCase();
               }
               n = "1";
               I = "1";
               b = "0";
               p = "1";
               e = "0";

               String cont = x + b + p + e;
               part2 = StringBInToHexs(lastbits + n + I);
               part3 = StringBInToHexs(cont);
               Result = part1 + part2 + part3 + part4;
               OPCODE[i][0] = inst[i];
               OPCODE[i][1] = Result;
       }

           //To check if it is base relative
            if(checkBase(Baseloc,TA))
            {
               //To get base reference
               int base = SearchBase(inst,count,ref,label,locttr);
               int result = TA - base;
               part4 =  Integer.toHexString(result);
               //For negative values to take last 3 bits
               if(part4.length() > 3)
               {
                   part4 = part4.substring(part4.length()-3,part4.length()).toUpperCase();
               }
               //For less than values
               while (part4.length() < 3)
               {
                   part4 = "0" + part4.toUpperCase();
               }
               n = "1";
               I = "1";
               b = "1";
               p = "0";
               e = "0";

               String cont = x + b + p + e;
               //Second part of opcode
               part2 = StringBInToHexs(lastbits + n + I);
               part3 = StringBInToHexs(cont);
               Result = part1 + part2 + part3 + part4;
               OPCODE[i][0] = inst[i];
               OPCODE[i][1] = Result;
           }
    }
    }


    //Format 4 Object code
    if (inst[i].startsWith("+"))
    {
        String Opcodebin = "";
        String part1="", part2="", part3, part4,Result;
        OpCOde = SearchOpcode(inst[i].substring(1), OPTAB);
        //Opcode
        Opcodebin = HexaTOBin(OpCOde);
        //In order to be
        while (Opcodebin.length() < 8)
        {
            Opcodebin = "0" + Opcodebin;
        }
        //With discarded two last bits
        String OpcodeRes = Opcodebin.substring(0, Opcodebin.length() - 2);
        part1 = StringBInToHexs(OpcodeRes.substring(0, 4));
        String lastbits = OpcodeRes.substring(4, 6);
            n = "1";
            I = "1";
            b = "0";
            p = "0";
            e = "1";
            if(ref[i].contains(",X"))
                x ="1";
            else
                x ="0";
            String cont= x + b + p + e;
            part2 = StringBInToHexs(lastbits + n + I);
            part3 = StringBInToHexs(cont);


             //Address is 20 bits 5 places
            int location = SearchRef(ref[i],count,label,locttr);
            part4 = "0" + Integer.toHexString(location).toUpperCase();
            //Counting all parts
            Result = part1 + part2 + part3 + part4;
            OPCODE[i][0] = inst[i];
            OPCODE[i][1] = Result;

    }

    //Check format four with hash
    if(inst[i].startsWith("+") && ref[i].startsWith("#"))
    {
        String Opcodebin = "";
        String part1, part2, part3, part4, Result;
        OpCOde = SearchOpcode(inst[i].substring(1), OPTAB);

        //Opcode in binary (if it returns object code with missing 0 or 1)
        Opcodebin = HexaTOBin(OpCOde);

        //In order to be 8 bits
        while (Opcodebin.length() < 8)
        {
            Opcodebin = "0" + Opcodebin;
        }

        //With discarded two last bits
        String OpcodeRes = Opcodebin.substring(0, Opcodebin.length() - 2);
        //first 4 bit of opcode
        part1 = StringBInToHexs(OpcodeRes.substring(0, 4));
       //second part
        String lastbits = OpcodeRes.substring(4, 6);
        n = "0";
        I = "1";
        x = "0";
        b = "0";
        p = "0";
        e = "1";
        String cont= x + b + p + e;
        part2 = StringBInToHexs(lastbits + n + I);
        part3 = StringBInToHexs(cont);


        //Convert ref to integer value
        //then integer to hexa
        String hexa = ref[i].substring(1);
        if(hexa.startsWith("0") || hexa.startsWith("1") || hexa.startsWith("2") || hexa.startsWith("3") || hexa.startsWith("4") || hexa.startsWith("5") ||
                hexa.startsWith("6") ||  hexa.startsWith("7") || hexa.startsWith("8")|| hexa.startsWith("9"))
        {
            part4 = Integer.toHexString(Integer.parseInt(ref[i].substring(1)));
            while (part4.length() < 5)
            {
                part4 = "0" + part4.toUpperCase();
            }
        }
        else
        {
            int TA = SearchRef(ref[i].substring(1), count, label,locttr);
            part4 = "0"+ Integer.toHexString(TA);
        }
        //Concatenation of all parts
        Result = part1 + part2 + part3 + part4;
        OPCODE[i][0] = inst[i];
        OPCODE[i][1] = Result;
    }

    //Assembly Directive
    if(inst[i].equals("BASE")|| inst[i].equals("START"))
    {
        OPCODE[i][0] = inst[i];
        OPCODE[i][1] = "No Object Code";

    }
    //Get the reference of the first inst
    if(inst[i].equals("END"))
    {
        OPCODE[i][0] = inst[i];
        OPCODE[i][1] = ref[0];
    }
    //Represented in three bytes
    if(inst[i].equals("WORD"))
    {
        ref[i] = Integer.toHexString(Integer.parseInt(ref[i]));
        //Since word is represented in 3 bytes
        //Add zero to complete the 6 bits
         while(ref[i].length() < 6)
         {
            ref[i] = "0" + ref[i].toUpperCase();
         }
            OPCODE[i][0] = inst[i];
            OPCODE[i][1] = ref[i];
    }

    //Byte Object code
    //Can start with C or X
    if (inst[i].equals("BYTE"))
    {
        OPCODE[i][0] = inst[i];
        if (ref[i].contains("X'"))
        {
            //Get the part of the code without X' '
            OpCOde = ref[i].substring(2, ref[i].length() - 1);
            OPCODE[i][1] = OpCOde.toUpperCase();
        }
    }
    //We will convert them to AscII code
    //Characters are byte
    if (ref[i].contains("C'")) {
        OpCOde = ref[i].substring(2, ref[i].length() - 1);
        OPCODE[i][1] = AscII(OpCOde);
        OPCODE[i][0] = inst[i];
    }
    //It has no object Code
    //RESW & RESB
    if(inst[i].equals("RESW"))
    {
        OPCODE[i][0] = inst[i];
        OPCODE[i][1] = "No Object Code";
    }
    //No object code for RESB
    if(inst[i].equals("RESB"))
    {
     OPCODE[i][0] = inst[i];
     OPCODE[i][1] = "No Object Code";

    }
    //Format 3 / 4
    //With no address part
    if(inst[i].equals("RSUB"))
    {
        //Format 4
        if(inst[i].startsWith("+"))
        {
        OPCODE[i][1] = inst[i];
        OPCODE[i][0] = "4F100000";

        }
        //Format 3
        else
        {
            OPCODE[i][0] = inst[i];
            OPCODE[i][1] = "4F0000";

        }

    }
}
        /*******************************Object Code**********************************/
        try
        {
            FileWriter writer = new FileWriter("OBJECT CODE.txt");
            writer.write("                 ----------------\n");
            writer.write("                 | Object Code TABLE |\n");

            writer.write("---------------------------------------------------------------\n");
            writer.write("| line |" + " Location Counter  |"  +  " Instruction |"  +   " | Object Code  " +"\n");
            writer.write("---------------------------------------------------------------\n");
            for (int i = 0; i < count; i++)
            {

                    writer.write("| "  + i + "    |    " +  lochex[i]  + "           |     " + OPCODE[i][0]+ "    |     "+ OPCODE[i][1]+"    |" +"\n");

            }
            writer.write("=======================================================\n");
            writer.close();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();

        }

     //To convert them to 6 bits
     for(int i = 1;i < count;i++)
     {
         if(OPCODE[i][1].equals("No Object Code") ||
                 OPCODE[i][1].equals(null))
             continue;
         while(OPCODE[i][1].length() < 6)
         {
             OPCODE[i][1] = "0" + OPCODE[i][1];
         }
     }
        /************************HTE RECORD**************************/
        try{
           FileWriter writer = new FileWriter("HTE Record.txt");
            writer.write("                       -------------\n");
            writer.write("                      | HTE RECORD |\n");
            writer.write("                       -------------\n");
            writer.write("--------------------------------------------------------\n");
            //Repersented in 6 bits
            while(ref[0].length() < 6)
            {
                ref[0] = "0" + ref[0];
            }
            while(program_length_hexa.length() < 6)
            {
              program_length_hexa = "0" + program_length_hexa ;
            }
            // H record
            writer.write("H. " + label[0] + ". " + ref[0] + ". " + program_length_hexa.toUpperCase());

            //T record
            //To store records of T and their locations
            ArrayList<String> records =new ArrayList<>();
            ArrayList<Integer> location =new ArrayList<>();

            //Flag to check if there more than resw or resb
            boolean flag =false;

            for(int i =1;i < count;i++)
            {
                if (!OPCODE[i][0].equals("RESW") && !OPCODE[i][0].equals("RESB") && !OPCODE[i][0].equals("END"))
                {

                    flag = true;
                    //break point
                    if (!OPCODE[i][0].equals("END"))
                    {
                        records.add(OPCODE[i][1]);
                        location.add(locttr[i]);
                    }

                }

                //To print the T record
                if ((OPCODE[i][0].equals("RESW".trim()) || OPCODE[i][0].equals("RESB".trim()) ||
                        OPCODE[i][0].equals("END".trim())) && flag)
                {
                    location.add(locttr[i]);
                    //First address of T record
                    String fisrtpos = Integer.toHexString(location.get(0));
                    //Repersented in 6 bits
                    while (fisrtpos.length() < 6)
                    {
                        fisrtpos = "0" + fisrtpos;
                    }
                    writer.write("\nT." + fisrtpos.toUpperCase());
                    //Length of T record
                    String hexa = Integer.toHexString(location.get(location.size() - 1) - location.get(0));
                    writer.write("." + hexa.toUpperCase());
                    //Remove unnecessary elements
                    records.removeAll(Collections.singleton("No Object Code"));
                    //To print the object code
                    for (int j = 0; j < records.size(); j++)
                    {
                        // Assuming arr is an ArrayList object
                        String current = records.get(j);
                        writer.write("." + current);

                    }
                    //Clear the last record values
                    records.clear();
                    location.clear();
                    System.out.println("\n");
                    flag  = false;

                }

               }

           //E record
           writer.write("\nE." +ref[0]);
           writer.write("\n=======================================================");
            writer.flush();
            writer.close();
           }


        catch (IOException ex)
        {
            ex.printStackTrace();
        }

    }
}
//End of main and class
