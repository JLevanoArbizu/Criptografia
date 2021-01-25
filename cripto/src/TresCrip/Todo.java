package TresCrip;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;


import java.io.FileInputStream;
import java.io.FileOutputStream;
//import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
//import javax.crypto.Cipher;

public class Todo {
    private static Cipher rsa;

    public static void main(String[] args) throws Exception {
        // Generar el par de claves
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();


        //=============


        // Generamos una clave de 128 bits adecuada para AES
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        Key key = keyGenerator.generateKey();

        // Alternativamente, una clave que queramos que tenga al menos 16 bytes
        // y nos quedamos con los bytes 0 a 15
        key = new SecretKeySpec("una clave de 16 bytes".getBytes(), 0, 16, "AES");

        // Ver como se puede guardar esta clave en un fichero y recuperarla
        // posteriormente en la clase RSAAsymetricCrypto.java

        // Texto a encriptar
        String texto = "levano";

        // Se obtiene un cifrador AES
        Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");

        // Se inicializa para encriptacion y se encripta el texto,
        // que debemos pasar como bytes.
        aes.init(Cipher.ENCRYPT_MODE, key);
        byte[] encriptado2 = aes.doFinal(texto.getBytes());

        // Se escribe byte a byte en hexadecimal el texto
        // encriptado para ver su pinta.

        System.out.println(encriptado2);

        System.out.println("\n DESPUES DEL FOR");
        System.out.println("");

        // Se iniciliza el cifrador para desencriptar, con la
        // misma clave y se desencripta
        aes.init(Cipher.DECRYPT_MODE, key);
        byte[] desencriptado2 = aes.doFinal(encriptado2);

        // Texto obtenido, igual al original.
        System.out.println("------demo------");
        System.out.println(new String(desencriptado2));

        //=============

        // Se salva y recupera de fichero la clave publica
        saveKey(publicKey, "publickey.dat");
        publicKey = loadPublicKey("publickey.dat");

        // Se salva y recupera de fichero la clave privada
        saveKey(privateKey, "privatekey.dat");
        privateKey = loadPrivateKey("privatekey.dat");

        // Obtener la clase para encriptar/desencriptar
        rsa = Cipher.getInstance("RSA/ECB/PKCS1Padding");

        // Texto a encriptar
        String text = "Levano";

        // Se encripta
        rsa.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encriptado = rsa.doFinal(text.getBytes());
        System.out.println("TEXTO ENCRIPTADO");
        System.out.println(encriptado);
        // Escribimos el encriptado para verlo, con caracteres visibles
        System.out.println("ENCRIPTADO EXAGECIMAL");
        for (byte b : encriptado) {
            System.out.print(Integer.toHexString(0xFF & b));

        }


        System.out.println("\n TEXTO ENCRIPTADO2");
        for (byte b : encriptado2) {
            System.out.print(Integer.toHexString(0xFF & b));
        }
        System.out.println("\n quiiiiiiiiiiiiiiiiiiiii");


        System.out.println(encriptado);

        // Se desencripta
        rsa.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] bytesDesencriptados = rsa.doFinal(encriptado);
        String textoDesencripado = new String(bytesDesencriptados);
        System.out.println("TEXTO DESNCRIPTADO");
        // Se escribe el texto desencriptado
        System.out.println(textoDesencripado);



    }



    private static PublicKey loadPublicKey(String fileName) throws Exception {
        FileInputStream fis = new FileInputStream(fileName);
        int numBtyes = fis.available();
        byte[] bytes = new byte[numBtyes];
        fis.read(bytes);
        fis.close();

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        KeySpec keySpec = new X509EncodedKeySpec(bytes);
        PublicKey keyFromBytes = keyFactory.generatePublic(keySpec);
        return keyFromBytes;
    }

    private static PrivateKey loadPrivateKey(String fileName) throws Exception {
        FileInputStream fis = new FileInputStream(fileName);
        int numBtyes = fis.available();
        byte[] bytes = new byte[numBtyes];
        fis.read(bytes);
        fis.close();

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        KeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
        PrivateKey keyFromBytes = keyFactory.generatePrivate(keySpec);
        return keyFromBytes;
    }

    private static void saveKey(Key key, String fileName) throws Exception {
        byte[] publicKeyBytes = key.getEncoded();
        FileOutputStream fos = new FileOutputStream(fileName);
        fos.write(publicKeyBytes);
        fos.close();
    }
}
