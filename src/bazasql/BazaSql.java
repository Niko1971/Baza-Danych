package bazasql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;

public class BazaSql {

    public static Connection dateBaseConnection() {
        try {
            String DB_URL = "jdbc:oracle:thin:@155.158.112.45:1521:oltpstud";
            String USER = "ziibd1";
            String PASS = "4420152";
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);

            return connection;
        } catch (SQLException e) {
            System.out.println("Błąd SQLa" + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Błąd sterownika JDBC" + e.getMessage());
        }
        return null;
    }

    public static void main(String[] args) throws SQLException {
        Singleton singleton = new Singleton();

        System.out.println("Zadanie 1");
        System.out.println("ID pracownika i jego wynagrodzenie");
        ResultSet wyswietlenie = singleton.executeQuery("SELECT * FROM employees");
        while (wyswietlenie.next()) {
            int employee_ID = wyswietlenie.getInt("employee_id");
            String salary = wyswietlenie.getString("salary");
            System.out.println(employee_ID + " " + salary);
        }
        int update = singleton.executeUpdate("UPDATE employees SET salary=1000 WHERE employee_id=176");
        wyswietlenie = singleton.executeQuery("SELECT * FROM employees");
        System.out.println("\nID pracownika i jego wynagrodzenie po zmianie");
        while (wyswietlenie.next()) {
            int employee_ID = wyswietlenie.getInt("employee_ID");
            String salary = wyswietlenie.getString("salary");
            System.out.println(employee_ID + " " + salary);
        }

        System.out.println("\nZadanie 2");
        Singleton singleton2 = new Singleton();
        ResultSet wynik = singleton2.executeQuery("SELECT * FROM employees");
        ArrayList arraylist = new ArrayList();
        while (wynik.next()) {
            Person person = new Person();
            person.setImie(wynik.getString("first_name"));
            person.setNazwisko(wynik.getString("last_name"));
            person.setWyplata(wynik.getString("salary"));
            person.setTelefon(wynik.getString("phone_number"));
            arraylist.add(person);
        }
        for (int i = 0; i < arraylist.size(); i++) {
            System.out.println(((Person) arraylist.get(i)).getImie() + " " + ((Person) arraylist.get(i)).getNazwisko() + " " + ((Person) arraylist.get(i)).getWyplata() + " " + ((Person) arraylist.get(i)).getTelefon());
        }

        System.out.println("\nZadanie 3");
        Singleton singleton3 = new Singleton();
        long rozpoczecie = System.nanoTime();
        ResultSet wynik2 = singleton3.executeQuery("SELECT last_name FROM employees ORDER BY last_name DESC");
        long koniec = System.nanoTime();
        long roznica1 = (koniec - rozpoczecie);
        System.out.println("Sortowanie SQL " + roznica1);
        wynik2 = null;
        wynik2 = singleton3.executeQuery("SELECT * FROM employees");
        ArrayList<String> arraylista = new ArrayList<String>();
        while (wynik2.next()) {
            arraylista.add(wynik2.getString("last_name"));
        }
        rozpoczecie = System.nanoTime();
        Collections.sort(arraylista);
        koniec = System.nanoTime();
        long roznica2 = (koniec - rozpoczecie);
        System.out.println("Sortowanie ArrayList " + roznica2);
        if (roznica1 == roznica2) {
            System.out.println("Sa rowne");
        } else if (roznica1 > roznica2) {
            System.out.println("Lokalne sa szybsze");
        } else {
            System.out.println("Bazodanowe sa szybsze");
        }
    }

}
