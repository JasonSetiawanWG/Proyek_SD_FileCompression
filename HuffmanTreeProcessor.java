package FileCompression;
import FileCompression.ByteNode;
import FileCompression.MinPriorityQueue;

import java.util.HashMap;
import java.util.Map;

//Kenenza : creating huffmantree, traverse the tree, and assign new bits to the character
public class HuffmanTreeProcessor {

    private static Map<Byte, String> huffmap = new HashMap<>();

    public static ByteNode buildHuffmanTree(byte[] input) {
        // get byte frequency nodes
        MinPriorityQueue<ByteNode> nodes = getByteNodes(input);
        // create huffman tree
        return createHuffmanTree(nodes);
    }

    public static void traverseHuffmanTree(ByteNode root) {
        huffmap.clear();
        if (root != null) {
            traverse(root, "", new StringBuilder());
        }
    }

    private static void traverse(ByteNode node, String code, StringBuilder path) {
        if (node != null) {
            StringBuilder currentPath = new StringBuilder(path);
            currentPath.append(code);

            if (node.left == null && node.right == null) {
                huffmap.put(node.data, currentPath.toString());
            } else {
                // traverse ke kiri bernilai 0
                traverse(node.left, "0", currentPath);
                // traverse ke kanan bernilai 1
                traverse(node.right, "1", currentPath);
            }
        }
    }

    public static Map<Byte, String> getHuffmanCodes() {
        return huffmap;
    }

    private static MinPriorityQueue<ByteNode> getByteNodes(byte[] bytes) {
        MinPriorityQueue<ByteNode> nodes = new MinPriorityQueue<>();
        Map<Byte, Integer> freqMap = new HashMap<>();

        for (byte b : bytes) {
            freqMap.put(b, freqMap.getOrDefault(b, 0) + 1);
        }

        for (Map.Entry<Byte, Integer> entry : freqMap.entrySet()) {
            nodes.add(new ByteNode(entry.getKey(), entry.getValue()));
        }

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

    public static void main(String[] args) {
        String text = "this is an example of a huffman tree";
        byte[] bytes = text.getBytes();

        // membangun huffman tree
        ByteNode root = buildHuffmanTree(bytes);

        // traverse huffman tree dan assign new bits
        traverseHuffmanTree(root);

        // print huffman code nya
        System.out.println("Huffman Codes:");
        for (Map.Entry<Byte, String> entry : getHuffmanCodes().entrySet()) {
            System.out.println((char) entry.getKey().byteValue() + ": " + entry.getValue());
        }
    }
}
