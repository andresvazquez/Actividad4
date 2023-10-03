import java.io.*;
import java.util.*;

public class AddressBook {

    private final HashMap<String, String> contacts = new HashMap<>();
    private final String inputFile,outputFile;

    public AddressBook(){
        this.inputFile = "src/files/input.txt";
        this.outputFile = "src/files/output.txt";
        try {
            load(inputFile);
        }
        catch (IOException e) {
            System.out.println("Error de IOException: " + e);
        }
    }

    public void load(String inputFile) throws IOException{
        BufferedReader reader;
        reader = new BufferedReader(new FileReader(inputFile));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length == 2) {
                String number = parts[0];
                String name = parts[1];
                contacts.put(number, name);
            }
        }
        reader.close();
    }

    public void save() throws IOException{
        BufferedWriter writer = new BufferedWriter(new FileWriter(this.outputFile));
        for (Map.Entry<String, String> contact : this.contacts.entrySet()) {
            writer.write(contact.getKey() + "," + contact.getValue());
            writer.newLine();
        }
        writer.close();
    }

    public void list(){
        System.out.println("Contactos: ");
        for (Map.Entry<String, String> contact : this.contacts.entrySet()) {
            String phone = contact.getKey();
            String name = contact.getValue();
            System.out.println(phone + " : " + name);
        }
    }

    public void create(String phone, String name){
        this.contacts.put(phone, name);
    }

    public void delete(String phone){
        this.contacts.remove(phone);
    }

    public boolean isEmpty(){
        return this.contacts.isEmpty();
    }

    public boolean phoneExist(String phone){
        return this.contacts.containsKey(phone);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String phone = "";
        String name = "";
        boolean exit = false;
        AddressBook AdBoo = new AddressBook();
        while (!exit) {
            System.out.println("\nAgenda Telefónica:");
            System.out.println("1. Enlistar contactos");
            System.out.println("2. Crear contacto");
            System.out.println("3. Remover contacto");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    if(AdBoo.isEmpty()){
                        System.out.println("El Address book esta vacío.");
                    }else {
                        AdBoo.list();
                    }
                    break;
                case 2:
                    boolean validPhone = false;
                    while (!validPhone){
                        System.out.print("Ingrese número de teléfono: ");
                        phone = scanner.nextLine();
                        if(phone.length()==10 && phone.matches("^[0-9]+$")){
                            validPhone=true;
                        }else{
                            System.out.println("El número telefonico es invalido, tiene que ser de 10 digitos númericos.");
                        }
                    }
                    boolean validName = false;
                    while(!validName){
                        System.out.print("Ingrese nombre: ");
                        name = scanner.nextLine();
                        if(name.matches("^[a-zA-ZÁÉÍÓÚáéíóúÑñ ]+$")){
                            validName=true;
                        }else{
                            System.out.println("El nombre es invalido, favor de utilizar solo letras.");
                        }
                    }
                    AdBoo.create(phone, name);
                    System.out.println("Se crea el contacto en la agenda con éxito.");
                    break;
                case 3:
                    System.out.print("Ingrese el número de teléfono a remover: ");
                    phone = scanner.nextLine();
                    if (AdBoo.phoneExist(phone)){
                        AdBoo.delete(phone);
                        System.out.println("Se remueve el registro de contacto con éxito.");
                    }else {
                        System.out.println("El contacto a remover no existe.");
                    }
                    break;
                case 4:
                    try{
                        AdBoo.save();
                        System.out.println("Se guardo con éxito los cambios.");
                    }catch (IOException e) {
                        System.out.println("Falla al guardar: "+ e);
                    }
                    exit = true;
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }

        System.out.println("Agenda telefónica cerrada.");
    }
}