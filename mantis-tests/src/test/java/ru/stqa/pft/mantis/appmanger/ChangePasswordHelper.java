package ru.stqa.pft.mantis.appmanger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.openqa.selenium.By;
import ru.stqa.pft.mantis.model.UserData;

import java.util.List;

import static ru.stqa.pft.mantis.tests.TestBase.applicationManager;

public class ChangePasswordHelper extends HelperBase {

    public ChangePasswordHelper (ApplicationManager app) {
        super(app);
    }

    public void manageUser () {
        click(By.linkText("Управление"));
        click(By.linkText("Управление пользователями"));
        List<UserData> userList = applicationManager.changePassword().getUsersListWithoutAdmin();
        click(By.linkText((userList.iterator().next().getUsername())));
        click(By.xpath("//input[@value='Сбросить пароль']"));
        click(By.linkText("Продолжить"));


    }

    private List<UserData> getUsersList () {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        List<UserData> result = session.createQuery("from UsersData").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    public List<UserData> getUsersListWithoutAdmin () {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        List<UserData> result = session.createQuery("from UserData where username != 'administrator'").list();
        session.close();
        return result;
    }


    public void login (String admin, String passwordAdmin) {
        wd.get(applicationManager.getProperty("web.baseUrl") + "/login.php");
        type(By.name("username"), admin);
        wd.findElement(By.cssSelector("input[type='submit']")).click();
        type(By.name("password"), passwordAdmin);
        click(By.cssSelector("input[type='submit']"));


    }

    public void finish (String confirmationLink, String password) {
        wd.get(confirmationLink);
        type(By.name("password"), password);
        type(By.name("password_confirm"), password);
        click(By.cssSelector("span.bigger-110"));
    }
}
