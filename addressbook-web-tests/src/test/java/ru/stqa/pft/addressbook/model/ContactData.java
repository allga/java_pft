package ru.stqa.pft.addressbook.model;

import com.google.gson.annotations.Expose;

import java.io.File;

public class ContactData {
    private int id = Integer.MAX_VALUE;
    @Expose
    private String firstname;

    @Expose
    private String lastname;

    @Expose
    private String company;

    @Expose
    private String address;

    @Expose
    private String homephone;

    @Expose
    private String mobilephone;

    @Expose
    private String workphone;

    private String allPhones;

    @Expose
    private String group;

    @Expose
    private String email1;

    @Expose
    private String email2;

    @Expose
    private String email3;

    private String allEmails;

    private String allContent;

    public File getPhoto() {
        return photo;
    }

    public ContactData setPhoto(File photo) {
        this.photo = photo;
        return this;
    }

    private File photo;

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

    public ContactData setGroup(String group) {
        this.group = group;
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

    public String getGroup() {
        return group;
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
                ", allPhones='" + allPhones + '\'' +
                ", group='" + group + '\'' +
                ", email1='" + email1 + '\'' +
                ", email2='" + email2 + '\'' +
                ", email3='" + email3 + '\'' +
                ", allEmails='" + allEmails + '\'' +
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
        return address != null ? address.equals(that.address) : that.address == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }
}
