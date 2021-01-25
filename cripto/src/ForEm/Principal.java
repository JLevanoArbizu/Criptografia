package ForEm;

public class Principal {

    public static void main(String[] args){
        String mensaje = "hola mundo";

        char array[] = mensaje.toCharArray();

        for(int i= 0; i< array.length ; i++)
        {
            array[i] = (char)(array[i]+(char)5);
        }

        String encriptado = String.valueOf(array);
        System.out.println(mensaje);
        System.out.println(encriptado);

        char arrayd[] = encriptado.toCharArray();

        for(int i= 0; i< arrayd.length ; i++)
        {
            arrayd[i] = (char)(arrayd[i]-(char)5);
        }
        String desencriptado = String.valueOf(arrayd);

        System.out.println(desencriptado);
    }
}
