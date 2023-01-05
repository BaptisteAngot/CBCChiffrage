import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length != 3) {
            System.out.println("Usage: java -jar CBCProject.jar 4 1010 test.txt");
            return;
        }

        String fileName = args[2];
        String phrase = readFile(fileName);

        if(!args[0].matches("[0-9]+")) {
            System.out.println("La clé doit être un entier");
            return;
        }


        int key = Integer.parseInt(args[0]);
        if (!args[1].matches("[01]+")) {
            System.out.println("Le vecteur initiale doit être en binaire");
            return;
        }
        byte[] iv = convertIvToBinary(args[1]);


        byte[] messageWithPadding = stringToBinaryWithComplement(phrase, key);
        byte[][] blocks = new byte[messageWithPadding.length / key][key];
        for (int i = 0; i < messageWithPadding.length / key; i++) {
            for (int j = 0; j < key; j++) {
                blocks[i][j] = messageWithPadding[i * key + j];
            }
        }

        for (int i = 0; i < blocks.length; i++) {
            byte[] bXorV = xor(blocks[i], iv);
            byte[] cypher = cypher(bXorV);
            blocks[i] = cypher;
            iv = cypher;
        }

        byte[] result = new byte[blocks.length * blocks[0].length];
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                result[i * blocks[0].length + j] = blocks[i][j];
            }
        }

        String fileNameEncrypted = fileName + ".encrypted";
        saveFile(fileNameEncrypted, result);
    }

    private static void saveFile(String fileNameEncrypted, byte[] result) {
        try {
            FileOutputStream fos = new FileOutputStream(fileNameEncrypted);
            fos.write(result);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] stringToBinaryWithComplement(String s, int key) {
        byte[] bytes = stringToBinary(s);
        int nbBlocks = (int) Math.ceil((double) bytes.length / key);
        byte[] bytesWithPadding = new byte[nbBlocks * key];
        System.arraycopy(bytes, 0, bytesWithPadding, 0, bytes.length);
        return bytesWithPadding;
    }

    static byte[] convertIvToBinary(String iv) {
        byte[] bytes = new byte[iv.length()];
        for (int i = 0; i < iv.length(); i++) {
            bytes[i] = (byte) (iv.charAt(i) - '0');
        }
        return bytes;
    }

    static byte[] stringToBinary(String s) {
        byte[] bytes = s.getBytes();
        byte[] binary = new byte[bytes.length * 8];
        for (int i = 0; i < bytes.length; i++) {
            for (int j = 0; j < 8; j++) {
                binary[i * 8 + j] = (byte) ((bytes[i] >> (7 - j)) & 1);
            }
        }
        return binary;
    }

    public static byte[] xor(byte[] a, byte[] b) {
        byte[] result = new byte[a.length];
        for (int i = 0; i < a.length; i++) {
            result[i] = (byte) (a[i] ^ b[i]);
        }
        return result;
    }

    public static byte[] cypher(byte[] message) {
        if (message.length != 4) {
            throw new IllegalArgumentException("Le tableau a doit avoir une longueur de 4");
        }
        byte[] result = new byte[4];
        result[0] = message[1];
        result[1] = message[2];
        result[2] = message[3];
        result[3] = message[0];

        return result;
    }

    private static String readFile(String filename) throws IOException {
        try (FileInputStream fis = new FileInputStream(filename)) {
            byte[] bytes = new byte[fis.available()];
            fis.read(bytes);
            return new String(bytes);
        }
    }
}