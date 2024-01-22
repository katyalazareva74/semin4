package hw4;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


import java.sql.SQLException;
public class Program {
    /*
    Создайте базу данных (например, SchoolDB).
    В этой базе данных создайте таблицу Courses с полями id (ключ), title, и duration.
    Настройте Hibernate для работы с вашей базой данных.
    Создайте Java-класс Course, соответствующий таблице Courses, с необходимыми аннотациями Hibernate.
    Используя Hibernate, напишите код для вставки, чтения, обновления и удаления данных в таблице Courses.
    убедитесь, что каждая операция выполняется в отдельной транзакции.
     */
    public static void main(String[] args) {
        // Создание фабрики сессий
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Course.class)
                .buildSessionFactory();
        // Создание сессии
        try  {
            initialDatabase(sessionFactory);
            Course course = new Course("Математика для старших классов", 450);
            saveDatabase(sessionFactory, course);
            readDatabase(sessionFactory, 4);
            updateDatabase(sessionFactory, 1, "", 250);
            deleteDatabase(sessionFactory, 3);

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }
    private static void initialDatabase(SessionFactory sessionFactory) throws SQLException{
        String[] titles = new String[] {"Математика", "Информатика", "Физика"};
        int[] durations = new int[] {220, 240, 200};
        Course[] courss = new Course[titles.length];
        try(Session sessionnew = sessionFactory.getCurrentSession()) {
            sessionnew.beginTransaction();
            for (int i = 0; i < titles.length ; i++) {
                courss[i] = new Course(titles[i], durations[i]);
                sessionnew.save(courss[i]);
            }
            System.out.println("Initialization successfully");
            sessionnew.getTransaction().commit();
            System.out.println("Transaction commit successfully");
        }
    }
    private static void saveDatabase(SessionFactory sessionFactory, Course course) throws SQLException{
        try(Session sessionnew = sessionFactory.getCurrentSession()) {
            sessionnew.beginTransaction();
            sessionnew.save(course);
            System.out.println("Object course save successfully");
            sessionnew.getTransaction().commit();
            System.out.println("Transaction commit successfully");
        }
    }
    private static void readDatabase(SessionFactory sessionFactory, int id) throws SQLException{
        try(Session sessionnew = sessionFactory.getCurrentSession()) {
            sessionnew.beginTransaction();
            Course courseread = sessionnew.get(Course.class, id);
            System.out.println(courseread);
            System.out.println("Object course read successfully");
            sessionnew.getTransaction().commit();
            System.out.println("Transaction commit successfully");
        }
    }
    private static void updateDatabase(SessionFactory sessionFactory, int id, String str, int duration) throws SQLException{
        try(Session sessionnew = sessionFactory.getCurrentSession()) {
            sessionnew.beginTransaction();
            Course courseupdate = sessionnew.get(Course.class, id);
            System.out.println(courseupdate);
            if (!str.equals("")) {
                courseupdate.setTitle(str);
            }
            if (duration > 0) {
                courseupdate.setDuration(duration);
            }
            sessionnew.save(courseupdate);
            System.out.println("Object course update successfully");
            sessionnew.getTransaction().commit();
            System.out.println("Transaction commit successfully");
        }
    }
    private static void deleteDatabase(SessionFactory sessionFactory, int id) throws SQLException{
        try(Session sessionnew = sessionFactory.getCurrentSession()) {
            sessionnew.beginTransaction();
            Course coursedel = sessionnew.get(Course.class, id);
            System.out.println(coursedel);
            sessionnew.delete(coursedel);
            System.out.println("Object course delete successfully");
            sessionnew.getTransaction().commit();
            System.out.println("Transaction commit successfully");
        }
    }
}
