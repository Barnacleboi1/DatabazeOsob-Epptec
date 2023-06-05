package DatabazeOsob;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static Map<String, Osoba> databazeOsob = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {

        while (true) {
            System.out.println("""
                    Vyberte akci:
                    1. Přídání osoby
                    2. Odebrání osoby
                    3. Vyhledání osoby""");
            String vstup = scanner.next();
            switch (vstup) {
                case "1"-> pridaniOsoby();
                case "2"-> odebraniOsoby();
                case "3"-> vyhledaniOsoby();
                default -> {
                    System.out.println("Zadejte pouze číslo akce, kterou chcete zvolit. \n ");
                    continue;
                }
            }
            System.out.println("Chcete program ukončit? Pokud ano, zadejte A, pokud ne, zadejte cokoli jiného");
            scanner.nextLine();
            vstup = scanner.nextLine();
            if (vstup.equalsIgnoreCase("A")) {
                break;
            }
        }
    }
    public static void pridaniOsoby() {
        while (true){
            System.out.println("Zadejte křestní jméno: ");
            String jmeno = scanner.next();
            System.out.println("Zadejte příjmení: ");
            String prijmeni = scanner.next();
            System.out.println("Zadejte rodné číslo: ");
            String rodneCislo = scanner.next();
            try {
                validaceOsoby(jmeno, prijmeni, rodneCislo);

                databazeOsob.put(rodneCislo, new Osoba(jmeno, prijmeni, rodneCislo));
                System.out.println("Osoba byla přidána. \n");
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                return;
            }
        }
    }
    public static void odebraniOsoby() {

    }
    public static void vyhledaniOsoby() {

    }
    public static void validaceOsoby(String jmeno, String prijmeni, String rodneCislo) {
        if (databazeOsob.containsKey(rodneCislo)) {
            throw new IllegalArgumentException("Tato osoba už v databázi je. \n");
        }
        if (jmeno.isEmpty() || prijmeni.isEmpty()) {
            throw new IllegalArgumentException("Jméno a příjmení nesmí být prázdné. \n");
        }
        if (!rodneCislo.matches("\\d{6}/?\\d{4}")) {
            throw new IllegalArgumentException("Rodné číslo musí být ve formátu YYMMDDXXXX nebo YYMMDD/XXXX. \n");
        }
    }
}