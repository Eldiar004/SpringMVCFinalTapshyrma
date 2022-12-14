package peaksoft.repository.repositoryImpl;
import org.springframework.stereotype.Repository;
import peaksoft.model.Group;
import peaksoft.model.Student;
import peaksoft.repository.StudentRepository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
@Repository
@Transactional
public class StudentRepositoryImpl implements StudentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Student> getAllStudent() {
        return entityManager.createQuery("select s from Student s").getResultList();
    }

    @Override
    public void saveStudent(Long id,Student student) {
        Group group = entityManager.find(Group.class,id);
        group.addStudent(student);
        student.setGroup(group);
        entityManager.persist(student);
    }

    @Override
    public void deleteById(Long id) {
        Student student = entityManager.find(Student.class , id);
        entityManager.remove(student);
    }

    @Override
    public List<Student> getAll(Long groupId) {
        return entityManager.createQuery("select s from Student s where s.group.id = :groupId")
                .setParameter("groupId",groupId).getResultList();
    }

    @Override
    public void update(Long id,Student student) {
        Student student1 = entityManager.find(Student.class,id);
        student1.setPhoneNumber(student.getPhoneNumber());
        student1.setEmail(student.getEmail());
        student1.setStudyFormat(student.getStudyFormat());
        student1.setFirstName(student.getFirstName());
        student1.setLastName(student.getLastName());
        student1.setId(student.getId());
        entityManager.merge(student1);
    }

    @Override
    public void assignStudentToGroupById(Long studentId, Long groupId) {
        Student student = entityManager.find(Student.class,studentId);
        Group group = entityManager.find(Group.class,groupId);
        if (group.getStudents()!=null){
            for (Student g : group.getStudents()) {
                if (g.getId() == studentId) {
                    System.out.println("This student already exists!");;
                }
            }
        }
        student.setGroup(group);
        entityManager.merge(student);
    }

    @Override
    public Student getById(Long id) {
        return entityManager.find(Student.class,id);
    }
}
