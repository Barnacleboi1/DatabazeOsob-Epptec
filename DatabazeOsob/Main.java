package DatabazeOsob;

import java.time.LocalDate;
import java.time.Period;
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
                case "1" -> pridaniOsoby();
                case "2" -> odebraniOsoby();
                case "3" -> vyhledaniOsoby();
                default -> {
                    System.out.println("Zadejte pouze číslo akce, kterou chcete zvolit. \n ");
                    continue;
                }
            }
            System.out.println("Chcete program ukončit? Pokud ano, zadejte \"ANO\", pokud ne, zadejte cokoli jiného");
            scanner.nextLine();
            vstup = scanner.nextLine();
            if (vstup.equalsIgnoreCase("ano")) {
                break;
            }
        }
    }
    public static void pridaniOsoby() {
        System.out.println("Zadejte křestní jméno: ");
        String jmeno = scanner.next();
        System.out.println("Zadejte příjmení: ");
        String prijmeni = scanner.next();
        System.out.println("Zadejte rodné číslo: ");
        String rodneCislo = scanner.next();

        //Zde validaci osoby provádím přes gettery, protože v konstruktoru osoby se z rodného čísla odebere lomítko.
        // Tím se zajišťuje, že nemůže být přidána osoba se stejnm rodným číslem, jedna s lomítkem a jedna bez lomítka
        try {
            Osoba osoba = new Osoba(jmeno, prijmeni, rodneCislo);
            validaceOsoby(osoba.getJmeno(), osoba.getPrijmeni(), osoba.getRodneCislo());

            databazeOsob.put(osoba.getRodneCislo(), osoba);
            System.out.println("Osoba byla přidána. \n");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void odebraniOsoby() {
        System.out.println("Zadejte rodné číslo osoby, kterou chcete odebrat. Zadávejte bez lomítka (YYMMDDXXXX): ");
        String rodneCislo = scanner.next();
        if (databazeOsob.containsKey(rodneCislo)) {
            databazeOsob.remove(rodneCislo);
            System.out.println("Osoba byla odebrána. \n");
        } else {
            throw new RuntimeException("Osoba s daným rodným číslem není v databázi.");
        }
    }
    public static void vyhledaniOsoby() {
        System.out.println("Zadejte rodné číslo osoby k vyhledání. Zadávejte bez lomítka (YYMMDDXXXX): ");
        String rodneCislo = scanner.next();
        if (databazeOsob.containsKey(rodneCislo)) {
            Osoba o = databazeOsob.get(rodneCislo);
            System.out.println("Informace o hledané osobě:");
            System.out.println("Jméno: " + o.getJmeno());
            System.out.println("Příjmení: " + o.getPrijmeni());
            System.out.println("Rodné číslo: " + o.getRodneCislo());
            System.out.println("Věk: " + vypocetVeku(rodneCislo));
            System.out.println();
        } else {
            throw new RuntimeException("Osoba s daným rodným číslem není v databázi.");
        }
    }
    public static void validaceOsoby(String jmeno, String prijmeni, String rodneCislo) {
        if (databazeOsob.containsKey(rodneCislo)) {
            throw new IllegalArgumentException("Tato osoba již v databázi je. \n");
        }
        /* Toto kontrolovat není potřeba, protože jméno ani přijmeni není možné zadat tak, aby to bylo prázdné,
        protože jsme při zadávání těchto hodnot použili scanner.next() místo scanner.nextLine()
        
        if (jmeno.isEmpty() || prijmeni.isEmpty()) {
            throw new IllegalArgumentException("Jméno a příjmení nesmí být prázdné. \n");
        }
        */
        if (!rodneCislo.matches("\\d{6}/?\\d{4}")) {
            throw new IllegalArgumentException("Rodné číslo musí být ve formátu YYMMDDXXXX nebo YYMMDD/XXXX. \n");
        }
    }
    public static int vypocetVeku(String rodneCislo) {
        int stoletiNarozeni;

        if (LocalDate.now().getYear() - Integer.parseInt(rodneCislo.substring(0, 2)) < 2000) {
            stoletiNarozeni = 19;
        } else {
            stoletiNarozeni = 20;
        }

        int rokNarozeni = Integer.parseInt(stoletiNarozeni + rodneCislo.substring(0, 2));
        int mesicNarozeni = Integer.parseInt(rodneCislo.substring(2, 4));
        int denNarozeni = Integer.parseInt(rodneCislo.substring(4, 6));

        LocalDate datumNarozeni = LocalDate.of(rokNarozeni, mesicNarozeni, denNarozeni);

        int vek = Period.between(datumNarozeni, LocalDate.now()).getYears();
        return vek;
    }
}
