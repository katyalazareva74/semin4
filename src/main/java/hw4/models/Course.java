package hw4;

import javax.persistence.*;

@Entity
@Table(name="courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private int duration;

    public Course(String title, int duration){
        this.title=title;
        this.duration=duration;
    }
    public Course(int id, String title, int duration){
        this.id=id;
        this.title=title;
        this.duration=duration;
    }
    public Course(){}
    public int getDuration() {
        return duration;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", duration=" + duration +
                '}';
    }
}
