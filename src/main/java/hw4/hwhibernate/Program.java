package hw4.hwhibernate;

import hw4.models.Course;
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
        try {
            initialDatabase(sessionFactory);
            Course course = new Course("Математика для младших классов", 100);
            saveDatabase(sessionFactory, course);
            readDatabase(sessionFactory, 2);
            updateDatabase(sessionFactory, 1, "Математика для старших классов", 360);
            deleteDatabase(sessionFactory, 3);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void initialDatabase(SessionFactory sessionFactory) throws SQLException {
        String[] titles = new String[]{"Математика", "Информатика", "Физика"};
        int[] durations = new int[]{220, 240, 200};
        Course[] courss = new Course[titles.length];
        try (Session sessionnew = sessionFactory.getCurrentSession()) {
            sessionnew.beginTransaction();
            for (int i = 0; i < titles.length; i++) {
                courss[i] = new Course(titles[i], durations[i]);
                sessionnew.save(courss[i]);
            }
            System.out.println("Initialization successfully");
            sessionnew.getTransaction().commit();
            System.out.println("Transaction commit successfully");
        }
    }

    private static void saveDatabase(SessionFactory sessionFactory, Course course) throws SQLException {
        try (Session sessionnew = sessionFactory.getCurrentSession()) {
            sessionnew.beginTransaction();
            sessionnew.save(course);
            System.out.println("Object course save successfully");
            sessionnew.getTransaction().commit();
            System.out.println("Transaction commit successfully");
        }
    }

    private static void readDatabase(SessionFactory sessionFactory, int id) throws SQLException {
        try (Session sessionnew = sessionFactory.getCurrentSession()) {
            sessionnew.beginTransaction();
            Course courseread = sessionnew.get(Course.class, id);
            if (courseread != null) {
                System.out.println(courseread);
                System.out.println("Object course read successfully");
            } else {
                System.out.println("No object with id " + id);
            }
            sessionnew.getTransaction().commit();
            System.out.println("Transaction commit successfully");
        }
    }

    private static void updateDatabase(SessionFactory sessionFactory, int id, String str, int duration) throws SQLException {
        try (Session sessionnew = sessionFactory.getCurrentSession()) {
            sessionnew.beginTransaction();
            Course courseupdate = sessionnew.get(Course.class, id);
            if (courseupdate != null) {
                System.out.println(courseupdate);
                if (!str.equals("")) {
                    courseupdate.setTitle(str);
                }
                if (duration > 0) {
                    courseupdate.setDuration(duration);
                }
                sessionnew.save(courseupdate);
                System.out.println(courseupdate);
                System.out.println("Object course update successfully");
            } else {
                System.out.println("No object with id " + id);
            }
            sessionnew.getTransaction().commit();
            System.out.println("Transaction commit successfully");
        }
    }

    private static void deleteDatabase(SessionFactory sessionFactory, int id) throws SQLException {
        try (Session sessionnew = sessionFactory.getCurrentSession()) {
            sessionnew.beginTransaction();
            Course coursedel = sessionnew.get(Course.class, id);
            if (coursedel != null) {
                System.out.println(coursedel);
                sessionnew.delete(coursedel);
                System.out.println("Object course delete successfully");
            } else {
                System.out.println("No object with id " + id);
            }
            sessionnew.getTransaction().commit();
            System.out.println("Transaction commit successfully");
        }
    }
}
