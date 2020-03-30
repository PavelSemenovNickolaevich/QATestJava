package ru.stqa.pft.addressbook.model;

import com.google.gson.annotations.Expose;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "addressbook")
public class ContactData {
   @XStreamOmitField
   @Id
   @Column(name = "id")
   private int id;

   @Expose
   @Column(name = "firstname")
   private final String firstname;

   @Expose
   @Column(name = "lastname")
   private final String lastname;

   @Expose
   @Column(name = "middlename")
   private final String middlename;

   @Expose
   @Column(name = "company")
   private final String company;

   @Expose
   @Type(type = "text")
   @Column(name = "address")
   private final String adress;

   @Expose
   @Column(name = "home")
   @Type(type = "text")
   private final String phoneHome;

   @Expose
   @Column(name = "mobile")
   @Type(type = "text")
   private final String phoneMobile;

   @Expose
   @Column(name = "work")
   @Type(type = "text")
   private final String phoneWork;

   @Expose
   @Type(type = "text")
   @Column(name = "email")
   private final String emailOne;

   @Expose
   @Type(type = "text")
   @Column(name = "email2")
   private final String emailTwo;

   @Expose
   transient private String allEmails;
   @Expose
   transient private String allPhones;

   public  ContactData() {
        id = Integer.MAX_VALUE;
        firstname = null;
        lastname = null;
        middlename = null;
        company = null;
        adress = null;
        phoneHome = null;
        phoneMobile = null;
        phoneWork = null;
        emailOne = null;
        emailTwo = null;
    }

   public ContactData (String firstname, String lastname, String middlename, String company, String adress,
                       String phoneHome, String phoneMobile, String phoneWork, String emailOne, String emailTwo) {
       this.id = Integer.MAX_VALUE;
       this.firstname = firstname;
       this.lastname = lastname;
       this.middlename = middlename;
       this.company = company;
       this.adress = adress;
       this.phoneHome = phoneHome;
       this.phoneMobile = phoneMobile;
       this.phoneWork = phoneWork;
       this.emailOne = emailOne;
       this.emailTwo = emailTwo;
   }

   public ContactData (int id, String firstname, String lastname, String middlename, String company, String adress,
                       String phoneHome, String phoneMobile, String phoneWork, String emailOne, String emailTwo) {
       this.firstname = firstname;
       this.lastname = lastname;
       this.middlename = middlename;
       this.company = company;
       this.adress = adress;
       this.phoneHome = phoneHome;
       this.phoneMobile = phoneMobile;
       this.phoneWork = phoneWork;
       this.id = id;
       this.emailOne = emailOne;
       this.emailTwo = emailTwo;
   }

   public ContactData (int id, String firstname, String lastname, String middlename, String company, String adress,
                       String allPhones, String allEmails) {
       this.firstname = firstname;
       this.lastname = lastname;
       this.middlename = middlename;
       this.company = company;
       this.adress = adress;
       this.phoneHome = null;
       this.phoneMobile = null;
       this.phoneWork = null;
       this.id = id;
       this.allPhones = allPhones;
       this.allEmails = allEmails;
       this.emailOne = null;
       this.emailTwo = null;
   }

   public String getEmailOne() {
       return emailOne;
   }

   public String getEmailTwo() {
       return emailTwo;
   }

   public String getFirstname () {
       return firstname;
   }

   public String getLastName () {
       return lastname;
   }

   public String getMiddlename () {
       return middlename;
   }

   public String getCompany () {
       return company;
   }

   public String getAdress () {
       return adress;
   }

   public String getPhoneHome () {
       return phoneHome;
   }

   public String getPhoneMobile() {
       return phoneMobile;
   }

   public String getPhoneWork() {
       return phoneWork;
   }

   public int getId () {
       return id;
   }

   public String getAllEmails () {
       return allEmails;
   }

   public String getAllPhones () {
       return allPhones;
   }

   public void allEmails (String allEmails) {
       this.allEmails = allEmails;
   }

   @Override
   public String toString () {
       return "ContactData{" +
               "id='" + id + '\'' +
               ", firstname='" + firstname + '\'' +
               ", lastname='" + lastname + '\'' +
               '}';
   }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactData that = (ContactData) o;

        if (id != that.id) return false;
        if (firstname != null ? !firstname.equals(that.firstname) : that.firstname != null) return false;
        if (lastname != null ? !lastname.equals(that.lastname) : that.lastname != null) return false;
        if (middlename != null ? !middlename.equals(that.middlename) : that.middlename != null) return false;
        if (company != null ? !company.equals(that.company) : that.company != null) return false;
        if (adress != null ? !adress.equals(that.adress) : that.adress != null) return false;
        if (phoneHome != null ? !phoneHome.equals(that.phoneHome) : that.phoneHome != null) return false;
        if (phoneMobile != null ? !phoneMobile.equals(that.phoneMobile) : that.phoneMobile != null) return false;
        if (phoneWork != null ? !phoneWork.equals(that.phoneWork) : that.phoneWork != null) return false;
        if (emailOne != null ? !emailOne.equals(that.emailOne) : that.emailOne != null) return false;
        if (emailTwo != null ? !emailTwo.equals(that.emailTwo) : that.emailTwo != null) return false;
        if (allEmails != null ? !allEmails.equals(that.allEmails) : that.allEmails != null) return false;
        return allPhones != null ? allPhones.equals(that.allPhones) : that.allPhones == null;
    }

    @Override
    public int hashCode () {
        int result = id;
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + (middlename != null ? middlename.hashCode() : 0);
        result = 31 * result + (company != null ? company.hashCode() : 0);
        result = 31 * result + (adress != null ? adress.hashCode() : 0);
        result = 31 * result + (phoneHome != null ? phoneHome.hashCode() : 0);
        result = 31 * result + (phoneMobile != null ? phoneMobile.hashCode() : 0);
        result = 31 * result + (phoneWork != null ? phoneWork.hashCode() : 0);
        result = 31 * result + (emailOne != null ? emailOne.hashCode() : 0);
        result = 31 * result + (emailTwo != null ? emailTwo.hashCode() : 0);
        result = 31 * result + (allEmails != null ? allEmails.hashCode() : 0);
        result = 31 * result + (allPhones != null ? allPhones.hashCode() : 0);
        return result;
    }

    public void setId (int id) {
       this.id = id;
   }

}
