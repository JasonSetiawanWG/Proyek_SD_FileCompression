package FileCompression;
import java.util.*;
import java.io.*;
public class HuffCompression {
    private static StringBuilder sb = new StringBuilder();
    private static Map<Byte, String> huffmap = new HashMap<>();

    public static void compress(String src, String dst) {
        try {
            //Reading the text file by using FileInputStream class
            FileInputStream inStream = new FileInputStream(src);
            //Available function is used to find the size of the remaining bits in the file. Because file hasn't been read yet,therefore
            //the size of 'b'array is the size of the file.
            byte[] b = new byte[inStream.available()];
            //The function read is used to read every single byte in the file.Because the size of a character is one byte,
            //this function is used to read every single character,then the characters are kept in the 'b' array
            inStream.read(b);
            byte[] huffmanBytes = createZip(b);
            OutputStream outStream = new FileOutputStream(dst);
            ObjectOutputStream objectOutStream = new ObjectOutputStream(outStream);
            objectOutStream.writeObject(huffmanBytes);
            objectOutStream.writeObject(huffmap);
            inStream.close();
            objectOutStream.close();
            outStream.close();
        } catch (Exception e) { e.printStackTrace(); }
    }

    private static byte[] createZip(byte[] bytes) {
        MinPriorityQueue<ByteNode> nodes = getByteNodes(bytes);
        ByteNode root = createHuffmanTree(nodes);
        Map<Byte, String> huffmanCodes = getHuffCodes(root);
        byte[] huffmanCodeBytes = zipBytesWithCodes(bytes, huffmanCodes);
        return huffmanCodeBytes;
    }

    private static MinPriorityQueue<ByteNode> getByteNodes(byte[] bytes) {
        MinPriorityQueue<ByteNode> nodes = new MinPriorityQueue<ByteNode>();
        Map<Byte, Integer> tempMap = new HashMap<>();
        for (byte b : bytes) {
            Integer value = tempMap.get(b);
            if (value == null)
                tempMap.put(b, 1);
            else
                tempMap.put(b, value + 1);
        }
        for (Map.Entry<Byte, Integer> entry : tempMap.entrySet())
            nodes.add(new ByteNode(entry.getKey(), entry.getValue()));
        return nodes;
    }

    private static ByteNode createHuffmanTree(MinPriorityQueue<ByteNode> nodes) {
        while (nodes.len() > 1) {
            ByteNode left = nodes.poll();
            ByteNode right = nodes.poll();
            ByteNode parent = new ByteNode(null, left.frequency + right.frequency);
            parent.left = left;
            parent.right = right;
            nodes.add(parent);
        }
        return nodes.poll();
    }

    private static Map<Byte, String> getHuffCodes(ByteNode root) {
        if (root == null) return null;
        getHuffCodes(root.left, "0", sb);
        getHuffCodes(root.right, "1", sb);
        return huffmap;
    }

    private static void getHuffCodes(ByteNode node, String code, StringBuilder sb1) {
        StringBuilder sb2 = new StringBuilder(sb1);
        sb2.append(code);
        if (node != null) {
            if (node.data == null) {
                getHuffCodes(node.left, "0", sb2);
                getHuffCodes(node.right, "1", sb2);
            } else
                huffmap.put(node.data, sb2.toString());
        }
    }

    private static byte[] zipBytesWithCodes(byte[] bytes, Map<Byte, String> huffCodes) {
        StringBuilder strBuilder = new StringBuilder();
        for (byte b : bytes)
            strBuilder.append(huffCodes.get(b));

        int length=(strBuilder.length()+7)/8;
        byte[] huffCodeBytes = new byte[length];
        int idx = 0;
        for (int i = 0; i < strBuilder.length(); i += 8) {
            String strByte;
            if (i + 8 > strBuilder.length())
                strByte = strBuilder.substring(i);
            else strByte = strBuilder.substring(i, i + 8);
            huffCodeBytes[idx] = (byte) Integer.parseInt(strByte, 2);
            idx++;
        }
        return huffCodeBytes;
    }

    public static void decompress(String src, String dst) {
        try {
            //Placing the compressed file to the FileInputStream class
            FileInputStream inStream = new FileInputStream(src);
            //The file is treated as an object because the huffmanBytes and the HuffmanCode/Map is written as an object inside the
            //Output object of the compressed file
            ObjectInputStream objectInStream = new ObjectInputStream(inStream);
            //Reading the huffmanBytes object,then cast it back to array of byte
            byte[] huffmanBytes = (byte[]) objectInStream.readObject();
            //Reading the huffmanCode/Map object,then cast it back to a HashMap
            Map<Byte, String> huffmanCodes =
                    (Map<Byte, String>) objectInStream.readObject();
            //Do the decompress process so we can get the original bytes,in the meaning of the original characterse
            byte[] bytes = decomp(huffmanCodes, huffmanBytes);
            //Set the output to the desired destination
            OutputStream outStream = new FileOutputStream(dst);
            //Write the decompressed bytes back
            outStream.write(bytes);
            //Close all of the I/O stream
            inStream.close();
            objectInStream.close();
            outStream.close();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static byte[] decomp(Map<Byte, String> huffmanCodes, byte[] huffmanBytes) {
        StringBuilder sb1 = new StringBuilder();
        for (int i=0; i<huffmanBytes.length; i++) {
            byte b = huffmanBytes[i];
            boolean flag = (i == huffmanBytes.length - 1);
            sb1.append(convertbyteInBit(!flag, b));
        }
        Map<String, Byte> map = new HashMap<>();
        for (Map.Entry<Byte, String> entry : huffmanCodes.entrySet()) {
            map.put(entry.getValue(), entry.getKey());
        }
        java.util.List<Byte> list = new java.util.ArrayList<>();
        for (int i = 0; i < sb1.length();) {
            int count = 1;
            boolean flag = true;
            Byte b = null;
            while (flag) {
                String key = sb1.substring(i, i + count);
                b = map.get(key);
                if (b == null) count++;
                else flag = false;
            }
            list.add(b);
            i += count;
        }
        byte b[] = new byte[list.size()];
        for (int i = 0; i < b.length; i++)
            b[i] = list.get(i);
        return b;
    }

    private static String convertbyteInBit(boolean flag, byte b) {
        int byte0 = b;
        if (flag) byte0 |= 256;
        String str0 = Integer.toBinaryString(byte0);
        if (flag || byte0 < 0)
            return str0.substring(str0.length() - 8);
        else return str0;
    }
    //Image Compression
    //Graphic of an image consist 2,they are raster graphics and vector graphics
    //Raster images are resolution-dependent, meaning that zooming in or enlarging them
    // will cause them to lose quality and become pixelated.
    //On the other hand,Vector graphics are resolution-independent, meaning they can be scaled without losing quality.
    //In this scenario,we will do a compression to a BMP File,where it is an uncompressed raster graphics
    //BMP File usually uses 24bit(3bytes) per pixel for color channel,where it represents RGB Colors,where every color has
    //1 byte size,so it can adjust the depths of the color in the range of : 1-255

    public static void compressImage(String srcImagePath, String dstCompressedPath) {
        try {
            // Reading the file as an array of byte
            FileInputStream inStream = new FileInputStream(srcImagePath);
            byte[] imageBytes = inStream.readAllBytes();
            inStream.close();

            // Compressing the bytes with Huffman
            byte[] huffmanBytes = createZip(imageBytes);

            // Writing the result
            FileOutputStream outStream = new FileOutputStream(dstCompressedPath);
            ObjectOutputStream objectOutStream = new ObjectOutputStream(outStream);
            objectOutStream.writeObject(huffmanBytes);
            objectOutStream.writeObject(huffmap);
            objectOutStream.close();
            outStream.close();

            System.out.println("Gambar berhasil dikompresi dan disimpan ke: " + dstCompressedPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void decompressImage(String srcCompressedPath, String dstImagePath) {
        try {
            //
            FileInputStream inStream = new FileInputStream(srcCompressedPath);
            ObjectInputStream objectInStream = new ObjectInputStream(inStream);

            // Membaca data terkompresi dan peta Huffman
            byte[] huffmanBytes = (byte[]) objectInStream.readObject();
            Map<Byte, String> huffmanCodes = (Map<Byte, String>) objectInStream.readObject();
            objectInStream.close();
            inStream.close();

            // Dekompresi byte array
            byte[] decompressedBytes = decomp(huffmanCodes, huffmanBytes);

            // Menulis kembali data dekompresi ke file gambar
            FileOutputStream outStream = new FileOutputStream(dstImagePath);
            outStream.write(decompressedBytes);
            outStream.close();

            System.out.println("Gambar berhasil didekompresi dan disimpan ke: " + dstImagePath);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //Compressing Text File
        compress("C:\\Users\\USER\\OneDrive\\Desktop\\Story Original.txt","C:\\Users\\USER\\OneDrive\\Desktop\\Story Original(Compressed).txt");
        decompress("C:\\Users\\USER\\OneDrive\\Desktop\\Story Original(Compressed).txt","C:\\Users\\USER\\OneDrive\\Desktop\\Story Original(Uncompressed).txt");

        
        //Compressing BMP File
        compressImage("C:\\Users\\USER\\OneDrive\\Desktop\\sample_5184×3456.bmp","C:\\Users\\USER\\OneDrive\\Desktop\\sample_5184×3456(Compressed).bmp");
        decompressImage("C:\\Users\\USER\\OneDrive\\Desktop\\sample_5184×3456(Compressed).bmp","C:\\Users\\USER\\OneDrive\\Desktop\\sample_5184×3456(Uncompressed).bmp");
    }
}
