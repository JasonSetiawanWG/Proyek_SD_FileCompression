//This class is used to keep the characters inside the text input file
//It implements the concept of tree so we can do Heap operations in order to create the Huffman Tree

public class ByteNode implements Comparable<ByteNode>{
    Byte data;
    int frequency;

    // in TestByteNode for demo
    //ByteNode left;
    //ByteNode right;

    //creating ByteNode class,
    //Input : AABBBCCCCC
    //A : 2 B : 3 C : 5  -> Data [ byte ] and frequency [ int or long ]

    // Bytenode data
    public ByteNode(Byte data, int weight)
    {
        this.data = data; // A,B,C, ,.....
        this.frequency = weight; //Amount.. = ... ( 3 )
    }

    //compare bytenode total frequency with other Bytenode frequency
    public int compareTo(ByteNode other)
    {
        //Comparing the frequency of this ByteNode with the input ByteNode
        return this.frequency - other.frequency;
    }
}
