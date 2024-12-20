import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class TestByteNode {
    public static void main(String[] args) {

        // Input Text
        String input = "waxed lightly weathered cut copper stairs";

        // Create Hash Map and Queue
        Map<Byte, Integer> frequencyMap = new HashMap<>();
        PriorityQueue<ByteNode> queue = new PriorityQueue<>();

        // Count Frequency
        for (byte b : input.getBytes()) {
            //add frequenncy and also create new one if it's new
            frequencyMap.put(b, frequencyMap.getOrDefault(b, 0) + 1);
        }

        // Add Data from Frequency into queue
        for (Map.Entry<Byte, Integer> entry : frequencyMap.entrySet()) {
            queue.add(new ByteNode(entry.getKey(), entry.getValue()));
        }

        // Show in detail
        System.out.println("sorted by Frequency ~ ");
        //until queue empty keep calling it
        while (!queue.isEmpty()) {
            // set the data that pulled by queue into ByteNode
            ByteNode node = queue.poll();
            // showing data..
            System.out.println("Byte: " + (char)(byte)node.data + " | Frequency: " + node.frequency);
        }

    }
}