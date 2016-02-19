package com.a1works.parralelFileAccess;


import java.io.StringReader;
import org.junit.Test;
import static org.junit.Assert.*;

public class ChunkerTest {

    @Test
    public void testIfChunksThatCutLinesGetCompleteLines(){
        String testString = "A 001 B C D E F G H I J K L M N O P Q R S T U V W X Y Z\n" +
                "A 002 B C D E F G H I J K L M N O P Q R S T U V W X Y Z\n" +
                "A 003 B C D E F G H I J K L M N O P Q R S T U V W X Y Z\n" +
                "A 004 B C D E F G H I J K L M N O P Q R S T U V W X Y Z\n" +
                "A 005 B C D E F G H I J K L M N O P Q R S T U V W X Y Z\n" +
                "A 006 B C D E F G H I J K L M N O P Q R S T U V W X Y Z\n" +
                "A 007 B C D E F G H I J K L M N O P Q R S T U V W X Y Z\n" +
                "A 008 B C D E F G H I J K L M N O P Q R S T U V W X Y Z\n" +
                "A 009 B C D E F G H I J K L M N O P Q R S T U V W X Y Z\n" +
                "A 010 B C D E F G H I J K L M N O P Q R S T U V W X Y Z\n" +
                "A 011 B C D E F G H I J K L M N O P Q R S T U V W X Y Z\n" +
                "A 012 B C D E F G H I J K L M N O P Q R S T U V W X Y Z\n" +
                "A 013 B C D E F G H I J K L M N O P Q R S T U V W X Y Z\n" +
                "A 014 B C D E F G H I J K L M N O P Q R S T U V W X Y Z\n" +
                "A 015 B C D E F G H I J K L M N O P Q R S T U V W X Y Z\n" +
                "A 016 B C D E F G H I J K L M N O P Q R S T U V W X Y Z\n" +
                "A 017 B C D E F G H I J K L M N O P Q R S T U V W X Y Z\n" +
                "A 018 B C D E F G H I J K L M N O P Q R S T U V W X Y Z\n" +
                "A 019 B C D E F G H I J K L M N O P Q R S T U V W X Y Z\n" +
                "A 020 B C D E F G H I J K L M N O P Q R S T U V W X Y Z\n" +
                "A 021 B C D E F G H I J K L M N O P Q R S T U V W X Y Z\n" +
                "A 022 B C D E F G H I J K L M N O P Q R S T U V W X Y Z\n" +
                "A 023 B C D E F G H I J K L M N O P Q R S T U V W X Y Z\n" +
                "A 024 B C D E F G H I J K L M N O P Q R S T U V W X Y Z\n" +
                "A 025 B C D E F G H I J K L M N O P Q R S T U V W X Y Z\n" +
                "A 026 B C D E F G H I J K L M N O P Q R S T U V W X Y Z\n" +
                "A 027 B C D E F G H I J K L M N O P Q R S T U V W X Y Z\n" +
                "A 028 B C D E F G H I J K L M N O P Q R S T U V W X Y Z\n" +
                "A 029 B C D E F G H I J K L M N O P Q R S T U V W X Y Z\n" +
                "A 030 B C D E F G H I J K L M N O P Q R S T U V W X Y Z";
        int chunksCount = 7;
        long chunkSize = (long)Math.ceil((double)testString.length() / (double)chunksCount);
        int index = 0;
        System.out.println("Length: " + testString.length());
        while (index < testString.length()) {
            int endIndex = (int)(index+chunkSize);
            if (endIndex >= testString.length()) endIndex = testString.length();
            System.out.println("\n======" + index + "=============" + endIndex + "============");
            System.out.println(testString.substring(index, endIndex));
            System.out.println("--------------------------------\n");
            index += chunkSize;
        }
        StringReader reader = new StringReader(testString);
        Chunker chunker = Chunker.getInstance(reader, chunksCount, chunkSize);
        int lastNum = 0;

        ////////////////TODO currenly it is returning only one chunk reader. Please check what is the problem
        for (ChunkReader chunkReader : chunker.readers()) {
            String line;
            while ((line = chunkReader.readLine()) != null) {
                System.out.println("--> " + line);
                String[] parts = line.split(" ");
                int num = Integer.parseInt(parts[1]);
                assertEquals("First character in line must be A", "A", parts[0]);
                assertEquals("Last character in line must be Z", "Z", parts[parts.length-1]);
                assertTrue(String.format("lines are not ordered correctly. it appears that line %d is right after line %d", num, lastNum), num == lastNum + 1);
                lastNum = num;
            }
        }
    }
}
