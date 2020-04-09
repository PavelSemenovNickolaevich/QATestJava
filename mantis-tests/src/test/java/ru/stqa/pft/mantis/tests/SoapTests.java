package ru.stqa.pft.mantis.tests;

import biz.futureware.mantis.rpc.soap.client.MantisConnectLocator;
import biz.futureware.mantis.rpc.soap.client.MantisConnectPortType;
import biz.futureware.mantis.rpc.soap.client.ProjectData;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.Issue;
import ru.stqa.pft.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Set;

import static org.testng.AssertJUnit.assertEquals;

public class SoapTests extends TestBase{

    @Test
    public void testGetProjects() throws MalformedURLException, ServiceException, RemoteException {
        Set<Project> projects = applicationManager.soap().getProjects();
       // skipIfNotFixed(1);
       try{
            skipIfNotFixed(1);
        } catch (Exception e){
           return;
        }
        System.out.println(projects.size());
        for (Project project : projects) {
            System.out.println(project.getName());
        }
    }

    @Test
    public void yestCreateIssue() throws RemoteException, ServiceException, MalformedURLException {
        Set<Project> project = applicationManager.soap().getProjects();
        Issue issue = new Issue().withSummary("Test issue").withDescription("test issue description")
                .withProject(project.iterator().next());
      Issue created =  applicationManager.soap().addIssue(issue);
      assertEquals(issue.getSummary(), created.getSummary());
    }
}
