import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class ProjectTest {

    @Test
    public void grosTest() {
        byte[] message = {1, 0, 1, 1, 0, 0, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0};
        int t = 4;
        byte[] iv = {1,0,1,0};

        byte[][] blocks = new byte[message.length / t][t];
        for (int i = 0; i < message.length / t; i++) {
            for (int j = 0; j < t; j++) {
                blocks[i][j] = message[i * t + j];
            }
        }

        for (int i = 0; i < blocks.length; i++) {
            byte[] bXorV = Main.xor(blocks[i], iv);
            byte[] cypher = Main.cypher(bXorV);
            blocks[i] = cypher;
            iv = cypher;
        }

        byte[] result = new byte[blocks.length * blocks[0].length];
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                result[i * blocks[0].length + j] = blocks[i][j];
            }
        }
        byte[] excepted = {0, 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 1, 1, 0, 1};
        assertArrayEquals(excepted, result);
    }

    @Test
    public void testStringToBinary() {
        String s = "test";
        byte[] actual = Main.stringToBinary(s);

        byte[] expected = {0, 1, 1, 1, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 0, 0 };
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testStringToBinaryWithPadding() {
        String s = "test";
        byte[] actual = Main.stringToBinaryWithComplement(s, 5);
        byte[] expected = {0, 1, 1, 1, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0 };
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testXor() {
        byte[] a = {1,0,1,1};
        byte[] b = {1,0,1,0};
        byte[] actual = Main.xor(a, b);
        byte[] expected = {0,0,0,1};
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testCypher() {
        byte[] message = {0,0,0,1};
        byte[] actual = Main.cypher(message);
        byte[] expected = {0,0,1,0};
        assertArrayEquals(expected, actual);
    }
}
