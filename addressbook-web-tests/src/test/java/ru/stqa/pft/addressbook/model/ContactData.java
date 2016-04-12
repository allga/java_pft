package ru.stqa.pft.addressbook.model;

import com.google.gson.annotations.Expose;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

@Entity //сущность, для привязки к БД
@Table(name = "addressbook")
public class ContactData {

    @Id
    @Column(name = "id")
    private int id = Integer.MAX_VALUE;

    @Expose
    @Column(name = "firstname")
    private String firstname;

    @Expose
    @Column(name = "lastname")
    private String lastname;

    @Expose
    @Column(name = "company")
    private String company;

    @Expose
    @Column(name = "address")
    @Type(type = "text")
    private String address;

    @Expose
    @Column(name = "home")
    @Type(type = "text")
    private String homephone;

    @Expose
    @Column(name = "mobile")
    @Type(type = "text")
    private String mobilephone;

    @Expose
    @Column(name = "work")
    @Type(type = "text")
    private String workphone;

    @Transient
    private String allPhones;

    @Expose
    @Column(name = "email")
    @Type(type = "text")
    private String email1;

    @Expose
    @Column(name = "email2")
    @Type(type = "text")
    private String email2;

    @Expose
    @Column(name = "email3")
    @Type(type = "text")
    private String email3;

    @Transient
    private String allEmails;

    @Transient
    private String allContent;

    @Column(name = "photo")
    @Type(type = "text")
    private String photo;

    @ManyToMany
    @JoinTable(name = "address_in_groups",
            joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<GroupData> groups = new HashSet<GroupData>();

    public ContactData setPhoto(File photo) {
        this.photo = photo.getPath();
        return this;
    }

    public File getPhoto() {
        try {
            return new File(photo);
        } catch (NullPointerException ex) {
            return null;
        }
    }

    public Groups getGroups() {
        return new Groups(groups);
    }

    public String getAllContent() {
        return allContent;
    }

    public ContactData setAllContent(String allContent) {
        this.allContent = allContent;
        return this;
    }

    public String getEmail1() {
        return email1;
    }

    public ContactData setEmail1(String email1) {
        this.email1 = email1;
        return this;
    }

    public String getEmail2() {
        return email2;
    }

    public ContactData setEmail2(String email2) {
        this.email2 = email2;
        return this;
    }

    public String getEmail3() {
        return email3;
    }

    public ContactData setEmail3(String email3) {
        this.email3 = email3;
        return this;
    }

    public String getAllEmails() {
        return allEmails;
    }

    public ContactData setAllEmails(String allEmails) {
        this.allEmails = allEmails;
        return this;
    }

    public ContactData setAllPhones(String allPhones) {
        this.allPhones = allPhones;
        return this;

    }

    public ContactData setWorkphone(String workphone) {
        this.workphone = workphone;
        return this;
    }
    public ContactData setId(int id) {
        this.id = id;
        return this;
    }

    public ContactData setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public ContactData setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public ContactData setCompany(String company) {
        this.company = company;
        return this;
    }

    public ContactData setAddress(String address) {
        this.address = address;
        return this;
    }

    public ContactData setHomephone(String homephone) {
        this.homephone = homephone;
        return this;
    }

    public ContactData setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
        return this;
    }

    public String getAllPhones() {
        return allPhones;
    }

    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getCompany() {
        return company;
    }

    public String getAddress() {
        return address;
    }

    public String getHomephone() {
        return homephone;
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public String getWorkphone() {
        return workphone;
    }

    @Override
    public String toString() {
        return "ContactData{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", company='" + company + '\'' +
                ", address='" + address + '\'' +
                ", homephone='" + homephone + '\'' +
                ", mobilephone='" + mobilephone + '\'' +
                ", workphone='" + workphone + '\'' +
                ", email1='" + email1 + '\'' +
                ", email2='" + email2 + '\'' +
                ", email3='" + email3 + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactData that = (ContactData) o;

        if (id != that.id) return false;
        if (firstname != null ? !firstname.equals(that.firstname) : that.firstname != null) return false;
        if (lastname != null ? !lastname.equals(that.lastname) : that.lastname != null) return false;
        if (company != null ? !company.equals(that.company) : that.company != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (homephone != null ? !homephone.equals(that.homephone) : that.homephone != null) return false;
        if (mobilephone != null ? !mobilephone.equals(that.mobilephone) : that.mobilephone != null) return false;
        if (workphone != null ? !workphone.equals(that.workphone) : that.workphone != null) return false;
        if (email1 != null ? !email1.equals(that.email1) : that.email1 != null) return false;
        if (email2 != null ? !email2.equals(that.email2) : that.email2 != null) return false;
        return email3 != null ? email3.equals(that.email3) : that.email3 == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + (company != null ? company.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (homephone != null ? homephone.hashCode() : 0);
        result = 31 * result + (mobilephone != null ? mobilephone.hashCode() : 0);
        result = 31 * result + (workphone != null ? workphone.hashCode() : 0);
        result = 31 * result + (email1 != null ? email1.hashCode() : 0);
        result = 31 * result + (email2 != null ? email2.hashCode() : 0);
        result = 31 * result + (email3 != null ? email3.hashCode() : 0);
        return result;
    }

    public ContactData inGroup(GroupData group) {
        groups.add(group);
        return this;
    }
}
